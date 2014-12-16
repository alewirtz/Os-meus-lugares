package com.ale.osmeuslugares.modelo;

import com.example.osmeuslugares.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class EditarCategoriaActivity extends Activity {

	
	private Categoria categoriaNueva;
	private Categoria categoriaEdit;
	private DBManager db = new DBManager(this);
	private Bundle extras;
	private TextView editTextNombre;
	private Spinner spinnerIcono;
	private boolean add;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_categoria);

		editTextNombre = (TextView) findViewById(R.id.editNombreCat);
		spinnerIcono = (Spinner) findViewById(R.id.spinnerIcono);
		categoriaEdit = new Categoria();
		extras = new Bundle();
		extras = getIntent().getExtras();
		add = extras.getBoolean("add");

		if (add) {
			Toast.makeText(getBaseContext(), "CREAR NUEVA CATEGORIA",
					Toast.LENGTH_LONG).show();

		} else {
			Toast.makeText(getBaseContext(),
					"EDITAR " + extras.getString(Categoria.C_NOMBRE),
					Toast.LENGTH_LONG).show();
			categoriaEdit.setBundle(extras);

		}

		establecerValoresEditar();
	}


	private void establecerValoresEditar() {

		editTextNombre.setText(categoriaEdit.getNombre());

		int position = 0;
		if (!add) {
			position = obtenerPosicionIcono(categoriaEdit.getIcono());
		}
		spinnerIcono.setSelection(position);

	}

	
	private int obtenerPosicionIcono(String icono) {
		SpinnerAdapter sa = this.spinnerIcono.getAdapter();
		String txt;
		int pos = -1;
		for (int i = 0; i < sa.getCount(); i++) {
			txt = (String) sa.getItem(i);
			if (txt.equals(icono)) {
				pos = i;
				break;
			}
		}
		return pos;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_categoria, menu);
		return true;
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.guardar_edCat: {
			if (add) {
				crearCategoriaEnBd();
			} else {
				actualizarCategoriaEnBd();
			}
			finish();
			break;
		}
		case R.id.eliminar_edCat: {
			eliminarCategoriaEnBd();
			finish();
			break;
		}
		case R.id.cerrar_edCat: {
			finish();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void crearCategoriaEnBd() {
		db.createCategoria(getCategoriaDesdeCampos());
		Toast.makeText(getBaseContext(), "GUARDADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}


	private void actualizarCategoriaEnBd() {
		db.updateCategoria(getCategoriaEdit());
		Toast.makeText(getBaseContext(), "ACTUALIZADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	private void eliminarCategoriaEnBd() {
		db.deleteCategoria(categoriaEdit);
		Toast.makeText(getBaseContext(), "ELIMINADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
	}

	
	private Categoria getCategoriaDesdeCampos() {
		categoriaNueva = new Categoria();
		categoriaNueva.setNombre(editTextNombre.getText().toString());
		categoriaNueva.setIcono(spinnerIcono.getSelectedItem().toString());
		return categoriaNueva;
	}

	
	private Categoria getCategoriaEdit() {
		categoriaEdit.setNombre(editTextNombre.getText().toString());
		categoriaEdit.setIcono(spinnerIcono.getSelectedItem().toString());
		return categoriaEdit;
	}

	
	private void alertaSimple(String titulo, String mensaje) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(titulo);
		builder.setMessage(mensaje);
		builder.setPositiveButton("OK", null);
		builder.create();
		builder.show();
	}
}
