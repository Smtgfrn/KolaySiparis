package com.smtgfrn.smtgf.restoransiparis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RestaurantListActivity extends AppCompatActivity {

    String Sehir,Ilce;
    int Sehirid,Ilceid;
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    ArrayList<String> restoranAdlari = new ArrayList<>();
    ArrayList<Integer> restoranIdler = new ArrayList<>();
    Baglanti baglanti = new Baglanti();
    String kAdi;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        Bundle extras = getIntent().getExtras();
        kAdi = extras.getString("kAdi");
        Sehir = extras.getString("Sehir");
        Ilce = extras.getString("Ilce");
        listView = findViewById(R.id.listViewOzet);


        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(RestaurantListActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "select * from İl where ŞehirAdi='"+Sehir+"'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if(rs.next()) {
                     Sehirid = rs.getInt("İlid");
                }

            }
        } catch (Exception ex) {
            Toast.makeText(RestaurantListActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(RestaurantListActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "select * from İlçe where İlçeAdi='"+Ilce+"'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if(rs.next()) {
                    Ilceid = rs.getInt("ilçeid");
                }

            }
        } catch (Exception ex) {
            Toast.makeText(RestaurantListActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(RestaurantListActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {

                String sorgu = "select * from Restoran where İlid = '"+Sehirid+"' and ilçeid = '"+Ilceid+"'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                while (rs.next())
                {
                    restoranAdlari.add(rs.getString("Adı"));
                    restoranIdler.add(rs.getInt("restoranid"));
                }

            }
        } catch (Exception ex) {
            Toast.makeText(RestaurantListActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,restoranAdlari){
            @Override
            public View getView(int position,  View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);

                return view;
            }
        };

        listView.setAdapter(veriAdaptoru);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ıntent = new Intent(getApplicationContext(),RestaurantMenuActivity.class);
                ıntent.putExtra("restoranId",restoranIdler.get(position));
                getIntent().putExtra("kAdi",kAdi);
                startActivity(ıntent);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_item)
        {
            Intent ıntent = new Intent(getApplicationContext(),LoginActivity.class);
            ıntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(ıntent);
        }
        else if(item.getItemId() == R.id.duzenle_item)
        {
            Intent ıntent = new Intent(getApplicationContext(),Bilgileri_Duzenle_KullaniciActivity.class);
            ıntent.putExtra("kAdi", kAdi);
            startActivity(ıntent);
        }
        else if(item.getItemId() == R.id.degistir_item)
        {
            Intent ıntent = new Intent(getApplicationContext(),Sifre_DegistirActivity.class);
            ıntent.putExtra("kAdi", kAdi);
            startActivity(ıntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
