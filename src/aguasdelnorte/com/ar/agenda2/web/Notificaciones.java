package aguasdelnorte.com.ar.agenda2.web;

public class Notificaciones {
	 private long id;
	 private long idproceso;
	 private String fechaalta;
	 private String fechaautoriza;
	 private String idusuarioautoriza;
	 private long idprocesoautoriza;
	 private String observacion;
	// private long legajo;
	public long get_id() {
		return id;
	}
	public void set_id(long _id) {
		this.id = _id;
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
	
	 
}
