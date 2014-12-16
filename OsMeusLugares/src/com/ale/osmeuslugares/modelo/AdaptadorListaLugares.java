package com.ale.osmeuslugares.modelo;

import java.util.Vector;

import com.example.osmeuslugares.R;

import android.app.Activity;
import android.database.SQLException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorListaLugares extends BaseAdapter {

	/**
	 * Atributos.
	 */
	private final Activity activity;
	private Vector<Lugar> lista;
	private DBManager lugaresDb;
	private RecursoIcono recursoIcono;

	public AdaptadorListaLugares(Activity activity) {
		super();
		this.activity = activity;
		this.lista = new Vector<Lugar>();
		lugaresDb = new DBManager(activity);
		actualizarDesdeDb();
		this.recursoIcono = new RecursoIcono(activity);
	}


	public void actualizarDesdeDb() throws SQLException {
		this.lista = lugaresDb.cargarLugaresDesdeBD();
	}

	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.elementAt(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View view = inflater.inflate(R.layout.elemento_lista, null, true);
		cargaDatos(position, view);
		return view;
	}


	private void cargaDatos(int position, View view) {
		TextView textViewTitulo = (TextView) view
				.findViewById(R.id.textViewTitulo);
		TextView txtNombre = (TextView) view.findViewById(R.id.tvNombre);
		TextView txtLugar = (TextView) view.findViewById(R.id.tvTipo);
		TextView txtDireccion = (TextView) view.findViewById(R.id.tvDireccion);
		TextView txtCiudad = (TextView) view.findViewById(R.id.tvCiudad);
		TextView txtUrl = (TextView) view.findViewById(R.id.tvUrl);
		TextView txtTelf = (TextView) view.findViewById(R.id.tvTelefono);
		TextView txtComent = (TextView) view.findViewById(R.id.tvComentario);
		Lugar lugar = (Lugar) lista.elementAt(position);
		ImageView imgViewIcono = (ImageView) view.findViewById(R.id.icono);
		Log.i(this.getClass().toString(), "ICONO OBTENIDO DE LA CATEGORIA= "
				+ lugar.getCategoria().getIcono());
		Drawable icon = recursoIcono.obtenerDrawableIcon(lugar.getCategoria()
				.getIcono());
		imgViewIcono.setImageDrawable(icon);
		textViewTitulo.setText(lugar.getNombre());
		txtNombre.setText(lugar.getNombre());
		txtLugar.setText(lugar.getCategoria().getNombre());
		txtDireccion.setText(lugar.getDireccion());
		txtCiudad.setText(lugar.getCiudad());
		txtUrl.setText(lugar.getUrl());
		txtTelf.setText(lugar.getTelefono());
		txtComent.setText(lugar.getComentario());
	}
	
}