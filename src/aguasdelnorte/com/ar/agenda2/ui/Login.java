package aguasdelnorte.com.ar.agenda2.ui;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import aguasdelnorte.com.ar.agenda2.clases.Elemento;
import aguasdelnorte.com.ar.agenda2.clases.Usuario;
import aguasdelnorte.com.ar.agenda2.custom.CustomActivity;
import aguasdelnorte.com.ar.agenda2.provider.ElementoAD;
import aguasdelnorte.com.ar.agenda2.utils.Utils;
import aguasdelnorte.com.ar.agenda2.web.Agenda_pendiente;
import aguasdelnorte.com.ar.agenda2.web.Agenda_pendienteLista;
import aguasdelnorte.com.ar.agenda2.web.UsuarioLista;
import aguasdelnorte.com.ar.agenda2.web.UsuarioWeb;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * The Class Login is an Activity class that shows the login screen to users.
 * The current implementation simply includes the options for Login and button
 * for Register. On login button click, it sends the Login details to Parse
 * server to verify user.
 */
public class Login extends CustomActivity
{
	private EditText user;
	private EditText pwd;
	public ProgressDialog progress;
	public Gson gson;
	private ImageView img;
	private Bitmap p_img;
	private File file;
	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	 public void borrar_anterior() {
		  ElementoAD.DELETE_DB(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		setTouchNClick(R.id.btnLogin);
		//setTouchNClick(R.id.btnReg);
		//Usuario.desloguearce(this);
		Util.hideActionBar(this);
		/*
		Log.v("agenda_pendiente", "=>setCursor ini");
		Agenda_pendienteAD ppp= new Agenda_pendienteAD(this);
		ppp.load("180");
		Log.v("agenda_pendiente", "=>setCursor fin");
		*/
		user = (EditText) findViewById(R.id.user);
		pwd = (EditText) findViewById(R.id.pwd);
		img= (ImageView) findViewById(R.id.imgDefoult);		
		
		user.setOnFocusChangeListener( new OnFocusChangeListener() {			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				 if (!hasFocus) {
					 if(user.getText().toString().indexOf("@")==-1){						 
						 user.setText(user.getText().toString()+"@cosaysa.com.ar");
						 user.setText(user.getText().toString().replace(" ", "_"));
					 }
					 user.setText(user.getText().toString().toLowerCase());
		           }
			}
		});		
		//requestLoguin();
	}

	/* (non-Javadoc)
	 * @see com.chatt.custom.CustomActivity#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		super.onClick(v);
		if (false /*v.getId() == R.id.btnReg*/){
			Intent intent = getIntent();
			setResult(RESULT_CANCELED, intent);
			finish();			
		}
		else
		{ 			
			Usuario usu= new Usuario();
			usu.load(this);
			if (usu.getClaveapi().length()>0){
				if(usu.correo.equals(user.getText().toString())){				
					if(usu.pass.equals(pwd.getText().toString())){					
						Util.Log(Login.this, "Bienvenido");									
						volver();
					}else{
						Utils.showDialog(
								Login.this,
								  "Error:  No se pudo conectar"
										);					
					}
				}else{			
					requestLoguin();
				}
				
			}else{
				requestLoguin();
				
			}
		
			
		}
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 10 && resultCode == RESULT_OK)
			finish();

	}
	
	class Tarea1 extends  AsyncTask<String,String,String>{
	   	boolean status=false;
		private String legajo;
		private String nombre;
		private Usuario usuario;
	   	
	@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();		    
			progress= ProgressDialog.show(Login.this, null,
					getString(R.string.alert_wait));
		}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub				
	        String s= Util.getClient(Configuracion.getUrlLogueo(params[0],params[1]));
	        try {         	 
	        	 JSONObject obj = new JSONObject(s);		
	        	 status=obj.getBoolean("status");	        	 
	        	 legajo=obj.getString("legajo");
	        	 nombre=obj.getString("nombre");
	        	 usuario= new Usuario(legajo,"",nombre,params[0],"");
	        	 Util.Log("ban:"+status);	        	 
	        	 return "legajo";				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Util.Log("error");				
			}		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result!=null){
			progress.dismiss();
			usuario.commit(Login.this);			 
			Util.Log(Login.this, "Bienvenido nueva conexion");
		}else{
			Utils.showDialog(
					Login.this,
					  "Error:  No se pudo conectar"
							);			
		}
	  }		
	
   }
	
	public boolean requestLoguin() {
	    RequestQueue queue = Volley.newRequestQueue(this);
	    String url = Configuracion.getAuthorizationLoguin();
	    Util.Log("url=>"+url);	 	
	    if(!Util.isOnline(this)){
	    	Util.crearDialogoAlerta(this, "Error", "Se encuentra sin conexion",Login.this.getLayoutInflater());
	    	return false;
	    }
	    progress= ProgressDialog.show(Login.this, null,
				getString(R.string.alert_wait));	    
	  
	    Util.Log("url=>"+url);
	    StringRequest postRequest = new StringRequest(Request.Method.POST, url, 
	        new Response.Listener<String>(){	    	 
				@Override
	            public void onResponse(String response) {
	                Util.Log(response);
	                progress.dismiss();	  
	                try {
	                	JSONObject json =  (JSONObject) new JSONTokener(response).nextValue();
	                	Util.Log("json=>"+json.getString("estado"));
	                	Util.Log("json=>"+json.getString("usuario"));	                	
	                 	if(json.getInt("estado")==1){
	                		JSONObject _usuario =  (JSONObject) new JSONTokener(json.getString("usuario")).nextValue();
	                		Usuario us= new Usuario(_usuario.getString("nombre"),_usuario.getString("legajo")  , _usuario.getString("correo"), _usuario.getString("imei"),
	                				_usuario.getString("claveApi") , pwd.getText().toString(), "");
	                		us.commit(Login.this);	                		
	                		Util.Log(
	            					Login.this,
	            					  "Bienvenido inicio nuevo"
	            							);
	                		borrar_anterior();	                		
	                		volver();
	                	}else{
	                		Utils.showDialog(Login.this, "Datos incorrectos");
	                		
	                	}
	                
	                } catch (JSONException e) {
						// TODO Auto-generated catch block
	                	Utils.showDialog(Login.this, "Datos incorrectos");
						 Util.Log("error=>"+e.getMessage());
					}
	                
	            }
	        }, 
	        new Response.ErrorListener() 
	        {
	            @Override
	            public void onErrorResponse(VolleyError error) {
	                // TODO Auto-generated method stub
	            	 progress.dismiss();
	                Log.d("ERROR","error => "+error.toString()+"-"+error.getMessage());
	                
	                // As of f605da3 the following should work
	                NetworkResponse response = error.networkResponse;
	                if (error instanceof ServerError && response != null) {
	                    try {
	                       /* String res = new String(response.data,
	                                   HttpHeaderParser.parseCharset(response.headers, "utf-8"));
	                        */
	                        String res = new String(response.data);	                        
	                        // Now you can use any deserializer to make sense of data
	                        JSONObject obj = new JSONObject(res);
	                    /*} catch (UnsupportedEncodingException e1) {
	                        // Couldn't properly decode data to string
	                        e1.printStackTrace();*/
	                    } catch (JSONException e2) {
	                        // returned data is not JSONObject?
	                        e2.printStackTrace();
	                    }
	                }
	            }
	        }
	    ) {     
	    	 @Override
	         protected Map<String,String> getParams(){
	             Map<String,String> params = new HashMap<String, String>();
	             return params;
	         }
	    
	        @Override
	        public Map<String, String> getHeaders() throws AuthFailureError { 
	                Map<String, String>  params = new HashMap<String, String>();  

	                return params;  
	        }
	        @Override
	        public String getBodyContentType() {
	            return "application/json";
	        }
			@Override
			public byte[] getBody() throws AuthFailureError {
				// TODO Auto-generated method stub
				return _getBody();
			}
	        
	    };
	    int socketTimeout = 30000;//30 seconds - change to what you want
	    postRequest.setRetryPolicy(new DefaultRetryPolicy(
	    		socketTimeout, 
		            DefaultRetryPolicy.DEFAULT_MAX_RETRIES, 
		            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));  
	    queue.add(postRequest);
	    return true;

	}
	protected void volver() {
		// TODO Auto-generated method stub
		pwd.setText("");
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public byte[] _getBody() throws AuthFailureError {
		JSONObject jsonObject = new JSONObject();
        String body = null;
        try {        	
        	/*
            jsonObject.put("contrasena", "123456");
            jsonObject.put("correo", "andres_vucerakovich@cosaysa.com.ar");
        	 */
        	 jsonObject.put("contrasena", pwd.getText().toString());
             jsonObject.put("correo", user.getText().toString());
         	
            body = jsonObject.toString();
        } catch (JSONException e)
        {
            // TODO Auto-generated catch block
        	Log.d("ERROR","error-Body(1) => "+e.toString());
        }

        try
        {
            return body.toString().getBytes("utf-8");
        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            Log.d("ERROR","error-Body(2) => "+e.toString());
        }
        return null;

	}
}
