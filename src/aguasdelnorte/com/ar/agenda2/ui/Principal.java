package aguasdelnorte.com.ar.agenda2.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import aguasdelnorte.com.ar.agenda2.clases.EstadisticoAD;
import aguasdelnorte.com.ar.agenda2.clases.NotificacionesAD;
import aguasdelnorte.com.ar.agenda2.clases.Usuario;
import aguasdelnorte.com.ar.agenda2.utils.Utils;
import aguasdelnorte.com.ar.agenda2.web.Agenda_pendiente;
import aguasdelnorte.com.ar.agenda2.web.Agenda_pendienteLista;
import aguasdelnorte.com.ar.agenda2.web.Notificaciones;
import aguasdelnorte.com.ar.agenda2.web.NotificacionesLista;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Principal extends Fragment{
	private static final String ARG_SECTION_NUMBER = "section_number";
	protected static final String NOTIFICACIONES = null;
	private AdapterListPrincipal adaptador;
	public static PrincipalSeleccion onPrincipalSeleccion;
	public static PrincipalNotificaciones onPrincipalNotificaciones;
	
	private ListView listPrincipal;
	private PieChart mChart;
	private Gson gson = new Gson();
	private ArrayList<ObjectView> datos;
	private EstadisticoAD estadistico;
	private RelativeLayout ly_notificaciones;
 
	 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.ppal, container,
				false);
		return rootView;
	}
	  @Override
	  public void onActivityCreated(Bundle state) {
	        super.onActivityCreated(state);		        
	         			 
		    listPrincipal = (ListView)getActivity().findViewById(R.id.listPrincipal);
			mChart = (PieChart) getActivity().findViewById(R.id.chart2);
			ly_notificaciones= (RelativeLayout)getActivity().findViewById(R.id.ly_notificaciones);
			
			listPrincipal.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					ObjectView o=((ObjectView)adaptador.getItem(position));
					try {
						Principal.onPrincipalSeleccion.OnButton(o.getId());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Util.Log("E:"+e.getMessage());
					}
				}
			});
			ly_notificaciones.setOnClickListener( new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Principal.onPrincipalNotificaciones.OnButton();
				}
			});
			exe();
			requestWithSomeHttpHeaders();
			
			
			//requestNotificacionesHttpHeaders();
					      
	    }
	
	public void exe() {
		    datos = new ArrayList<ObjectView>();
			estadistico= new EstadisticoAD(getActivity());
			datos=estadistico.Resumen();			 
			adaptador = new AdapterListPrincipal(getActivity(),datos);						 
			listPrincipal.setAdapter(adaptador);
		
			 /*
			 BarChart chart = (BarChart) getActivity().findViewById(R.id.chart);
			 
		     BarData data = new BarData(getXAxisValues(), getDataSet());
		     chart.setData(data);
		     chart.setDescription("My Chart");
		     chart.animateXY(2000, 2000);
		     chart.invalidate();
		       
	        
	        /* */	      
		     
		      mChart.setHoleColorTransparent(false);
		      mChart.setDrawHoleEnabled(true);
		      mChart.setUsePercentValues(true);
		   
		      mChart.setHoleColor(Color.BLACK);
		      
		      mChart.setHoleRadius(7);
		      
		      mChart.setTransparentCircleRadius(10);
		      
		      mChart.setLabelFor(R.string.username);
		      mChart.setDescription("Porcentaje de pendientes");
		      
		      PIEaddData();	
		      
		      actualizar_notificacion();
	}
	  private void PIEaddData() {
		   EstadisticoAD estadistico= new EstadisticoAD(getActivity());
		   estadistico.setSQL(EstadisticoAD.AGENDA_PENDIENTE);
	        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
	        Float[] ff=estadistico.PIEgetArray();
	        for (int i = 0; i < ff.length; i++){
	        	yVals1.add(new Entry(ff[i].floatValue(), i));
	        	
	        }	            

	        ArrayList<String> xVals = new ArrayList<String>();
	        String[] ss=estadistico.PIEgetTitulo();
	        for (int i = 0; i < ss.length; i++){
	        	 xVals.add(ss[i]);	          
	        }

	        // create pie data set
	        PieDataSet dataSet = new PieDataSet(yVals1, "Market Share");
	       // dataSet.setSliceSpace(3);
	       
	      //  dataSet.setSelectionShift(5);
	        dataSet.setSliceSpace(3f);

	        // add many colors
	        ArrayList<Integer> colors = new ArrayList<Integer>();
/*
	        for (int c : ColorTemplate.VORDIPLOM_COLORS)
	            colors.add(c);
	        */
/**/
	       
	        for (int c : ColorTemplate.JOYFUL_COLORS)
	            colors.add(c);
	        /*
	        for (int c : ColorTemplate.COLORFUL_COLORS)
	            colors.add(c);
*//*
	        for (int c : ColorTemplate.LIBERTY_COLORS)
	            colors.add(c);

	        //est
	        for (int c : ColorTemplate.PASTEL_COLORS)
	            colors.add(c);
*/ //
	        colors.add(ColorTemplate.getHoloBlue());
	       
	        dataSet.setColors(colors);

	        // instantiate pie data object now
	        PieData data = new PieData(xVals, dataSet);
	        data.setValueFormatter(new PercentFormatter());
	        data.setValueTextSize(11f);
	        data.setValueTextColor(Color.GRAY);

	        mChart.setData(data);

	        // undo all highlights
	        mChart.highlightValues(null);

	        // update pie chart
	        mChart.invalidate();
	        mChart.getLegend().setEnabled(false);
	    }
	  
	  private ArrayList<BarDataSet> getDataSet() {
	        ArrayList<BarDataSet> dataSets = null;
	 
	        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
	        BarEntry v1e1 = new BarEntry(110.000f, 0); // Jan
	        valueSet1.add(v1e1);
	        BarEntry v1e2 = new BarEntry(40.000f, 1); // Feb
	        valueSet1.add(v1e2);
	        BarEntry v1e3 = new BarEntry(60.000f, 2); // Mar
	        valueSet1.add(v1e3);
	        BarEntry v1e4 = new BarEntry(30.000f, 3); // Apr
	        valueSet1.add(v1e4);
	        BarEntry v1e5 = new BarEntry(90.000f, 4); // May
	        valueSet1.add(v1e5);
	        BarEntry v1e6 = new BarEntry(100.000f, 5); // Jun
	        valueSet1.add(v1e6);
	 
	        ArrayList<BarEntry> valueSet2 = new ArrayList<BarEntry>();
	        BarEntry v2e1 = new BarEntry(150.000f, 0); // Jan
	        valueSet2.add(v2e1);
	        BarEntry v2e2 = new BarEntry(90.000f, 1); // Feb
	        valueSet2.add(v2e2);
	        BarEntry v2e3 = new BarEntry(120.000f, 2); // Mar
	        valueSet2.add(v2e3);
	        BarEntry v2e4 = new BarEntry(60.000f, 3); // Apr
	        valueSet2.add(v2e4);
	        BarEntry v2e5 = new BarEntry(20.000f, 4); // May
	        valueSet2.add(v2e5);
	        BarEntry v2e6 = new BarEntry(80.000f, 5); // Jun
	        valueSet2.add(v2e6);
	 
	        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
	        barDataSet1.setColor(Color.rgb(0, 155, 0));
	        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Brand 2");
	        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
	 
	        dataSets = new ArrayList<BarDataSet>();
	        dataSets.add(barDataSet1);
	        dataSets.add(barDataSet2);
	        return dataSets;
	    }
	 
	    private ArrayList<String> getXAxisValues() {
	        ArrayList<String> xAxis = new ArrayList<String>();
	        xAxis.add("JAN");
	        xAxis.add("FEB");
	        xAxis.add("MAR");
	        xAxis.add("APR");
	        xAxis.add("MAY");
	        xAxis.add("JUN");
	        return xAxis;
	    }
	    public interface PrincipalSeleccion {
	    	public void OnButton(int key);
	    	
		}
		public PrincipalSeleccion getOnPrincipalSeleccion() {
			return onPrincipalSeleccion;
		}
		public void setOnPrincipalSeleccion(PrincipalSeleccion onPrincipalSeleccion) {
			this.onPrincipalSeleccion = onPrincipalSeleccion;
		}
		
		 public interface PrincipalNotificaciones {
		    	public void OnButton();
		}
		 public PrincipalNotificaciones getOnPrincipalNotificaciones() {
				return onPrincipalNotificaciones;
			}
			public void setOnPrincipalNotificaciones(PrincipalNotificaciones onPrincipalSeleccion) {
				this.onPrincipalNotificaciones = onPrincipalSeleccion;
			}
		 
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
		public static Principal newInstance(int sectionNumber) {
			Principal fragment = new Principal();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);			
			
			return fragment;
		}
		public void requestWithSomeHttpHeaders() {			
			    RequestQueue queue = Volley.newRequestQueue(getActivity());
			    String url = Configuracion.getAuthorization(); 
			    StringRequest postRequest = new StringRequest(Request.Method.GET, url, 
			        new Response.Listener<String>(){
			            @Override
			            public void onResponse(String response) {			                		                	                
			                Agenda_pendienteLista data = gson.fromJson(response, Agenda_pendienteLista.class);
	                        List<Agenda_pendiente> pp=data.getPendientes(); 
			                data.PendientesActualizar(getActivity()); 
			                exe();
			            }
			        }, 
			        new Response.ErrorListener() 
			        {
			            @Override
			            public void onErrorResponse(VolleyError error) {
			                // TODO Auto-generated method stub
			                Log.d("ERROR","error => "+error.toString());
			               
			            }
			        }
			    ) {     
			        @Override
			        public Map<String, String> getHeaders() throws AuthFailureError {
			        	 //ba4d1a5f35f8cf5600a74fa4e80a6fff 
			                Map<String, String>  params = new HashMap<String, String>();  
			                params.put("Authorization", Usuario.getApi(getActivity()));
			                //params.put("Usuario", Usuario.getUsuarioActivo(MainActivity.this));
			                
			                return params;  
			        }
			        
			    };
			    queue.add(postRequest);
			  //------------------------------------------
			    //exe();

			}
		public void actualizar_notificacion(){
			TextView txt= (TextView)getActivity().findViewById(R.id.txt_notificacion_ppal);
			NotificacionesAD notificaciones = new NotificacionesAD(getActivity());
			int cantidad=notificaciones.Cantidad();
			
			txt.setText("Notificaciones nuevas("+String.valueOf(cantidad)+")");
		}
		public void requestNotificacionesHttpHeaders() {
		    Util.Log("Actualizando....");
		    Util.Log("API=>"+Usuario.getApi(getActivity()));
		    RequestQueue queue = Volley.newRequestQueue(getActivity());
		    String url = Configuracion.getNotificaciones();
		    Util.Log("url=>"+url);
		    Util.Log("url-api=>"+Usuario.getApi(getActivity()));
		    
		    StringRequest postRequest = new StringRequest(Request.Method.GET, url, 
		        new Response.Listener<String>() 
		        {
		            @Override
		            public void onResponse(String response) {
		                    	
		                Util.Log("pendiente=>"+response);			                
		                NotificacionesLista data = gson.fromJson(response, NotificacionesLista.class);
                        Util.Log("pendiente=>Estado:"+data.getEstado());                        
                        	try {                        		
                        		Util.Log("pendiente=>1");                        		
                        		data.setResponse(response);                        		
								List<Notificaciones> pp=data.getNotificacion();								            
								Util.Log("pendiente=>"+pp.size());
								if(pp.size()>1){
									data.PendientesActualizar(getActivity());								
									Utils.showDialog(getActivity(), "Existen notificaciones sin leer");
									actualizar_notificacion();
								}							
                        	
                        	} catch (Exception e) {
								// TODO Auto-generated catch block
								//e.printStackTrace();
								Util.Log("pendiente=>"+e.getMessage());
							}		                
		            }
		        }, 
		        new Response.ErrorListener() 
		        {
		            @Override
		            public void onErrorResponse(VolleyError error) {
		                // TODO Auto-generated method stub
		                Log.d("ERROR","error => "+error.toString());
		               
		            }
		        }
		    ) {     
		        @Override
		        public Map<String, String> getHeaders() throws AuthFailureError {
		        	 //ba4d1a5f35f8cf5600a74fa4e80a6fff 
		                Map<String, String>  params = new HashMap<String, String>();  
		                params.put("Authorization", Usuario.getApi(getActivity()));
		                //params.put("Usuario", Usuario.getUsuarioActivo(MainActivity.this));
		                
		                return params;  
		        }
		        
		    };
		    queue.add(postRequest);
		  

		}
}
