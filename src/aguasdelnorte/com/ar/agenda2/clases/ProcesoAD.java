package aguasdelnorte.com.ar.agenda2.clases;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;

public class ProcesoAD extends Elemento {
	
	private static final long serialVersionUID = 8727873610139844816L;
	public static final String table="proceso"; 
	public static final String table_sql="proceso";
	public ProcesoAD(Activity oActivity) {
		super(oActivity);
		// TODO Auto-generated constructor stub
	}
	
	public ProcesoAD(Activity oActivity, String valor, String etiqueta,
			String fecha, String descripcion, String id_remota) {
		super(oActivity);
		this.valor = valor;
		this.etiqueta = etiqueta;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.id_remota = id_remota;
		
	}

	private long _id;
	 private String valor;
	 private String etiqueta;
	 private String fecha;
	 private String descripcion;
	 private String id_remota;
	 private long estado;
	 private long pendiente_insercion;
	 private String usuario;
	 public static String str="CREATE TABLE proceso ( "+
"_id INTEGER PRIMARY KEY AUTOINCREMENT "+
", valor TEXT "+
", etiqueta TEXT "+
", fecha TEXT "+
", descripcion TEXT "+
",idRemota TEXT "+
",estado INTEGER NOT NULL DEFAULT 0"+
",pendiente_insercion INTEGER NOT NULL DEFAULT 0 "+
",usuario TEXT )";
	//---------------------------
	public  String _update(){
	String s="update proceso set "+ "valor= "+"'"+getMonto()+"'"+","+ "etiqueta= "+"'"+getEtiqueta()+"'"+","+ "fecha= "+"'"+getFecha()+"'"+","+ "descripcion= "+"'"+getDescripcion()+"'"+","+ "usuario= "+"'"+getUsuario()+"'"+" where "+ "_id= "+get_id();
	return s;
	}

	//---------------------------
	public String _select (long id) {
		return "select _id,valor,etiqueta,fecha,descripcion,usuario,idRemota from  proceso where _id="+id;
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
	setUsuario(c.getString(5));
	setId_remota(c.getString(6));
	}
	} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.v("proceso", "error select-setCursor");			
			}
	}
	//---------------------------
	public String _insert(){
	String s="insert into proceso ( monto,etiqueta,fecha,descripcion,usuario) values ("+"'"+getMonto()+"'"+","+"'"+getEtiqueta()+"'"+","+"'"+getFecha()+"'"+","+"'"+getDescripcion()+"'"+","+"'"+getUsuario()+"'"+")";
	return s;
	}

	

	public String getMonto() {
		return valor;
	}

	public void setMonto(String monto) {
		this.valor = monto;
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public static String getStr() {
		return str;
	}

	public static void setStr(String str) {
		ProcesoAD.str = str;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId_remota() {
		return id_remota;
	}

	public void setId_remota(String id_remota) {
		this.id_remota = id_remota;
	}
	
}
