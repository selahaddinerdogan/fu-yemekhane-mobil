package com.gulsumerdogan.fuyemekhane;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.ArrayList;

public class AylikMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aylik_menu);

        ListView listView = findViewById(R.id.menuListView);
        ArrayList<GunlukMenu> menuler = new ArrayList<>();

        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://172.20.160.1/yemekhane/aylik_menu.php")
                        .build();
                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject menu = jsonArray.getJSONObject(i);
                        menuler.add(new GunlukMenu(
                                menu.getString("tarih"),
                                menu.getString("corba"), menu.getInt("corba_kalori"),
                                menu.getString("ana_yemek"), menu.getInt("ana_yemek_kalori"),
                                menu.getString("yardimci_yemek"), menu.getInt("yardimci_yemek_kalori"),
                                menu.getString("salata"), menu.getInt("salata_kalori"),
                                menu.getString("tatli"), menu.getInt("tatli_kalori"),
                                menu.getString("icecek"), menu.getInt("icecek_kalori")
                        ));
                    }

                    runOnUiThread(() -> {
                        YemekMenuAdapter adapter = new YemekMenuAdapter(this, menuler);
                        listView.setAdapter(adapter);
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}