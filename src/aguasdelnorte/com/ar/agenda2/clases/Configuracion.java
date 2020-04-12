package aguasdelnorte.com.ar.agenda2.clases;

import aguasdelnorte.com.ar.agenda2.ui.Util;
import android.app.Activity;
import android.net.Uri;

public class Configuracion {
	
public static final String  PROCESO_ACTUAL = "ID";
public static final String  BIBLIO = "agenda";
public static final boolean SELECT_PRUEBA = false;
public static final String  USUARIO_PRUEBA = "jgutierrez";
public static final String  USUARIO_IMAGEN_DEFAULT = "http://10.0.2.2//ot/ot/user.png";
public static final String NOTIFICACIONES_PENDIENTES = "notificaciones_pendientes";

public static boolean produccion=true;
	
	public static String  server="http://10.0.2.2//ot/";
	
	//public static String  server_produccion="http://200.45.174.166/";//"http://ot.aguasdelnortesalta.com.ar/wses2.php";
	public static String  server_produccion="http://200.45.174.162/";//"http://ot.aguasdelnortesalta.com.ar/wses2.php";

	public static String CATEGORIA_COMPRAS="C";
	public static String CATEGORIA_FACTURACION="F";
	public static String CATEGORIA_RRHH="R";
	
	public static String getServer() {
		// TODO Auto-generated method stub
		if(produccion){
			return server_produccion;
		}else{
			return server;
		}		
	}

	public static String getBiblioLat() {
		// TODO Auto-generated method stub
		return "lat";
	}

	public static String getBiblioLng() {
		// TODO Auto-generated method stub
		return "lng";
	}

	public static String getBiblio() {
		// TODO Auto-generated method stub
		return BIBLIO;
	}

	public static String getBiblioUser() {
		// TODO Auto-generated method stub
		return "user";
	}

	public static String getBiblioPass() {
		// TODO Auto-generated method stub
		return "pass";
	}

	public static String getBiblioNombre() {
		// TODO Auto-generated method stub
		return "nombre";
	}

	public static String getUrlLogueo(String params,String imei) {
		// TODO Auto-generated method stub
		
		return getServer()+"?getUser="+params+"&imei="+imei;
	}
	public static String getBiblioMail() {
		// TODO Auto-generated method stub
		return "mail";
	}
	public static String getBiblioImagenUsuario() {
		// TODO Auto-generated method stub
		return "imagenUsuario";
	}
	public static String getBiblioClaveApi() {
		// TODO Auto-generated method stub
		return "claveapi";
	}
	public static String getActivo(String imei, String usuarioActivo, String nombre) {
		// TODO Auto-generated method stub
		return getServer()+"?getUserActivo="+Util.UrlParse(usuarioActivo)+"&imei="+Util.UrlParse(imei)+"&nombre="+Util.UrlParse(nombre);
	}
	public static String getAuthorization() {
		// TODO Auto-generated method stub
		return getServer()+"wsagenda/v1/agenda";//"http://10.0.2.2/wsrest/v1/agenda";//"http://10.0.2.2/prueba/ahorro/obtener_gastos.php";//"http://10.0.2.2/wsrest/v1/agenda";
	}
	public static String getNotificaciones() {
		// TODO Auto-generated method stub
		return getServer()+"wsagenda/v1/notificaciones";//"http://10.0.2.2/wsrest/v1/agenda";//"http://10.0.2.2/prueba/ahorro/obtener_gastos.php";//"http://10.0.2.2/wsrest/v1/agenda";
	}
	public static String getRegistrar() {
		// TODO Auto-generated method stub
		return getServer()+"wsagenda/v1/respuesta/registro";//"http://10.0.2.2/wsrest/v1/agenda";//"http://10.0.2.2/prueba/ahorro/obtener_gastos.php";//"http://10.0.2.2/wsrest/v1/agenda";
	}
	public static String getAuthorizationLoguin() {
		// TODO Auto-generated method stub
		return getServer()+"wsagenda/v1/usuarios/login";
	}
	public static String getAuthorizationCambioDeClave() {
		// TODO Auto-generated method stub
		return getServer()+"wsagenda/v1/usuarios/cambiarclave";
	}
	public static String getAuthorizationDownload(String archivo) {
		// TODO Auto-generated method stub
		//return getServer()+"wsagenda/v1/12122.pdf";
		//return getServer()+"wsagenda/v1/archivo/12122";
		return getServer()+"wsagenda/v1/archivo/"+archivo;
		
	}
	public static String IsAuthorizationDownload(String proceso_actual_id) {
		// TODO Auto-generated method stub
		//return getServer()+"wsagenda/v1/12122.pdf";
		//return getServer()+"wsagenda/v1/archivo/12122";
		return getServer()+"wsagenda/v1/consulta.php?id_proceso="+proceso_actual_id;
		
	}
	public static String getBiblioSincronizacion() {
		// TODO Auto-generated method stub
		return "sincronizacion";
	}
	public static void Actualizar_sincronizacion(Activity a) {
		// TODO Auto-generated method stub		
		Util.SpSet(a, getBiblio(), getBiblioSincronizacion(), Util.getFechaActualFormat()+" "+Util.getHoraActualFormat());
	}
	
	///-24.7883|-65.4103

}
