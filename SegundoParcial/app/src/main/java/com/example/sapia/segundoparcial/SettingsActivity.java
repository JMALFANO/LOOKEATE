package com.example.sapia.segundoparcial;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity {
    int IdUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setings);

        SettingsFragment fragment = new SettingsFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit();
        loadData();
    }

    private void loadData() {

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        IdUsuario = extras.getInt("IdUsuario", 0);

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.putExtra("IdUsuario", IdUsuario);
        startActivity(intent);
        finish();
    }
}
