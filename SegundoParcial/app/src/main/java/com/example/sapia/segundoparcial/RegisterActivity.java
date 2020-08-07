package com.example.sapia.segundoparcial;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sapia.segundoparcial.database.DBHelper;
import com.example.sapia.segundoparcial.model.Usuario;

import static com.example.sapia.segundoparcial.R.id.toolbarMain;

public class RegisterActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    TextView txtLogin;
    EditText etUsuario, etContraseña, etRContraseña;
    Button btnCrear, btnVolver;
    Toolbar toolbarMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        SetupUI();
        setupToolbar();
        btnListener();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle(R.string.register);
        toolbarMain.setTitleTextColor(ContextCompat.getColor(this,R.color.colorAccent));
    }

    private void SetupUI()    {
        toolbarMain= (Toolbar) findViewById(R.id.toolbarMain);
        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etContraseña = (EditText) findViewById(R.id.etContraseña);
        etRContraseña = (EditText) findViewById(R.id.etRContraseña);
        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnCrear = (Button) findViewById(R.id.btnCrear);
    }

    private int CampoLleno() {
            int valor = -1;
        if (!etUsuario.getText().toString().isEmpty() || !etContraseña.getText().toString().isEmpty() || !etRContraseña.getText().toString().isEmpty()) {

            if (etContraseña.getText().toString().equals(etRContraseña.getText().toString())) valor = 1; //USUARIO Y CONTRASEÑAS COINCIDEN.
            else valor = 0; //USUARIO INGRESADO, CONTRASEÑA NO COINCIDEN
        } else valor = -1; //INGRESE DATOS

        return valor;
    }

    private Usuario NuevoUsuario(){

        Usuario usuario = new Usuario();

        usuario.setNombre(etUsuario.getText().toString());
        usuario.setContraseña(etContraseña.getText().toString());
        return usuario;
    }

    private void btnListener() {
        btnVolver.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnCrear.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                dbHelper = new DBHelper(RegisterActivity.this);

                int compruebo = dbHelper.ComprobarNombre(etUsuario.getText().toString());

                if (compruebo == 0)  //EL NOMBRE DE USUARIO YA SE ENCUENTRA EN LA BASE DE DATOS.
                {
                    Toast.makeText(RegisterActivity.this, "El nombre de usuario ya se está utilizando. Por favor ingrese otro.", Toast.LENGTH_SHORT).show();
                }
                else {

                    int valido = CampoLleno();

                    switch (valido) {
                        case 1:
                            dbHelper = new DBHelper(RegisterActivity.this);
                            dbHelper.insertarUsuario(NuevoUsuario());
                            crearNotificacion();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                            break;

                        case 0:
                            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                            break;

                        case -1:
                            Toast.makeText(RegisterActivity.this, "Asegurese de completar todos los campos", Toast.LENGTH_SHORT).show();
                            break;

                    }
                }

            }
        });

    }

    private void crearNotificacion() {

        final SharedPreferences prefs = this.getSharedPreferences("Configuracion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        final boolean estado = prefs.getBoolean("switchNotifications", true);

        if (estado == true) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(RegisterActivity.this);
            builder.setContentTitle("¡BIENVENIDO A LOOKEATE!")
                    .setContentText("Su cuenta ha sido creada exitosamente.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("LOOKEAME")
                    .setVibrate(new long[]{1000, 1000});

            Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(RegisterActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);//pending intent
            builder.setContentIntent(pendingIntent);

            int id = (int) (Math.random() * 10000) + 1;
            Log.d("JUANMA", "crearNotificacion: " + id);
            NotificationManager managerCompat = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            managerCompat.notify(id, builder.build());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);    //Obtengo las preferencias del settings.xml
        boolean mostrarNotificaciones = preferences.getBoolean("switchNotifications", true);
    }
}
