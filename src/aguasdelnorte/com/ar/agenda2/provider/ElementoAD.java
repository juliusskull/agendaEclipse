package aguasdelnorte.com.ar.agenda2.provider;

import java.util.ArrayList;
import java.util.List;

import aguasdelnorte.com.ar.agenda2.clases.Agenda_pendienteAD;
import aguasdelnorte.com.ar.agenda2.clases.GastoAD;
import aguasdelnorte.com.ar.agenda2.clases.NotificacionAD;
import aguasdelnorte.com.ar.agenda2.clases.NotificacionesAD;
import aguasdelnorte.com.ar.agenda2.clases.Notificaciones_vistasAD;
import aguasdelnorte.com.ar.agenda2.clases.ProcesoAD;
import android.content.Context;


public class ElementoAD {
	public static List<String> tablas_insert(){
		List<String>	lista= new ArrayList<String>();
		lista.add(Agenda_pendienteAD.str);
		lista.add(GastoAD.str);
		lista.add(ProcesoAD.str);
		lista.add(NotificacionesAD.str);		
		lista.add(Notificaciones_vistasAD.str);
		
		return lista;
	}
	public static List<String> tablas_name(){
		List<String>	lista= new ArrayList<String>();
		lista.add(Agenda_pendienteAD.table);
		lista.add(GastoAD.table);
		lista.add(ProcesoAD.table);
		lista.add(NotificacionesAD.table);
		lista.add(Notificaciones_vistasAD.table);
	
		return lista;
	}
	public static void DELETE_DB(Context oActivity){
		BD mdb= new BD(oActivity);		
		mdb.onUpgradeExpress();
		
	}
}
