package aguasdelnorte.com.ar.agenda2.clases;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONObject;

import aguasdelnorte.com.ar.agenda2.provider.BD;
import aguasdelnorte.com.ar.agenda2.ui.ObjectView;
import aguasdelnorte.com.ar.agenda2.ui.Util;
//import json.JSONObject;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Elemento implements Serializable{
	protected BD mdb;
	protected SQLiteDatabase db;
	protected Activity oActivity;
	protected int Error;
	protected String s_error="";
	protected static String table_sql="";

	public String json(){		
		org.json.JSONObject tjson = new org.json.JSONObject((Map) this);
		return tjson.toString();		
	}
	public Elemento(Activity oActivity){
		this.oActivity=oActivity;
		
	}
	
	
	protected void openBD() {
		
		try {
			mdb= new BD(oActivity);
			db=mdb.getWritableDatabase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	protected void closeBD() {
		try {
			db.close();
			mdb.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	public int insert(){
		openBD();
		 Error=0;
		
		try {
			String sqlCreate=_insert();
			Log.v(table_sql, sqlCreate);
			Util.Log(sqlCreate);
			db.execSQL(sqlCreate);
			
			Error= 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Util.Log("E:"+e.getMessage());
			Error=  0;
		}
			
			closeBD();
			return Error;
	}
	public int insert2(){
		
		 Error=0;
		
		try {
			String sqlCreate=_insert();
			Log.v(table_sql, sqlCreate);
			db.execSQL(sqlCreate);
			
			Error= 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Error=  0;
		}
			
			return Error;
	}
	public int update(){
		openBD();
		 Error=0;
		
		try {
			String sqlCreate=_update();
			Log.v(table_sql, sqlCreate);
			db.execSQL(sqlCreate);
			
			Error= 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Error=  0;
		}
			
			closeBD();
			return Error;
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
	public int delete(String where){
		openBD();
		 Error=0;
		
		try {
			String sql="delete from "+table_sql+ " where "+where;
			Log.v(table_sql, sql);
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
	public  int  ini(long _id){		
		 Error=0;
		try {
			this.openBD();
			Cursor c = db.rawQuery(_select(_id),null);
			c.moveToFirst();
			setCursor(c);
			Error= 1;
			this.closeBD();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v(table_sql, "existe");
			this.closeBD();
			Error=  0;
		}
		return Error;
	}
	public String _insert() {
		// TODO Auto-generated method stub
		return null;
	}
	public String _update() {
		// TODO Auto-generated method stub
		return null;
	}
	public String _select (long id) {
		// TODO Auto-generated method stub
				return null;
	}
	public  void setCursor(Cursor c){
		// TODO Auto-generated method stub
	}
	protected  long validar_item_int(String s,int flag,String v) throws  NumberFormatException{
		 s_error=v;
		 
		 return Long.parseLong(s);
		
	}
	protected  String validar_item(String s,int flag,String v) throws  NumberFormatException{
		s_error=v;
		switch (flag) {
		case 1:
			//validar nombre 
			 if(!Util.isValidarNombre(s, 3)){
			    s_error="El campo "+v +" ingresado es demaciado corto";
				throw new NumberFormatException("");
			 }
			break;
		case 2:
			//validar si en un numero 
			 if(!Util.isNumeric(s)){
			    s_error="El campo "+v +" ingresado no es valido";
				throw new NumberFormatException("");
			 }
			break;
			

		default:
			break;
		}
		 
		 return s;
		
	}
	public void setJson(JSONObject a) {
		
	}
	public ArrayList<ObjectView>   getObjectView(){
		return null;
	}
}
