package aguasdelnorte.com.ar.agenda2.web;

public class Gasto {
	 public String idGasto;
	    public int monto;
	    public String etiqueta;
	    public String fecha;
	    public String descripcion;
	    public String usuario;

	    public Gasto(String idGasto, int monto, String etiqueta, String fecha, String descripcion, String usuario) {
	        this.idGasto = idGasto;
	        this.monto = monto;
	        this.etiqueta = etiqueta;
	        this.fecha = fecha;
	        this.descripcion = descripcion;
	        this.usuario = usuario;
	    }
}
