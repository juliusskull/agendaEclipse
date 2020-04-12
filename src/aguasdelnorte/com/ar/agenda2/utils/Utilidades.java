package aguasdelnorte.com.ar.agenda2.utils;


import android.database.Cursor;
import android.os.Build;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import aguasdelnorte.com.ar.agenda2.provider.ContractParaGastos;
import aguasdelnorte.com.ar.agenda2.provider.ContractParaProceso;

public class Utilidades {
    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID = 0;
    public static final int COLUMNA_ID_REMOTA = 1;
    public static final int COLUMNA_VALOR =2;
    public static final int COLUMNA_ETIQUETA = 3;
    public static final int COLUMNA_FECHA = 4;
    public static final int COLUMNA_DESCRIPCION = 5;    
    
    public static final int COLUMNA_MONTO = 6;
    

    

    /**
     * Determina si la aplicación corre en versiones superiores o iguales
     * a Android LOLLIPOP
     *
     * @return booleano de confirmación
     */
    public static boolean materialDesign() {
        //return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    	return true;
    }

    /**
     * Copia los datos de un gasto almacenados en un cursor hacia un
     * JSONObject
     *
     * @param c cursor
     * @return objeto jason
     */
    public static JSONObject deCursorAJSONObject(Cursor c) {
        JSONObject jObject = new JSONObject();
        String monto;
        String etiqueta;
        String fecha;
        String descripcion;

        monto = c.getString(COLUMNA_MONTO);
        etiqueta = c.getString(COLUMNA_ETIQUETA);
        fecha = c.getString(COLUMNA_FECHA);
        descripcion = c.getString(COLUMNA_DESCRIPCION);

        try {
            jObject.put(ContractParaGastos.Columnas.MONTO, monto);
            jObject.put(ContractParaGastos.Columnas.ETIQUETA, etiqueta);
            jObject.put(ContractParaGastos.Columnas.FECHA, fecha);
            jObject.put(ContractParaGastos.Columnas.DESCRIPCION, descripcion);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject", String.valueOf(jObject));

        return jObject;
    }
    public static JSONObject deCursorAJSONObject_prueba(Cursor c) {
        JSONObject jObject = new JSONObject();
        String idproceso;
        String fechaautoriza;
        String observacion;
        String accion;
        /* posibles columnas
         * {
			"idproceso":13,
			"fechaautoriza":"2016-04-20 13:18:16",
			"fechacad":"",
			"observacion": "",
			"accion": ""
			} 
         * 
         * */
        /*
         * salida anterior
        monto = c.getString(COLUMNA_MONTO);
        etiqueta = c.getString(COLUMNA_ETIQUETA);
        fecha = c.getString(COLUMNA_FECHA);
        descripcion = c.getString(COLUMNA_DESCRIPCION);
        */
        idproceso=c.getString(COLUMNA_ID_REMOTA);
        fechaautoriza=c.getString(COLUMNA_FECHA);
        observacion=c.getString(COLUMNA_DESCRIPCION);
        accion=c.getString(COLUMNA_VALOR);
        try {
            jObject.put("idproceso", idproceso);
            jObject.put("fechaautoriza", fechaautoriza);
            jObject.put("fechacad", "");
            jObject.put("observacion", observacion);
            jObject.put("accion", accion);
            Log.v("Cursor a enviar", jObject.toString());
            
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("Cursor a JSONObject", String.valueOf(jObject));

        return jObject;
    }
}

