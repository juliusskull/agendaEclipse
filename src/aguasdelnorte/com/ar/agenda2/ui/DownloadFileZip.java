package aguasdelnorte.com.ar.agenda2.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadFileZip extends AsyncTask<String, Void, Void>{

	private Context context;
	private File folder;
	private File pdfFile;
	private String name_folder_desc="prueba";
	private String foldeName="recorrido2";
	private ProgressDialog progress;
	private String fileName;
	private String fileUrl;
	private boolean zipexp=true;
	  @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progress=new ProgressDialog(context, ProgressDialog.THEME_HOLO_DARK);
		    progress.setMessage("Actualizando...");
		    progress.show();
		}
	public DownloadFileZip(Context context) {
		super();
		this.context=context;
		zipexp=true;
		Log.v("DownloadFile","DownloadFile=>inicio");
		// TODO Auto-generated constructor stub
	}
	public DownloadFileZip(Context context,boolean zipexp) {
		super();
		this.context=context;
		this.zipexp=zipexp;
		Log.v("DownloadFile","DownloadFile=>inicio");
		// TODO Auto-generated constructor stub
	}

	@Override
    protected Void doInBackground(String... strings) {
     Log.v("DownloadFile","descargando...");
        fileUrl  = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
        fileName = strings[1];  // -> maven.pdf
        foldeName= strings[2];
        Log.v("DownloadFile","fileUrl 1=>"+fileUrl);        
        folder = new File(context.getFilesDir(), foldeName);
        Log.v("DownloadFile","DownloadFile=>"+folder.getAbsolutePath());
        
        folder.mkdir();
        Log.v("DownloadFile","fileUrl=>mkdir");
        pdfFile = new File(folder, fileName);
        	
        try{
            pdfFile.createNewFile();
        }catch (IOException e){
        	Log.v("DownloadFile","Error descargar=>"+e.getMessage());
            //e.printStackTrace();
        }
        DownloadFile(fileUrl, pdfFile);
        if(zipexp){        	
        	unzip_exec();
        }
        return null;
    }
	private void unzip_exec() {
		Log.v("DownloadFile","DownloadFile=>descompresion iniciada");	
		  try {
				unzip(pdfFile, folder);					
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Log.v("DownloadFile","DownloadFile=>error en descompresion");
				Log.v("DownloadFile","DownloadFile=>error:"+e.getMessage());
				
			}
		
	}
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(zipexp){			
			pdfFile.delete();
		}
		progress.dismiss();
		
	}
	
	public static void unzip(File zipFile, File targetDirectory) throws IOException {
	    ZipInputStream zis = new ZipInputStream(
	            new BufferedInputStream(new FileInputStream(zipFile)));
	   
	    try {
	        ZipEntry ze;
	        int count;
	        byte[] buffer = new byte[8192];
	        while ((ze = zis.getNextEntry()) != null) {
	            File file = new File(targetDirectory, ze.getName());
	            File dir = ze.isDirectory() ? file : file.getParentFile();
	            if (!dir.isDirectory() && !dir.mkdirs())
	                throw new FileNotFoundException("Failed to ensure directory: " +
	                        dir.getAbsolutePath());
	            if (ze.isDirectory())
	                continue;
	            FileOutputStream fout = new FileOutputStream(file);
	            try {
	                while ((count = zis.read(buffer)) != -1)
	                    fout.write(buffer, 0, count);
	            } finally {
	                fout.close();
	            }
	            /* if time should be restored as well
	            long time = ze.getTime();
	            if (time > 0)
	                file.setLastModified(time);
	            */
	        }
	    } finally {
	        zis.close();
	    }
	}
	public static Boolean DownloadFile(String fileURL, File directory) {
        try {

                FileOutputStream f = new FileOutputStream(directory);
                URL u = new URL(fileURL);
                HttpURLConnection c = (HttpURLConnection) u.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();

                InputStream in = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = in.read(buffer)) > 0) {
                        f.write(buffer, 0, len1);
                }
                f.close();
                Log.v("DownloadFile","DownloadFile=>Finalizada");
                return true;
        } catch (Exception e) {
                e.printStackTrace();
                Log.v("DownloadFile","DownloadFile=>Error en la descarga");
                return false;
        }
}

}
