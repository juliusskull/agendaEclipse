package aguasdelnorte.com.ar.agenda2.ui;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.acl.Owner;
import java.util.ArrayList;

import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.NotificacionesAD;
import aguasdelnorte.com.ar.agenda2.clases.Categoria;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;

import android.app.AlertDialog;
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
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ListNotificacionesActivity extends ActionBarActivity {
	private static final int requestCode = 101;
	//private CustomAdapter adapter;
	private ListView list;
	public ArrayList<ObjectView> datos;
	public ArrayList<ObjectView> datos2;
	private NotificacionesAD elemento;
	private ListViewAdapterTipo adaptador;
	private ListView lstOpciones;
	private Gson gson = new Gson();
	private MenuItem menuItem;
	private ShareActionProvider provider;
	private SearchView searchView;
	public  ProgressDialog progress;
	public  AgendaItemClick onAgendaItemClick;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista);
		list =(ListView)findViewById(R.id.list2);
		setToolbar();
		elemento= new NotificacionesAD(this);
		elemento.ActualizarVistos();
		Tarea tarea = new Tarea();
		tarea.execute("");
		
	}
	
  

	private void setToolbar() {       
    	if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setHomeButtonEnabled(true);
         getSupportActionBar().setSubtitle("Notificaciones");       
         
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
			  datos=elemento.getObjectView();	
			 
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub			
			componentes_ini();
			super.onPostExecute(result);
		}
		
	}
	public void componentes_ini() {
		// TODO Auto-generated method stub
		Util.Log("datos:"+datos.size());
		adaptador = new ListViewAdapterTipo(this,datos);
		lstOpciones = (ListView)this.findViewById(R.id.list2);	
		lstOpciones.setAdapter(adaptador);
		
		try {
			menuItem.collapseActionView();
			menuItem.setActionView(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Util.Log("E:"+e.getMessage());
		}
		lstOpciones.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ObjectView o= (ObjectView)adaptador.getItem(position);
				volver(o);
			}});
	}
	public void volver(ObjectView o){	
		Intent intent= new Intent( this, ItemAgendaActivity.class );
		intent.putExtra("id", String.valueOf(o.getId()));
		Util.SpSet(this, Configuracion.getBiblio(), Configuracion.PROCESO_ACTUAL, String.valueOf(o.getId_proceso()));
		startActivityForResult(intent, requestCode);
	}

	public static void crearDialogoAlerta(Context  t, ObjectView o, LayoutInflater inf){	
		
		AlertDialog.Builder builder = DialogoItem.create(t, inf, o);			  
	    builder.setNegativeButton("cancelar", new OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	        }			
	    });
	    builder.setPositiveButton("OK", new OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        	
	        }

			
	    });
	    builder.show();	 
	  
	}
   

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:          
                this.finish();
                return true;
            case R.id.action_actualizar2:
              	menuItem = item;
                menuItem.setActionView(R.layout.barradeprogreso);
                menuItem.expandActionView();               
                Tarea tarea = new Tarea();
        		tarea.execute("");
                return true; 
            case R.id.action_search:
                return true;   
            case R.id.action_filter:
            	ot_tipo_ver();
                return true;    
            default:
                return super.onOptionsItemSelected(item);
        }
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {	
			getMenuInflater().inflate(R.menu.main2, menu);			
			 MenuItem searchItem = menu.findItem(R.id.action_search);			 
			 searchView =
			            (SearchView) MenuItemCompat.getActionView(searchItem);
			
			 searchView.setOnQueryTextListener(new OnQueryTextListener() {
				
				@Override
				public boolean onQueryTextSubmit(String arg0) {
					// TODO Auto-generated method stub
					Util.Log("TextSubmit:"+arg0 );
					return false;
				}
				
				@Override
				public boolean onQueryTextChange(String arg0) {
					// TODO Auto-generated method stub
					Util.Log("TextChange:"+arg0 );
					adaptador.filter(arg0);
					return false;
				}
			});
		
		return super.onCreateOptionsMenu(menu);
	}   
	
	public void filterShow() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);

			// set title
			alertDialogBuilder.setTitle("Filter");

			// set dialog message
			alertDialogBuilder
				.setMessage("Click yes to exit!")
				.setCancelable(false)
				.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						
					}
				  })
				.setNegativeButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, just close
						// the dialog box and do nothing
						dialog.cancel();
					}
				});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		
	public void ot_tipo_ver() {	
		datos2 = new ArrayList<ObjectView>(); 
		datos2.add(new ObjectView(0, "Ordenar Por fecha", "", R.drawable.ic_sort_by_alpha_white_48dp));  
		datos2.add(new ObjectView(1, "Buscar Fecha", "", R.drawable.ic_filter_none_white_48dp));  
		  
		
		ArrayAdapter<ObjectView> adapter = new ArrayAdapter<ObjectView>(
				this
				,
		    android.R.layout.select_dialog_item,
		    android.R.id.text1,datos2
		    ){
		        public View getView(int position, View convertView, ViewGroup parent) {
		            //Use super class to create the View
		            View v = super.getView(position, convertView, parent);
		            TextView tv = (TextView)v.findViewById(android.R.id.text1);

		            //Put the image on the TextView
		            tv.setCompoundDrawablesWithIntrinsicBounds(((ObjectView)datos2.get(position)).getDrableid(), 0, 0, 0);

		            //Add margin between image and text (support various screen densities)
		            int dp5 = (int) (5 * getResources().getDisplayMetrics().density + 0.5f);
		            tv.setCompoundDrawablePadding(dp5);

		            return v;
		        }
		    };
		    
		    LayoutInflater inflater = getLayoutInflater();
		    //View dialoglayout = inflater.inflate(R.layout.fondo_main, (ViewGroup) getCurrentFocus());
		   
		    new AlertDialog.Builder(this)
		    /*.setView(dialoglayout)*/
		    .setTitle("Filtrar por")		    
		    .setAdapter(adapter, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int item) {
		            validar(item);
		        }
		    }).show();
}

	protected void validar(int item) {
		// TODO Auto-generated method stub
		
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
				progress=new ProgressDialog(ListNotificacionesActivity.this, ProgressDialog.THEME_HOLO_DARK);
			    progress.setMessage("Actualizando...");
			    progress.show();
			}

			@Override
	        protected Void doInBackground(String... strings) {
	         Util.Log("descargando...");
	            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
	            String fileName = strings[1];  // -> maven.pdf
	            Util.Log("fileUrl 1=>"+fileUrl);
	            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
	            File folder = new File(extStorageDirectory, "testthreepdf");
	            folder.mkdir();
	            Util.Log("fileUrl=>mkdir");
	            File pdfFile = new File(folder, fileName);
	            	
	            try{
	                pdfFile.createNewFile();
	            }catch (IOException e){
	            	Util.Log("Error descargar=>"+e.getMessage());
	                //e.printStackTrace();
	            }
	            downloadFile(fileUrl, pdfFile);
	            
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
    public  void view_pdf() {
    	Util.Log("Ver PDF");
    	File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "maven_2.pdf");  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        

        try{
        	Util.Log("Ver PDF- abrir");
        	Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
        }
		
	}
    public  void download_pdf() {
    	new DownloadFile().execute(Configuracion.getAuthorizationDownload("123"), "maven_2.pdf"); 
		
	}
	public interface AgendaItemClick {
		  void OnButton(int key);
	}
	public AgendaItemClick getOnAgendaItemClick() {
		return onAgendaItemClick;
	}

	public void setOnAgendaItemClick(AgendaItemClick onAgendaItemClick) {
		this.onAgendaItemClick = onAgendaItemClick;
	}
	@Override
	public void onActivityResult(int requestCode1, int
			resultCode, Intent data) {
		Tarea tarea = new Tarea();
		tarea.execute("");
	  }
}
