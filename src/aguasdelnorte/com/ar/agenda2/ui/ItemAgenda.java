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

import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class ItemAgenda extends Fragment {
	private ListView list;
	private Agenda_pendienteAD elemento;
	public ProgressDialog progress;
	private ArrayList<ObjectView> datos;
	private AdapterItemCampo adaptador;
	private String Itemid;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.agenda_item, container,
				false);
		return rootView;
	}
	  @Override
	  public void onActivityCreated(Bundle state) {
	        super.onActivityCreated(state);
	        list =(ListView)getActivity().findViewById(R.id.list_agenda_item);
			//setToolbar();
			elemento= new Agenda_pendienteAD(getActivity());
			Itemid = getActivity().getIntent().getExtras().getString("id"); //String.valueOf(this.getIntent().getExtras().getInt("id"));
			setItemid(Itemid);
			
			elemento.load(Itemid);
			datos= elemento.getCamposObjectView();
			adaptador = new AdapterItemCampo(getActivity(),datos);
			list.setAdapter(adaptador);
			Util.Log("A:"+elemento.toString());
	       
	  }
	  
	  public void alClickearBotonAdd(View v) {
			download_pdf();
		}
	
		 private void download_pdf() {
			// TODO Auto-generated method stub
			 new DownloadFile().execute(Configuracion.getAuthorizationDownload(String.valueOf(elemento.getIdproceso())), "maven_3.pdf"); 
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
					progress=new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_DARK);
				    progress.setMessage("Actualizando...");
				    progress.show();
				}

				@Override
		        protected Void doInBackground(String... strings) {
		         Util.Log("descargando...");
		            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
		            String fileName = strings[1];  // -> maven.pdf
		            Util.Log("fileUrl 1=>"+fileUrl);
		           // String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
		            
		            //File folder = new File(extStorageDirectory, "testthreepdf");
		            File folder = new File(getContextFile(), "testthreepdf");
		            
		            folder.mkdir();
		            Util.Log("fileUrl=>mkdir");
		            Util.Log("fileUrl=>"+folder.getAbsolutePath());
		            Util.Log("fileUrl name=>"+fileName);
		            File pdfFile = new File(folder, fileName);
		            	
		            try{
		                pdfFile.createNewFile();
		            }catch (IOException e){
		            	Util.Log("Error descargar=>"+e.getMessage());
		                //e.printStackTrace();
		            }
		            Util.Log("fileUrl => ini descarga");
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
		    
	 public String getItemid() {
				return Itemid;
				
			}
			public File getContextFile() {
		// TODO Auto-generated method stub
						return getActivity().getFilesDir(); 
				}
			public void setItemid(String id) {
				this.Itemid = id;
			}
	public  void view_pdf() {
	 	Util.Log("Ver PDF");
	 	//File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "maven_2.pdf");  // -> filename = maven.pdf
	 	File pdfFile = new File(getContextFile().getAbsolutePath() + "/testthreepdf/" + "maven_3.pdf");  // -> filename = maven.pdf
	 	Util.Log("fileUrl-pdf=>"+pdfFile.getAbsolutePath());
	 	Uri path = Uri.fromFile(pdfFile);
	     

	     try{
	     	Util.Log("fileUrl=>Ver PDF- abrir");
	     	Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
	        pdfIntent.setDataAndType(path, "application/pdf");
	        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	         
	        startActivity(pdfIntent);
	     }catch(ActivityNotFoundException e){
	         Toast.makeText(getActivity(), "No Application available to view PDF", Toast.LENGTH_SHORT).show();
	     }
	 }
	public Agenda_pendienteAD getElemento() {
		return elemento;
	}
	public void setElemento(Agenda_pendienteAD elemento) {
		this.elemento = elemento;
	}
	
}
