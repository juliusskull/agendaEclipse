package aguasdelnorte.com.ar.agenda2.ui;

import com.gc.materialdesign.widgets.Dialog;

import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import aguasdelnorte.com.ar.agenda2.provider.ContractParaProceso;
import aguasdelnorte.com.ar.agenda2.sync.SyncAdapter;
import aguasdelnorte.com.ar.agenda2.web.Tipo_Acciones;
import aguasdelnorte.com.ar.agenda2.web.Tipo_AccionesLista;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class ItemAgendaFinalizar extends Fragment {
	private EditText txtObservacion;
	private Spinner spAcciones;
	private String[] datos;
	String Procesoid="0"; 
	private Agenda_pendienteAD elemento;
	private OnItemAgendaClick onItemAgendaClick;
	private Tipo_AccionesLista lista;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.agenda_item_finalizar, container,
				false);
		return rootView;
	}
	  @Override
	  public void onActivityCreated(Bundle state) {
	        super.onActivityCreated(state);
	     	
	        elemento= new Agenda_pendienteAD(getActivity());
	        Procesoid = getActivity().getIntent().getExtras().getString("id"); //String.valueOf(this.getIntent().getExtras().getInt("id"));
			elemento.load(Procesoid);
			Util.Log("Nro Proceso:"+elemento.getIdproceso() + ",Procesoid="+Procesoid);
	        txtObservacion=(EditText)getActivity().findViewById(R.id.txtObservacionAgregar);
	        InputMethodManager imm = (InputMethodManager)getActivity(). getSystemService(getActivity().INPUT_METHOD_SERVICE); 
	        imm.hideSoftInputFromWindow(txtObservacion.getWindowToken(), 0);
	        
	        txtObservacion.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					  InputMethodManager imm = (InputMethodManager)getActivity(). getSystemService(getActivity().INPUT_METHOD_SERVICE); 
				      imm.hideSoftInputFromWindow(txtObservacion.getWindowToken(), 0);
				}
			});
	        
	        crear_acciones();
	       
	        /*
	        ArrayAdapter<String> adaptador =
	        	    new ArrayAdapter<String>(getActivity(),
	        	        android.R.layout.simple_list_item_1, datos);
	        
	        */
	        lista= new Tipo_AccionesLista(elemento.getCategoria());
	        
	        ArrayAdapter<Tipo_Acciones> adaptador =
	        	    new ArrayAdapter<Tipo_Acciones>(getActivity(),
	        	        android.R.layout.simple_list_item_1, lista.getDatos());
	        
	        spAcciones=(Spinner)getActivity().findViewById(R.id.spAccion);
	        spAcciones.setAdapter(adaptador);
	        
	       
	  }
	  public boolean validar(){		  
		  return (spAcciones.getSelectedItemPosition()==-1)?false:true;
	  }
	  public boolean exe(){	
		  Dialog dialog =null;
		  if(!validar()){
			  dialog = new Dialog(getActivity(),"Agenda", "Debe seleccionar una accion");			  
			  dialog.addCancelButton("Cancelar");
			  dialog.show();
			 
			  
		  }else{
		  String accion=lista.getDatos().get(spAcciones.getSelectedItemPosition()).getDescripcion();
		  dialog = new Dialog(getActivity(),"Agenda", "¿Quiere \""+accion.toLowerCase()+"\" esta tarea?" );
		  dialog.addCancelButton("Cancelar");
			
		  dialog.setOnAcceptButtonClickListener(new  OnClickListener(){

				@Override
				public void onClick(View v) {					
					// TODO Auto-generated method stub
					insert();
					SyncAdapter syn= new SyncAdapter(getActivity(), true);
					syn.realizarSincronizacionRemota_bis();
					onItemAgendaClick.OnButton(1);
					
					
				}});
			
			dialog.show();
		  }
		  return true;
	  }
	  public void insert(){
		 
		  String montoText = String.valueOf(lista.getDatos().get(spAcciones.getSelectedItemPosition()).getId());
	      String etiquetaText = elemento.getTitulo();
	      
	      Util.Log("insert-----------------------------------");
	      String fechaText = Util.getDateNowFormat();
	      String descripcionText = txtObservacion.getText().toString();
	      
	      ContentValues values = new ContentValues();
	      values.put(ContractParaProceso.Columnas.VALOR, montoText);
	      values.put(ContractParaProceso.Columnas.ETIQUETA, etiquetaText);
	      values.put(ContractParaProceso.Columnas.FECHA, fechaText);
	      values.put(ContractParaProceso.Columnas.DESCRIPCION, descripcionText);
	      values.put(ContractParaProceso.Columnas.PENDIENTE_INSERCION, 1);
	      values.put(ContractParaProceso.Columnas.ID_REMOTA, elemento.getIdproceso());
	      values.put(ContractParaProceso.Columnas.USUARIO, Configuracion.USUARIO_PRUEBA);
	     
	      Util.Log("VALOR:"+montoText);
	      Util.Log("ETIQUETA:"+etiquetaText);
	      Util.Log("FECHA:"+fechaText);
	      Util.Log("DESCRIPCION:"+descripcionText);
	      Util.Log("ID_REMOTA:"+elemento.getIdproceso());
	      
	      getActivity().getContentResolver().insert(ContractParaProceso.CONTENT_URI, values);
	     
	      Util.Log("inserto..");
		  //SyncAdapter.sincronizarAhora(getActivity(), true);
	  }
	  
	  
	  public void crear_acciones(){
		  
		  datos =new String[]{(new Acciones()).toString(),(new Acciones(1,"Finalizar")).toString(),(new Acciones(2,"Rechazar")).toString()};
	  }
	  public class Acciones{
		  public int _id;
		  public String titulo;
		@Override
		public String toString() {
			return titulo;
		}
		public Acciones(int _id, String titulo) {
			super();
			this._id = _id;
			this.titulo = titulo;
		}
		public Acciones() {
			super();
			this._id = 0;
			this.titulo = "<<Acciones>>";
		}
		  
	  }
	public void setId(String id) {
		// TODO Auto-generated method stub
		//id = getActivity().getIntent().getExtras().getString("id"); //String.valueOf(this.getIntent().getExtras().getInt("id"));
		Util.Log("Nro Proceso id:"+id);
		 elemento= new Agenda_pendienteAD(getActivity());
		 elemento.load(id);
		Util.Log("Nro Proceso:"+elemento.getIdproceso());
		/**/
	}
	public interface OnItemAgendaClick {
		  void OnButton(int key);
	}
	public OnItemAgendaClick getOnItemAgendaClick() {
		return onItemAgendaClick;
	}
	public void setOnItemAgendaClick(OnItemAgendaClick onItemAgendaClick) {
		this.onItemAgendaClick = onItemAgendaClick;
	}  
	
}
