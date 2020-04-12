package aguasdelnorte.com.ar.agenda2.ui;

import java.util.ArrayList;
import java.util.List;

import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.ui.AdapterListPrincipal.ViewHolder;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdapterMenu extends BaseAdapter{
	private String [] titulos = null;
	LayoutInflater inflater;
	Context mContext;
	public AdapterMenu(Context context,
			String []  worldpopulationlist) {
		mContext = context;
		titulos = worldpopulationlist;
		inflater = LayoutInflater.from(mContext);
		
	}
	public class ViewHolder {
		TextView lblTitulo;
		public ImageView img;			
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return titulos.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return titulos[position];
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
			view = inflater.inflate(R.layout.lista_menu_item, null);
			holder.lblTitulo = (TextView)view.findViewById(R.id.txtListaMenuTitulo);
			holder.img = (ImageView)view.findViewById(R.id.img_notificacion);
			
			Typeface font = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf");
			
			//Typeface font = Typeface.createFromAsset(mContext.getAssets(), "Roboto-Regular.ttf");
			
			holder.lblTitulo.setTypeface(font);
			view.setTag(holder);
		} else {
			
			
			holder = (ViewHolder) view.getTag();
		}	
		holder.lblTitulo.setText(titulos[position]);
		holder.img.setVisibility(ImageView.INVISIBLE);
		return view;
	}

}
