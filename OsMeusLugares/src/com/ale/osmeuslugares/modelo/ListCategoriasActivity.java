package com.ale.osmeuslugares.modelo;

import com.example.osmeuslugares.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ListCategoriasActivity extends ListActivity {


	private AdaptadorListaCategorias listCategoriasAdapter;
	private DBManager db;
	Bundle extras;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_categorias);
		registerForContextMenu(super.getListView());
		db = new DBManager(this);
		extras = new Bundle();
		listCategoriasAdapter = new AdaptadorListaCategorias(this);
		this.setListAdapter(listCategoriasAdapter);
	}


	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Categoria categoria = (Categoria) listCategoriasAdapter.getItem(position);
		Bundle extras = categoria.getBundle();
		extras.putBoolean("add", false);
		lanzarEditCategoria(extras);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_categorias, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.add_categoria: {
			extras.clear();
			extras.putBoolean("add", true);
			lanzarEditCategoria(extras);
			return true;
		}
		case R.id.cerrar_list_cat: {
			finish();
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.list_categorias_contextual, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Categoria categoria = (Categoria) listCategoriasAdapter
				.getItem(info.position);

		switch (item.getItemId()) {
		case R.id.edit_categoria:
			Bundle extras = categoria.getBundle();
			extras.putBoolean("add", false);
			lanzarEditCategoria(extras);
			Toast.makeText(getBaseContext(),
					"Editar: " + categoria.getNombre(), Toast.LENGTH_SHORT)
					.show();
			return true;

		case R.id.delete_categoria:
			eliminarCategoriaEnBd(categoria);
			Toast.makeText(getBaseContext(),
					"Eliminar: " + categoria.getNombre(), Toast.LENGTH_SHORT)
					.show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void eliminarCategoriaEnBd(Categoria categoria) {
		db.deleteCategoria(categoria);
		Toast.makeText(getBaseContext(), "ELIMINADA CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
		onRestart();
	}

	private void lanzarEditCategoria(Bundle extras) {
		Intent i = new Intent(this, EditarCategoriaActivity.class);
		i.putExtras(extras);
		startActivity(i);

	}

	@Override
	protected void onRestart() {
		super.onRestart();
		listCategoriasAdapter.cargarDatosDesdeBd();
		listCategoriasAdapter.notifyDataSetChanged();
	}

}
