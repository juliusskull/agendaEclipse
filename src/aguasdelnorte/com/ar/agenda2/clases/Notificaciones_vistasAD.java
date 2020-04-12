package aguasdelnorte.com.ar.agenda2.clases;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
public class Notificaciones_vistasAD extends Elemento {
private static final long serialVersionUID = -3282533166668560302L;
public static final String table="notificaciones_vistas"; 
public static final String table_sql="notificaciones_vistas";

private long _id;
private long idproceso;

public Notificaciones_vistasAD(Activity oActivity) {
	super(oActivity);
	// TODO Auto-generated constructor stub
}
 public static String str="create table notificaciones_vistas (_id INTEGER ,idproceso INTEGER )";
//---------------------------
public  String _update(){
String s="update notificaciones_vistas set "+ "idproceso= "+getIdproceso()+" where "+ "_id= "+get_id();
return s;
}

//---------------------------
public String _select (long id) {
	return "select _id,idproceso from  notificaciones_vistas where _id="+id;
}
//---------------------------
public  void setCursor(Cursor c){
try {
if (c.moveToFirst()) {
set_id(c.getLong(0));
setIdproceso(c.getLong(1));
}
} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("notificaciones_vistas", "error select-setCursor");			
		}
}
//---------------------------
public String _insert(){
String s="insert into notificaciones_vistas ( _id,idproceso) values ("+get_id()+","+getIdproceso()+")";
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

}
