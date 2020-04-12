package aguasdelnorte.com.ar.agenda2.web;

public class Proceso {
	public String idProceso;
    public String valor;
    public String etiqueta;
    public String fecha;
    public String descripcion;
    public String usuario;


    public Proceso(String idProceso, String valor, String etiqueta, String fecha, String descripcion, String usuario) {
        this.idProceso = idProceso;
        this.valor = valor;
        this.etiqueta = etiqueta;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.usuario = usuario;
    }
}
