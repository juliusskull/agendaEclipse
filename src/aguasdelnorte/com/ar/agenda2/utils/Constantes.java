package aguasdelnorte.com.ar.agenda2.utils;



/**
 * Constantes
 */
public class Constantes {

    /**
     * Puerto que utilizas para la conexión.
     * Dejalo en blanco si no has configurado esta característica.
     */
    //private static final String PUERTO_HOST = ":63343";
	private static final String PUERTO_HOST = "";

    /**
     * Dirección IP de genymotion o AVD
     */
    //private static final String IP = "http://10.0.3.2";
	private static final String IP = "http://10.0.2.2";

    /**
     * URLs del Web Service
     */
    //public static String  server="http://10.0.2.2//ot/ot/p1.php";
    //public static final String GET_URL = IP + PUERTO_HOST + "/Crunch%20Expenses/web/obtener_gastos.php";
	public static final String GET_URL    = IP + PUERTO_HOST + "/prueba/ahorro/obtener_gastos.php";
    public static final String INSERT_URL = IP + PUERTO_HOST + "/prueba/ahorro/insertar_gasto.php";
 
    /**
     * Campos de las respuestas Json
     */
    public static final String ID_GASTO = "idGasto";
    public static final String ESTADO   = "estado";
    public static final String GASTOS   = "gastos";
    public static final String MENSAJE  = "mensaje";
    public static final String PROCESO  = "proceso";
    public static final String IDPROCESO  = "idProceso";

    /**
     * Códigos del campo {@link ESTADO}
     */
    public static final String SUCCESS = "5";/*"1";*/
    public static final String FAILED  = "2";

    /**
     * Tipo de cuenta para la sincronización
     */
  //  public static final String ACCOUNT_TYPE = "com.herprogramacion.crunch_expenses.account";
    public static final String ACCOUNT_TYPE = "aguasdelnorte.com.ar.agenda2.account";


}
