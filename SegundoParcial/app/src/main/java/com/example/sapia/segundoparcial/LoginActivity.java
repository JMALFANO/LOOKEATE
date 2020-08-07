package com.example.sapia.segundoparcial;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sapia.segundoparcial.database.DBHelper;
import com.example.sapia.segundoparcial.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    Toolbar toolbarMain;
    EditText etUsuario, etContraseña;
    Button btnIngresar, btnCrear;
    int IdUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SetupUI();
        setupToolbar();
        btnListener();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle(R.string.loggin);
        toolbarMain.setTitleTextColor(ContextCompat.getColor(this,R.color.colorAccent));
    }

    private void SetupUI()
    {
        toolbarMain= (Toolbar) findViewById(R.id.toolbarMain);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnCrear = (Button) findViewById(R.id.btnCrear);
    }

    private boolean CampoLleno() {
        boolean valor = false;
        if (!etUsuario.getText().toString().isEmpty() || !etContraseña.getText().toString().isEmpty()) {
            valor = true;
        } else
        {
            valor = false;
        }
        return valor;
    }

    private void SetearIdUsuario() {
        dbHelper = new DBHelper(LoginActivity.this);
       IdUsuario= dbHelper.obtenerIDUsuario(etUsuario.getText().toString());
    }

    private void btnListener() {
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CampoLleno()) {
                    dbHelper = new DBHelper(LoginActivity.this);
                int valido = dbHelper.ValidarUsuario(etUsuario.getText().toString(), etContraseña.getText().toString());
                    switch (valido){
                        case 1:
                            Toast.makeText(LoginActivity.this, "Ingresando", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                          SetearIdUsuario();
                        intent.putExtra("IdUsuario", IdUsuario);
                            startActivity(intent);
                            finish();
                            break;
                        case 0:
                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                            break;
                        case -1:
                            Toast.makeText(LoginActivity.this, "ERRROR EN LOGGIN.", Toast.LENGTH_SHORT).show();
                            break;
                        case -2:
                            Toast.makeText(LoginActivity.this, "Usuario incorrecto.", Toast.LENGTH_SHORT).show();
                            break;
                    }

                } else
                    Toast.makeText(LoginActivity.this, "Asegurese de completar todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
