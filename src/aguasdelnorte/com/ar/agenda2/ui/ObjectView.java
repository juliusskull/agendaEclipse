package aguasdelnorte.com.ar.agenda2.ui;

public class ObjectView {
	String titulo;
	String descripcion;
	String fecha;
	String titulo_left="";
	String descripcion_left="";
	String etiqueta;
	String tipo="";
	//----------------- AGREGADO SOLO PARA LA TABLA AGENDA PENDIENTE
	int id_proceso=0;
	//------------------
	
	int drableid=0;
	int _id;
	public final int  TIPO_TEXTO=1;
	public final int  TIPO_TEXTO_NUMBER=2; 
	public final int  TIPO_CHECK_BOX=3; 
	int tipo_dato=TIPO_TEXTO;
	
	public String getTitulo() {		
		return (titulo.compareTo("null")==0)?"-": titulo;
	}
	
	public ObjectView(int _id,String titulo, String descripcion, int drableid,String tipo) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.drableid = drableid;
		this._id = _id;
		this.tipo = tipo;
	}
	public ObjectView(int _id,String titulo, String descripcion, int drableid) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.drableid = drableid;
		this._id = _id;
	}
	public ObjectView(int _id,String titulo, String descripcion, int drableid,String titulo_left, String descripcion_left) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.drableid = drableid;
		
		this.titulo_left = titulo_left;
		this.descripcion_left= descripcion_left;
		this._id = _id;
	}
	public ObjectView() {
		super();
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion==null?"":(descripcion.equals("null")?"":descripcion);
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}


	public String toString2() {
		return "ObjectView [titulo=" + titulo + ", descripcion=" + descripcion
				+ ", drableid=" + drableid + ", _id=" + _id + "]";
	}
	@Override
	public String toString() {
		return titulo;
	}
	public int getDrableid() {
		return drableid;
	}

	public void setDrableid(int drableid) {
		this.drableid = drableid;
	}

	public int getTipo_dato() {
		return tipo_dato;
	}

	public void setTipo_dato(int tipo_dato) {
		this.tipo_dato = tipo_dato;
	}


	public String getTitulo_left() {
		return titulo_left;
	}


	public void setTitulo_left(String titulo_left) {
		this.titulo_left = titulo_left;
	}


	public String getDescripcion_left() {
		return descripcion_left;
	}


	public void setDescripcion_left(String descripcion_left) {
		this.descripcion_left = descripcion_left;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getEtiqueta() {
		return etiqueta;
	}


	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	public int getId_proceso() {
		return id_proceso;
	}

	public void setId_proceso(int id_proceso) {
		this.id_proceso = id_proceso;
	}

		

}
