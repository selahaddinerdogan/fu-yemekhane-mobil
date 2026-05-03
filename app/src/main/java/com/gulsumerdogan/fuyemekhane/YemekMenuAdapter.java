package com.gulsumerdogan.fuyemekhane;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class YemekMenuAdapter extends ArrayAdapter<GunlukMenu> {
    public YemekMenuAdapter(Context context, List<GunlukMenu> menuler) {
        super(context, 0, menuler);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GunlukMenu menu = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.menu_item, parent, false);
        }

        TextView tarih = convertView.findViewById(R.id.tarih);
        TextView detaylar = convertView.findViewById(R.id.detaylar);

        tarih.setText(menu.tarih);
        detaylar.setText(
                "Çorba: " + menu.corba + " (" + menu.corbaKalori + " kcal)\n" +
                        "Ana Yemek: " + menu.anaYemek + " (" + menu.anaYemekKalori + " kcal)\n" +
                        "Yardımcı Yemek: " + menu.yardimciYemek + " (" + menu.yardimciYemekKalori + " kcal)\n" +
                        "Salata: " + menu.salata + " (" + menu.salataKalori + " kcal)\n" +
                        "Tatlı: " + menu.tatli + " (" + menu.tatliKalori + " kcal)\n" +
                        "İçecek: " + menu.icecek + " (" + menu.icecekKalori + " kcal)\n" +
                        "Toplam: " + menu.getToplamKalori() + " kcal"
        );

        return convertView;
    }
}
