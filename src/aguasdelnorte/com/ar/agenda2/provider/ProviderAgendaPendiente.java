package aguasdelnorte.com.ar.agenda2.provider;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Content Provider personalizado para los gastos
 */
public class ProviderAgendaPendiente extends ContentProvider {
    /**
     * Nombre de la base de datos
     */
    private static final String DATABASE_NAME = BD.DATABASE_NAME;
    /**
     * Versión actual de la base de datos
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Instancia global del Content Resolver
     */
    private ContentResolver resolver;
    /**
     * Instancia del administrador de BD
     */
    private BD databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
     	  databaseHelper = new BD(
                   getContext()
                   
           );
        resolver = getContext().getContentResolver();

        return true;
    }

    @Override
    public Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {

        // Obtener base de datos
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // Comparar Uri
        int match = ContractAgenda_Pendiente.uriMatcher.match(uri);

        Cursor c;

        switch (match) {
            case ContractAgenda_Pendiente.ALLROWS:
                // Consultando todos los registros
                c = db.query(ContractAgenda_Pendiente.TABLA, projection,
                        selection, selectionArgs,
                        null, null, sortOrder);
                c.setNotificationUri(
                        resolver,
                        ContractAgenda_Pendiente.CONTENT_URI);
                break;
            case ContractAgenda_Pendiente.SINGLE_ROW:
                // Consultando un solo registro basado en el Id del Uri
                long idGasto = ContentUris.parseId(uri);
                c = db.query(ContractAgenda_Pendiente.TABLA, projection,
                		ContractAgenda_Pendiente.Columnas._ID + " = " + idGasto,
                        selectionArgs, null, null, sortOrder);
                c.setNotificationUri(
                        resolver,
                        ContractAgenda_Pendiente.CONTENT_URI);
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return c;

    }

    @Override
    public String getType(Uri uri) {
        switch (ContractAgenda_Pendiente.uriMatcher.match(uri)) {
            case ContractAgenda_Pendiente.ALLROWS:
                return ContractAgenda_Pendiente.MULTIPLE_MIME;
            case ContractAgenda_Pendiente.SINGLE_ROW:
                return ContractAgenda_Pendiente.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de gasto desconocido: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Validar la uri
        if (ContractAgenda_Pendiente.uriMatcher.match(uri) != ContractAgenda_Pendiente.ALLROWS) {
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }

        // Inserción de nueva fila
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(ContractAgenda_Pendiente.TABLA, null, contentValues);
        if (rowId > 0) {
            Uri uri_gasto = ContentUris.withAppendedId(
            		ContractAgenda_Pendiente.CONTENT_URI, rowId);
            resolver.notifyChange(uri_gasto, null, false);
            return uri_gasto;
        }
        throw new SQLException("Falla al insertar fila en : " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int match = ContractAgenda_Pendiente.uriMatcher.match(uri);
        int affected;

        switch (match) {
            case ContractAgenda_Pendiente.ALLROWS:
                affected = db.delete(ContractAgenda_Pendiente.TABLA,
                        selection,
                        selectionArgs);
                break;
            case ContractAgenda_Pendiente.SINGLE_ROW:
                long idGasto = ContentUris.parseId(uri);
                affected = db.delete(ContractAgenda_Pendiente.TABLA,
                		ContractAgenda_Pendiente.Columnas.IDPROCESO + "=" + idGasto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                resolver.
                        notifyChange(uri, null, false);
                break;
            default:
                throw new IllegalArgumentException("Elemento "+ContractAgenda_Pendiente.TABLA+" desconocido: " +
                        uri);
        }
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (ContractAgenda_Pendiente.uriMatcher.match(uri)) {
            case ContractAgenda_Pendiente.ALLROWS:
                affected = db.update(ContractAgenda_Pendiente.TABLA, values,
                        selection, selectionArgs);
                break;
            case ContractAgenda_Pendiente.SINGLE_ROW:
                String idGasto = uri.getPathSegments().get(1);
                affected = db.update(ContractAgenda_Pendiente.TABLA, values,
                		ContractAgenda_Pendiente.Columnas.IDPROCESO + "=" + idGasto
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        resolver.notifyChange(uri, null, false);
        return affected;
    }

}