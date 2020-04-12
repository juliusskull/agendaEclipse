package aguasdelnorte.com.ar.agenda2.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

public class ContractParaProceso {
    /**
     * Autoridad del Content Provider
     */

    public final static String AUTHORITY
    = "aguasdelnorte.com.ar.agenda2.provider.ProviderDeProceso";
    
   
    /**
     * Representación de la tabla a consultar
     */
    public static final String TABLE = "proceso";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */
    public final static String SINGLE_MIME =
            "vnd.android.cursor.item/vnd." + AUTHORITY + TABLE;
    /**
     * Tipo MIME que retorna la consulta de {@link CONTENT_URI}
     */
    public final static String MULTIPLE_MIME =
            "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLE;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + TABLE);
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
        uriMatcher.addURI(AUTHORITY, TABLE, ALLROWS);
        uriMatcher.addURI(AUTHORITY, TABLE + "/#", SINGLE_ROW);
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
        public final  static String VALOR = "valor";
        public final  static String ETIQUETA = "etiqueta";
        public final  static String FECHA = "fecha";
        public final  static String DESCRIPCION = "descripcion";
        public static final String ID_REMOTA = "idRemota";
        public static final String ESTADO = "estado";        
        public final  static String PENDIENTE_INSERCION = "pendiente_insercion";
        public static final String USUARIO = "usuario";
    }
}
