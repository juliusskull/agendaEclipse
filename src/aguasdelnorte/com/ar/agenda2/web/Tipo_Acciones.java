package aguasdelnorte.com.ar.agenda2.web;

public class Tipo_Acciones {
	private int _id;
	private String categoria;
	private String descripcion;
	
	
	public Tipo_Acciones(int id, String categoria, String descripcion) {
		super();
		this._id = id;
		this.categoria = categoria;
		this.descripcion = descripcion;
	}
	public int getId() {
		return _id;
	}
	public void setId(int id) {
		this._id = id;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(	String categoria) {
		this.categoria = categoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return descripcion;
	}
	
	
}
