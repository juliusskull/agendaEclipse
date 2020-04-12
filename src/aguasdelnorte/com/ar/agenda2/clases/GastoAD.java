package aguasdelnorte.com.ar.agenda2.clases;

import java.util.ArrayList;

import aguasdelnorte.com.ar.agenda2.ui.ObjectView;
import aguasdelnorte.com.ar.agenda2.ui.Util;


import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
public class GastoAD extends Elemento {
 public GastoAD(Activity oActivity) {
		super(oActivity);
		// TODO Auto-generated constructor stub
	}
 
 /*
public GastoAD(Activity oActivity, long _id, String monto, String etiqueta,
		String fecha, String descripcion, String id_remota, long estado,
		long pendiente_insercion, String usuario) {
	super(oActivity);
	this._id = _id;
	this.monto = monto;
	this.etiqueta = etiqueta;
	this.fecha = fecha;
	this.descripcion = descripcion;
	this.id_remota = id_remota;
	this.estado = estado;
	this.pendiente_insercion = pendiente_insercion;
	this.usuario = usuario;
}*/
public GastoAD(Activity oActivity, String monto, String etiqueta, String fecha,
		String descripcion, String id_remota, String usuario) {
	super(oActivity);
	this.monto = monto;
	this.etiqueta = etiqueta;
	this.fecha = fecha;
	this.descripcion = descripcion;
	this.id_remota = String.valueOf(id_remota);
	this.estado = 0;
	this.pendiente_insercion = 0;
	this.usuario = usuario;
}

public static final String table="gasto"; 
public static final String table_sql="gasto"; 
private static final long serialVersionUID = -2936788826550319463L;
private long _id;
 private String monto;
 private String etiqueta;
 private String fecha;
 private String descripcion;
 private String id_remota;
 private long estado;
 private long pendiente_insercion;
 private String usuario;
 public static String str="CREATE TABLE gasto (_id INTEGER PRIMARY KEY AUTOINCREMENT, monto TEXT, etiqueta TEXT, fecha TEXT, descripcion TEXT,idRemota TEXT UNIQUE,estado INTEGER NOT NULL DEFAULT 0,pendiente_insercion INTEGER NOT NULL DEFAULT 0,usuario TEXT )";
//---------------------------
public String _update(){
String s="update "+table+" set "+ "_id= "+get_id()+","+ "monto= "+"'"+getMonto()+"'"+","+ "etiqueta= "+"'"+getEtiqueta()+"'"+","+ "fecha= "+"'"+getFecha()+"'"+","+ "descripcion= "+"'"+getDescripcion()+"'"+","+ "idremota= "+"'"+getId_remota()+"'"+","+ "estado= "+getEstado()+","+ "pendiente_insercion= "+getPendiente_insercion()+" where ";
return s;
}

//---------------------------
public String _select (long id) {
	return "select _id,monto,etiqueta,fecha,descripcion,idremota,estado,pendiente_insercion,usuario from  "+table+" where _id="+id;
}




//---------------------------
public  void setCursor(Cursor c){
try {
if (c.moveToFirst()) {
set_id(c.getLong(0));
setMonto(c.getString(1));
setEtiqueta(c.getString(2));
setFecha(c.getString(3));
setDescripcion(c.getString(4));
setId_remota(c.getString(5));
setEstado(c.getLong(6));
setPendiente_insercion(c.getLong(7));
setUsuario(c.getString(8));
}
} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("gasto", "error select-setCursor");			
		}
}
//---------------------------
public String _insert(){
String s="insert into "+table+" (monto,etiqueta,fecha,descripcion,idremota,estado,pendiente_insercion,usuario) values ("+"'"+getMonto()+"'"+","+"'"+getEtiqueta()+"'"+","+"'"+getFecha()+"'"+","+"'"+getDescripcion()+"'"+","+"'"+getId_remota()+"'"+","+getEstado()+","+getPendiente_insercion()+",'"+ getUsuario()+ "')";
return s;
}

public ArrayList<ObjectView>   getObjectView(){
	//table_sql="ot";
	//deleteAll();
	openBD();
	
	ArrayList<ObjectView> datos= new ArrayList<ObjectView>();
	String[] campos =SqlCampos();
	Cursor c = db.query(table, campos, null, null, null, null, null);
	if (c.moveToFirst()) { //Recorremos el cursor hasta que no haya más registros
		do {
			ObjectView o = new ObjectView();
			o.setId(c.getInt(0));		
			o.setTitulo(c.getString(1));		
			o.setDescripcion(c.getString(2));
			o.setFecha(c.getString(3));
			//o.setFecha(c.getString(3));			
			Util.Log("R:"+o.toString());
			datos.add(o);
			
		} while(c.moveToNext());
		}
	db.close();
	return datos;
	
}

private String[] SqlCampos() {
	// TODO Auto-generated method stub
	return new String[] {_SqlCampos()};
}
private String _SqlCampos() {
	// TODO Auto-generated method stub
	return "_id,monto,etiqueta,fecha,descripcion,idremota,estado,pendiente_insercion,usuario";
}
public long get_id() {
	return _id;
}

public void set_id(long _id) {
	this._id = _id;
}

public String getMonto() {
	return monto;
}

public void setMonto(String monto) {
	this.monto = monto;
}

public String getEtiqueta() {
	return etiqueta;
}

public void setEtiqueta(String etiqueta) {
	this.etiqueta = etiqueta;
}

public String getFecha() {
	return fecha;
}

public void setFecha(String fecha) {
	this.fecha = fecha;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}

public String getId_remota() {
	return id_remota;
}

public void setId_remota(String id_remota) {
	this.id_remota = id_remota;
}

public long getEstado() {
	return estado;
}

public void setEstado(long estado) {
	this.estado = estado;
}

public long getPendiente_insercion() {
	return pendiente_insercion;
}

public void setPendiente_insercion(long pendiente_insercion) {
	this.pendiente_insercion = pendiente_insercion;
}

public String getUsuario() {
	return usuario;
}

public void setUsuario(String usuario) {
	this.usuario = usuario;
}

}
