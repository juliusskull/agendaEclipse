package aguasdelnorte.com.ar.agenda2.provider;



import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContractAgenda_Pendiente{
	/**
     * Autoridad del Content Provider
     */
   /* public final static String AUTHORITY
            = "com.herprogramacion.crunch_expenses";
    */
    public final static String AUTHORITY
    = "com.ct.emp.provider.ProviderDeGastos";
    
   
    /**
     * Representación de la tabla a consultar
     */
    public static final String TABLA = Agenda_pendienteAD.table_sql;
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA;
    /**
     * Tipo MIME que retorna la consulta de {@link CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + TABLA);
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * Código para URIs de multiples registros
     */
    public static final int ALLROWS = 1;
    /**
     * Código para URIS de un solo registro
     */
    public static final int SINGLE_ROW = 2;


    // Asignación de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLA, ALLROWS);
        uriMatcher.addURI(AUTHORITY, TABLA + "/#", SINGLE_ROW);
    }

    // Valores para la columna ESTADO
    public static final int ESTADO_OK = 0;
    public static final int ESTADO_SYNC = 1;


    /**
     * Estructura de la tabla
     */
    public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

         public static String IDPROCESO="idproceso";
	   	 public static String CATEGORIA="categoria";
	   	 public static String IDSUBCATEGORIA="idsubcategoria";
	   	 public static String SUBCATEGORIA="subcategoria";
	   	 public static String LEGAJO="legajo";
	   	 public static String NUMERO="numero";
	   	 public static String TITULO="titulo";
	   	 public static String FCHINGRESO="fchingreso";
	   	 public static String FCHGESTION="fchgestion";
	   	 public static String FCHDESDE="fchdesde";
	   	 public static String FCHHASTA="fchhasta";
	   	 public static String UNIDADMEDIDA="unidadmedida";
	   	 public static String MONTO="monto";
	   	 public static String OBSERVACION="observacion";
	   	 public static String IDGERENCIA="idgerencia";
	   	 public static String GERENCIA="gerencia";
	   	 public static String ACCION="accion";
	   	 
	   	 public static final String ESTADO = "estado";
	       
	        public final static String PENDIENTE_INSERCION = "pendiente_insercion";
    }
}
