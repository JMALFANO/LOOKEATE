package com.example.sapia.segundoparcial;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sapia.segundoparcial.database.DBHelper;
import com.example.sapia.segundoparcial.model.Look;

import java.util.List;

public class Looks extends AppCompatActivity {
    ProgressDialog progressDialog;
    ListView ListLooks;
    DBHelper dbHelper;
    Toolbar toolbarMain;
    int IdUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looks);
        setupUI();
        setupToolbar();
        LoadData();
        MyTask myTask = new MyTask();
        myTask.execute();
        initializeListView();
        progressDialog.setMessage("Cargando...");
        progressDialog.setTitle("MIS LOOKS");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
    }

    private void LoadData() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        IdUsuario = extras.getInt("IdUsuario", 0);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbarMain);
        getSupportActionBar().setTitle(R.string.looks);
        toolbarMain.setTitleTextColor(ContextCompat.getColor(this,R.color.colorAccent));
    }

    private void initializeListView(){

        dbHelper = new DBHelper(Looks.this);

        final List<Look> lookss= dbHelper.obtenerLooks(IdUsuario);

        LooksAdapter adapter = new LooksAdapter(this, R.layout.items_looks, lookss);

        ListLooks.setAdapter(adapter);

                ListLooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(Looks.this, BMLooks.class);
                        intent.putExtra("IdLook", lookss.get(position).getId());
                        intent.putExtra("IdUsuario", IdUsuario);
                    startActivity(intent);

                    finish();
            }
        });
                ListLooks.setVisibility(View.INVISIBLE);
    }

    private void setupUI() {
        toolbarMain= (Toolbar) findViewById(R.id.toolbarMain);
        ListLooks = (ListView) findViewById(R.id.ListLooks);
        progressDialog = new ProgressDialog(this);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Looks.this, MainActivity.class);
        intent.putExtra("IdUsuario", IdUsuario);
        startActivity(intent);
        finish();
    }

    private class MyTask extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                for (int i = 0; i <= 100; i++) {
                    Thread.sleep(10);
                    publishProgress(i);
                }
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return "Carga exitosa";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int p = Math.round(100*values[0]);
            progressDialog.setProgress(p);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            ListLooks.setVisibility(View.VISIBLE);
            super.onPostExecute(s);
        }
    }

}
