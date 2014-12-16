package com.ale.osmeuslugares.modelo;

import com.example.osmeuslugares.R;
import com.example.osmeuslugares.R.id;
import com.example.osmeuslugares.R.layout;
import com.example.osmeuslugares.R.menu;
import com.ale.osmeuslugares.modelo.Lugar;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;


public class MainActivity extends ListActivity {


	private DBManager db;
	private AdaptadorListaLugares listLugaresAdapter;
	Bundle extras = new Bundle();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_lugares);

		db = new DBManager(this);
		listLugaresAdapter = new AdaptadorListaLugares(this);
		setListAdapter(listLugaresAdapter);
		registerForContextMenu(super.getListView());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Lugar itemLugar = (Lugar) getListAdapter().getItem(position);
		Bundle extras = itemLugar.getBundle();
		extras.putBoolean("add", false);
		lanzarEditLugar(extras);
	}

	
	private void lanzarEditLugar(Bundle extras) {
		Intent i = new Intent(this, EditarLugarActivity.class);
		i.putExtras(extras);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_lugares, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.add_lugar:
			extras.clear();
			extras.putBoolean("add", true);
			lanzarEditLugar(extras);
			return true;

        case R.id.cerrar:
			finish();
			break;
			
			
		case R.id.acerca_de:
			Intent i = new Intent(this, AcercaDeActivity.class);
			startActivity(i);
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.m_contextual, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Lugar lugar = (Lugar) listLugaresAdapter.getItem(info.position);

		switch (item.getItemId()) {
		case R.id.item_sitio_web:
			if (lugar.getUrl().isEmpty() || lugar.getUrl() == "") {
				Toast.makeText(getBaseContext(), "No hay dirección",
						Toast.LENGTH_SHORT).show();
			} else {
				irSitioWeb(lugar);
			}
			return true;

		case R.id.item_llamar:
			hacerLlamada(lugar.getTelefono());
			return true;

		case R.id.item_enviar_email:
			enviarEmail(lugar);
			return true;

		case R.id.item_editar_lugar:
			Bundle extras = lugar.getBundle();
			extras.putBoolean("add", false);
			lanzarEditLugar(extras);
			Toast.makeText(getBaseContext(), "Editar: " + lugar.getNombre(),
					Toast.LENGTH_SHORT).show();
			return true;

		case R.id.item_borrar_lugar:
			borrarLugarBD(lugar);
			Toast.makeText(getBaseContext(), "Eliminar: " + lugar.getNombre(),
					Toast.LENGTH_SHORT).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void borrarLugarBD(Lugar lugar) {
		db.deleteLugar(lugar);
		Toast.makeText(getBaseContext(), "ELIMINADO CORRECTAMENTE",
				Toast.LENGTH_LONG).show();
		onRestart();
	}



	private void enviarEmail(Lugar lugar) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		String[] to = { "alewirtz@gmail.com" };
		String subject = "Lugar " + lugar.getNombre();
		String body = lugar.toString();
		i.putExtra(Intent.EXTRA_EMAIL, to);
		i.putExtra(Intent.EXTRA_SUBJECT, subject);
		i.putExtra(Intent.EXTRA_TEXT, body);
		startActivity(i);
	}

	private void hacerLlamada(String telefono) {
		if (!telefono.isEmpty()) {
			Intent i = new Intent(Intent.ACTION_DIAL);
			i.setData(Uri.parse("tel:" + telefono));
			startActivity(i);
		}
	}

	
	private void irSitioWeb(Lugar lugar) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse("http://" + lugar.getUrl()));
		this.startActivity(i);
	}

	


	public boolean getPreferenciaVerInfoAmpliada() {
		SharedPreferences preferencias = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		return preferencias.getBoolean("ver_info_ampliada", false);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		listLugaresAdapter.actualizarDesdeDb();
		listLugaresAdapter.notifyDataSetChanged();
	}
}
