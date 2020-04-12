package aguasdelnorte.com.ar.agenda2.clases;
import aguasdelnorte.com.ar.agenda2.ui.Util;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

public class Usuario {
		
	
	public String nombre="";
	public String legajo="";
	public String correo="";
	public String imei="";
	public String claveapi="";	
	public String pass="";
	public String imagenusuario="";

	public Usuario(String usu, String pass, String nombre, String mail,String imagenusuario) {
		super();
		this.legajo = usu;
		this.pass = pass;
		this.nombre = nombre;
		this.correo = mail;
		this.imagenusuario=imagenusuario;
	}
	


	public Usuario(String nombre, String legajo, String correo, String imei,
			String claveapi, String pass, String imagenusuario) {
		super();
		this.nombre = nombre;
		this.legajo = legajo;
		this.correo = correo;
		this.imei = imei;
		this.claveapi = claveapi;
		this.pass = pass;
		this.imagenusuario = imagenusuario;
	}



	public String getUsu() {
		//es el usuario
		return legajo;
	}
	public void setUsu(String usu) {
		this.legajo = usu;
	}
	public String getPass() {
		
		return pass;
		
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public Usuario(String usu, String pass) {		
		this.legajo = usu;
		this.pass = pass;
	}
	public Usuario() {		
	
	}
	public void commit(Activity a) {
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioUser(), legajo);
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioPass(), pass);
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioNombre(),nombre);
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioMail(),correo);
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioImagenUsuario(),imagenusuario);
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioClaveApi(),claveapi);
		
		
	}
	
	public static void desloguearce(Activity a) {
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioUser(), "");
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioPass(), "");
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioNombre(),"");
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioMail(),"");
		Util.SpSet(a, Configuracion.getBiblio(), Configuracion.getBiblioImagenUsuario(),"");

	}
	public void load(Activity a) {
		legajo=Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioUser(),"");
		pass=Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioPass(),"");
		nombre=Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioNombre(),"");
		correo=Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioMail(),"");
		imagenusuario=Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioImagenUsuario(),"");
		claveapi=Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioClaveApi(),"");
		
	}
	public static boolean  Exists(Activity a) {
		String usu1=Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioUser(),"");
		
		return (usu1.length()>0)?true:false;
	}
	
	public static String  getUsuarioActivo(Activity a) {
		return Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioUser(),"");
		
		 
	}
	@Override
	public String toString() {
		return "Usuario [usu=" + legajo + ", pass=" + pass + "]";
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	

	public int getLegajo() {
		// TODO Auto-generated method stub
		return Integer.parseInt(legajo);
	}
	public String getMail() {
		return correo;
	}
	public void setMail(String mail) {
		this.correo = mail;
	}
	public String getImagenusuario() {
		return imagenusuario;
	}
	
	public void setImagenusuario(String imagenusuario) {
		this.imagenusuario = imagenusuario;
	}
	public boolean isImagenusuario() {
		return (imagenusuario.length()>0)?true:false;
	}
	public static String getNombreActivo(Activity a) {
		// TODO Auto-generated method stub
		return Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioNombre(),"");
		
	}
	public static String getApi(Activity a) {
		// TODO Auto-generated method stub
		return Util.SpGet(a, Configuracion.getBiblio(), Configuracion.getBiblioClaveApi(),"");
		
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getClaveapi() {
		return claveapi;
	}
	public void setClaveapi(String claveapi) {
		this.claveapi = claveapi;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}



	public static String getApi(Context context) {
		// TODO Auto-generated method stub
		return Util.SpGet(context, Configuracion.getBiblio(), Configuracion.getBiblioClaveApi(),"");
		
	}
	

}
