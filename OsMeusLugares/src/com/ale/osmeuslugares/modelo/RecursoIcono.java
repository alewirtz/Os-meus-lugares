package com.ale.osmeuslugares.modelo;

import java.util.Arrays;
import java.util.List;

import com.example.osmeuslugares.R;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class RecursoIcono {

	private Activity activity;
	private Resources res;
	private TypedArray drawableIconosLugares;
	private List<String> valoresIconosLugares;

	public RecursoIcono(Activity activity) {
		super();
		this.activity = activity;
		cargarIconos(activity);
	}

	private void cargarIconos(Activity activity) {
		res = activity.getResources();
		drawableIconosLugares = res.obtainTypedArray(R.array.iconos_lugares);
		valoresIconosLugares = (List<String>) Arrays.asList(res
				.getStringArray(R.array.valores_iconos_lugares));
	}

	public Drawable obtenerDrawableIcon(String icon) {
		res = activity.getResources();
		int posicion = valoresIconosLugares.indexOf(icon);

		if (posicion == -1) {
			posicion = 0;
		}
		return drawableIconosLugares.getDrawable(posicion);
	}

	
}
