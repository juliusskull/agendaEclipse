package aguasdelnorte.com.ar.agenda2.ui;

import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;


public class Settings extends Fragment{
	private static final String ARG_SECTION_NUMBER = "section_number";
	private Button btnPruebaConexion;
	private WebView wv;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.settings_main, container,
				false);
		return rootView;
	}
	  @Override
	    public void onActivityCreated(Bundle state) {
	        super.onActivityCreated(state);	
	        btnPruebaConexion=(Button)getActivity().findViewById(R.id.btnPruebaConexion);
	        wv= (WebView)getActivity().findViewById(R.id.wvReferencia);
	        wv.loadData(Util.justifyText(exe()),"text/html;charset=utf-8", null);
	    }
	  
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
		public static Settings newInstance(int sectionNumber) {
			Settings fragment = new Settings();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			
			
			
			return fragment;
		}
		public String exe() {
			String s="";
			s+="<h3><b>" +"Usuario:</b>"+ Util.SpGet(getActivity(), Configuracion.getBiblio(), Configuracion.getBiblioNombre())+ "</h3>";
			s+="<h3><b>" +"Mail:</b>"+ Util.SpGet(getActivity(), Configuracion.getBiblio(), Configuracion.getBiblioMail())+ "</h3>";
			s+="<h3><b>" +"sincronizacion:</b>"+ Util.SpGet(getActivity(), Configuracion.getBiblio(), Configuracion.getBiblioSincronizacion())+ "</h3>";
			return s;
		}
}
