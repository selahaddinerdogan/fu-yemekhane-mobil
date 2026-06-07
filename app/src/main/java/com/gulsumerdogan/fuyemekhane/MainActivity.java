package com.gulsumerdogan.fuyemekhane;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textDate;
    private TextView anaYemekTxt;
    private TextView yardimciYemekTxt;
    private TextView corbaTxt;
    private TextView salataTxt;
    private TextView tatliTxt;
    private TextView icecekTxt;
    private TextView toplamKaloriTxt;
    private TextView haftalikToplamKaloriTxt;
    private TextView aylikToplamKaloriTxt;
    private Button btnHaftalikMenu;

    public static String SERVER_IP = "192.168.1.102";

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(
                    ContextCompat.getColor(this, R.color.my_primary)
            ));
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textDate = (TextView) findViewById(R.id.textDate);
        anaYemekTxt = (TextView) findViewById(R.id.anaYemek);
        yardimciYemekTxt = (TextView) findViewById(R.id.yardimciYemek);
        corbaTxt = (TextView) findViewById(R.id.corba);
        salataTxt = (TextView) findViewById(R.id.salata);
        tatliTxt = (TextView) findViewById(R.id.tatli);
        icecekTxt = (TextView) findViewById(R.id.icecek);
        toplamKaloriTxt = (TextView) findViewById(R.id.toplamKalori);
        haftalikToplamKaloriTxt = (TextView) findViewById(R.id.haftalikToplamKalori);
        aylikToplamKaloriTxt = (TextView) findViewById(R.id.aylikToplamKalori);
        btnHaftalikMenu = findViewById(R.id.btnHaftalikMenu);

        btnHaftalikMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        });
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://" + SERVER_IP + "/yemekhane/gunluk_menu.php")
                        .build();
// sunucuya reguest olusturma
                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && response.body() != null) {
                    //Sunucudan dönen data
                    String responseData = response.body().string();
                    //Sunucudan dönen datayı json formatına çevirme
                    JSONObject menu = new JSONObject(responseData);
                    try {
                        //jsondan verilri okuma
                        String ana_yemek = menu.getString("ana_yemek");
                        String yardimci_yemek = menu.getString("yardimci_yemek");
                        String corba = menu.getString("corba");
                        String salata = menu.getString("salata");
                        String tatli = menu.getString("tatli");
                        String icecek = menu.getString("icecek");
                        String tarih = menu.getString("tarih");

                        int ana_yemek_kalori = menu.getInt("ana_yemek_kalori");
                        int yardimci_yemek_kalori = menu.getInt("yardimci_yemek_kalori");
                        int corba_kalori = menu.getInt("corba_kalori");
                        int salata_kalori = menu.getInt("salata_kalori");
                        int tatli_kalori = menu.getInt("tatli_kalori");
                        int icecek_kalori = menu.getInt("icecek_kalori");
                        int haftalik_toplam_kalori = menu.getInt("haftalik_toplam_kalori");
                        int aylik_toplam_kalori = menu.getInt("aylik_toplam_kalori");

                        int toplamKalori = ana_yemek_kalori + yardimci_yemek_kalori + corba_kalori + salata_kalori + tatli_kalori + icecek_kalori;
                        // verileri ara yüzde gösterme
                        runOnUiThread(() -> {
                            anaYemekTxt.setText(ana_yemek + " - " + ana_yemek_kalori + " kcal");
                            yardimciYemekTxt.setText(yardimci_yemek + " - " + yardimci_yemek_kalori + " kcal");
                            corbaTxt.setText(corba + " - " + corba_kalori + " kcal");
                            salataTxt.setText(salata + " - " + salata_kalori + " kcal");
                            tatliTxt.setText(tatli + " - " + tatli_kalori + " kcal");
                            icecekTxt.setText(icecek + " - " + icecek_kalori + " kcal");
                            toplamKaloriTxt.setText("Günlük Toplam: " + toplamKalori + " kcal");
                            haftalikToplamKaloriTxt.setText("Haftalık Toplam: " + haftalik_toplam_kalori + " kcal");
                            aylikToplamKaloriTxt.setText("Aylık Toplam: " + aylik_toplam_kalori + " kcal");
                            textDate.setText("Günün Menüsü(" + tarih + ")");
                        });
                        Log.d("TARİH", tarih);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

//    private void setDate() {
//        // Şu anki tarih ve saat
//        Date date = new Date();
//        // İstediğin formatı belirle
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy EEEE", new Locale("tr", "TR"));
//        // Formatlanmış tarihi TextView’e bas
//        textDate.setText(sdf.format(date));
//    }
}