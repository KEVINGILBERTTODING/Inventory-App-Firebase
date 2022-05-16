package com.example.firebasebarang.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class BarangModel implements Serializable {

    String kdbrg, nmbrg, satuan, hrgbeli, hrgjual, stok, stokmin, key;


    public BarangModel() {

    }

    public BarangModel(String key, String kdbrg, String nmbrg, String satuan, String hrgbeli, String hrgjual, String stok, String stokmin ) {
        this.key        = key;
        this.kdbrg      =   kdbrg;
        this.nmbrg      =   nmbrg;
        this.satuan     =   satuan;
        this.hrgbeli    =   hrgbeli;
        this.hrgjual    =   hrgjual;
        this.stok       =  stok;
        this.stokmin    =   stokmin;


    }

    public String getKdbrg() {
        return kdbrg;
    }

    public void setKdbrg(String kdbrg) {
        this.kdbrg = kdbrg;
    }

    public String getNmbrg() {
        return nmbrg;
    }

    public void setNmbrg(String nmbrg) {
        this.nmbrg = nmbrg;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getHrgbeli() {
        return hrgbeli;
    }

    public void setHrgbeli(String hrgbeli) {
        this.hrgbeli = hrgbeli;
    }

    public String getHrgjual() {
        return hrgjual;
    }

    public void setHrgjual(String hrgjual) {
        this.hrgjual = hrgjual;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getStokmin() {
        return stokmin;
    }

    public void setStokmin(String stokmin) {
        this.stokmin = stokmin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
