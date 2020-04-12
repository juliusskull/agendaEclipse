package aguasdelnorte.com.ar.agenda2.provider;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Clase envoltura para el gestor de Bases de datos
 */
public class BD extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "agenda.db";
    /**
     * Versión actual de la base de datos
     */
    private static final int DATABASE_VERSION = 73;
    
    public BD(Context context) {
    		super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public BD(Context context,
                          String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

     /**
     * Crear tabla en la base de datos
     *
     * @param database Instancia de la base de datos
     */
    @Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		createStructura(arg0);
		
	}
	public void createStructura(SQLiteDatabase db){
		Log.v("BD", "crear bd");
		for (int i = 0; i < ElementoAD.tablas_insert().size(); i++) {
			db.execSQL(ElementoAD.tablas_insert().get(i));
		}
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < ElementoAD.tablas_name().size(); i++) {
			db.execSQL("drop table if exists "+ElementoAD.tablas_name().get(i));
		}
		
		createStructura(db);
	}
	
	public void onUpgradeExpress() {
		// TODO Auto-generated method stub
		SQLiteDatabase db = this.getWritableDatabase();
		for (int i = 0; i < ElementoAD.tablas_name().size(); i++) {
			db.execSQL("drop table if exists "+ElementoAD.tablas_name().get(i));
		}
		
		createStructura(db);
	}

}