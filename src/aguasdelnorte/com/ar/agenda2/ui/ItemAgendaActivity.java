package aguasdelnorte.com.ar.agenda2.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import aguasdelnorte.com.ar.agenda2.clases.Usuario;
import aguasdelnorte.com.ar.agenda2.ui.ItemAgendaFinalizar.OnItemAgendaClick;
import aguasdelnorte.com.ar.agenda2.ui.ListActivity.DownloadFile;
import aguasdelnorte.com.ar.agenda2.ui.ListActivity.Tarea;
import aguasdelnorte.com.ar.agenda2.utils.Utils;
import aguasdelnorte.com.ar.agenda2.web.NotificacionesLista;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class ItemAgendaActivity  extends ActionBarActivity{
	private ListView list;
	private Agenda_pendienteAD elemento;
	public ProgressDialog progress;
	private ArrayList<ObjectView> datos;
	private AdapterItemCampo adaptador;
	private final int VER=1;
	private final int FINALIZAR=2;
	private int FRAGMENT_ACTUAL=VER;
	private ItemAgenda oItemAgenda= new ItemAgenda();
	private ItemAgendaFinalizar oItemAgendaFinalizar= new ItemAgendaFinalizar();
	private MenuItem oMenuOk;
	private MenuItem oMenuFinalizarOk;
	private String id;
	private String procesoid;
	public String file_name="maven_2.pdf";
	public String folder_name="/agendapdf/";
	public String dir_name="";
	private String proceso_actual_id;
	private Gson gson = new Gson();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agenda_item_ppal);
		/*
		list =(ListView)findViewById(R.id.list_agenda_item);
		//setToolbar();
		elemento= new Agenda_pendienteAD(this);
		String id = getIntent().getExtras().getString("id"); //String.valueOf(this.getIntent().getExtras().getInt("id"));
		elemento.load(id);
		datos= elemento.getCamposObjectView();
		adaptador = new AdapterItemCampo(this,datos);
		list.setAdapter(adaptador);
		*/
		
		//InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);  
		//imm.hideSoftInputFromWindow(ed.getWindowToken(), 0);  
		/**/
		/*
		id = getIntent().getExtras().getString("id"); //String.valueOf(this.getIntent().getExtras().getInt("id"));
		elemento.load(id);
		*/
		
		procesoid = getIntent().getExtras().getString("id"); //String.valueOf(this.getIntent().getExtras().getInt("id"));
		//elemento= new Agenda_pendienteAD(this);
		//elemento.load(id);
		//elemento.getTitulo().toLowerCase().replaceAll(" ", "_");
		//file_name=elemento.getTitulo().toLowerCase().replaceAll(" ", "_")+ ".pdf";
		//file_name=elemento+ ".pdf";
	
		setToolbar();
		dir_name=this.getFilesDir().getAbsolutePath();
		ir(VER);
		oItemAgendaFinalizar.setOnItemAgendaClick(new OnItemAgendaClick(){
			@Override
			public void OnButton(int key) {
				// TODO Auto-generated method stub	
				volver();
			}});
		
		
	}
	protected void volver() {
		// TODO Auto-generated method stub
		finish();
	}
	public void ir(int key) {
		
		try {
			oMenuOk.setVisible(false);
			oMenuFinalizarOk.setVisible(true);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FRAGMENT_ACTUAL=key;
		switch (key) {
		case VER:			
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, oItemAgenda).commit();
			break;
		case FINALIZAR:
			
			elemento=oItemAgenda.getElemento();
			
			Log.v("", "=>"+elemento.toString());
			
			if(elemento.isPuedeAutorizar()){
				oMenuOk.setVisible(true);
				oMenuFinalizarOk.setVisible(false);
				getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, oItemAgendaFinalizar).commit();
			}else{
				Utils.showDialog(this, R.string.no_puede_autorizar);
				
			}
			break;
		default:
			break;
		}
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main3, menu);
		oMenuOk= menu.findItem(R.id.action_finalizar_ok); 
		oMenuOk.setVisible(false);
		oMenuFinalizarOk= menu.findItem(R.id.action_finalizar); 
		oMenuFinalizarOk.setVisible(true);
		
		return true;
	}
	public void alClickearBotonAdd(View v) {
	
		proceso_actual_id=Util.SpGet(this, Configuracion.BIBLIO, Configuracion.PROCESO_ACTUAL);
		Util.Log("Clickear-1:"+ proceso_actual_id);
		elemento=oItemAgenda.getElemento();
		file_name=elemento.getTitulo().toLowerCase().replaceAll(" ", "_")+ ".pdf";
				
		final File pdfFile = new File(dir_name + folder_name + file_name);
		
		/**/
		if(pdfFile.exists()){			
			
			Utils.showDialog(this, "Visualizar PDF","Local","En Acrobat ", new OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					view_pdf();
				}
			}, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ver_adbobe_reader(pdfFile,file_name);
				}
			});
			
			
		}else{
			download_pdf();
		}
	
	}
	public  void ver_adbobe_reader(File pdfFile,String file_name){		
		
		try {
			Util.Log("File=>(1)");
			String dstPath = Environment.getExternalStorageDirectory() + File.separator + file_name + File.separator;
			File dst = new File(dstPath);
			Util.Log("File=>(Copiar Archivo)");
			Util.copy(pdfFile, dst);
			Util.Log("File=>"+dst.getAbsolutePath());
			
			if (!dst.exists()) {
				Util.Log("File=>No existe archivo");
			}else{
			
				try {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromFile(dst));
					intent.setType("application/pdf");
					intent.setPackage("com.adobe.reader");          
					startActivity(intent);
				} catch (ActivityNotFoundException e) {
					// TODO Auto-generated catch block
					Utils.showDialog(this, "Debe instalar Adobe Reader para poder ver este PDF");
				}         
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Util.Log("File=>e:"+e.getMessage());
			
		}
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: 
            	if(FRAGMENT_ACTUAL==VER){
            		this.finish();
            	}else{
            		ir(VER);            		
            	}
                return true;
            case R.id.action_finalizar:
            	//oItemAgendaFinalizar= new ItemAgendaFinalizar();
            	//oItemAgendaFinalizar.setId(oItemAgenda.getItemid());
                ir(FINALIZAR);
                return true;  
            case R.id.action_finalizar_ok: 
            	/*if(oItemAgendaFinalizar.exe()){
            		this.finish();
            	}*/
            	oItemAgendaFinalizar.exe();
                return true;      
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	private void  download_pdf() {
		RequestQueue queue = Volley.newRequestQueue(this);
		
		//String url = "http://200.45.174.162/wsagenda/v1/consulta.php?id_proceso="+proceso_actual_id;
		String url = Configuracion.IsAuthorizationDownload(proceso_actual_id);
				//Configuracion.getAuthorizationDownload( proceso_actual_id);
	    //String url =Configuracion.getAuthorizationDownload( String.valueOf(procesoid));
	    
	    StringRequest postRequest = new StringRequest(Request.Method.GET, url, 
	        new Response.Listener<String>(){
	            @Override
	            public void onResponse(String response) {	            	
	            	JSONObject obj;
					try {
						obj = new JSONObject(response);						
						if(obj.getBoolean("status")){
							download_pdf_exe();							
						}else{
							 Utils.showDialog(ItemAgendaActivity.this, "Se produjo un error! No se encuentra el archivo en el servidor");				               
						}
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						Utils.showDialog(ItemAgendaActivity.this, "Se produjo un error en la descarga del archivo");			               
					}           	
	            	
	            }
	        }, 
	        new Response.ErrorListener() 
	        {
	            @Override
	            public void onErrorResponse(VolleyError error) {
	                // TODO Auto-generated method stub
	                Log.d("ERROR","error => "+error.toString());
	                Utils.showDialog(ItemAgendaActivity.this, "Se produjo un error en la descarga del archivo");
	               
	            }
	        }
	    ) {     
	    	  @Override
	    	    protected Map<String,String> getParams(){
	    	        Map<String,String> params = new HashMap<String, String>();
	    	        params.put("Authorization", Usuario.getApi(ItemAgendaActivity.this));	    	       
	    	        return params;
	    	    }
	        @Override
	        public Map<String, String> getHeaders() throws AuthFailureError {
	        	 //ba4d1a5f35f8cf5600a74fa4e80a6fff 
	                Map<String, String>  params = new HashMap<String, String>();  
	                params.put("Authorization", Usuario.getApi(ItemAgendaActivity.this));
	                //params.put("Usuario", Usuario.getUsuarioActivo(MainActivity.this));
	                
	                return params;  
	        }
	        
	        
	    };        
	   
	    queue.add(postRequest);
	}
	 private void download_pdf_exe() {
		// TODO Auto-generated method stub
		 
		 new DownloadFile().execute(Configuracion.getAuthorizationDownload(String.valueOf(proceso_actual_id)), file_name); 
	}
	private static final int  MEGABYTE = 1024 * 1024;

	    public static void downloadFile(String fileUrl, File directory){
	        try {
	        	Util.Log("fileUrl=>conectandoce");
	            URL url = new URL(fileUrl);
	            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
	           
	            urlConnection.setReadTimeout(10000 /* milliseconds */);
	            urlConnection.setConnectTimeout(15000 /* milliseconds */);
	            urlConnection.setRequestMethod("GET");
	            urlConnection.setDoInput(true);
	            urlConnection.setAllowUserInteraction(false);
	            String authorization="0e9c1b1e026068b04e03c537f34fc83b";
	            urlConnection.addRequestProperty("Authorization", authorization);
	           
	            urlConnection.connect();
	            int responseCode = urlConnection.getResponseCode();
	            Util.Log("responseCode=>"+responseCode);
	            
	            InputStream inputStream = urlConnection.getInputStream();
	            FileOutputStream fileOutputStream = new FileOutputStream(directory);
	            int totalSize = urlConnection.getContentLength();
	            Util.Log("fileUrl-totalSize=>"+totalSize);
	            byte[] buffer = new byte[MEGABYTE];
	            int bufferLength = 0;
	            while((bufferLength = inputStream.read(buffer))>0 ){
	                fileOutputStream.write(buffer, 0, bufferLength);
	            }
	            Util.Log("fileUrl=>se descargo");
	            fileOutputStream.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    public  class DownloadFile extends AsyncTask<String, Void, Void>{
	    	
	        @Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				progress=new ProgressDialog(ItemAgendaActivity.this, ProgressDialog.THEME_HOLO_DARK);
			    progress.setMessage("Actualizando...");
			    progress.show();
			}

			@Override
	        protected Void doInBackground(String... strings) {
	         Util.Log("descargando...");
	            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
	            String fileName = strings[1];  // -> maven.pdf
	            Util.Log("fileUrl 1=>"+fileUrl);
	            //String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
	            String extStorageDirectory = dir_name;
	            	
	            try{
	            	
	                File folder = new File(extStorageDirectory,folder_name );
	 	            folder.mkdir();
	 	            Util.Log("fileUrl=>mkdir");
	 	            File pdfFile = new File(folder, fileName);
	            	
	                pdfFile.createNewFile();
	                downloadFile(fileUrl, pdfFile);
	                
	            	
	            	 downloadFile(fileUrl, folder);
	            }catch (IOException e){
	            	Util.Log("Error descargar=>"+e.getMessage());
	                //e.printStackTrace();
	            }
	            
	            
	            return null;
	        }

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				progress.dismiss();
				view_pdf();
			}
	        
	    }
	    private void setToolbar() {
	        
	    	if (getSupportActionBar() != null)
	            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	          getSupportActionBar().setHomeButtonEnabled(true);         
	       
	         
	      }
 public  void view_pdf() {
	    Intent intent = new Intent(this, MyPdfViewerActivity.class);
	    intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, getFilesDir()+folder_name+file_name);
	    startActivity(intent);
	    
	 
 }

	
	
}
