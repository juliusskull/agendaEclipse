package aguasdelnorte.com.ar.agenda2.ui;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Util {
	public static void copy(File src, File dst) throws IOException {
	    InputStream in = new FileInputStream(src);
	    OutputStream out = new FileOutputStream(dst);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	}
	public static String justifyText(String text) {
		String header = "<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\"></head>"
				+ "<p style=\"text-align: justify;\">";
		String footer = "</p></html>";
		return header + text + footer;
	}
	public static String CambiarFecha(String fch){
		String fch_salida="";
		if(!fch.equals("0000-00-00")){
		try {
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			date = dt.parse(fch);
			SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy"); 		       
			fch_salida=dt1.format(date);	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}else{
			 fch_salida="00-00-0000";
			
		}
		return fch_salida;
		
	}
	
	public static String priceWithDecimal (Double price) {
	    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
	    return formatter.format(price);
	}

	public static String formatDecimal(float number) {
		  float epsilon = 0.004f; // 4 tenths of a cent
		  if (Math.abs(Math.round(number) - number) < epsilon) {
		     return String.format("%10.0f", number); // sdb
		  } else {
		     return String.format("%10.2f", number); // dj_segfault
		  }
		}
	public static void hideActionBar(ActionBarActivity context) {
		context.getSupportActionBar().hide();
	}
	public static void hideActionBar(FragmentActivity context) {
		context.getActionBar().hide();
	}
	
	public static Bitmap getBitmapFromAsset(Context context, String filePath) {
	    AssetManager assetManager = context.getAssets();

	    InputStream istr;
	    Bitmap bitmap = null;
	    try {
	        istr = assetManager.open(filePath);
	        bitmap = BitmapFactory.decodeStream(istr);
	    } catch (IOException e) {
	        // handle exception
	    }

	    return bitmap;
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
	private boolean unpackZip(String path, String zipname)
	{       
	     InputStream is;
	     ZipInputStream zis;
	     try 
	     {
	         is = new FileInputStream(path + zipname);
	         zis = new ZipInputStream(new BufferedInputStream(is));          
	         ZipEntry ze;

	         while((ze = zis.getNextEntry()) != null) 
	         {
	             ByteArrayOutputStream baos = new ByteArrayOutputStream();
	             byte[] buffer = new byte[1024];
	             int count;

	             String filename = ze.getName();
	             FileOutputStream fout = new FileOutputStream(path + filename);

	             // reading and writing
	             while((count = zis.read(buffer)) != -1) 
	             {
	                 baos.write(buffer, 0, count);
	                 byte[] bytes = baos.toByteArray();
	                 fout.write(bytes);             
	                 baos.reset();
	             }

	             fout.close();               
	             zis.closeEntry();
	         }

	         zis.close();
	     } 
	     catch(IOException e)
	     {
	         e.printStackTrace();
	         return false;
	     }

	    return true;
	}
	
	 public static void CopyStream(InputStream is, OutputStream os)
	    {
	        final int buffer_size=1024;
	        try
	        {
	            byte[] bytes=new byte[buffer_size];
	            for(;;)
	            {
	              int count=is.read(bytes, 0, buffer_size);
	              if(count==-1)
	                  break;
	              os.write(bytes, 0, count);
	            }
	        }
	        catch(Exception ex){}
	    }
	public static void Log(Activity a,String msg){
		Toast.makeText(a, msg,	Toast.LENGTH_LONG).show();
	}
	public static void Log(String msg){
		android.util.Log.v("Agenda", msg);
	}
	public static void Log2(Activity a,String msg){
		android.util.Log.v(a.getLocalClassName(), msg);
	}
	public static Date sumaDias(Date fecha, int dias){ 
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(fecha); 
		cal.add(Calendar.DAY_OF_YEAR, dias); 
		return cal.getTime(); 
		} 
	public static void crearDialogoAlerta(Context  t, String titulo, String mesage, LayoutInflater inf)
	{
		
		
		AlertDialog.Builder builder = MyDialogo.create(t, inf, titulo, mesage);
			  
	    builder.setNegativeButton("OK", new OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	        }

			
	    });
	    builder.show();	 
	  
	}
	public static void crearDialogoAlerta2(Context  t, String titulo, String mesage, OnClickListener oNegativeButton, OnClickListener oPositiveButton, LayoutInflater inf)
	{	
		AlertDialog.Builder builder = MyDialogo.create(t, inf, titulo, mesage);//new AlertDialog.Builder(t);		
	    builder.setNegativeButton("Cancelar",oNegativeButton);
	    builder.setPositiveButton("Aceptar",oPositiveButton);
	    builder.show();	    
	 
	  
	}
	public static void crearDialogoAlerta3(Context  t, String titulo, String mesage, OnClickListener oPositiveButton, LayoutInflater inf)
	{	
		AlertDialog.Builder builder = MyDialogo.create(t, inf, titulo, mesage);//new AlertDialog.Builder(t);		
	    builder.setNegativeButton("Cancelar",new OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	            dialog.cancel();
	        }

			
	    });
	    builder.setPositiveButton("Aceptar",oPositiveButton);
	    builder.show();	    
	 
	  
	}	public static String primeraletraMayuscula(String s){
		String salida="";
		String[] result = s.split(" ");
		try {
			for(int i=0;i<result.length;i++){
				if(result[i].trim().length()>0){
				salida+=result[i].substring(0, 1).toUpperCase()+result[i].substring(1)+" ";
				}
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("primeraletraMayuscula","e:"+e.getMessage());
			//e.printStackTrace();
		}
		return salida;
	}
	public static void readStream(InputStream in) {
		  BufferedReader reader = null;
		  try {
		    reader = new BufferedReader(new InputStreamReader(in));
		    String line = "";
		    while ((line = reader.readLine()) != null) {
		     Log.v("util:readstream",line);
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
		  } finally {
		    if (reader != null) {
		      try {
		        reader.close();
		      } catch (IOException e) {
		        e.printStackTrace();
		        }
		    }
		  }
		} 
	public static void escrbir_sd(String salida){
		boolean sdDisponible = false;
		boolean sdAccesoEscritura = false;
		
		//Comprobamos el estado de la memoria externa (tarjeta SD)
		String estado = Environment.getExternalStorageState();

		if (estado.equals(Environment.MEDIA_MOUNTED))
		{
			sdDisponible = true;
			sdAccesoEscritura = true;
		} 
		else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
		{
			sdDisponible = true;
			sdAccesoEscritura = false;
		} 
		else 
		{
			sdDisponible = false;
			sdAccesoEscritura = false;
		}
		
		//Si la memoria externa está disponible y se puede escribir
		if (sdDisponible && sdAccesoEscritura)
		{
			try
			{
				File ruta_sd = Environment.getExternalStorageDirectory();
				
				File f = new File(ruta_sd.getAbsolutePath(), "k_"+getDateNow()+".txt");
				
				OutputStreamWriter fout = 
					new OutputStreamWriter(
							new FileOutputStream(f));
				
				fout.write(salida);
			
				fout.close();
				
				
			}
			catch (Exception ex)
			{
				Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
			}
		}
	
	}
	public static void escrbir_sd_csv(String salida){
		boolean sdDisponible = false;
		boolean sdAccesoEscritura = false;
		
		//Comprobamos el estado de la memoria externa (tarjeta SD)
		String estado = Environment.getExternalStorageState();

		if (estado.equals(Environment.MEDIA_MOUNTED))
		{
			sdDisponible = true;
			sdAccesoEscritura = true;
		} 
		else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY))
		{
			sdDisponible = true;
			sdAccesoEscritura = false;
		} 
		else 
		{
			sdDisponible = false;
			sdAccesoEscritura = false;
		}
		
		//Si la memoria externa está disponible y se puede escribir
		if (sdDisponible && sdAccesoEscritura)
		{
			try
			{
				File ruta_sd = Environment.getExternalStorageDirectory();
				
				File f = new File(ruta_sd.getAbsolutePath(), "k_"+getDateNow()+".csv");
				
				OutputStreamWriter fout = 
					new OutputStreamWriter(
							new FileOutputStream(f));
				
				fout.write(salida);
			
				fout.close();
				
				
			}
			catch (Exception ex)
			{
				Log.e("Ficheros", "Error al escribir fichero a tarjeta SD");
			}
		}
	
	}
	//----------------------------validaciones
	 private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
	            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	 
	    /**
	     * Validate given email with regular expression.
	     * 
	     * @param email
	     *            email for validation
	     * @return true valid email, otherwise false
	     */
	   /* public static boolean validateEmail(String email) {
	 
	        // Compiles the given regular expression into a pattern.
	        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
	 
	        // Match the given input against this pattern
	        Matcher matcher = pattern.matcher(email);
	        return matcher.matches();
	    	if (email == null) {
	            return false;
	        } else {
	            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	        }
	 
	    }*/
	    public static boolean validateEmail(String email)
	    {
	    	
	        boolean isValidEmail = false;
	        
	      
	        String emailExpression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	        CharSequence inputStr = email;
	        

	        Pattern pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE);
	        Matcher matcher = pattern.matcher(inputStr);
	        if (matcher.matches())
	        {
	            isValidEmail = true;
	        }
	        return isValidEmail;
	    	
	    }
	    public static boolean isValidarNombre(String theString, int cant_min){
	    	// cant_min es la cantidad minima de la cadena 
	    	String nombre=theString.toUpperCase().replace(" ", "");
	    	return nombre.matches("([a-z]|[A-Z]|[0-9]|\\s)+"); //nombre.matches("[A-Z][a-zA-Z]*\\D{"+cant_min+"}");
	         	
	    	
	    }
	    public static boolean isNumeric(String s) {  
	        //return s.matches("[-+]?\\d*\\.?\\d+");  
	    	return s.matches("d*\\.?\\d+");
	    } 
	
	    //----------------------------------------------------------------
	    
	    public static String getDateNow(){
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			Log.v("",dateFormat.format(date));
			return dateFormat.format(date);
	    }
	    public static String getDateNowFormat(){
	    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			Log.v("",dateFormat.format(date));
			return dateFormat.format(date);
	    }
	    public static String getMesName(int d){
	    	
			String s="";
			switch (d) {
			case 1:
				s= "Ene";
				break;
			case 2:
				s= "Feb";
				break;
			case 3:
				s= "Mar";
				break;
			case 4:
				s= "Abr";
				break;
			case 5:
				s= "May";
				break;
			case 6:
				s= "Jun";
				break;
			case 7:
				s= "Jul";
				break;
			case 8:
				s= "Ago";
				break;
			case 9:
				s= "Sep";
				break;	
			case 10:
				s= "Oct";
				break;
			case 11:
				s= "Nov";
				break;
			case 12:
					s= "Dic";
					break;
			default:
				break;
			}
			return s;
	    }
	    
	    public static String getSemanaName(int d){
	    	
			String s="";
			switch (d) {
			case 1:
				s= "Domingo";
				break;
			case 2:
				s= "Lunes";
				break;
			case 3:
				s= "Martes";
				break;
			case 4:
				s= "Miercoles";
				break;
			case 5:
				s= "Jueves";
				break;
			case 6:
				s= "Viernes";
				break;
			case 7:
				s= "Sabado";
				break;
		
			default:
				break;
			}
			return s;
	    }
		public static String getFechaFormat(int selectedYear,
				int selectedMonth, int selectedDay) {
		int year = selectedYear;
		int month = selectedMonth;
		int day = selectedDay;
		Calendar c = Calendar.getInstance();
		c.set(selectedYear, selectedMonth, selectedDay);
		int dia_s=c.get(Calendar.DAY_OF_WEEK);
		return Util.getSemanaName(dia_s)+", "+new StringBuilder().append(day)
				.append("/").append(month + 1).append("/").append(year)
				.append(" ");
		
	}
		public static String getFechaFormat3(int selectedYear,
				int selectedMonth, int selectedDay) {
		int year = selectedYear;
		int month = selectedMonth;
		int day = selectedDay;
		Calendar c = Calendar.getInstance();
	
		c.set(selectedYear, selectedMonth, selectedDay);
		int dia_s=c.get(Calendar.DAY_OF_WEEK);
		
		Log.v("util", "s:"+dia_s);
		return Util.getSemanaName(dia_s)+", "+new StringBuilder().append(day)
				.append("/").append(month).append("/").append(year)
				.append(" ");
		
	}
		public static String getFechaFormat2(int selectedYear,
				int selectedMonth, int selectedDay) {
		int year = selectedYear;
		int month = selectedMonth;
		int day = selectedDay;
		Calendar c = Calendar.getInstance();
		c.set(selectedYear, selectedMonth, selectedDay);
		int dia_s=c.get(Calendar.DAY_OF_WEEK);
		return Util.getSemanaName(dia_s)+", "+new StringBuilder().append(day)
				.append("/").append(month + 1).append("/").append(year)
				.append(" ");
		
	}
		public static void call(Activity activity,String telefono){
			   Intent intent = new Intent(Intent.ACTION_CALL);
			   intent.setData(Uri.parse(telefono));
			   activity.startActivity(intent);
			}
		public static String getJsonArrayResult(String readTwitterFeed){
			try {
				Log.v("","empieza la opne json");
				 //JSONObject object = (JSONObject) new JSONTokener(readTwitterFeed).nextValue();
				// JSONObject object = new JSONObject(readTwitterFeed);
				 JSONObject object =  getjson(readTwitterFeed);
				 if (object!=null){
				 JSONArray locations = object.getJSONArray("results");
				 Log.v("j","R="+locations.getString(0));
				 Log.v("j","cant:"+locations.length()); 
				 
				 JSONObject jsonObject = locations.getJSONObject(0); 
				 Log.v("j","descarga "+jsonObject.getString("formatted_address"));
				 return	 jsonObject.getString("formatted_address");
				
				 }
			} catch (JSONException  e) {
				Log.v("","error");
				Log.v("","error cadena json");		
				return "";
				
				
			}
			return "";
		}

		public static String readTwitterFeed(String url) {
			Log.v("","empieza la descarga json");
			StringBuilder builder = new StringBuilder();
			HttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
				} else {
					Log.e("json", "Fallo la descarga");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return builder.toString();
		}
		public static JSONObject getjson(String readTwitterFeed){
			Log.v("","c:"+readTwitterFeed);
			JSONObject object =null;
			try {
				object = (JSONObject) new JSONTokener(readTwitterFeed).nextValue();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 try {
					object = new JSONObject(readTwitterFeed);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			return object;
		}
		public static void getJsonArray(String readTwitterFeed){
			try {
				Log.v("","empieza la opne json");
				 //JSONObject object = (JSONObject) new JSONTokener(readTwitterFeed).nextValue();
				// JSONObject object = new JSONObject(readTwitterFeed);
				 JSONObject object =  getjson(readTwitterFeed);
				 if (object!=null){
				 JSONArray locations = object.getJSONArray("results");
				 Log.v("j","R="+locations.getString(0));
				 Log.v("j","cant:"+locations.length()); 
				 
				 JSONObject jsonObject = locations.getJSONObject(0); 
				 Log.v("j","descarga "+jsonObject.getString("formatted_address"));
					 
				
				 }
			} catch (JSONException  e) {
				Log.v("","error");
				Log.v("","error cadena json");		
				
				
				
			}
		}
		public static String addAdressToUrl(String url, String address){
	        if(!url.endsWith("?"))
	            url += "?";

	        List<NameValuePair> params = new LinkedList<NameValuePair>();


	        params.add(new BasicNameValuePair("address", address));

	        String paramString = URLEncodedUtils.format(params, "utf-8");
	        url += paramString+"&sensor=true";
	        return url;
	        }
		public static String addLocationToUrl(String url, double lat,double lon ){
	    	    if(!url.endsWith("?"))
	    	        url += "?";

	    	    List<NameValuePair> params = new LinkedList<NameValuePair>();

	    	    if (lat != 0.0 && lon != 0.0){
	    	        params.add(new BasicNameValuePair("latlng", String.valueOf(lat)+","+String.valueOf(lon)));
	    	        //params.add(new BasicNameValuePair("lon", String.valueOf(lon)));
	    	    }

	    	  
	    	    //params.add(new BasicNameValuePair("user", agent.uniqueId));

	    	    String paramString = URLEncodedUtils.format(params, "utf-8");

	    	    url += paramString;
	    	    return url+"&sensor=true";
	    	}
		  public static String SpGet(Activity a, String biblio,String clave){
			  try {
				SharedPreferences prefs =
						  a.getSharedPreferences(biblio,Context.MODE_PRIVATE);
						  String pclave = prefs.getString(clave,"");
				  return pclave;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.v("Configuracion", "error SP");
				return "";
			}
		  }
		  public static boolean SpGetB(Activity a, String biblio,String clave){
			  try {
				SharedPreferences prefs =
						  a.getSharedPreferences(biblio,Context.MODE_PRIVATE);
						  boolean pclave = prefs.getBoolean(clave,false);
				  return pclave;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.v("Configuracion", "error SP");
				return false;
			}
		  }
		  public static void SpSet(Activity a, String biblio,String clave, String valor){
			  SharedPreferences prefs =
					  a.getSharedPreferences(biblio,Context.MODE_PRIVATE);
					  SharedPreferences.Editor editor = prefs.edit();
					  editor.putString(clave,valor);
					  
					  editor.commit();
		  }
		  public static void SpSetB(Activity a, String biblio,String clave, boolean valor){
			  SharedPreferences prefs =
					  a.getSharedPreferences(biblio,Context.MODE_PRIVATE);
					  SharedPreferences.Editor editor = prefs.edit();
					  editor.putBoolean(clave,valor);
					  
					  editor.commit();
		  }
		  
	 public static String getClient(String url){
    	HttpGet request = new HttpGet(url);
    	
    	HttpClient client = new DefaultHttpClient();
    	HttpResponse httpResponse;
    	int responseCode;
    	String message;
    	try {
    	httpResponse = client.execute(request);//Bloqueante!!!!!
    	responseCode = httpResponse.getStatusLine().getStatusCode();
    	message = httpResponse.getStatusLine().getReasonPhrase();
    	HttpEntity entity = httpResponse.getEntity();
    	if (entity != null) {
    	String response = EntityUtils.toString(entity);
    	return response;
    	}else{
    	return null;
    	}
    	} catch (ClientProtocolException e) {
    	e.printStackTrace();
    	} catch (IOException e) {
    	e.printStackTrace();
    	}return null; 
    	}
		      
		      public static boolean isOnline(Context contex) {
		    	    ConnectivityManager cm = 
		    	         (ConnectivityManager) contex.getSystemService(Context.CONNECTIVITY_SERVICE);
		    	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
		    	    if (netInfo != null && netInfo.isConnected()) {
		    	        return true;
		    	    }
		    	    return false;
		    	}
		      
		      private static final Pattern DOUBLE_PATTERN = Pattern.compile(
		    		    "[\\x00-\\x20]*[+-]?(NaN|Infinity|((((\\p{Digit}+)(\\.)?((\\p{Digit}+)?)" +
		    		    "([eE][+-]?(\\p{Digit}+))?)|(\\.((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|" +
		    		    "(((0[xX](\\p{XDigit}+)(\\.)?)|(0[xX](\\p{XDigit}+)?(\\.)(\\p{XDigit}+)))" +
		    		    "[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*");

		      public static boolean isFloat(String s)
		      {
		    		    return DOUBLE_PATTERN.matcher(s).matches();
		      }
		      public static double parseDouble(String text) {
		    	  double value=0;
		    	  
		    	  try {
					value = Double.parseDouble(text);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Util.Log("Error double "+text);
				}
		    	  
		    	  return value;
			}
		  	public static void deleteCache(Context context) {
			    try {
			        File dir = context.getCacheDir();
			        if (dir != null && dir.isDirectory()) {
			            deleteDir(dir);
			        }
			    } catch (Exception e) {}
			}

			public static boolean deleteDir(File dir) {
			    if (dir != null && dir.isDirectory()) {
			        String[] children = dir.list();
			        for (int i = 0; i < children.length; i++) {
			            boolean success = deleteDir(new File(dir, children[i]));
			            if (!success) {
			                return false;
			            }
			        }
			    }
			    return dir.delete();
			}
			public static String SpGet(Context context, String biblio,String clave, String por_defecto) {
				// TODO Auto-generated method stub
				 try {
						SharedPreferences prefs =
								  context.getSharedPreferences(biblio,Context.MODE_PRIVATE);
								  String pclave = prefs.getString(clave,por_defecto);
						  return pclave;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Log.v("Configuracion", "error SP");
						return "";
					}
			}
			public static String getAppVersion(Context context) {
				String versionCode="";
				try {
					 versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				return versionCode;
				
			}
			  public static String getHoraActual() {
			        Date ahora = new Date();
			        SimpleDateFormat formateador = new SimpleDateFormat("HHmmss");
			        return formateador.format(ahora);
			    }
			  public static String getHoraActualFormat() {
			        Date ahora = new Date();
			        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm:ss");
			        return formateador.format(ahora);
			    }
				 public static String getFechaActualFormat() {
				        Date ahora = new Date();
				        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
				        return formateador.format(ahora);
				    }
				 public static String getFechaActual() {
				        Date ahora = new Date();
				        SimpleDateFormat formateador = new SimpleDateFormat("ddMMyyyy");
				        return formateador.format(ahora);
				    }
				 public static String getIMEI(Context context) {
					 TelephonyManager mngr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
					 return mngr.getDeviceId();
					  
				}
				 
				    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {  
				        //double earthRadius = 3958.75;//miles  
				        double earthRadius = 6371;//kilometers  
				        double dLat = Math.toRadians(lat2 - lat1);  
				        double dLng = Math.toRadians(lng2 - lng1);  
				        double sindLat = Math.sin(dLat / 2);  
				        double sindLng = Math.sin(dLng / 2);  
				        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
				                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
				        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));  
				        double dist = earthRadius * c;  
				  
				        return dist;  
				    } 
				    
				    public static String UrlParse(String e) {
				    	String a="";
				    	try {
							a=URLEncoder.encode(e, "UTF-8");
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
						}
				    	return a;
					}
				    
					public static long getDiasHastaHoy(String string) {
						// TODO Auto-generated method stub
						
						final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
						java.util.Date hoy = new Date(); //Fecha de hoy 
						String[] x=string.split("-");     
						int año = Integer.parseInt(x[0]); int mes =  Integer.parseInt(x[1]); int dia =  Integer.parseInt(x[2]); //Fecha anterior 
						Calendar calendar = new GregorianCalendar(año, mes-1, dia); 
						java.sql.Date fecha = new java.sql.Date(calendar.getTimeInMillis());

						long diferencia = ( hoy.getTime() - fecha.getTime() )/MILLSECS_PER_DAY;
						return diferencia;
					}
					public static boolean FiltroPalabrasOfencivas(String p1) {
						 String p=p1;
						 String [] array={"puta","puto","choto","chota","idiota","Pija","verga","chota","poronga","chorizo","Maraca","trabuco","maricon"
								 ,"trolo","brisco","Tortillera","trola","Boludo","pelotudo","pajero","nabo"
								 ,"Croto","Tetas","Culos","ojete","orto","coger","garchar","curtir"
								 ,"empernar","fifar","montar","Culear","Paja","sorete","tereso","cagada","Meada"}; 
						 if(p.length()==0){						 
							 return true;
						 }
						for (int i = 0; i < array.length; i++) {
							if(p.toUpperCase().indexOf(array[i].toUpperCase())!=-1){
								return false;							
							};
							if(p.toUpperCase().indexOf(array[i].toUpperCase()+"S")!=-1){
								return false;							
							};
						}
						
						return true;
						
					}
					
				
}
