package aguasdelnorte.com.ar.agenda2.clases;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
public class NotificacionAD extends Elemento {

 private static final long serialVersionUID = -2492576486949604143L;
 public static final String table="notificacion"; 
 public static final String table_sql="notificacion";
 private long _id;
 private String descripcion;
 private String legajo;
 private String fchalta;
 public static String str="create table notificacion (_id INTEGER PRIMARY KEY ,descripcion TEXT ,legajo TEXT ,fchalta TEXT )";
//---------------------------
 
 public NotificacionAD(Activity oActivity) {
		super(oActivity);
		// TODO Auto-generated constructor stub
}
 
public NotificacionAD(Activity oActivity, long _id, String descripcion,
		String legajo, String fchalta) {
	super(oActivity);
	this._id = _id;
	this.descripcion = descripcion;
	this.legajo = legajo;
	this.fchalta = fchalta;
}

public  String _update(){
	String s="update notificacion set "+ "id= "+get_id()+","+ "descripcion= "+"'"+getDescripcion()+"'"+","+ "legajo= "+"'"+getLegajo()+"'"+","+ "fchalta= "+"'"+getFchalta()+"'"+" where ";
	return s;
}

//---------------------------
public String _select (long id) {
	return "select _id,descripcion,legajo,fchalta from  notificacion where _id="+id;
}
//---------------------------
public  void setCursor(Cursor c){
try {
	if (c.moveToFirst()) {
	set_id(c.getLong(0));
	setDescripcion(c.getString(1));
	setLegajo(c.getString(2));
	setFchalta(c.getString(3));
	}
} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("notificacion", "error select-setCursor");			
		}
}
//---------------------------
public String _insert(){
String s="insert into notificacion (id,descripcion,legajo,fchalta) values ("+get_id()+","+"'"+getDescripcion()+"'"+","+"'"+getLegajo()+"'"+","+"'"+getFchalta()+"'"+")";
return s;
}

public long get_id() {
	return _id;
}

public void set_id(long _id) {
	this._id = _id;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public String getLegajo() {
	return legajo;
}

public void setLegajo(String legajo) {
	this.legajo = legajo;
}

public String getFchalta() {
	return fchalta;
}

public void setFchalta(String fchalta) {
	this.fchalta = fchalta;
}

}