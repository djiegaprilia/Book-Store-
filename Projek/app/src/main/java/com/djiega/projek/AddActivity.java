package com.djiega.projek;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    DBHelper dbHelper;
    TextView TvStatus;
    Button BtnProses;
    EditText TxID, TxNama, TxJudul, TxHarga, TxJumlah, TxAlamat, TxtglBeli, TxStatus;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbHelper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        TxID = (EditText)findViewById(R.id.txID);
        TxNama = (EditText)findViewById(R.id.txNamaAnggota);
        TxJudul = (EditText)findViewById(R.id.txJudul);
        TxHarga= (EditText)findViewById(R.id.txtHarga);
        TxJumlah= (EditText)findViewById(R.id.txtJumlah);
        TxAlamat= (EditText)findViewById(R.id.txtAlamat);
        TxtglBeli = (EditText)findViewById(R.id.TxtglBeli);
        TxStatus = (EditText)findViewById(R.id.txStatus);

        TvStatus = (TextView)findViewById(R.id.tVStatus);
        BtnProses = (Button)findViewById(R.id.btnProses);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        getData();

        TxtglBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });


        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
    }
    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TxtglBeli.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getData() {

        Cursor cur = dbHelper.oneData(id);
        if(cur.moveToFirst()){
            String id = cur.getString(cur.getColumnIndex(DBHelper.row_id));
            String nama = cur.getString(cur.getColumnIndex(DBHelper.row_nama));
            String judul = cur.getString(cur.getColumnIndex(DBHelper.row_judul));
            String harga = cur.getString(cur.getColumnIndex(DBHelper.row_harga));
            String jumlah = cur.getString(cur.getColumnIndex(DBHelper.row_jumlah));
            String alamat= cur.getString(cur.getColumnIndex(DBHelper.row_alamat));
            String beli = cur.getString(cur.getColumnIndex(DBHelper.row_beli));
            String status = cur.getString(cur.getColumnIndex(DBHelper.row_status));

            TxID.setText(id);
            TxNama.setText(nama);
            TxJudul.setText(judul);
            TxHarga.setText(harga);
            TxAlamat.setText(alamat);
            TxJumlah.setText(jumlah);
            TxtglBeli.setText(beli);
            TxStatus.setText(status);

            if (TxID.equals("")){
                TvStatus.setVisibility(View.GONE);
                TxStatus.setVisibility(View.GONE);
                BtnProses.setVisibility(View.GONE);
            }else{
                TvStatus.setVisibility(View.VISIBLE);
                TxStatus.setVisibility(View.VISIBLE);
                BtnProses.setVisibility(View.VISIBLE);
            }

            if(status.equals("DiBeli")){
                BtnProses.setVisibility(View.VISIBLE);
            }else {
                BtnProses.setVisibility(View.GONE);
                TxNama.setEnabled(false);
                TxJudul.setEnabled(false);
                TxHarga.setEnabled(false);
                TxJumlah.setEnabled(false);
                TxAlamat.setEnabled(false);
                TxtglBeli.setEnabled(false);
                TxStatus.setEnabled(false);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        String idpinjam = TxID.getText().toString().trim();
        String status = TxStatus.getText().toString().trim();

        MenuItem itemDelete = menu.findItem(R.id.action_delete);
        MenuItem itemClear = menu.findItem(R.id.action_clear);
        MenuItem itemSave = menu.findItem(R.id.action_save);

        if (idpinjam.equals("")){
            itemDelete.setVisible(false);
            itemClear.setVisible(true);
        }else {
            itemDelete.setVisible(true);
            itemClear.setVisible(false);
        }

        if(status.equals("Dikembalikan")){
            itemSave.setVisible(false);
            itemDelete.setVisible(false);
            itemClear.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                insertAndUpdate();
        }
        switch (item.getItemId()){
            case R.id.action_clear:
                TxNama.setText("");
                TxJudul.setText("");
                TxHarga.setText("");
                TxJumlah.setText("");
                TxAlamat.setText("");
                TxtglBeli.setText("");
        }
        switch (item.getItemId()){
            case R.id.action_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
                builder.setMessage("Data ini akan dihapus");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteData(id);
                        Toast.makeText(AddActivity.this, "Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertAndUpdate(){
        String idpinjam = TxID.getText().toString().trim();
        String nama = TxNama.getText().toString().trim();
        String judul = TxJudul.getText().toString().trim();
        String harga = TxHarga.getText().toString().trim();
        String jumlah = TxJumlah.getText().toString().trim();
        String alamat = TxAlamat.getText().toString().trim();
        String tglBeli = TxtglBeli.getText().toString().trim();
        String status = "DiBeli";

        ContentValues values = new ContentValues();

        values.put(DBHelper.row_nama, nama);
        values.put(DBHelper.row_judul, judul);
        values.put(DBHelper.row_harga, harga);
        values.put(DBHelper.row_jumlah, jumlah);
        values.put(DBHelper.row_alamat, alamat);
        values.put(DBHelper.row_beli, tglBeli);
        values.put(DBHelper.row_status, status);

        if (nama.equals("") || judul.equals("") || tglBeli.equals("")){
            Toast.makeText(AddActivity.this, "Isi Data Dengan Lengkap", Toast.LENGTH_SHORT).show();
        }else {
            if(idpinjam.equals("")){
                values.put(DBHelper.row_judul, tglBeli);
                dbHelper.insertData(values);
            }else {
                dbHelper.updateData(values, id);
            }

            Toast.makeText(AddActivity.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
