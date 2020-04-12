package aguasdelnorte.com.ar.agenda2.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.ui.AdapterListPrincipal.ListViewAdapterCosaClick;
import aguasdelnorte.com.ar.agenda2.ui.AdapterListPrincipal.ViewHolder;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterItemCampo extends BaseAdapter {
	
	Context mContext;
	LayoutInflater inflater;
	private List<ObjectView> tipolist = null;
	private ArrayList<ObjectView> tipoarray;
	private ListViewAdapterCosaClick listener;
	private Cursor cursor;
	//ImageLoader imageLoader;
	public AdapterItemCampo(Context context,
			List<ObjectView> worldpopulationlist) {
		mContext = context;
		this.tipolist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.tipoarray = new ArrayList<ObjectView>();
		this.tipoarray.addAll(worldpopulationlist);		
		Util.Log("datos2:"+tipolist.size());
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tipolist.size();
	}
	public class ViewHolder {
		TextView lblTitulo;		
		TextView lblDescripcion;	
		LinearLayout lyraiting;
		
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tipolist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.agenda_item_campos, null);
			holder.lblTitulo = (TextView)view.findViewById(R.id.txtTitulo);						
			holder.lblDescripcion = (TextView)view.findViewById(R.id.txtDescripcion);	
			view.setTag(holder);
		} else {
			
			
			holder = (ViewHolder) view.getTag();
		}	
		Util.Log("tt:"+tipolist.get(position).getTitulo());
	      
		holder.lblTitulo.setText(tipolist.get(position).getTitulo());	
		holder.lblDescripcion.setText(tipolist.get(position).getDescripcion());
		
		if(tipolist.get(position).getTitulo().equals(Agenda_pendienteAD.CAMPO_MONTO)){
			holder.lblDescripcion.setText("$"+Util.priceWithDecimal(Double.parseDouble((tipolist.get(position).getDescripcion()))));
		
			if(tipolist.get(position).tipo.length()!=0){
				
				int t=Integer.parseInt(tipolist.get(position).tipo);
				if(t==11){
					holder.lblTitulo.setText("Dias:");
					holder.lblDescripcion.setText(tipolist.get(position).getDescripcion());
				}
				if(t==10){
					holder.lblTitulo.setText("Horas:");
					holder.lblDescripcion.setText(tipolist.get(position).getDescripcion());
				}
				
			}
		}		
		/**/
		if(tipolist.get(position).getTitulo().equals(Agenda_pendienteAD.CAMPO_FECHA_DESDE)){
			holder.lblDescripcion.setText(Util.CambiarFecha(tipolist.get(position).getDescripcion()));
		}
		if(tipolist.get(position).getTitulo().equals(Agenda_pendienteAD.CAMPO_FECHA_HASTA)){
			holder.lblDescripcion.setText(Util.CambiarFecha(tipolist.get(position).getDescripcion()));
		}
		
		if(tipolist.get(position).getTitulo().equals(Agenda_pendienteAD.CAMPO_FECHA_GESTION)){
			holder.lblDescripcion.setText(Util.CambiarFecha(tipolist.get(position).getDescripcion()));
		}
		
		if(tipolist.get(position).getTitulo().equals(Agenda_pendienteAD.CAMPO_FECHA_INGRESO)){
			holder.lblDescripcion.setText(Util.CambiarFecha(tipolist.get(position).getDescripcion()));
		}
		
		
		
		
		
		
		return view	;
	}
	 

		

}

