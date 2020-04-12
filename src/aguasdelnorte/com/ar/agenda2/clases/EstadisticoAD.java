package aguasdelnorte.com.ar.agenda2.clases;

import java.util.ArrayList;
import java.util.List;

import aguasdelnorte.com.ar.agenda2.provider.BD;
import aguasdelnorte.com.ar.agenda2.ui.ObjectView;
import aguasdelnorte.com.ar.agenda2.ui.Util;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class EstadisticoAD {
	protected BD mdb;
	protected SQLiteDatabase db;
	protected Activity oActivity;
	public static final String G="select etiqueta,count(*) valores from gasto group by etiqueta";  
	public static final String AGENDA_PENDIENTE="select categoria etiqueta,count(*) valores from agenda_pendiente group by 1";
	
	public  String SQL=G;
	public EstadisticoAD(Activity oActivity){
		this.oActivity=oActivity;
		
	}
	protected void openBD() {		
		try {
			mdb= new BD(oActivity);
			db=mdb.getWritableDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}		
	}
	protected void closeBD() {
		try {
			db.close();
			mdb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}		
	}
 public Float[] PIEgetArray(){
	 openBD();
	 Cursor cursor = db.rawQuery(SQL, null);
	 cursor.moveToFirst();
	    List<Float> names = new ArrayList<Float>();
	   
	    while(!cursor.isAfterLast()) {
	    		
	        names.add(cursor.getFloat(cursor.getColumnIndex("valores")));
	        cursor.moveToNext();
	    }
	    cursor.close();	
	    
	    closeBD();  
	 return names.toArray((new Float[names.size()]));
 }
 public String[] PIEgetTitulo(){
	 openBD();
	 Cursor cursor = db.rawQuery(SQL, null);
	 cursor.moveToFirst();
	    ArrayList<String> names = new ArrayList<String>();
	    while(!cursor.isAfterLast()) {
	        names.add(cursor.getString(cursor.getColumnIndex("etiqueta")));
	        cursor.moveToNext();
	    }
	    cursor.close();	
	    closeBD();  
	 return names.toArray((new String[names.size()]));
 }
public String getSQL() {
	return SQL;
}
public void setSQL(String sQL) {
	SQL = sQL;
}

	public  ArrayList<ObjectView>   Resumen(){
	 openBD();
	 ArrayList<ObjectView> datos= new ArrayList<ObjectView>();	
	 String sql="select * from(" +
	 		" select 'Compras' as categoria,'Tareas pendientes de Compras' as subtitulo,( "
	+" select count(*)from  agenda_pendiente where idproceso not in (select idRemota from proceso ) and categoria='C') cantidad"
	+" union select 'Facturas' as categoria,'Facturas Pendientes' as subtitulo,("
	+" select count(*)from  agenda_pendiente where idproceso not in (select idRemota from proceso ) and categoria='F') cantidad"
	+" union select 'RRHH' as categoria,'Tareas pendientes' as subtitulo,("
	+" select count(*)from  agenda_pendiente where idproceso not in (select idRemota from proceso ) and categoria='R') cantidad"
	+" )  order by 3 desc";
	 Util.Log("sql=>"+sql);
	 Cursor cursor = db.rawQuery(sql, null);
	 cursor.moveToFirst();
	    
	    while(!cursor.isAfterLast()) {
	        String descripcion=cursor.getString(cursor.getColumnIndex("categoria"));
	        datos.add(new ObjectView(Categoria.getCategoriaPosicion(descripcion.toString().charAt(0)),descripcion,cursor.getString(cursor.getColumnIndex("subtitulo")),1,cursor.getString(cursor.getColumnIndex("cantidad")),"pendientes"));
	        cursor.moveToNext();
	    }
	    cursor.close();	
	    closeBD();  
	 return datos;
}
 


}
