package aguasdelnorte.com.ar.agenda2.web;

import java.util.ArrayList;
import java.util.List;

import aguasdelnorte.com.ar.agenda2.clases.Configuracion;

public class Tipo_AccionesLista {
	
	private int estado;
	private List<Tipo_Acciones> datos= null;
	public Tipo_AccionesLista(String categoria ) {		
		super();
		datos=  new ArrayList<Tipo_Acciones>();
		
		if(categoria.equals(Configuracion.CATEGORIA_COMPRAS)){		
		datos.add(new Tipo_Acciones(10, Configuracion.CATEGORIA_COMPRAS, "APROBAR"));
		datos.add(new Tipo_Acciones(12, Configuracion.CATEGORIA_COMPRAS, "RECHAZAR"));
		datos.add(new Tipo_Acciones(16, Configuracion.CATEGORIA_COMPRAS, "APROBAR PARCIALMENTE"));
		datos.add(new Tipo_Acciones(20, Configuracion.CATEGORIA_COMPRAS, "DERIVACION A GER. SOLICITANTE"));
		}
		if(categoria.equals(Configuracion.CATEGORIA_FACTURACION)){		
		datos.add(new Tipo_Acciones(3, Configuracion.CATEGORIA_FACTURACION, "AUTORIZAR"));
		datos.add(new Tipo_Acciones(7, Configuracion.CATEGORIA_FACTURACION, "VISADA"));
		}
		if(categoria.equals(Configuracion.CATEGORIA_RRHH)){		
		datos.add(new Tipo_Acciones(1, Configuracion.CATEGORIA_RRHH, "APROBAR"));
		datos.add(new Tipo_Acciones(2, Configuracion.CATEGORIA_RRHH, "RECHAZAR"));
		}
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public List<Tipo_Acciones> getDatos() {
		return datos;
	}
	public void setDatos(List<Tipo_Acciones> datos) {
		this.datos = datos;
	} 
	
	

}
