package com.ale.osmeuslugares.modelo;

import com.example.osmeuslugares.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class EditarLugarActivity extends Activity {

	private Lugar lugarNuevo;
	private Lugar lugarEdit;
	private DBManager db = new DBManager(this);
	private Spinner spinnerCategoria;
	private TextView editTextNombre;
	private TextView editTextCiudad;
	private TextView editTextDireccion;
	private TextView editTextTelefono;
	private TextView editTextUrl;
	private TextView editTextComentario;
	AdaptadorCategorias categoriasAdapter;
	private boolean add;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_lugar);
		editTextNombre = (TextView) findViewById(R.id.editNombre);
		spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);
		categoriasAdapter = new AdaptadorCategorias(this);
		spinnerCategoria.setAdapter(categoriasAdapter);
		editTextCiudad = (TextView) findViewById(R.id.editCiudad);
		editTextDireccion = (TextView) findViewById(R.id.editDireccion);
		editTextTelefono = (TextView) findViewById(R.id.editTelefono);
		editTextUrl = (TextView) findViewById(R.id.editUrl);
		editTextComentario = (TextView) findViewById(R.id.editComentario);
		lugarEdit = new Lugar();
		Bundle extras = new Bundle();
		extras = getIntent().getExtras();
		add = extras.getBoolean("add");
		if (add) {
			Toast.makeText(getBaseContext(), "CREAR NUEVO LUGAR",
					Toast.LENGTH_LONG).show();

		} else {
			Toast.makeText(getBaseContext(),
					"EDITAR " + extras.getString(Lugar.C_NOMBRE),
					Toast.LENGTH_LONG).show();
			lugarEdit.setBundle(extras);

		}

		establecerValoresEditar();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_lugar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.guardar_edLugar: {
			if (comprobarCatSeleccionada()) {
				if (add) {
					crearLugarEnBd();
				} else {
					actualizarLugarEnBd();
				}
				finish();
			} else {
				alertaSimple("Tipo Lugar",
						"Debe de seleccionar una categoria para poder continuar");
			}

			break;
		}
		case R.id.eliminar_edLugar: {
			eliminarLugarEnBd();
			finish();
			break;
		}
		case R.id.cerrar_edLugar: {
			finish();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	private Boolean comprobarCatSeleccionada() {
		int position = spinnerCategoria.getSelectedItemPosition();
		if (position == 0) {
			return false;
		} else {
			return true;
		}
	}


	private void crearLugarEnBd() {
		db.createLugar(getLugarDesdeCampos());
		Toast.makeText(getBaseContext(), "GUARDADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}


	private void actualizarLugarEnBd() {
		db.updateLugar(getLugarEdit());
		Toast.makeText(getBaseContext(), "ACTUALIZADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}


	private void eliminarLugarEnBd() {
		db.deleteLugar(lugarEdit);
		Toast.makeText(getBaseContext(), "ELIMINADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}


	private Lugar getLugarDesdeCampos() {

		lugarNuevo = new Lugar();
		lugarNuevo.setNombre(editTextNombre.getText().toString());
		int position = spinnerCategoria.getSelectedItemPosition();
		lugarNuevo
				.setCategoria((Categoria) categoriasAdapter.getItem(position));
		lugarNuevo.setCiudad(editTextCiudad.getText().toString());
		lugarNuevo.setDireccion(editTextDireccion.getText().toString());
		lugarNuevo.setTelefono(editTextTelefono.getText().toString());
		lugarNuevo.setUrl(editTextUrl.getText().toString());
		lugarNuevo.setComentario(editTextComentario.getText().toString());

		return lugarNuevo;
	}


	private Lugar getLugarEdit() {
		lugarEdit.setNombre(editTextNombre.getText().toString());
		int position = spinnerCategoria.getSelectedItemPosition();
		lugarEdit.setCategoria((Categoria) categoriasAdapter.getItem(position));
		lugarEdit.setCiudad(editTextCiudad.getText().toString());
		lugarEdit.setDireccion(editTextDireccion.getText().toString());
		lugarEdit.setTelefono(editTextTelefono.getText().toString());
		lugarEdit.setUrl(editTextUrl.getText().toString());
		lugarEdit.setComentario(editTextComentario.getText().toString());
		return lugarEdit;
	}


	private void establecerValoresEditar() {

		editTextNombre.setText(lugarEdit.getNombre());

		int position = 0;
		if (!add) {
			position = categoriasAdapter.getPositionById(lugarEdit
					.getCategoria().getId());
		}
		spinnerCategoria.setSelection(position);
		editTextCiudad.setText(lugarEdit.getCiudad());
		editTextDireccion.setText(lugarEdit.getDireccion());
		editTextTelefono.setText(lugarEdit.getTelefono());
		editTextUrl.setText(lugarEdit.getUrl());
		editTextComentario.setText(lugarEdit.getComentario());
	}


	public void onButtonClickCategoria(View v) {
		lanzarListadoCategorias();
	}


	private void lanzarListadoCategorias() {
		Intent i = new Intent(this, ListCategoriasActivity.class);
		startActivity(i);
	}


	private void alertaSimple(String titulo, String mensaje) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		builder.setMessage(mensaje);
		builder.setPositiveButton("OK", null);
		builder.create();
		builder.show();
	}


	@Override
	protected void onRestart() {
		super.onRestart();
		categoriasAdapter.cargarDatosDesdeBd();
		categoriasAdapter.notifyDataSetChanged();
	}
}