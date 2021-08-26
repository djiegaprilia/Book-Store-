package com.djiega.projek;

public class Product {
    private int id;
    private String name;
    private String judul;
    private int harga;
    private String alamat;
    private int quantity;

    public Product(String name, String judul, int harga, String alamat, int quantity){
        this.name = name;
        this.judul= judul;
        this.harga=harga;
        this.alamat=alamat;
        this.quantity = quantity;
    }
    public Product(int id, String name, String judul, int harga, String alamat, int quantity){
        this.id=id;
        this.name=name;
        this.judul= judul;
        this.harga=harga;
        this.alamat=alamat;
        this.quantity=quantity;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public  void setName(String name){
        this.name=name;
    }
    public String getJudul() {
        return judul;
    }
    public void setJudul(String judul) {
        this.judul = judul;
    }
    public int getHarga(){
        return harga;
    }
    public  void setHarga(int harga){
        this.harga=harga;
    }
    public String getAlamat(){
        return alamat;
    }
    public  void setAlamat(String alamat){
        this.alamat=alamat;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity=quantity;
    }
}
