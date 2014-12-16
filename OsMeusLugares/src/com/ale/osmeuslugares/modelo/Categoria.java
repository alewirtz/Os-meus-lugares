package com.ale.osmeuslugares.modelo;

import android.content.ContentValues;
import android.os.Bundle;

public class Categoria {
	private int id;
	private String nombre;
	private String icono;

	
	public static final String C_ID = "cat_id";
	public static final String C_NOMBRE = "cat_nombre";
	public static final String C_ICONO = "cat_icono";

	
	public Categoria() {
		super();
	}

	public Categoria(int id, String nombre, String icono) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.icono = icono;
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public String getNombre() {
		return nombre;
	}

	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public String getIcono() {
		return icono;
	}

	
	public void setIcono(String icono) {
		this.icono = icono;
	}

	
	ContentValues getContentValues() {
		ContentValues reg = new ContentValues();
		reg.put(C_NOMBRE, nombre);
		reg.put(C_ICONO, icono);
		return reg;
	}

	
	Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putInt(C_ID, id);
		bundle.putString(C_NOMBRE, nombre);
		bundle.putString(C_ICONO, icono);
		return bundle;
	}

	
	void setBundle(Bundle bundle) {
		id = bundle.getInt(C_ID);
		nombre = bundle.getString(C_NOMBRE);
		icono = bundle.getString(C_ICONO);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Categoria) {
			Categoria tmpCategoria = (Categoria) o;
			if (getId() == tmpCategoria.getId()) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", icono="
				+ icono + "]";
	}
}
