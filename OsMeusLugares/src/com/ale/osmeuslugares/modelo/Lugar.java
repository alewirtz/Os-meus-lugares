package com.ale.osmeuslugares.modelo;

import android.content.ContentValues;
import android.os.Bundle;

public class Lugar {
	
	private Long id;
	private String nombre;
	private Categoria categoria;
	private String ciudad;
	private String direccion;
	private String url;
	private String telefono;
	private String comentario;

	public static final String C_ID = "lug_id";
	public static final String C_NOMBRE = "lug_nombre";
	public static final String C_CATEGORIA_ID = "lug_categoria_id";
	public static final String C_DIRECCION = "lug_direccion";
	public static final String C_CIUDAD = "lug_ciudad";
	public static final String C_TELEFONO = "lug_telefono";
	public static final String C_URL = "lug_url";
	public static final String C_COMENTARIO = "lug_comentario";


	public Lugar() {
		super();
	}


	public Long getId() {
		return id;
	}

	
	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}

	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Categoria getCategoria() {
		return categoria;
	}

	
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	
	public String getCiudad() {
		return ciudad;
	}

	
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	ContentValues getContentValues() {
		ContentValues reg = new ContentValues();
		reg.put(C_NOMBRE, nombre);
		reg.put(C_CATEGORIA_ID, categoria.getId());
		reg.put(C_DIRECCION, direccion);
		reg.put(C_CIUDAD, ciudad);
		reg.put(C_URL, url);
		reg.put(C_TELEFONO, telefono);
		reg.put(C_COMENTARIO, comentario);
		return reg;
	}

	Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putLong(C_ID, id);
		bundle.putString(C_NOMBRE, nombre);
		bundle.putInt(C_CATEGORIA_ID, categoria.getId());
		bundle.putString(Categoria.C_NOMBRE, categoria.getNombre());
		bundle.putString(Categoria.C_ICONO, categoria.getIcono());
		bundle.putString(C_DIRECCION, direccion);
		bundle.putString(C_CIUDAD, ciudad);
		bundle.putString(C_URL, url);
		bundle.putString(C_TELEFONO, telefono);
		bundle.putString(C_COMENTARIO, comentario);
		return bundle;
	}

	void setBundle(Bundle bundle) {
		id = bundle.getLong(C_ID);
		nombre = bundle.getString(C_NOMBRE);
		categoria = new Categoria(bundle.getInt(C_CATEGORIA_ID),
				bundle.getString(Categoria.C_NOMBRE),
				bundle.getString(Categoria.C_ICONO));
		ciudad = bundle.getString(C_CIUDAD);
		direccion = bundle.getString(C_DIRECCION);
		url = bundle.getString(C_URL);
		telefono = bundle.getString(C_TELEFONO);
		comentario = bundle.getString(C_COMENTARIO);
	}

	@Override
	public String toString() {
		return "Lugar [id=" + id + ", nombre=" + nombre + ", categoria="
				+ categoria.toString() + ", direccion=" + direccion
				+ ", ciudad=" + ciudad + ", url=" + url + ", telefono="
				+ telefono + ", comentario=" + comentario + "]";
	}
}
