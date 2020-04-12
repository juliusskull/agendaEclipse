package aguasdelnorte.com.ar.agenda2.web;

import java.util.List;

import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.clases.GastoAD;
import android.app.Activity;

public class GastoLista {
	private int estado;
	private List<Gasto> gastos= null;

	public List<Gasto> getPendientes() {
		return gastos;
	}

	public void setPendientes(List<Gasto> datos) {
		this.gastos = datos;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	} 
    public void PendientesActualizar(Activity a){
    	
    	boolean primero= true;
    	for (Gasto pendiente : gastos) {
    	    // do some work here on intValue
    		GastoAD g = new GastoAD(a,  String.valueOf(pendiente.monto)
    				, pendiente.etiqueta
    				, pendiente.fecha
    				, pendiente.descripcion
    				, pendiente.idGasto 
    				, pendiente.usuario);
    				
    		if(primero){
    			g.deleteAll();
    			primero=false;
    		}
    		
    		g.insert();
    		
    		
    	}
    }
    	
}
