package aguasdelnorte.com.ar.agenda2.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.gc.materialdesign.widgets.Dialog;
import com.google.gson.Gson;
import aguasdelnorte.com.ar.agenda2.R;
import aguasdelnorte.com.ar.agenda2.clases.Configuracion;
import aguasdelnorte.com.ar.agenda2.clases.Usuario;
import aguasdelnorte.com.ar.agenda2.provider.ContractParaGastos;
import aguasdelnorte.com.ar.agenda2.provider.ContractParaProceso;
import aguasdelnorte.com.ar.agenda2.ui.Util;
import aguasdelnorte.com.ar.agenda2.utils.Constantes;
import aguasdelnorte.com.ar.agenda2.utils.Utilidades;
import aguasdelnorte.com.ar.agenda2.web.Gasto;
import aguasdelnorte.com.ar.agenda2.web.Proceso;
import aguasdelnorte.com.ar.agenda2.web.VolleySingleton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maneja la transferencia de datos entre el servidor y el cliente
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getSimpleName();

    ContentResolver resolver;
    private Gson gson = new Gson();

	private Context mycontext;

    /**
     * Proyección para las consultas
     */
    private static final String[] PROJECTION = new String[]{
        ContractParaProceso.Columnas._ID,
        ContractParaProceso.Columnas.ID_REMOTA,
        ContractParaProceso.Columnas.VALOR,
        ContractParaProceso.Columnas.ETIQUETA,
        ContractParaProceso.Columnas.FECHA,
        ContractParaProceso.Columnas.DESCRIPCION
};

// Indices para las columnas indicadas en la proyección
		public static final int COLUMNA_ID = 0;
		public static final int COLUMNA_ID_REMOTA = 1;
		public static final int COLUMNA_MONTO = 2;
		public static final int COLUMNA_ETIQUETA = 3;
		public static final int COLUMNA_FECHA = 4;
		public static final int COLUMNA_DESCRIPCION = 5;
/*
    private static final String[] PROJECTION = new String[]{
            ContractParaGastos.Columnas._ID,
            ContractParaGastos.Columnas.ID_REMOTA,
            ContractParaGastos.Columnas.MONTO,
            ContractParaGastos.Columnas.ETIQUETA,
            ContractParaGastos.Columnas.FECHA,
            ContractParaGastos.Columnas.DESCRIPCION
    };

    // Indices para las columnas indicadas en la proyección
    public static final int COLUMNA_ID = 0;
    public static final int COLUMNA_ID_REMOTA = 1;
    public static final int COLUMNA_MONTO = 2;
    public static final int COLUMNA_ETIQUETA = 3;
    public static final int COLUMNA_FECHA = 4;
    public static final int COLUMNA_DESCRIPCION = 5;
*/
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        resolver = context.getContentResolver();
    }

    /**
     * Constructor para mantener compatibilidad en versiones inferiores a 3.0
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        resolver = context.getContentResolver();
        mycontext=context;
        
    }

    public static void inicializarSyncAdapter(Context context) {
        obtenerCuentaASincronizar(context);
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              final SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()...");

        boolean soloSubida = extras.getBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, false);

        if (!soloSubida) {
            realizarSincronizacionLocal(syncResult);
        } else {
            realizarSincronizacionRemota_bis();
        }
    }

    private void realizarSincronizacionLocal(final SyncResult syncResult) {
        Log.i(TAG, "Actualizando el cliente.");

        VolleySingleton.getInstance(getContext()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        Constantes.GET_URL,
                        null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                procesarRespuestaGet(response, syncResult);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(TAG, error.networkResponse.toString());
                            }
                        }
                )
        );
    }
    

    /**
     * Procesa la respuesta del servidor al pedir que se retornen todos los gastos.
     *
     * @param response   Respuesta en formato Json
     * @param syncResult Registro de resultados de sincronización
     */
    private void procesarRespuestaGet(JSONObject response, SyncResult syncResult) {
        try {
            // Obtener atributo "estado"
            String estado = response.getString(Constantes.ESTADO);
            if(estado.compareTo(Constantes.SUCCESS)==0){
            	 actualizarDatosLocales(response, syncResult);
            }else{
            	String mensaje = response.getString(Constantes.MENSAJE);
            	 Log.i(TAG, mensaje);
            }
         /*   switch (estado) {
                case Constantes.SUCCESS: // EXITO
                    actualizarDatosLocales(response, syncResult);
                    break;
                case Constantes.FAILED: // FALLIDO
                    String mensaje = response.getString(Constantes.MENSAJE);
                    Log.i(TAG, mensaje);
                    break;
            }*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void realizarSincronizacionRemota_bis() {
        Log.i(TAG, "Actualizando el servidor...");

        iniciarActualizacion();

        Cursor c = obtenerRegistrosSucios();

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros sucios.");           
        
        if (c.getCount() > 0) {
            try {
				while (c.moveToNext()) {
				    final int idLocal = c.getInt(COLUMNA_ID);
				    
				    VolleySingleton.getInstance(getContext()).addToRequestQueue(
				            new JsonObjectRequest(
				                    Request.Method.POST,
				                    Configuracion.getRegistrar(),
				                    Utilidades.deCursorAJSONObject_prueba(c),
				                    new Response.Listener<JSONObject>() {
				                       // private Dialog dialog;
										@Override
				                        public void onResponse(JSONObject response) {
											Util.Log("response-1");
				                            procesarRespuestaInsert(response, idLocal);
				                        	Util.Log("response:"+response);				                       
				                        }
				                    },
				                    new Response.ErrorListener() {
				                        @Override
				                        public void onErrorResponse(VolleyError error) {
				                        	try {
												Log.d(TAG,"Error Volley:statusCode=>"+ error.networkResponse.statusCode);
												Log.d(TAG, "Error Volley: idLocal:"+idLocal+"-" + error.getMessage()+"-" +error.toString());
											} catch (Exception e) {
												// TODO Auto-generated catch block
												//e.printStackTrace();
												
												Util.Log("Sin conexion");
											}
				                        	
				                        }                                                                      
				                    }                             

				            ) {
				                
								@Override
				                public Map<String, String> getHeaders() {
				                    Map<String, String> headers = new HashMap<String, String>();
				                    //headers.put("Authorization", "0e9c1b1e026068b04e03c537f34fc83b");
				                    headers.put("Authorization", Usuario.getApi(getContext()));
				                   
				                    return headers;
				                }

				                @Override
				                public String getBodyContentType() {
				                    return "application/json; charset=utf-8" + getParamsEncoding();
				                }
				            }
				    );
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				Util.Log("e=>"+e.getMessage());
			}

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }
        c.close();
    }
    
    private void realizarSincronizacionRemota() {
        Log.i(TAG, "Actualizando el servidor...");

        iniciarActualizacion();

        Cursor c = obtenerRegistrosSucios();

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros sucios.");

        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                final int idLocal = c.getInt(COLUMNA_ID);

                VolleySingleton.getInstance(getContext()).addToRequestQueue(
                        new JsonObjectRequest(
                                Request.Method.POST,
                                Constantes.INSERT_URL,
                                Utilidades.deCursorAJSONObject(c),
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        procesarRespuestaInsert(response, idLocal);
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "Error Volley: " + error.getMessage());
                                    }
                                }

                        ) {
                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<String, String>();
                                headers.put("Content-Type", "application/json; charset=utf-8");
                                headers.put("Accept", "application/json");
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json; charset=utf-8" + getParamsEncoding();
                            }
                        }
                );
            }

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }
        c.close();
    }

    /**
     * Obtiene el registro que se acaba de marcar como "pendiente por sincronizar" y
     * con "estado de sincronización"
     *
     * @return Cursor con el registro.
     */
    private Cursor obtenerRegistrosSucios() {
        Uri uri = ContractParaProceso.CONTENT_URI;
        String selection = ContractParaProceso.Columnas.PENDIENTE_INSERCION + "=? AND "
                + ContractParaProceso.Columnas.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaProceso.ESTADO_SYNC + ""};

        return resolver.query(uri, PROJECTION, selection, selectionArgs, null);
    }
    private Cursor obtenerRegistrosSucios2() {
        Uri uri = ContractParaGastos.CONTENT_URI;
        String selection = ContractParaGastos.Columnas.PENDIENTE_INSERCION + "=? AND "
                + ContractParaGastos.Columnas.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaGastos.ESTADO_SYNC + ""};

        return resolver.query(uri, PROJECTION, selection, selectionArgs, null);
    }

    /**
     * Cambia a estado "de sincronización" el registro que se acaba de insertar localmente
     */
    private void iniciarActualizacion() {
        Uri uri = ContractParaProceso.CONTENT_URI;
        String selection = ContractParaProceso.Columnas.PENDIENTE_INSERCION + "=? AND "
                + ContractParaProceso.Columnas.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaProceso.ESTADO_OK + ""};

        ContentValues v = new ContentValues();
        v.put(ContractParaProceso.Columnas.ESTADO, ContractParaProceso.ESTADO_SYNC);

        int results = resolver.update(uri, v, selection, selectionArgs);
        Log.i(TAG, "Registros puestos en cola de inserción:" + results);
    }
    private void iniciarActualizacion2() {
        Uri uri = ContractParaGastos.CONTENT_URI;
        String selection = ContractParaGastos.Columnas.PENDIENTE_INSERCION + "=? AND "
                + ContractParaGastos.Columnas.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaGastos.ESTADO_OK + ""};

        ContentValues v = new ContentValues();
        v.put(ContractParaGastos.Columnas.ESTADO, ContractParaGastos.ESTADO_SYNC);

        int results = resolver.update(uri, v, selection, selectionArgs);
        Log.i(TAG, "Registros puestos en cola de inserción:" + results);
    }

    /**
     * Limpia el registro que se sincronizó y le asigna la nueva id remota proveida
     * por el servidor
     *
     * @param idRemota id remota
     */
    private void finalizarActualizacion(String idRemota, int idLocal) {
        Uri uri = ContractParaProceso.CONTENT_URI;
        String selection = ContractParaProceso.Columnas._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(idLocal)};

        ContentValues v = new ContentValues();
        v.put(ContractParaProceso.Columnas.PENDIENTE_INSERCION, "0");
        v.put(ContractParaProceso.Columnas.ESTADO, ContractParaGastos.ESTADO_OK);
        v.put(ContractParaProceso.Columnas.ID_REMOTA, idRemota);

        resolver.update(uri, v, selection, selectionArgs);
    }
    
    private void finalizarActualizacion2(String idRemota, int idLocal) {
        Uri uri = ContractParaGastos.CONTENT_URI;
        String selection = ContractParaGastos.Columnas._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(idLocal)};

        ContentValues v = new ContentValues();
        v.put(ContractParaGastos.Columnas.PENDIENTE_INSERCION, "0");
        v.put(ContractParaGastos.Columnas.ESTADO, ContractParaGastos.ESTADO_OK);
        v.put(ContractParaGastos.Columnas.ID_REMOTA, idRemota);

        resolver.update(uri, v, selection, selectionArgs);
    }


    /**
     * Procesa los diferentes tipos de respuesta obtenidos del servidor
     *
     * @param response Respuesta en formato Json
     */
    public void procesarRespuestaInsert(JSONObject response, int idLocal) {

        try {
            // Obtener estado
        	/*
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);
            // Obtener identificador del nuevo registro creado en el servidor
            String idRemota = response.getString(Constantes.ID_GASTO);
            */
            String estado = response.getString(Constantes.ESTADO);
            // Obtener mensaje
            String mensaje = response.getString(Constantes.MENSAJE);
            // Obtener identificador del nuevo registro creado en el servidor
            String idRemota = response.getString(Constantes.IDPROCESO);
            
            if(estado.compareTo(Constantes.SUCCESS)==0){
            	Log.i(TAG, mensaje);
                finalizarActualizacion(idRemota, idLocal);
            	
            }else{
            	 Log.i(TAG, mensaje);
            	
            }

           /* switch (estado) {
                case Constantes.SUCCESS:
                    Log.i(TAG, mensaje);
                    finalizarActualizacion(idRemota, idLocal);
                    break;

                case Constantes.FAILED:
                    Log.i(TAG, mensaje);
                    break;
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Actualiza los registros locales a través de una comparación con los datos
     * del servidor
     *
     * @param response   Respuesta en formato Json obtenida del servidor
     * @param syncResult Registros de la sincronización
     */
    private void actualizarDatosLocales(JSONObject response, SyncResult syncResult) {

        JSONArray proceso = null;

        try {
            // Obtener array "gastos"
        	proceso = response.getJSONArray(Constantes.PROCESO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Proceso[] res = gson.fromJson(proceso != null ? proceso.toString() : null, Proceso[].class);
        List<Proceso> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir las entradas entrantes
        HashMap<String, Proceso> expenseMap = new HashMap<String, Proceso>();
        for (Proceso e : data) {
            expenseMap.put(e.idProceso, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractParaProceso.CONTENT_URI;
        String select = ContractParaProceso.Columnas.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, PROJECTION, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        int monto;
        String etiqueta;
        String fecha;
        String descripcion;
        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(COLUMNA_ID_REMOTA);
            monto = c.getInt(COLUMNA_MONTO);
            etiqueta = c.getString(COLUMNA_ETIQUETA);
            fecha = c.getString(COLUMNA_FECHA);
            descripcion = c.getString(COLUMNA_DESCRIPCION);

            Proceso match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractParaProceso.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                /**/
                //boolean b = match.valor != monto;
                boolean b1 = match.etiqueta != null && !match.etiqueta.equals(etiqueta);
                boolean b2 = match.fecha != null && !match.fecha.equals(fecha);
                boolean b3 = match.descripcion != null && !match.descripcion.equals(descripcion);
			
                if (/*trueb ||*/ b1 || b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContractParaProceso.Columnas.VALOR, match.valor)
                            .withValue(ContractParaProceso.Columnas.ETIQUETA, match.etiqueta)
                            .withValue(ContractParaProceso.Columnas.FECHA, match.fecha)
                            .withValue(ContractParaProceso.Columnas.DESCRIPCION, match.descripcion)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractParaProceso.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Proceso e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de: " + e.idProceso);
            ops.add(ContentProviderOperation.newInsert(ContractParaProceso.CONTENT_URI)
                    .withValue(ContractParaProceso.Columnas.ID_REMOTA, e.idProceso)
                    .withValue(ContractParaProceso.Columnas.VALOR, e.valor)
                    .withValue(ContractParaProceso.Columnas.ETIQUETA, e.etiqueta)
                    .withValue(ContractParaProceso.Columnas.FECHA, e.fecha)
                    .withValue(ContractParaProceso.Columnas.DESCRIPCION, e.descripcion)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                try {
					resolver.applyBatch(ContractParaProceso.AUTHORITY, ops);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            } catch ( OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractParaProceso.CONTENT_URI,
                    null,
                    false);
            Log.i(TAG, "Sincronización finalizada.");

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }

    }
    private void actualizarDatosLocales2(JSONObject response, SyncResult syncResult) {

        JSONArray gastos = null;

        try {
            // Obtener array "gastos"
            gastos = response.getJSONArray(Constantes.GASTOS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Parsear con Gson
        Gasto[] res = gson.fromJson(gastos != null ? gastos.toString() : null, Gasto[].class);
        List<Gasto> data = Arrays.asList(res);

        // Lista para recolección de operaciones pendientes
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        // Tabla hash para recibir las entradas entrantes
        HashMap<String, Gasto> expenseMap = new HashMap<String, Gasto>();
        for (Gasto e : data) {
            expenseMap.put(e.idGasto, e);
        }

        // Consultar registros remotos actuales
        Uri uri = ContractParaGastos.CONTENT_URI;
        String select = ContractParaGastos.Columnas.ID_REMOTA + " IS NOT NULL";
        Cursor c = resolver.query(uri, PROJECTION, select, null, null);
        assert c != null;

        Log.i(TAG, "Se encontraron " + c.getCount() + " registros locales.");

        // Encontrar datos obsoletos
        String id;
        int monto;
        String etiqueta;
        String fecha;
        String descripcion;
        while (c.moveToNext()) {
            syncResult.stats.numEntries++;

            id = c.getString(COLUMNA_ID_REMOTA);
            monto = c.getInt(COLUMNA_MONTO);
            etiqueta = c.getString(COLUMNA_ETIQUETA);
            fecha = c.getString(COLUMNA_FECHA);
            descripcion = c.getString(COLUMNA_DESCRIPCION);

            Gasto match = expenseMap.get(id);

            if (match != null) {
                // Esta entrada existe, por lo que se remueve del mapeado
                expenseMap.remove(id);

                Uri existingUri = ContractParaGastos.CONTENT_URI.buildUpon()
                        .appendPath(id).build();

                // Comprobar si el gasto necesita ser actualizado
                boolean b = match.monto != monto;
                boolean b1 = match.etiqueta != null && !match.etiqueta.equals(etiqueta);
                boolean b2 = match.fecha != null && !match.fecha.equals(fecha);
                boolean b3 = match.descripcion != null && !match.descripcion.equals(descripcion);

                if (b || b1 || b2 || b3) {

                    Log.i(TAG, "Programando actualización de: " + existingUri);

                    ops.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(ContractParaGastos.Columnas.MONTO, match.monto)
                            .withValue(ContractParaGastos.Columnas.ETIQUETA, match.etiqueta)
                            .withValue(ContractParaGastos.Columnas.FECHA, match.fecha)
                            .withValue(ContractParaGastos.Columnas.DESCRIPCION, match.descripcion)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No hay acciones para este registro: " + existingUri);
                }
            } else {
                // Debido a que la entrada no existe, es removida de la base de datos
                Uri deleteUri = ContractParaGastos.CONTENT_URI.buildUpon()
                        .appendPath(id).build();
                Log.i(TAG, "Programando eliminación de: " + deleteUri);
                ops.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Insertar items resultantes
        for (Gasto e : expenseMap.values()) {
            Log.i(TAG, "Programando inserción de: " + e.idGasto);
            ops.add(ContentProviderOperation.newInsert(ContractParaGastos.CONTENT_URI)
                    .withValue(ContractParaGastos.Columnas.ID_REMOTA, e.idGasto)
                    .withValue(ContractParaGastos.Columnas.MONTO, e.monto)
                    .withValue(ContractParaGastos.Columnas.ETIQUETA, e.etiqueta)
                    .withValue(ContractParaGastos.Columnas.FECHA, e.fecha)
                    .withValue(ContractParaGastos.Columnas.DESCRIPCION, e.descripcion)
                    .build());
            syncResult.stats.numInserts++;
        }

        if (syncResult.stats.numInserts > 0 ||
                syncResult.stats.numUpdates > 0 ||
                syncResult.stats.numDeletes > 0) {
            Log.i(TAG, "Aplicando operaciones...");
            try {
                try {
					resolver.applyBatch(ContractParaGastos.AUTHORITY, ops);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            } catch ( OperationApplicationException e) {
                e.printStackTrace();
            }
            resolver.notifyChange(
                    ContractParaGastos.CONTENT_URI,
                    null,
                    false);
            Log.i(TAG, "Sincronización finalizada.");

        } else {
            Log.i(TAG, "No se requiere sincronización");
        }

    }

    /**
     * Inicia manualmente la sincronización
     *
     * @param context    Contexto para crear la petición de sincronización
     * @param onlyUpload Usa true para sincronizar el servidor o false para sincronizar el cliente
     */
    public static void sincronizarAhora(Context context, boolean onlyUpload) {
        Log.v(TAG, "Realizando petición de sincronización manual.");
        Bundle bundle = new Bundle();        
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        if (onlyUpload)
            bundle.putBoolean(ContentResolver.SYNC_EXTRAS_UPLOAD, true);
        ContentResolver.requestSync(obtenerCuentaASincronizar(context),
                context.getString(R.string.provider_authority), bundle);
    }

    /**
     * Crea u obtiene una cuenta existente
     *
     * @param context Contexto para acceder al administrador de cuentas
     * @return cuenta auxiliar.
     */
    public static Account obtenerCuentaASincronizar(Context context) {
        // Obtener instancia del administrador de cuentas
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Crear cuenta por defecto
        final Account newAccount = new Account(
                context.getString(R.string.app_name), Constantes.ACCOUNT_TYPE);

        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(newAccount)) {

            // Añadir la cuenta al account manager sin password y sin datos de usuario
            if (!accountManager.addAccountExplicitly(newAccount, "", null))
                return null;
        }
        
        Log.i(TAG, "Cuenta de usuario obtenida.");
        return newAccount;
    }

}