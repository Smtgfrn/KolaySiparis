package com.smtgfrn.smtgf.restoransiparis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main_PageActivity extends AppCompatActivity {
    ArrayList<String> iller = new ArrayList<String>();
    ArrayList<String> ilçeler = new ArrayList<String>();

    RelativeLayout rlt;
    private Spinner spinnerİller;
    private Spinner spinnerİlçeler;
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    String Şehir,İlce;
    int Sehirid;
    private ArrayAdapter<String> dataAdapterForIller;
    private ArrayAdapter<String> dataAdapterForIlceler;
    String kAdi;
    Baglanti baglanti = new Baglanti();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);
        Bundle extras = getIntent().getExtras();
        kAdi = extras.getString("kAdi");

        rlt = findViewById(R.id.rltMain_Page);
        spinnerİller = findViewById(R.id.spinnerIller);
        spinnerİlçeler = findViewById(R.id.spinnerIlceler);

        rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ıntent = new Intent(getApplicationContext(),RestaurantListActivity.class);
                ıntent.putExtra("Sehir", Şehir);
                ıntent.putExtra("kAdi", kAdi);
                ıntent.putExtra("Ilce", İlce);
                startActivity(ıntent);
            }
        });

        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(Main_PageActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "select * from İl";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                while (rs.next()) {
                    iller.add(rs.getString("ŞehirAdi"));
                }

            }
        } catch (Exception ex) {
            Toast.makeText(Main_PageActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dataAdapterForIller = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, iller);
        spinnerİller.setAdapter(dataAdapterForIller);

        spinnerİller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
               Şehir = spinnerİller.getSelectedItem().toString();
               ilçeler.clear();
                try {
                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(Main_PageActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        String sorgu = "select * from İl where ŞehirAdi='"+Şehir+"'";
                        Statement durum = con.createStatement();
                        ResultSet rs = durum.executeQuery(sorgu);
                        if(rs.next()) {
                            Sehirid = rs.getInt("İlid");
                        }

                    }
                } catch (Exception ex) {
                    Toast.makeText(Main_PageActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                try {
                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(Main_PageActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        String sorgu = "select * from İlçe where İlid='"+Sehirid+"'";
                        Statement durum = con.createStatement();
                        ResultSet rs = durum.executeQuery(sorgu);
                        while(rs.next()) {
                            ilçeler.add(rs.getString("İlçeAdi"));
                        }

                    }
                } catch (Exception ex) {
                    Toast.makeText(Main_PageActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dataAdapterForIlceler = new ArrayAdapter<String>(Main_PageActivity.this, android.R.layout.simple_spinner_item, ilçeler);
                spinnerİlçeler.setAdapter(dataAdapterForIlceler);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerİlçeler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.WHITE);
                İlce = spinnerİlçeler.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
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
