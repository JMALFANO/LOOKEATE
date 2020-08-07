package com.example.sapia.segundoparcial;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sapia.segundoparcial.database.DBHelper;
import com.example.sapia.segundoparcial.model.Look;

import static android.R.attr.defaultValue;

public class BMLooks extends AppCompatActivity {
    private DBHelper dbHelper;
    private Toolbar toolbarMain;
    int IdLook;
    int IdUsuario;
    Button btnUpdateLook;
    Button btnEliminarLook;
    Spinner spinnerRopaSuperior;
    Spinner spinnerRopaInferior;
    ImageView imgParteInferior;
    ImageView imgParteSuperior;
    EditText etNombreLook;
    Look look;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmlooks);
        setupUI();
        setupToolbar();
        initializeSpinnerRopaSuperior();
        initializeSpinnerRopaInferior();
        LoadData();
        ObtenerByID();
        buttonListener();
    }

    private void LoadData() {

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        IdLook = bundle.getInt("IdLook");
        IdUsuario = bundle.getInt("IdUsuario");
    }

    private void buttonListener() {
        btnUpdateLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CampoLleno()) {

                    dbHelper = new DBHelper(BMLooks.this);
                    dbHelper.actualizarLook(ModificarLook());
                    Toast.makeText(BMLooks.this, "Look actualizado correctamente.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BMLooks.this, MainActivity.class);
                    intent.putExtra("IdUsuario",IdUsuario);
                    startActivity(intent);

                    finish();

                } else Toast.makeText(BMLooks.this, "Asegurese de completar todos los campos", Toast.LENGTH_SHORT).show();

            }
        });

        btnEliminarLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder= new AlertDialog.Builder(BMLooks.this);
                builder.setTitle("ELIMINAR")
                        .setMessage("Una vez eliminado el LOOK no se podrá recuperar. ¿Continuar?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {

                                dbHelper = new DBHelper(BMLooks.this);
                                dbHelper.borrarLook(ModificarLook());
                                Toast.makeText(BMLooks.this, "Look eliminado correctamente.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BMLooks.this, MainActivity.class);
                                intent.putExtra("IdUsuario",IdUsuario);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });


    }

    private void initializeSpinnerRopaInferior() {
        spinnerRopaInferior.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypedArray img;
                img = getResources().obtainTypedArray(R.array.arrayImagenRopaInferior);
                imgParteInferior.setBackgroundResource(img.getResourceId(position, defaultValue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initializeSpinnerRopaSuperior() {
        spinnerRopaSuperior.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TypedArray img;
                img = getResources().obtainTypedArray(R.array.arrayImagenRopaSuperior);
                imgParteSuperior.setBackgroundResource(img.getResourceId(position, defaultValue));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle(R.string.modificacion);
        toolbarMain.setTitleTextColor(ContextCompat.getColor(this,R.color.colorAccent));
    }

    private Look ModificarLook(){
        Look lookUpdated = new Look();

        lookUpdated.setId(IdLook);//look.getId());
        lookUpdated.setNombre(etNombreLook.getText().toString());
        lookUpdated.setPrendaSuperior(spinnerRopaSuperior.getSelectedItem().toString());
        lookUpdated.setImagenSuperior(spinnerRopaSuperior.getSelectedItemPosition());
        lookUpdated.setPrendaInferior(spinnerRopaInferior.getSelectedItem().toString());
        lookUpdated.setImagenInferior(spinnerRopaInferior.getSelectedItemPosition());
        lookUpdated.setIdUsuario(IdUsuario);
        return lookUpdated;

    }

    private void ObtenerByID(){
        dbHelper = new DBHelper(BMLooks.this);
        look = dbHelper.obtenerLook(IdLook);

        etNombreLook.setText(look.getNombre());

        int posicionSuperior =look.getImagenSuperior();
        int posicionInferior = look.getImagenInferior();

        spinnerRopaSuperior.setSelection(posicionSuperior);
        spinnerRopaInferior.setSelection(posicionInferior);

        TypedArray img;
        img = getResources().obtainTypedArray(R.array.arrayImagenRopaSuperior);
        imgParteSuperior.setBackgroundResource(img.getResourceId(posicionSuperior, defaultValue));

        TypedArray imgs;
        imgs = getResources().obtainTypedArray(R.array.arrayImagenRopaInferior);
        imgParteInferior.setBackgroundResource(imgs.getResourceId(posicionInferior, defaultValue));

    }

    private boolean CampoLleno() {
        boolean valor = false;
        if (!etNombreLook.getText().toString().isEmpty()) {
            valor = true;
        } else
        {
            valor = false;
        }
    return valor;
    }

    private void setupUI() {
        toolbarMain= (Toolbar) findViewById(R.id.toolbarMain);
        btnEliminarLook = (Button) findViewById(R.id.btnEliminarLook);
        btnUpdateLook = (Button) findViewById(R.id.btnUpdateLook);
        spinnerRopaSuperior = (Spinner) findViewById(R.id.spinnerRopaSuperior);
        spinnerRopaInferior = (Spinner) findViewById(R.id.spinnerRopaInferior);
        imgParteInferior = (ImageView) findViewById(R.id.imgParteInferior);
        imgParteSuperior = (ImageView) findViewById(R.id.imgParteSuperior);
        etNombreLook = (EditText) findViewById(R.id.etNombreLook);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BMLooks.this, MainActivity.class);
        intent.putExtra("IdUsuario", IdUsuario);
        startActivity(intent);
        finish();
    }
}
