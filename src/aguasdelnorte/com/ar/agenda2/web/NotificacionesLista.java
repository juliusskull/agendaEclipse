package aguasdelnorte.com.ar.agenda2.web;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import aguasdelnorte.com.ar.agenda2.clases.NotificacionAD;
import aguasdelnorte.com.ar.agenda2.clases.NotificacionesAD;
import aguasdelnorte.com.ar.agenda2.ui.Util;
import android.app.Activity;

public class NotificacionesLista {
	private int estado;
	private List<Notificaciones> notificaciones= null;
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public List<Notificaciones> getNotificacion() {
		return notificaciones;
	}
	public void setNotificacion(List<Notificaciones> notificaciones) {
		this.notificaciones = notificaciones;
	}
	
    public void PendientesActualizar(Activity a){
    	Util.Log("--PendientesActualizar---");
    	boolean primero= true;
    	for (Notificaciones pendiente : notificaciones) {
    	    // do some work here on intValue
    		Util.Log("entro");
    		NotificacionesAD p= new NotificacionesAD(a, pendiente.get_id(), pendiente.getIdproceso(),
    				pendiente.getFechaalta(), pendiente.getFechaautoriza(), pendiente.getIdusuarioautoriza(),
    				pendiente.getIdprocesoautoriza(), pendiente.getObservacion(), 0);		
    		
    		if(primero){
    			//p.deleteAll();
    			primero=false;
    		}
    		
    		p.insert();
    		
    		
    	}
    }
	public void setResponse(String s) {
		// TODO Auto-generated method stub
		try {		
			org.json.JSONObject obj = new JSONObject(s);
	        org.json.JSONArray p = obj.getJSONArray("datos");
	        Util.Log("entro "+p.length());							
			
			for (int i = 0; i <p.length(); i++) {
				JSONObject a = p.getJSONObject(i);						
				Util.Log("entro "+i);				
				Util.Log("entro "+a.get("idusuarioautoriza"));
				Notificaciones onotificaciones= new Notificaciones();
				notificaciones=  new ArrayList<Notificaciones>();
				
				onotificaciones.set_id(a.getLong("id"));
				onotificaciones.setIdproceso(a.getLong("idproceso"));
				onotificaciones.setFechaalta(a.getString("fechaalta"));
				onotificaciones.setFechaautoriza(a.getString("fechaautoriza"));
				onotificaciones.setIdusuarioautoriza(a.getString("idusuarioautoriza"));
				onotificaciones.setIdprocesoautoriza(a.getLong("idprocesoautoriza"));
				onotificaciones.setObservacion(a.getString("observacion"));
				notificaciones.add(onotificaciones);
			} 
	        
			/*
        	JSONTokener x = new JSONTokener(s);					        	
        	JSONArray p= (JSONArray) x.nextValue();	
        			
			Util.Log("entro "+p.length());							
		
			for (int i = 0; i <p.length(); i++) {
				JSONObject a = p.getJSONObject(i);						
				Util.Log("entro "+i);				
				
			} 
			*/	
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			
			Util.Log("error:"+e.getMessage());
			//e.printStackTrace();
		}	
	}

}
