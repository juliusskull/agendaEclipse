package aguasdelnorte.com.ar.agenda2.web;

import java.util.ArrayList;
import java.util.List;

import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import aguasdelnorte.com.ar.agenda2.ui.Util;
import android.app.Activity;




public class Agenda_pendienteLista {
	private int estado;
	private List<Agenda_pendiente> datos= null; ;
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public List<Agenda_pendiente> getPendientes() {
		return datos;
	}
	public void setPendientes(List<Agenda_pendiente> pendientes) {
		this.datos = pendientes;
	}
	
    public void PendientesActualizar(Activity a){
    	boolean primero= true;
    	for (Agenda_pendiente pendiente : datos) {
    	    // do some work here on intValue
    		Util.Log("-------------");    		
    		Util.Log("entra=>"+pendiente.toString());
    		
    		Agenda_pendienteAD pendienteAD = 
    				new Agenda_pendienteAD(a, pendiente.idproceso,
    						  pendiente.categoria
    						, pendiente.idsubcategoria
    						, pendiente.subcategoria
    						, pendiente.legajo
    						, pendiente.numero
    						, pendiente.titulo
    						, pendiente.fchingreso
    						, pendiente.fchgestion
    						, pendiente.fchdesde
    						, pendiente.fchhasta
    						, pendiente.unidadmedida
    						, pendiente.monto
    						, pendiente.observacion
    						, pendiente.idgerencia
    						, pendiente.gerencia
    						, pendiente.accion,pendiente.autoriza);
    		if(primero){
    			pendienteAD.deleteAll();
    			primero= false;
    		}
    		
    		pendienteAD.insert();
    		
    		
    	}
    	Configuracion.Actualizar_sincronizacion(a);
    	
    }

}
