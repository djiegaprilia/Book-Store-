package com.djiega.projek.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.djiega.projek.Product;
import com.djiega.projek.R;
import com.djiega.projek.database.SqliteDatabase;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private Context context;
    private List<Product> listProducts;

    private SqliteDatabase mDatabase;
    public ProductAdapter(Context context, List<Product> listProducts){
        this.context = context;
        this.listProducts = listProducts;
        mDatabase = new SqliteDatabase(context);
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product singleProduct = listProducts.get(position);
        holder.name.setText(singleProduct.getName());
        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductAdapter.this.editTaskDialog(singleProduct);
            }
        });

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            //delete row from database
            public void onClick(View view) {
                mDatabase.deleteProduct(singleProduct.getId());
                //refresh the activity page
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }
        @Override
            public int getItemCount(){
            return listProducts.size();
        }
        private void editTaskDialog(final Product product){
            LayoutInflater inflater = LayoutInflater.from(context);
            View subView = inflater.inflate(R.layout.add_product_layout, null);

            final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
            final EditText judulField = (EditText)subView.findViewById(R.id.enter_judul);
            final EditText hargaField = (EditText)subView.findViewById(R.id.enter_harga);
            final EditText alamatField = (EditText)subView.findViewById(R.id.enter_alamat);
            final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);

            if(product != null){
                nameField.setText(product.getName());
                judulField.setText(product.getJudul());
                hargaField.setText(String.valueOf(product.getHarga()));
                alamatField.setText(product.getAlamat());
                quantityField.setText(String.valueOf(product.getQuantity()));
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Edit Product");
            builder.setView(subView);
            builder.create();

            builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String name = nameField.getText().toString();
                    final String judul= judulField.getText().toString();
                    final int harga = Integer.parseInt(hargaField.getText().toString());
                    final String alamat = alamatField.getText().toString();
                    final int quantity = Integer.parseInt(quantityField.getText().toString());
                    if(TextUtils.isEmpty(name) || quantity <= 0 ){
                        Toast.makeText(context, "Ada yang salah nih, coba cek inputan kamu :)",
                                Toast.LENGTH_LONG).show();
                    }else{
                        mDatabase.updateProduct(new Product(product.getId(), name,judul, harga, alamat,quantity));
                        //refresh activity
                        ((Activity)context).finish();
                        context.startActivity(((Activity)context).getIntent());
                    }
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context, "Batal", Toast.LENGTH_LONG).show();
                }
            });
            builder.show();
        }
    }
