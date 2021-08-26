package com.djiega.projek;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SessionManager session;
    Button btnLogout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        btnLogout = findViewById(R.id.out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                session.logoutUser();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
    }




    public void out(View view) {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
    public void katalogBuku(View view) {
        Intent i = new Intent(this, ListActivity.class);
        startActivity(i);
    }
    public void Map(View view) {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
    public void Profile(View view) {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }
    public void Pesan(View view) {
        Intent i = new Intent(this, PesanActivity.class);
        startActivity(i);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.profile){
            startActivity(new Intent(this, ProfileActivity.class));
        }else if (item.getItemId() == R.id.notif){
            startActivity(new Intent(this, NotifActivity.class));
        }
        return true;
    }

}

