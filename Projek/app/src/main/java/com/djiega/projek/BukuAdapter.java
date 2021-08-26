package com.djiega.projek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BukuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Buku> bukus;

    public void setBukus(ArrayList<Buku> bukus) {
        this.bukus = bukus;
    }
    public BukuAdapter(Context context){
        this.context = context;
        bukus = new ArrayList<>();
    }
    @Override
    public int getCount(){
        return bukus.size();
    }
    @Override
    public Object getItem(int position){
        return bukus.get(position);
    }
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_list_buku, parent,false);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        Buku buku = (Buku) getItem(position);
        viewHolder.bind(buku);
        return convertView;
    }
    private class  ViewHolder{
        private TextView txJudulBuku, txPenulis, txHarga;
        private ImageView imgBuku;

        ViewHolder(View view){
            txJudulBuku = view.findViewById(R.id.txtJudulBuku);
            txPenulis = view.findViewById(R.id.txtPenulis);
            txHarga = view.findViewById(R.id.txtHarga);
            imgBuku = view.findViewById(R.id.imgFotoBuku);
        }
        void bind(Buku buku){
            txJudulBuku.setText(buku.getJudulBuku());
            txPenulis.setText(buku.getPenulisBuku());
            txHarga.setText(buku.getHargaBuku());
            imgBuku.setImageResource(buku.getFotoBuku());
        }
    }
}

