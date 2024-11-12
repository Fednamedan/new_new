package com.example.kurs;

public class ProductData {

    private  String towar_nazv;
    private  String marka;
    private  String cena;
    private  String photo;
    public ProductData(String towar_nazv, String marka , String cena , String photo ) {
        this.towar_nazv = towar_nazv;
        this.marka = marka;
        this.cena = cena;
        this.photo = photo;
    }

    public String getCena() {
        return this.cena;
    }
    public String getName() {
        return this.towar_nazv;
    }

    public String getMarka(){
        return this.marka;
    }
    public String getPhoto(){
        return this.photo;
    }



}
