package aguasdelnorte.com.ar.agenda2.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import aguasdelnorte.com.ar.agenda2.R;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterListPrincipal extends BaseAdapter {
	
	Context mContext;
	LayoutInflater inflater;
	private List<ObjectView> tipolist = null;
	private ArrayList<ObjectView> tipoarray;
	private ListViewAdapterCosaClick listener;
	private Cursor cursor;
	//ImageLoader imageLoader;
	public AdapterListPrincipal(Context context,
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
		TextView lblSubtitulo;
		TextView lblDescripcion;	
		LinearLayout lyraiting;
		ImageView img;
		public TextView lblTitulo_left;
		public TextView lblSubtitulo_left;
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
			view = inflater.inflate(R.layout.lista_ppal_item, null);
			holder.lblTitulo = (TextView)view.findViewById(R.id.txtLista_titulo);						
			holder.lblSubtitulo = (TextView)view.findViewById(R.id.txtLista_subtitulo);						
			holder.lblTitulo_left= (TextView)view.findViewById(R.id.txtLista_titulo_left);
			holder.lblSubtitulo_left= (TextView)view.findViewById(R.id.txtLista_subtitulo_left);
			holder.img = (ImageView) view.findViewById(R.id.imgLista_icono);
			//holder.lyraiting = (LinearLayout)view.findViewById(R.id.lyratingbar);
			view.setTag(holder);
		} else {
			
			
			holder = (ViewHolder) view.getTag();
		}	
		Util.Log("tt:"+tipolist.get(position).getTitulo());
		
		String sourceString = "<b>" + tipolist.get(position).getTitulo().substring(0, 1) + "</b> " + tipolist.get(position).getTitulo().substring(1); 
		//String sourceString = "<span style=\"background-color: yellow;\">" + tipolist.get(position).getTitulo().substring(0, 1) + "</span> " + tipolist.get(position).getTitulo().substring(1);
		
		holder.lblTitulo.setText(Html.fromHtml(sourceString));
		//holder.lblTitulo.set
		holder.lblSubtitulo.setText(tipolist.get(position).getDescripcion());	
		holder.lblTitulo_left.setText(tipolist.get(position).getTitulo_left());
		holder.lblSubtitulo_left.setText(tipolist.get(position).getDescripcion_left());
		
		/**/
		if(tipolist.get(position).getDrableid()!=0){
			Bitmap bImage = BitmapFactory.decodeResource(mContext.getResources(),tipolist.get(position).getDrableid() );
	
			holder.img.setImageBitmap(bImage);		
		}
		//imageLoader.DisplayImage(Configuracion.getUrlImagen()+tipolist.get(position).getIMAGEN(), holder.img);
		//ViewGroup.LayoutParams params = holder.lyraiting.getLayoutParams();
		//params.width=5;
		//params.height=5;
		//holder.lyraiting.setLayoutParams(new LinearLayout.LayoutParams(params));
		//holder.lyraiting.setVisibility(LinearLayout.INVISIBLE);
		
		return view	;
	}
	 
	
	// Filter Class
		public void filter(String charText) {
			charText = charText.toLowerCase(Locale.getDefault());
			tipolist.clear();
			if (charText.length() == 0) {
				tipolist.addAll(tipoarray);
			} else {
				for (ObjectView wp : tipoarray) {
					if (wp.getTitulo().toLowerCase(Locale.getDefault())
							.contains(charText)) {
						tipolist.add(wp);
					}
				}
			}
			notifyDataSetChanged();
		}
		
		public interface ListViewAdapterCosaClick {
			  void OnButton(int key);
		}

		public ListViewAdapterCosaClick getListener() {
			return listener;
		}
		public void setListener(ListViewAdapterCosaClick listener) {
			this.listener = listener;
		}
		

}
