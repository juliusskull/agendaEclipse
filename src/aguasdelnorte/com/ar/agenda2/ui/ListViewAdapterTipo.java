package aguasdelnorte.com.ar.agenda2.ui;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import aguasdelnorte.com.ar.agenda2.R;



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

public class ListViewAdapterTipo extends BaseAdapter {
	
	Context mContext;
	LayoutInflater inflater;
	private List<ObjectView> tipolist = null;
	private ArrayList<ObjectView> tipoarray;
	private ListViewAdapterCosaClick listener;
	private Cursor cursor;
	//ImageLoader imageLoader;
	public ListViewAdapterTipo(Context context,
			List<ObjectView> worldpopulationlist) {
		mContext = context;
		this.tipolist = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		this.tipoarray = new ArrayList<ObjectView>();
		this.tipoarray.addAll(worldpopulationlist);
		//imageLoader = new ImageLoader(context);
		Util.Log("datos2:"+tipolist.size());
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tipolist.size();
	}
	public class ViewHolder {
		TextView lbCarTitulo;
		TextView lbCarObservacion;
		TextView lbCarFecha;	
		LinearLayout lyraiting;
		ImageView img;
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
			view = inflater.inflate(R.layout.lista_agenda_item, null);
			holder.lbCarTitulo = (TextView)view.findViewById(R.id.txtCardTitulo);						
			holder.lbCarObservacion= (TextView)view.findViewById(R.id.txtCardObservacion);						
			holder.lbCarFecha= (TextView)view.findViewById(R.id.txtCardFecha);
				view.setTag(holder);
		} else {			
			
			holder = (ViewHolder) view.getTag();
		}	
		Util.Log("tt:"+tipolist.get(position).getTitulo());
		holder.lbCarTitulo.setText(tipolist.get(position).getTitulo());
		
		holder.lbCarObservacion.setText(tipolist.get(position).getDescripcion());
		
		
		try {
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			date = dt.parse(tipolist.get(position).getFecha());
			SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy"); 		       
			holder.lbCarFecha.setText(dt1.format(date));	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		if(tipolist.get(position).getDrableid()!=0){
			Bitmap bImage = BitmapFactory.decodeResource(mContext.getResources(),tipolist.get(position).getDrableid() );
	
			holder.img.setImageBitmap(bImage);		
		}
		*/
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
		 public void swapCursor(Cursor newCursor) {
	        cursor = newCursor;
	        notifyDataSetChanged();
	    }

		

}
