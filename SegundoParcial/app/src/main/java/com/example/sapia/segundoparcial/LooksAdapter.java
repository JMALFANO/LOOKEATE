package com.example.sapia.segundoparcial;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sapia.segundoparcial.model.Look;

import java.util.List;

/**
 * Created by Sapia on 11/11/2017.
 */

public class LooksAdapter extends ArrayAdapter<Look> {
    public LooksAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Look> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Look look = getItem(position);

        View v;

        v = LayoutInflater.from(getContext()).inflate(R.layout.items_looks, null);

        TextView etNombreLook = (TextView) v.findViewById(R.id.etNombreLook);
        TextView etParteSuperior = (TextView) v.findViewById(R.id.etParteSuperior);
        TextView etParteInferior = (TextView) v.findViewById(R.id.etParteInferior);

        etNombreLook.setText(look.getNombre());
        etParteSuperior.setText(look.getPrendaSuperior());
        etParteInferior.setText(look.getPrendaInferior());
        return v;
    }
}