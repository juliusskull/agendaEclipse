package aguasdelnorte.com.ar.agenda2.web;

import java.util.List;

import aguasdelnorte.com.ar.agenda2.clases.GastoAD;
import aguasdelnorte.com.ar.agenda2.clases.ProcesoAD;
import android.app.Activity;

public class ProcesoLista {
	private int estado;
	private List<Proceso> proceso= null;

	public List<Proceso> getPendientes() {
		return proceso;
	}

	public void setPendientes(List<Proceso> datos) {
		this.proceso = datos;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	} 
    public void PendientesActualizar(Activity a){
    	
    	boolean primero= true;
    	for (Proceso pendiente : proceso) {
    	    // do some work here on intValue
    		ProcesoAD p= new ProcesoAD(a, pendiente.valor, pendiente.etiqueta,
    				pendiente.fecha, pendiente.descripcion, pendiente.idProceso);
    		
    				
    		if(primero){
    			p.deleteAll();
    			primero=false;
    		}
    		
    		p.insert();
    		
    		
    	}
    }
}
