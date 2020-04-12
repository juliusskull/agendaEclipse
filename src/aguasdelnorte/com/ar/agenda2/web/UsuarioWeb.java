package aguasdelnorte.com.ar.agenda2.web;

public class UsuarioWeb {
	public String nombre="";
	public String legajo="";
	public String correo="";
	public String imei="";
	public String claveApi="";
	public UsuarioWeb(String nombre, String legajo, String correo, String imei,
			String claveApi) {
		super();
		this.nombre = nombre;
		this.legajo = legajo;
		this.correo = correo;
		this.imei = imei;
		this.claveApi = claveApi;
	}

}
