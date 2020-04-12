package aguasdelnorte.com.ar.agenda2.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import aguasdelnorte.com.ar.agenda2.provider.ContractParaGastos;
import aguasdelnorte.com.ar.agenda2.provider.ContractParaProceso;
import aguasdelnorte.com.ar.agenda2.sync.SyncAdapter;
import aguasdelnorte.com.ar.agenda2.sync.SyncAdapterProceso;
import aguasdelnorte.com.ar.agenda2.ui.ItemAgenda.DownloadFile;
import aguasdelnorte.com.ar.agenda2.ui.Principal.PrincipalSeleccion;
import aguasdelnorte.com.ar.agenda2.ui.Principal.PrincipalNotificaciones;
import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.clases.Categoria;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import aguasdelnorte.com.ar.agenda2.clases.Usuario;
import aguasdelnorte.com.ar.agenda2.web.Agenda_pendiente;
import aguasdelnorte.com.ar.agenda2.web.Agenda_pendienteLista;
import aguasdelnorte.com.ar.agenda2.web.Gasto;
import aguasdelnorte.com.ar.agenda2.web.GastoLista;



import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;


public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	public final int C_CHANGE_LOGIN=5 ;
	public final int C_LOGOUT=4 ;
	public final int C_PRINCIPAL=0 ;
	public final int C_SETTING=100 ;
	public static final int C_LISTA_NOTIFICACIONES = 101;
	private static final int C_LISTA = 1;
	private static final int C_AGENDA_ITEM = 2;
	private static final int requestCode_loguin = 10001;
	private static final int requestCode_lista=10002;
	private static final int requestCode_Item=10003;
	private static final int requestCode_lista_notificaciones=10004;
	
	
	private NavigationDrawerFragment mNavigationDrawerFragment;
	public  Settings settings= new Settings();
	public  Principal principal= new Principal();
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private RequestQueue requestQueue;
	JsonObjectRequest jsArrayRequest;
	private Gson gson = new Gson();
	private int position_actual;
	private String smsNumber="3875211401";
	private MenuItem menuItem;
	public  ProgressDialog progress;
	private MenuItem menuItem_actual;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		

		conectarce();
		ir(C_PRINCIPAL);
		principal.setOnPrincipalSeleccion(new PrincipalSeleccion(){

			@Override
			public void OnButton(int key) {
				// TODO Auto-generated method stub				
				position_actual=key;				
		    	ir(C_LISTA);
		    	
			}
		});
		principal.setOnPrincipalNotificaciones(new PrincipalNotificaciones(){

			@Override
			public void OnButton() {
				// TODO Auto-generated method stub
				Util.Log("entro notificaciones");
				ir(C_LISTA_NOTIFICACIONES);
			}});
		
		
		
		}
			  @Override
	    protected void onStart()
	    {
	        super.onStart();

	        SyncAdapter syn= new SyncAdapter(this, true);
			syn.realizarSincronizacionRemota_bis();
	    }
 public void createfileinterno(){
		 String filename = "prueba1.txt";
		 String string = "Hello world!";
		 FileOutputStream outputStream;

		 try {
		    
		   File folder = new File(this.getFilesDir(), "recorrido");	 
		   folder.mkdir();
		   outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
		   outputStream.write(string.getBytes());
		   outputStream.close();
		 } catch (Exception e) {
		   e.printStackTrace();
		 }
		 
	 }	
 public void ExecFile(File dir2,String name) {
		File dir= new File(dir2, name);
		String[] children = dir.list();
		Util.Log("DownloadFile=>listar---------");
        for (int i = 0; i < children.length; i++) {
        	File oFile = new File(dir, children[i]);
        	Util.Log("DownloadFile=>"+oFile.getAbsolutePath());
        	
            
        }
	}  
	
	
	public static void shareWhatsApp(Activity appActivity, String texto) {
		Util.Log("Enviando..");
	    Intent sendIntent = new Intent(Intent.ACTION_SENDTO);     
	    sendIntent.setType("text/plain");
	    sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, texto);

	    PackageManager pm = appActivity.getApplicationContext().getPackageManager();
	    final List<ResolveInfo> matches = pm.queryIntentActivities(sendIntent, 0);
	    boolean temWhatsApp = false;
	    for (final ResolveInfo info : matches) {
	      if (info.activityInfo.packageName.startsWith("com.whatsapp"))  {
	          final ComponentName name = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);
	          sendIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	          sendIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
	          sendIntent.setComponent(name);
	          temWhatsApp = true;
	          break;
	      }
	    }               

	    if(temWhatsApp) {
	        //abre whatsapp
	        appActivity.startActivity(sendIntent);
	    } else {
	        //alerta - você deve ter o whatsapp instalado
	        Toast.makeText(appActivity, "xxx", Toast.LENGTH_SHORT).show();
	    }
	}
		
	public void onClickWhatsApp() {
		Util.Log("WhatsApp=>Enviando");
	    PackageManager pm=getPackageManager();
	    try {

	        //Intent waIntent = new Intent(Intent.ACTION_SENDTO);
	        //Uri uri = Uri.parse("smsto:" + smsNumber);
	        
	        Uri uri = Uri.parse("smsto: "+smsNumber+"@s.whatsapp.net");
	       
	        
	        Intent waIntent = new Intent(Intent.ACTION_SEND,uri);
	        waIntent.setType("text/plain");
	        String text = "YOUR TEXT HERE";
	        Util.Log("WhatsApp=>Enviando(1)");
	        PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
	        //Check if package exists or not. If not then code 
	        //in catch block will be called
	        waIntent.setPackage("com.whatsapp");
	        //waIntent.setPackage(info);
	        Util.Log("WhatsApp=>Enviando(2)");
	        waIntent.putExtra(Intent.EXTRA_TEXT, text);
	        Util.Log("WhatsApp=>Enviando(3)");
	        startActivity(Intent.createChooser(waIntent, "Share with"));
	        //startActivity(waIntent);
	       
	       // startActivity(waIntent,uri);

	   } catch (NameNotFoundException e) {
	        Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
	                .show();
	   }  

	}

	private void avisar_activo() {
		// TODO Auto-generated method stub
		  
		  new Thread(new Runnable() { 
	            public void run(){    
	            	if(Util.isOnline(MainActivity.this)){
	            		String s= Configuracion.getActivo(Util.getIMEI(MainActivity.this),Usuario.getUsuarioActivo(MainActivity.this),Usuario.getNombreActivo(MainActivity.this));
	            		Util.Log(s);
	            		Util.getClient(s);
	            	}
	            }
	        }).start();

	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		Util.Log("position=>"+position);
		if (!Usuario.Exists(this)){
			ir_logout();
					
		}else{
			
		    if(position>=1 && position<C_SETTING){
		    	position_actual=position;
		    	
		    	if(C_LOGOUT==position){
		    		ir_logout();
		    	}else{
		    		if(C_CHANGE_LOGIN==position){
		    			ir(C_CHANGE_LOGIN);		    			
		    		}else{
		    			ir(C_LISTA);
		    		}
		    	}
		    }else{
		    	ir(position);
		    }
		    
			//ir(position);
		}	 
		//ir_logout();
	
	}
	
	private void ir(int position){
		Intent intent=null;
		Util.Log("posicion:"+position);
		switch (position) {
		case C_LOGOUT:
			ir_logout();
			break;
		case C_SETTING:
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, settings.newInstance(position)).commit();

			break;
		case C_PRINCIPAL:
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, principal.newInstance(position)).commit();
			
			break;	
		case C_LISTA:
			intent = new Intent(this,ListActivity.class);		
			intent.putExtra("categoria", Categoria.getCategoria(position_actual));
			startActivityForResult(intent, requestCode_lista);
			break;	
		case C_LISTA_NOTIFICACIONES:
			intent = new Intent(this,ListNotificacionesActivity.class);		
			startActivityForResult(intent, requestCode_lista_notificaciones);
			break;	
		case C_CHANGE_LOGIN:
			intent = new Intent(this,LoginCambio.class);		
			startActivityForResult(intent, requestCode_lista_notificaciones);
			break;		
		default:
			break;
		}
		
		
	}

	private void ir_logout() {
		// TODO Auto-generated method stub	
		finish();
	}

	public void onSectionAttached(int number) {
		String [] menu_list= getResources().getStringArray(R.array.menu_list);
		
		try {
			mTitle = menu_list[number];
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mTitle = "Configuracion";
		}
		
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		actionBar.setHomeButtonEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}

		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		menuItem_actual=item;
		if (id == R.id.action_settings) {
			ir(C_SETTING);
			return true;
		}
		if (id == R.id.action_actualizar_main) {
			
			conectarce(item);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
 public void fin_barradeprogreso(){
	 try {
			menuItem.collapseActionView();
			menuItem.setActionView(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Util.Log("E:"+e.getMessage());
		}
	 
 }	
  public void conectarce(MenuItem item) {
	  Util.Log("cnx=>Conectandoce--------------");
	  menuItem = item;
      menuItem.setActionView(R.layout.barradeprogreso);
      menuItem.expandActionView();
      conectarce();
   
  }
  public void conectarce() {
	  Util.Log("cnx=>Conectandoce--------------");
	  if(Util.isOnline(this)){
		  requestWithSomeHttpHeaders();
	  }else{
		  Util.Log("cnx=>Conectandoce: Offline");
		  Util.Log(this, "Sin conexion con el servidor modo Offline");
	  }
   
  }
  public void exe() {
	  String montoText = "0";
      String etiquetaText = "Agenda";
      String fechaText = "2016-04-04";
      String descripcionText = "-prueba"+Util.getDateNow();
   
      ContentValues values = new ContentValues();
      values.put(ContractParaProceso.Columnas.VALOR, montoText);
      values.put(ContractParaProceso.Columnas.ETIQUETA, etiquetaText);
      values.put(ContractParaProceso.Columnas.FECHA, fechaText);
      values.put(ContractParaProceso.Columnas.DESCRIPCION, descripcionText);
      values.put(ContractParaProceso.Columnas.PENDIENTE_INSERCION, 1);

      getContentResolver().insert(ContractParaProceso.CONTENT_URI, values);
      SyncAdapter.sincronizarAhora(this, true);
      
    
}
	
  public void requestWithSomeHttpHeaders() {      
	    Util.Log("Actualizando....");	    
	    Util.Log("API=>"+Usuario.getApi(this));
	    RequestQueue queue = Volley.newRequestQueue(this);
	    String url = Configuracion.getAuthorization();
	    Util.Log("url=>"+url);
	    Util.Log("url-api=>"+Usuario.getApi(MainActivity.this));
	    
	    StringRequest postRequest = new StringRequest(Request.Method.GET, url, 
	        new Response.Listener<String>() 
	        {
	            @Override
	            public void onResponse(String response) {
	                // response
	                Util.Log("response=>"+response);	                
	                Agenda_pendienteLista data = gson.fromJson(response, Agenda_pendienteLista.class);	                
	                Util.Log("Estado:"+data.getEstado());
	                List<Agenda_pendiente> pp=data.getPendientes();	                
	                //Pendiente pendiente=data.getPendientes().;
	                Util.Log("pendiente:"+pp.size());
	                
	                data.PendientesActualizar(MainActivity.this);	
	                fin_barradeprogreso();
	                	                
	            }
	        }, 
	        new Response.ErrorListener() 
	        {
	            @Override
	            public void onErrorResponse(VolleyError error) {
	                // TODO Auto-generated method stub
	                Log.d("ERROR","error => "+error.toString());	              
	                fin_barradeprogreso();
	            }
	        }
	        
	    ) { 	    	  
	        @Override
	        public Map<String, String> getHeaders() throws AuthFailureError {
	        	 //ba4d1a5f35f8cf5600a74fa4e80a6fff 
	                Map<String, String>  params = new HashMap<String, String>();  
	                params.put("Authorization", Usuario.getApi(MainActivity.this));
	                //params.put("Usuario", Usuario.getUsuarioActivo(MainActivity.this));	                
	                return params;  
	        }	        
	        
	    };	
	    queue.add(postRequest);	 

	}
  
 
  @Override
	public void onActivityResult(int requestCode1, int
			resultCode, Intent data) {
	  Util.Log("requestCode1="+requestCode1+";resultCode="+resultCode);
	  conectarce();
	  if (resultCode != Activity.RESULT_CANCELED) {
		/*if(requestCode1==requestCode_loguin){			
			if (resultCode == RESULT_OK) {
				//ir(C_PRINCIPAL);
	        }else{
	        	finish();
	        }			
		}*/		
		if(requestCode1==requestCode_lista){
			if (resultCode == RESULT_OK) {
				//ir(C_LISTA);
				Intent intent = new Intent(this,ItemAgendaActivity.class);
				startActivityForResult(intent, requestCode_Item);
			
			 }
			
		}
	}
  }

@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	
}
  
  
}
