package com.ale.osmeuslugares.modelo;

import java.util.Vector;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBManager extends SQLiteOpenHelper {

	
	private SQLiteDatabase db;
	private static String nombre = "lugares.db";
	private static CursorFactory factory = null;
	private static int version = 5;
	private static String sql;

	
	public DBManager(Context context) {
		super(context, nombre, factory, version);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		try {
			crearTablaLugar(db);

			crearTablaCategoria(db);

			insertarCategoriasPrueba();

			insertarLugaresPrueba();

		} catch (SQLException e) {
			Log.e(getClass().toString(), e.getMessage());
		}
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("INFO", "Base de datos: onUpgrade" + oldVersion + "->"
				+ newVersion);
		if (newVersion > oldVersion) {
			try {
				db.execSQL("DROP TABLE IF EXISTS lugar");
				db.execSQL("DROP INDEX IF EXISTS idx_lug_nombre");
				db.execSQL("DROP TABLE IF EXISTS categoria");
				db.execSQL("DROP INDEX IF EXISTS idx_cat_nombre");
			} catch (Exception e) {
				Log.e(this.getClass().toString(), e.getMessage());
			}
			onCreate(db);

		
		}
	}

	
	private void crearTablaLugar(SQLiteDatabase db) {
		sql = "CREATE TABLE lugar("
				+ "lug_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "lug_nombre TEXT NOT NULL, "
				+ "lug_categoria_id INTEGER NOT NULL," + "lug_direccion TEXT,"
				+ "lug_ciudad TEXT," + "lug_telefono TEXT, " + "lug_url TEXT,"
				+ "lug_comentario TEXT);";

		db.execSQL(sql);

		sql = "CREATE UNIQUE INDEX idx_lug_nombre ON Lugar(lug_nombre ASC)";
		db.execSQL(sql);
	}

	
	private void crearTablaCategoria(SQLiteDatabase db) {
		sql = "CREATE TABLE Categoria("
				+ "cat_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "cat_nombre TEXT NOT NULL, " + "cat_icono TEXT);";

		db.execSQL(sql);

		sql = "CREATE UNIQUE INDEX idx_cat_nombre ON Categoria(cat_nombre ASC)";
		db.execSQL(sql);
	}

	
	private void insertarCategoriasPrueba() {
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('Hoteles','icono_hotel')");
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('Restaurantes','icono_restaurante')");
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('Otros','icono_otros')");
		db.execSQL("INSERT INTO Categoria(cat_nombre,cat_icono) "
				+ "VALUES('Playas','icono_playa')");
	}

	
	private void insertarLugaresPrueba() {
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Pizza Pepito',2, 'Av Pirulo, 2','Ourense','444555665','www.pizzapepito.com','La mejor pizza de Ourense.')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Hostal La Rosa',3, 'Calle Manolo, 78','Lugo','789789789','www.hotellarosa.com','Los mejores precios')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Playa de Riazor',1, 'Riazor','A Coruña','456789123','www.playaderiazor.com','Al lado del estadio del Depor')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Pub Victoria',4, 'Av Juan Justo, 90','A Coruña','123123123','www.pubvictoria.com','Las mejores copas.')");
		db.execSQL("INSERT INTO Lugar(lug_nombre, lug_categoria_id, lug_direccion, lug_ciudad, lug_telefono, lug_url, lug_comentario) "
				+ "VALUES('Playa del Orzán',1, 'Orzán','A Coruña','605789456','www.playaorzan.com','La playa mas concurrida de A Coruña.')");
	
	}

	
	public Vector<Lugar> cargarLugaresDesdeBD() {
		Vector<Lugar> vectorLugares = new Vector<Lugar>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT Lugar.*, cat_nombre, cat_icono "
				+ "FROM Lugar join Categoria on lug_categoria_id = cat_id",
				null);
		
		while (cursor.moveToNext()) {
			Lugar lugar = new Lugar();
			lugar.setId(cursor.getLong(0));
			lugar.setNombre(cursor.getString(cursor
					.getColumnIndex(Lugar.C_NOMBRE)));
			int idCategoria = cursor.getInt(cursor
					.getColumnIndex(Lugar.C_CATEGORIA_ID));
			String nombreCategoria = cursor.getString(cursor
					.getColumnIndex(Categoria.C_NOMBRE));
			String iconoCategoria = cursor.getString(cursor
					.getColumnIndex(Categoria.C_ICONO));

			lugar.setCategoria(new Categoria(idCategoria, nombreCategoria,
					iconoCategoria));
			lugar.setDireccion(cursor.getString(cursor
					.getColumnIndex(Lugar.C_DIRECCION)));
			lugar.setCiudad(cursor.getString(cursor
					.getColumnIndex(Lugar.C_CIUDAD)));
			lugar.setTelefono(cursor.getString(cursor
					.getColumnIndex(Lugar.C_TELEFONO)));
			lugar.setUrl(cursor.getString(cursor.getColumnIndex(Lugar.C_URL)));
			lugar.setComentario(cursor.getString(cursor
					.getColumnIndex(Lugar.C_COMENTARIO)));

			vectorLugares.add(lugar);
		}
		return vectorLugares;
	}

	
	public Vector<Categoria> cargarCategoriasDesdeBD(boolean opcSeleccionar) {
		Vector<Categoria> resultado = new Vector<Categoria>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"SELECT * FROM Categoria ORDER By cat_nombre", null);
		if (opcSeleccionar) {
			
			resultado.add(new Categoria(0, "Seleccionar...", "icono_nd"));
		}

		
		while (cursor.moveToNext()) {
			Categoria categoria = new Categoria();
			categoria
					.setId(cursor.getInt(cursor.getColumnIndex(Categoria.C_ID)));
			categoria.setNombre(cursor.getString(cursor
					.getColumnIndex(Categoria.C_NOMBRE)));
			categoria.setIcono(cursor.getString(cursor
					.getColumnIndex(Categoria.C_ICONO)));
			resultado.add(categoria);
		}
		return resultado;
	}

	

	
	public void createLugar(Lugar newLugar) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert("Lugar", null, newLugar.getContentValues());
	}

	
	public void updateLugar(Lugar lugarEdit) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.update("Lugar", lugarEdit.getContentValues(),
				"lug_id=" + lugarEdit.getId(), null);
	}

	
	public void deleteLugar(Lugar lugarDel) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("Lugar", "lug_id=" + lugarDel.getId(), null);
	}

	
	public void createCategoria(Categoria newCategoria) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert("Categoria", null, newCategoria.getContentValues());
	}

	
	public void updateCategoria(Categoria categoriaEdit) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.update("Categoria", categoriaEdit.getContentValues(), "cat_id="
				+ categoriaEdit.getId(), null);
	}

	
	public void deleteCategoria(Categoria categoriaDel) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete("Categoria", "cat_id=" + categoriaDel.getId(), null);
	}

}
