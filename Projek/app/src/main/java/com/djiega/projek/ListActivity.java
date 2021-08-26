package com.djiega.projek;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private String[] dtBuku, dtHarga,dtPenulis;
    private TypedArray dtFoto;
    private BukuAdapter adapter;
    private ArrayList<Buku> bukus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        adapter = new BukuAdapter(this);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        prepare();
        addBuku();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListActivity.this, bukus.get(position)
                        .getJudulBuku(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void addBuku(){
        bukus = new ArrayList<>();
        for(int i=0;i<dtBuku.length;i++){
            Buku buku = new Buku();
            buku.setFotoBuku(dtFoto.getResourceId(i, -1));
            buku.setJudulBuku(dtBuku[i]);
            buku.setPenulisBuku(dtPenulis[i]);
            buku.setHargaBuku(dtHarga[i]);
            bukus.add(buku);
        }
        adapter.setBukus(bukus);
    }
    private void prepare(){
        dtBuku = getResources().getStringArray(R.array.judul_buku);
        dtPenulis = getResources().getStringArray(R.array.penulis_buku);
        dtHarga = getResources().getStringArray(R.array.harga_buku);
        dtFoto = getResources().obtainTypedArray(R.array.foto_buku);
    }
    public void Buku(View view) {
        Intent i = new Intent(this, PesanActivity.class);
        startActivity(i);
    }


}
