package com.gulsumerdogan.fuyemekhane;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textDate;
    private TextView anaYemek;
    private TextView yardimciYemek;
    private TextView corba;
    private TextView salata;

    private static OkHttpClient client = new OkHttpClient();

    @SuppressLint("MissingInflatedId")
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
        anaYemek = (TextView) findViewById(R.id.anaYemek);
        yardimciYemek = (TextView) findViewById(R.id.yardimciYemek);
        corba = (TextView) findViewById(R.id.corba);
        salata = (TextView) findViewById(R.id.salata);
        setDate();

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.222.252.181/yemekhane/gunluk_menu.php")
                        .build();

                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && response.body() != null) {
                    String jsonData = response.body().string();
                    JSONObject menu = new JSONObject(jsonData);
                    try {
                        anaYemek.setText(menu.getString("ana_yemek"));
                        yardimciYemek.setText(menu.getString("yardimci_yemek"));
                        corba.setText(menu.getString("corba"));
                        salata.setText(menu.getString("salata"));
                    } catch (JSONException e) {
                        Log.d("YEMEKMENU Hata", e.toString());
                        throw new RuntimeException(e);
                    }
                    // UI güncellemesi için runOnUiThread kullanın
                    // örn: textView.setText(menu.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void setDate() {
        // Şu anki tarih ve saat
        Date date = new Date();
        // İstediğin formatı belirle
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy EEEE", new Locale("tr", "TR"));
        // Formatlanmış tarihi TextView’e bas
        textDate.setText(sdf.format(date));
    }

    public static JSONObject getYemekMenu(String urlString) {
        try {
            // Emülatörden bilgisayarınızdaki localhost’a erişmek için 10.0.2.2 kullanın
            String url = urlString;

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful() && response.body() != null) {
                String jsonData = response.body().string();
                return new JSONObject(jsonData);
            } else {
                System.out.println("İstek başarısız: " + response.code());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}