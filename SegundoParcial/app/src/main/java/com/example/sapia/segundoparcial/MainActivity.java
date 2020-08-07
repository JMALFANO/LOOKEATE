package com.example.sapia.segundoparcial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sapia.segundoparcial.database.DBHelper;
import com.example.sapia.segundoparcial.model.Look;

import static android.R.attr.defaultValue;


public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private Toolbar toolbarMain;
    Button btnCrearLook;
    Button btnMisLooks;
    Spinner spinnerRopaSuperior;
    Spinner spinnerRopaInferior;
    ImageView imgParteInferior;
    ImageView imgParteSuperior;
    EditText etNombreLook;
    int IdUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        initializeSpinnerRopaSuperior();
        initializeSpinnerRopaInferior();
        setupToolbar();
        btnListener();
        loadData();
        }

    private void btnListener() {
        btnMisLooks.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Looks.class);
                intent.putExtra("IdUsuario",IdUsuario);
                startActivity(intent);
                finish();
            }
        });

        btnCrearLook.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (CampoLleno()) {
                    Toast.makeText(MainActivity.this, "Look creado exitosamente.", Toast.LENGTH_SHORT).show();
                    dbHelper = new DBHelper(MainActivity.this);
                    dbHelper.insertarLook(NuevoLook());
                } else Toast.makeText(MainActivity.this, "Asegurese de completar todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Look NuevoLook(){

        Look look = new Look();

        look.setNombre(etNombreLook.getText().toString());

        look.setPrendaSuperior(spinnerRopaSuperior.getSelectedItem().toString());
        look.setImagenSuperior(spinnerRopaSuperior.getSelectedItemPosition());

        look.setPrendaInferior(spinnerRopaInferior.getSelectedItem().toString());
        look.setImagenInferior(spinnerRopaInferior.getSelectedItemPosition());

        look.setIdUsuario(IdUsuario);
        return look;

    }

    private void loadData() {

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        IdUsuario = extras.getInt("IdUsuario", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemInfo:
                Intent intent = new Intent(MainActivity.this, AboutMe.class);
                intent.putExtra("IdUsuario", IdUsuario);
                startActivity(intent);
                return true;
            case R.id.itemSettings:
                Intent intentt = new Intent(MainActivity.this, SettingsActivity.class);
                intentt.putExtra("IdUsuario", IdUsuario);
                startActivity(intentt);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void setupToolbar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle(R.string.toolbar);
        toolbarMain.setTitleTextColor(ContextCompat.getColor(this,R.color.colorAccent));
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

    private boolean CampoLleno() {
    boolean valor;
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
        btnCrearLook = (Button) findViewById(R.id.btnCrearLook);
        btnMisLooks = (Button) findViewById(R.id.btnMisLooks);
        spinnerRopaSuperior = (Spinner) findViewById(R.id.spinnerRopaSuperior);
        spinnerRopaInferior = (Spinner) findViewById(R.id.spinnerRopaInferior);
        imgParteInferior = (ImageView) findViewById(R.id.imgParteInferior);
        imgParteSuperior = (ImageView) findViewById(R.id.imgParteSuperior);
        etNombreLook = (EditText) findViewById(R.id.etNombreLook);
    }

    protected void onResume() {
        super.onResume();
    }


}
