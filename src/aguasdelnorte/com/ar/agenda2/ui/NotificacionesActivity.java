package aguasdelnorte.com.ar.agenda2.ui;

import java.util.ArrayList;

import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import aguasdelnorte.com.ar.agenda2.clases.NotificacionAD;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;

public class NotificacionesActivity  extends ActionBarActivity {
	private NotificacionAD elemento;
	public ArrayList<ObjectView> datos;
	private ListViewAdapterTipo adaptador;
	private ListView lstOpciones;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificaciones);
		elemento= new NotificacionAD(this);
		
	}
class Tarea extends  AsyncTask<String,String,String>{
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Util.Log("Actualizar Lista");
		}
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			  Util.Log("---------");			 
			  datos = new ArrayList<ObjectView>(); 			 
			  //Ot r= new Ot(getActivity());
			  datos=elemento.getObjectView();	
			 
			 
		
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			componentes_ini();
			//progress.cancel();
			super.onPostExecute(result);
		}
		
	}
public void componentes_ini() {
	// TODO Auto-generated method stub
	adaptador = new ListViewAdapterTipo(this,datos);
	lstOpciones = (ListView)this.findViewById(R.id.list_notificaciones);	
	lstOpciones.setAdapter(adaptador);
}
	
}
