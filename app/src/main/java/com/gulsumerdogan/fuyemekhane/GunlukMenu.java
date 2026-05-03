package com.gulsumerdogan.fuyemekhane;

public class GunlukMenu {
    String tarih, corba, anaYemek, yardimciYemek, salata, tatli, icecek;
    int corbaKalori, anaYemekKalori, yardimciYemekKalori, salataKalori, tatliKalori, icecekKalori;

    public GunlukMenu(String tarih, String corba, int corbaKalori,
                      String anaYemek, int anaYemekKalori,
                      String yardimciYemek, int yardimciYemekKalori,
                      String salata, int salataKalori,
                      String tatli, int tatliKalori,
                      String icecek, int icecekKalori) {
        this.tarih = tarih;
        this.corba = corba;
        this.corbaKalori = corbaKalori;
        this.anaYemek = anaYemek;
        this.anaYemekKalori = anaYemekKalori;
        this.yardimciYemek = yardimciYemek;
        this.yardimciYemekKalori = yardimciYemekKalori;
        this.salata = salata;
        this.salataKalori = salataKalori;
        this.tatli = tatli;
        this.tatliKalori = tatliKalori;
        this.icecek = icecek;
        this.icecekKalori = icecekKalori;
    }

    public int getToplamKalori() {
        return corbaKalori + anaYemekKalori + yardimciYemekKalori +
                salataKalori + tatliKalori + icecekKalori;
    }
}

