package aguasdelnorte.com.ar.agenda2.clases;

import java.util.ArrayList;

import aguasdelnorte.com.ar.agenda2.ui.ObjectView;
import aguasdelnorte.com.ar.agenda2.ui.Util;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
public class NotificacionesAD extends Elemento {
 private static final long serialVersionUID = 2277445452424780992L;
 public static final String table="notificaciones"; 
 public static final String table_sql="notificaciones";
 private long _id;
 private long idproceso;
 private String fechaalta;
 private String fechaautoriza;
 private String idusuarioautoriza;
 private long idprocesoautoriza;
 private String observacion;
 private long legajo;
 //---------------------------
 public NotificacionesAD(Activity oActivity) {
		super(oActivity);
		// TODO Auto-generated constructor stub
	}
 
public NotificacionesAD(Activity oActivity, long _id, long idproceso,
		String fechaalta, String fechaautoriza, String idusuarioautoriza,
		long idprocesoautoriza, String observacion, long legajo) {
	super(oActivity);
	this._id = _id;
	this.idproceso = idproceso;
	this.fechaalta = fechaalta;
	this.fechaautoriza = fechaautoriza;
	this.idusuarioautoriza = idusuarioautoriza;
	this.idprocesoautoriza = idprocesoautoriza;
	this.observacion = observacion;
	this.legajo = legajo;
}

public static String str="create table notificaciones (_id INTEGER ,idproceso INTEGER ,fechaalta TEXT ,fechaautoriza TEXT ,idusuarioautoriza TEXT ,idprocesoautoriza INTEGER ,observacion TEXT ,legajo INTEGER )";

public  String _update(){
String s="update notificaciones set "+ "idproceso= "+getIdproceso()+","+ "fechaalta= "+"'"+getFechaalta()+"'"+","+ "fechaautoriza= "+"'"+getFechaautoriza()+"'"+","+ "idusuarioautoriza= "+"'"+getIdusuarioautoriza()+"'"+","+ "idprocesoautoriza= "+getIdprocesoautoriza()+","+ "observacion= "+"'"+getObservacion()+"'"+","+ "legajo= "+getLegajo()+" where "+ "_id= "+get_id();
return s;
}

//---------------------------
public String _select (long id) {
	return "select _id,idproceso,fechaalta,fechaautoriza,idusuarioautoriza,idprocesoautoriza,observacion,legajo from  notificaciones where _id="+id;
}
//---------------------------
public  void setCursor(Cursor c){
try {
if (c.moveToFirst()) {
set_id(c.getLong(0));
setIdproceso(c.getLong(1));
setFechaalta(c.getString(2));
setFechaautoriza(c.getString(3));
setIdusuarioautoriza(c.getString(4));
setIdprocesoautoriza(c.getLong(5));
setObservacion(c.getString(6));
setLegajo(c.getLong(7));
}
} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("notificaciones", "error select-setCursor");			
		}
}
//---------------------------
public String _insert(){
String s="insert into notificaciones ( _id,idproceso,fechaalta,fechaautoriza,idusuarioautoriza,idprocesoautoriza,observacion,legajo) values ("+get_id()+","+getIdproceso()+","+"'"+getFechaalta()+"'"+","+"'"+getFechaautoriza()+"'"+","+"'"+getIdusuarioautoriza()+"'"+","+getIdprocesoautoriza()+","+"'"+getObservacion()+"'"+","+getLegajo()+")";
return s;
}

public long get_id() {
	return _id;
}

public void set_id(long _id) {
	this._id = _id;
}

public long getIdproceso() {
	return idproceso;
}

public void setIdproceso(long idproceso) {
	this.idproceso = idproceso;
}

public String getFechaalta() {
	return fechaalta;
}

public void setFechaalta(String fechaalta) {
	this.fechaalta = fechaalta;
}

public String getFechaautoriza() {
	return fechaautoriza;
}

public void setFechaautoriza(String fechaautoriza) {
	this.fechaautoriza = fechaautoriza;
}

public String getIdusuarioautoriza() {
	return idusuarioautoriza;
}

public void setIdusuarioautoriza(String idusuarioautoriza) {
	this.idusuarioautoriza = idusuarioautoriza;
}

public long getIdprocesoautoriza() {
	return idprocesoautoriza;
}

public void setIdprocesoautoriza(long idprocesoautoriza) {
	this.idprocesoautoriza = idprocesoautoriza;
}

public String getObservacion() {
	return observacion;
}

public void setObservacion(String observacion) {
	this.observacion = observacion;
}

public long getLegajo() {
	return legajo;
}

public void setLegajo(long legajo) {
	this.legajo = legajo;
}
public  int  Cantidad(){
	 openBD();
	 ArrayList<ObjectView> datos= new ArrayList<ObjectView>();	
	 String sql="select count(*) cantidad from "+table_sql + " where idproceso not in (select idproceso from "+ Notificaciones_vistasAD.table_sql+")";
	 Util.Log("sql=>"+sql);
	 Cursor cursor = db.rawQuery(sql, null);
	 int cantidad=0;
	 cursor.moveToFirst();
	    
	    while(!cursor.isAfterLast()) {
	        cantidad=cursor.getInt((cursor.getColumnIndex("cantidad")));
	        cursor.moveToNext();
	    }
	    cursor.close();	
	    closeBD();  
	 return cantidad;
}

private String[] SqlCampos() {
	// TODO Auto-generated method stub
	return new String[] {_SqlCampos()};
}
private String _SqlCampos() {
	// TODO Auto-generated method stub
	return "_id,idproceso,fechaalta,fechaautoriza,idusuarioautoriza,idprocesoautoriza,observacion,legajo";
}
public ArrayList<ObjectView>   getObjectView(){
	//table_sql="ot";
	//deleteAll();
	openBD();		
	ArrayList<ObjectView> datos= new ArrayList<ObjectView>();
	String[] campos =SqlCampos();
	//Cursor c = db.query(table, campos, null, null, null, null, null);
	Cursor c = db.rawQuery("select "+ _SqlCampos()+" from  notificaciones    order by fechaalta desc", null);
	if (c.moveToFirst()) { //Recorremos el cursor hasta que no haya más registros
		do {
			ObjectView o = new ObjectView();
			o.setId(c.getInt(1));		
			o.setTitulo("Error al procesar ");		
			o.setDescripcion(c.getString(6));
			o.setFecha(c.getString(3));				
			o.setId_proceso(c.getInt(1));	
			
			Util.Log("R:"+o.toString());
			datos.add(o);
			
		} while(c.moveToNext());
		}
	db.close();
	return datos;	
}
public void   ActualizarVistos(){
	openBD();
	String s="insert into notificaciones_vistas "
	+" select idproceso,idproceso from notificaciones where "
	+" idproceso not in (select idproceso from notificaciones_vistas)";
	db.execSQL(s);
	db.close();
}

}
