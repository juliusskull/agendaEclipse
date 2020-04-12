package aguasdelnorte.com.ar.agenda2.clases;
import java.util.ArrayList;

import aguasdelnorte.com.ar.agenda2.ui.ObjectView;
import aguasdelnorte.com.ar.agenda2.ui.Util;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.provider.BaseColumns;
import android.util.Log;
public class Agenda_pendienteAD extends Elemento {
	public Agenda_pendienteAD(Activity oActivity) {
		super(oActivity);
		// TODO Auto-generated constructor stub
	}
	public static String CAMPO_TITULO= "Titulo:";
	public static String CAMPO_CATEGORIA= "Categoria:";
	public static String CAMPO_SUBCATEGORIA= "SubCategoria:";
	public static String CAMPO_GERENCIA= "Gerencia:";
	public static String CAMPO_FECHA_INGRESO= "Fecha de Ingreso:";
	public static String CAMPO_FECHA_GESTION= "Fecha de Gestion:";
	public static String CAMPO_FECHA_DESDE= "Fecha desde:";
	public static String CAMPO_FECHA_HASTA= "Fecha Hasta:";
	public static String CAMPO_MONTO= "Monto:";
	public static String CAMPO_FECHA_NUMERO= "Numero:";
	public static String CAMPO_FECHA_OBSERVACION= "Observacion:";
	
	public static String ESTADO_AUTORIZA= "S";
	public static String ESTADO_NOAUTORIZA= "N";
	
	public Agenda_pendienteAD(Activity oActivity, String idproceso,
			String categoria, String idsubcategoria, String subcategoria,
			String legajo, String numero, String titulo, String fchingreso,
			String fchgestion, String fchdesde, String fchhasta,
			String unidadmedida, String monto, String observacion,
			String idgerencia, String gerencia, String accion, String autoriza) {
		super(oActivity);		
		this.idproceso = Long.parseLong(idproceso);
		this.categoria = categoria;
		this.idsubcategoria = Long.parseLong(idsubcategoria);
		this.subcategoria = subcategoria;
		this.legajo = Long.parseLong(legajo);
		this.numero = Long.parseLong(numero);
		this.titulo = titulo;
		this.fchingreso = fchingreso;
		this.fchgestion = fchgestion;
		this.fchdesde = fchdesde;
		this.fchhasta = fchhasta;
		this.unidadmedida = unidadmedida;
		this.monto = monto;
		this.observacion = observacion;
		this.idgerencia = Long.parseLong(idgerencia);
		this.gerencia = gerencia;
		this.accion = accion;
		this.autoriza = autoriza;
	}

	private static final long serialVersionUID = 6694308501329213575L;
	public static final String table = "agenda_pendiente";
	public static final String table_sql="agenda_pendiente";
		
	 private long   idproceso;
	 private String categoria;
	 private long   idsubcategoria;
	 private String subcategoria;
	 private long   legajo;
	 private long   numero;
	 private String titulo;
	 private String fchingreso;
	 private String fchgestion;
	 private String fchdesde;
	 private String fchhasta;
	 private String unidadmedida;
	 private String monto;
	 private String observacion;
	 private long   idgerencia;
	 private String gerencia;
	 private String accion;
	 private long   estado;
	 private long   pendiente_insercion;
	 private String autoriza;
	 
	 public void load(String p_idproceso) {
		 openBD();
		 String[] s_where = new String[] {p_idproceso};
		 Cursor c = db.query(table, SqlCampos(), "_id=?", s_where, null, null, null);
		// c.moveToFirst();
		 
		 setCursor(c);			
		 this.closeBD();
	}
	 public ArrayList<ObjectView>   getCamposObjectView(){
					
			ArrayList<ObjectView> datos= new ArrayList<ObjectView>();
			datos.add(new ObjectView(1,CAMPO_TITULO , getTitulo(), 0));
			datos.add(new ObjectView(2,CAMPO_CATEGORIA , getCategoria(), 0));
			datos.add(new ObjectView(3,CAMPO_SUBCATEGORIA , getSubcategoria(), 0));
			datos.add(new ObjectView(4,CAMPO_GERENCIA , getGerencia(), 0));
			datos.add(new ObjectView(5,CAMPO_FECHA_INGRESO ,getFchingreso(), 0));
			datos.add(new ObjectView(6,CAMPO_FECHA_GESTION ,getFchgestion(), 0));
			datos.add(new ObjectView(7,CAMPO_FECHA_DESDE , getFchdesde(), 0));
			datos.add(new ObjectView(8,CAMPO_FECHA_HASTA , getFchhasta(), 0));
			datos.add(new ObjectView(9,CAMPO_MONTO, getMonto(), 0,getUnidadmedida()));
			datos.add(new ObjectView(10,CAMPO_FECHA_NUMERO ,String.valueOf(numero), 0));
			datos.add(new ObjectView(11,CAMPO_FECHA_OBSERVACION ,getObservacion(), 0));
			Util.Log("Agenda=>"+toString());
			return datos;
			
		}	
	
	public long getIdproceso() {
		return idproceso;
	}
	public void setIdproceso(long idproceso) {
		this.idproceso = idproceso;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public long getIdsubcategoria() {
		return idsubcategoria;
	}
	public void setIdsubcategoria(long idsubcategoria) {
		this.idsubcategoria = idsubcategoria;
	}
	public String getSubcategoria() {
		return subcategoria;
	}
	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria;
	}
	public long getLegajo() {
		return legajo;
	}
	public void setLegajo(long legajo) {
		this.legajo = legajo;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getFchingreso() {
		return fchingreso;
	}
	public void setFchingreso(String fchingreso) {
		this.fchingreso = fchingreso;
	}
	public String getFchgestion() {
		return fchgestion;
	}
	public void setFchgestion(String fchgestion) {
		this.fchgestion = fchgestion;
	}
	public String getFchdesde() {
		return fchdesde;
	}
	public void setFchdesde(String fchdesde) {
		this.fchdesde = fchdesde;
	}
	public String getFchhasta() {
		return fchhasta;
	}
	public void setFchhasta(String fchhasta) {
		this.fchhasta = fchhasta;
	}
	public String getUnidadmedida() {
		return unidadmedida;
	}
	public void setUnidadmedida(String unidadmedida) {
		this.unidadmedida = unidadmedida;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public long getIdgerencia() {
		return idgerencia;
	}
	public void setIdgerencia(long idgerencia) {
		this.idgerencia = idgerencia;
	}
	public String getGerencia() {
		return gerencia;
	}
	public void setGerencia(String gerencia) {
		this.gerencia = gerencia;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
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


	public String getAutoriza() {
		return autoriza;
	}
	public void setAutoriza(String autoriza) {
		this.autoriza = autoriza;
	}
	public static String str="create table agenda_pendiente (_id INTEGER PRIMARY KEY,idproceso INTEGER ,categoria TEXT ,idsubcategoria INTEGER ,subcategoria TEXT ,legajo INTEGER ,numero INTEGER ,titulo TEXT ,fchingreso TEXT ,fchgestion TEXT ,fchdesde TEXT ,fchhasta TEXT ,unidadmedida TEXT ,monto TEXT ,observacion TEXT ,idgerencia INTEGER ,gerencia TEXT ,accion TEXT ,estado INTEGER NOT NULL DEFAULT 0,pendiente_insercion INTEGER NOT NULL DEFAULT 0,autoriza TEXT)";
	//---------------------------
	public  String _update(){
		String s="update agenda_pendiente set "+ "categoria= "+"'"+getCategoria()+"'"+","+ "idsubcategoria= "+getIdsubcategoria()+","+ "subcategoria= "+"'"+getSubcategoria()+"'"+","+ "legajo= "+getLegajo()+","+ "numero= "+getNumero()+","+ "titulo= "+"'"+getTitulo()+"'"+","+ "fchingreso= "+"'"+getFchingreso()+"'"+","+ "fchgestion= "+"'"+getFchgestion()+"'"+","+ "fchdesde= "+"'"+getFchdesde()+"'"+","+ "fchhasta= "+"'"+getFchhasta()+"'"+","+ "unidadmedida= "+"'"+getUnidadmedida()+"'"+","+ "monto= "+"'"+getMonto()+"'"+","+ "observacion= "+"'"+getObservacion()+"'"+","+ "idgerencia= "+getIdgerencia()+","+ "gerencia= "+"'"+getGerencia()+"'"+","+ "accion= "+"'"+getAccion()+"'"+" where "+ "idproceso= "+getIdproceso();
		return s;
	}

	//---------------------------
	public String _select (long id) {
		return "select idproceso,categoria,idsubcategoria,subcategoria,legajo,numero,titulo,fchingreso,fchgestion,fchdesde,fchhasta,unidadmedida,monto,observacion,idgerencia,gerencia,accion,estado,pendiente_insercion,autoriza from  agenda_pendiente where _id="+id;
	}
	//---------------------------
	public  void setCursor(Cursor c){
	try {
		Log.v("agenda_pendiente", "=>setCursor");
		String[] rrr = c.getColumnNames();
		
		for(int i=0;i<rrr.length;i++){
			Log.v("agenda_pendiente", "=>setCursor c"+i+":"+rrr[i]);
			
		}	
		
		if (c.moveToFirst()) {
			Log.v("agenda_pendiente", "=>setCursor ----------------------------------");
			
			setTitulo(c.getString(1));
			setObservacion(c.getString(c.getColumnIndex(Columnas.observacion)));			
			setIdproceso(c.getInt(c.getColumnIndex(Columnas.idproceso)));	
			
			setCategoria(c.getString(c.getColumnIndex(Columnas.categoria)));
			setIdsubcategoria(c.getInt(c.getColumnIndex(Columnas.idsubcategoria)));			
			setSubcategoria(c.getString(c.getColumnIndex(Columnas.subcategoria)));
			setLegajo(c.getInt(c.getColumnIndex(Columnas.legajo)));
			setNumero(c.getInt(c.getColumnIndex(Columnas.numero)));			
			setFchingreso(c.getString(c.getColumnIndex(Columnas.fchingreso)));
			setFchgestion(c.getString(c.getColumnIndex(Columnas.fchgestion)));
			setFchdesde(c.getString(c.getColumnIndex(Columnas.fchdesde)));
			setFchhasta(c.getString(c.getColumnIndex(Columnas.fchhasta)));
			setUnidadmedida(c.getString(c.getColumnIndex(Columnas.unidadmedida)));
			setMonto(c.getString(c.getColumnIndex(Columnas.monto)));
			/**/
			setIdgerencia(c.getInt(c.getColumnIndex(Columnas.idgerencia)));
			setGerencia(c.getString(c.getColumnIndex(Columnas.gerencia)));
			setAccion(c.getString(c.getColumnIndex(Columnas.accion)));
			//setEstado(c.getInt(c.getColumnIndex(Columnas.estado)));
			//setPendiente_insercion(c.getInt(c.getColumnIndex(Columnas.pendiente_insercion)));
			//setAutoriza(c.getString(18));	
			
			Log.v("agenda_pendiente", "=>setCursor id:"+c.getColumnIndex("autoriza"));	
			Log.v("agenda_pendiente", "=>setCursor:"+c.getString(18));	
			setAutoriza(c.getString(18));
			
		}
	} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.v("agenda_pendiente", "=>setCursor:error:"+e.getMessage());			
			}
	}
	//---------------------------
	public String _insert(){
	String s="insert into agenda_pendiente (idproceso,categoria,idsubcategoria,subcategoria,legajo,numero,titulo,fchingreso,fchgestion,fchdesde,fchhasta,unidadmedida,monto,observacion,idgerencia,gerencia,accion,autoriza) values ("+getIdproceso()+",'"+getCategoria()+"'"+","+getIdsubcategoria()+","+"'"+getSubcategoria()+"'"+","+getLegajo()+","+getNumero()+","+"'"+getTitulo()+"'"+","+"'"+getFchingreso()+"'"+","+"'"+getFchgestion()+"'"+","+"'"+getFchdesde()+"'"+","+"'"+getFchhasta()+"'"+","+"'"+getUnidadmedida()+"'"+","+"'"+getMonto()+"'"+","+"'"+getObservacion()+"'"+","+getIdgerencia()+","+"'"+getGerencia()+"'"+","+"'"+getAccion()+"','"+getAutoriza() +"')";
	Log.v("sql", "sql=>"+s);
	return s;
	}
	
	public ArrayList<ObjectView> getObjectView(){
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
				o.setFecha(c.getString(9));				
				o.setId_proceso(c.getInt(3));	
				
				Util.Log("R:"+o.toString());
				datos.add(o);
				
			} while(c.moveToNext());
			}
		db.close();
		return datos;
		
	}
	public ArrayList<ObjectView>   getObjectViewFilterCategoria(String categoria, boolean todos){
		Util.Log("sql=>getObjectViewFilterCategoria");
		if(categoria.equals("")){
			return getObjectView();
			
		}
		Util.Log("----agenda-----");
		try {
			openBD();		
			ArrayList<ObjectView> datos= new ArrayList<ObjectView>();
			String[] campos =SqlCampos();
			Cursor c = null;
			if(todos){
				Util.Log("sql=>(1)");
				c = db.query(table, campos, "categoria=?", new String[] { categoria  }, null, null, null);
			}else{
				Util.Log("sql=>(2)");
				Util.Log("sql(s)=>select "+_SqlCampos()+" from "+table+" where idproceso not in (select idRemota from proceso ) and categoria=? ");
				c = db.rawQuery("select "+_SqlCampos()+" from "+table+" where idproceso not in (select idRemota from proceso ) and categoria=? ", new String[] { categoria  });
				
			}			
			if (c.moveToFirst()) { //Recorremos el cursor hasta que no haya más registros
				do {
					ObjectView o = new ObjectView();
					o.setId(c.getInt(0));		
					o.setTitulo(c.getString(1));		
					o.setDescripcion(c.getString(2));
					o.setFecha(c.getString(9));					
					o.setId_proceso(c.getInt(3));
					
					Util.Log("R:"+o.toString());
					datos.add(o);
					
				} while(c.moveToNext());
				}
			db.close();
			return datos;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
			return null;
		}
		
	}


	private String[] SqlCampos() {
		// TODO Auto-generated method stub
		return new String[] {_SqlCampos()};
	}
	private String _SqlCampos() {
		// TODO Auto-generated method stub
		return "_id,titulo,observacion,idproceso,categoria,idsubcategoria,subcategoria,legajo,numero,fchingreso,fchgestion,fchdesde,fchhasta,unidadmedida,monto,idgerencia,gerencia,accion,autoriza";
	}
	public int deleteAll(){
		openBD();
		 Error=0;
		
		try {
			String sql="delete from "+table_sql;
			Log.v("OT-2","delete:"+ sql);
			db.execSQL(sql);
			
			Error= 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Error=  0;
		}
			
			closeBD();
			return Error;
	}


	@Override
	public String toString() {
		return "Agenda_pendienteAD [idproceso=" + idproceso + ", categoria="
				+ categoria + ", idsubcategoria=" + idsubcategoria
				+ ", subcategoria=" + subcategoria + ", legajo=" + legajo
				+ ", numero=" + numero + ", titulo=" + titulo + ", fchingreso="
				+ fchingreso + ", fchgestion=" + fchgestion + ", fchdesde="
				+ fchdesde + ", fchhasta=" + fchhasta + ", unidadmedida="
				+ unidadmedida + ", monto=" + monto + ", observacion="
				+ observacion + ", idgerencia=" + idgerencia + ", gerencia="
				+ gerencia + ", accion=" + accion + ", estado=" + estado
				+ ", pendiente_insercion=" + pendiente_insercion +",autoriza="+autoriza+ "]";
	}
	public static class Columnas implements BaseColumns {

        private Columnas() {
            // Sin instancias
        }

        public final static String idproceso = "idproceso";
        public final static String categoria = "categoria";
        public final static String idsubcategoria = "idsubcategoria";
        public final static String subcategoria = "subcategoria";
        public static final String legajo = "legajo";
        public static final String numero = "numero";
        public final static String titulo = "titulo";
        public final static String fchingreso ="fchingreso";
        public final static String fchgestion ="fchgestion";
        public final static String fchdesde ="fchdesde";
        public final static String fchhasta ="fchhasta";
        public final static String unidadmedida ="unidadmedida";
        public final static String monto ="monto";
        public final static String observacion="observacion";
        public final static String idgerencia="idgerencia";
        public final static String gerencia="gerencia";
        public final static String accion="accion";
        public final static String estado="estado";
        public final static String pendiente_insercion="pendiente_insercion";
        public final static String autoriza="autoriza";

    }
	public boolean isPuedeAutorizar() {
		// TODO Auto-generated method stub
		if(autoriza.endsWith(ESTADO_AUTORIZA)){
			return true;
		}else{
			return false;
		}
		
		
	}
	 
}
