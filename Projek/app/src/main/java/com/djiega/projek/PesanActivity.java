package com.djiega.projek;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.djiega.projek.adapter.ProductAdapter;
import com.djiega.projek.database.SqliteDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.zip.Inflater;
public class PesanActivity extends AppCompatActivity {
    private static final String TAG = PesanActivity.class.getSimpleName();
    private SqliteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);

        FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);
        RecyclerView productView = (RecyclerView) findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);

        mDatabase = new SqliteDatabase(this);
        List<Product> allProducts = mDatabase.listProducts();

        if (allProducts.size() > 0) {
            productView.setVisibility(View.VISIBLE);
            ProductAdapter mAdapter = new ProductAdapter(this, allProducts);
            productView.setAdapter(mAdapter);
        } else {
            productView.setVisibility(View.GONE);
            Toast.makeText(this, "Belum ada product nya. Silahkan ditambahkan", Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialog();
            }
        });
    }
    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_product_layout, null);
        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText judulField = (EditText)subView.findViewById(R.id.enter_judul);
        final EditText hargaField = (EditText)subView.findViewById(R.id.enter_harga);
        final EditText alamatField = (EditText)subView.findViewById(R.id.enter_alamat);
        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Pesanan");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String judul= judulField.getText().toString();
                final int harga = Integer.parseInt(hargaField.getText().toString());
                final String alamat = alamatField.getText().toString();
                final int quantity = Integer.parseInt(quantityField.getText().toString());
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(judul) ) {
                    Toast.makeText(PesanActivity.this, "Ada yang salah nih, coba cek inputan kamu :)",
                            Toast.LENGTH_LONG).show();
                }else if(harga <=0 ||TextUtils.isEmpty(alamat) || quantity <= 0 ) {
                    Toast.makeText(PesanActivity.this, "Ada yang salah nih, coba cek inputan kamu :)",
                            Toast.LENGTH_LONG).show();

                } else  {
                    Product newProduct = new Product(name,judul,harga,alamat, quantity);
                    mDatabase.addProduct(newProduct);
                    //refresh activity
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PesanActivity.this, "Batal", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }
}
