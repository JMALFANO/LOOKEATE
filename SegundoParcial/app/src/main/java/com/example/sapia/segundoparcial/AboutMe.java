package com.example.sapia.segundoparcial;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

public class AboutMe extends AppCompatActivity {

    private Toolbar toolbarMain;
    TextView txtMarca;
    TextView txtPara;
    TextView txtCreador;
    int IdUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        setupUI();
        setupToolbar();
        loadData();
    }
    private void loadData() {

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        IdUsuario = extras.getInt("IdUsuario", 0);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle(R.string.aboutme);
        toolbarMain.setTitleTextColor(ContextCompat.getColor(this,R.color.colorAccent));
    }

    private void setupUI() {
        toolbarMain= (Toolbar) findViewById(R.id.toolbarMain);
        txtMarca = (TextView) findViewById(R.id.txtMarca);
        txtPara = (TextView) findViewById(R.id.txtPara);
        txtCreador = (TextView) findViewById(R.id.txtCreador);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AboutMe.this, MainActivity.class);
        intent.putExtra("IdUsuario", IdUsuario);
        startActivity(intent);
        finish();
    }
}
