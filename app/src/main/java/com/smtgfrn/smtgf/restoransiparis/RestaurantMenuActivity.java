package com.smtgfrn.smtgf.restoransiparis;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuActivity extends AppCompatActivity {

    TabHost tabHost;
    Button btnSiparisOnay;
    TabHost.TabSpec tb1,tb2,tb3;
    ArrayList<Urun> urunler;
    ListView listView,listView2, listview3;
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    ArrayList<String> yiyecekAdlari = new ArrayList<>();
    ArrayList<String> yiyecekFiyatlari = new ArrayList<>();
    ArrayList<String> icecekAdlari = new ArrayList<>();
    ArrayList<String> icecekFiyatlari = new ArrayList<>();
    ArrayList<String> tatliAdlari = new ArrayList<>();
    ArrayList<String> tatliFiyatlari = new ArrayList<>();
    int restoranid,menuid;
    String kAdi;
    CustomListAdapter customListAdapter;
    Baglanti baglanti = new Baglanti();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        Bundle extras = getIntent().getExtras();
        restoranid = extras.getInt("restoranId");
        kAdi = extras.getString("kAdi");

        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        listView = findViewById(R.id.listViewYiyecek);
        urunler = new ArrayList<>();

        tb1 = tabHost.newTabSpec("Yiyecek");
        tb1.setIndicator("Yiyecek");
        tb1.setContent(R.id.Yiyecek);
        tabHost.addTab(tb1);

        tb2 = tabHost.newTabSpec("İcecek");
        tb2.setIndicator("İçecek");
        tb2.setContent(R.id.İcecek);
        tabHost.addTab(tb2);

        tb3 = tabHost.newTabSpec("Tatli");
        tb3.setIndicator("Tatlı");
        tb3.setContent(R.id.Tatli);
        tabHost.addTab(tb3);


        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(RestaurantMenuActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {

                String sorgu = "select * from Menu where restoranid='"+restoranid+"'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if (rs.next())
                {
                    menuid = rs.getInt("menuid");
                }

            }
        } catch (Exception ex) {
            Log.e("error here 5:", ex.getMessage());
        }

       try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(RestaurantMenuActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {

                String sorgu = "select * from Yiyecek where menuid='"+menuid+"'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                while(rs.next())
                {
                   yiyecekAdlari.add(rs.getString("yiyecekAdı"));
                   yiyecekFiyatlari.add(rs.getString("Fiyat"));
                }

            }
        } catch (Exception ex) {
            Log.e("error here 4:", ex.getMessage());
        }
        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(RestaurantMenuActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {

                String sorgu = "select * from İcecek where menuid='"+menuid+"'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                while(rs.next())
                {
                    icecekAdlari.add(rs.getString("icecekAdi"));
                    icecekFiyatlari.add(rs.getString("Fiyat"));
                }

            }
        } catch (Exception ex) {
            Log.e("error here 4:", ex.getMessage());
        }
        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(RestaurantMenuActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {

                String sorgu = "select * from Tatli where menuid='"+menuid+"'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                while(rs.next())
                {
                    tatliAdlari.add(rs.getString("tatliAdi"));
                    tatliFiyatlari.add(rs.getString("Fiyat"));
                }

            }
        } catch (Exception ex) {
            Log.e("error here 4:", ex.getMessage());
        }

        initialize();
        fill(yiyecekAdlari,yiyecekFiyatlari);
        initialize2();
        fill(icecekAdlari,icecekFiyatlari);
        initialize3();
        fill(tatliAdlari,tatliFiyatlari);
    }

    private void initialize() {
        urunler = new ArrayList<Urun>();
        listView=(ListView)findViewById(R.id.listViewYiyecek);
        customListAdapter=new CustomListAdapter (RestaurantMenuActivity.this,urunler);
        listView.setAdapter(customListAdapter);
    }
    private void initialize2() {
        urunler = new ArrayList<Urun>();
        listView2=(ListView)findViewById(R.id.listViewİcecek);
        customListAdapter=new CustomListAdapter (RestaurantMenuActivity.this,urunler);
        listView2.setAdapter(customListAdapter);
    }
    private void initialize3() {
        urunler = new ArrayList<Urun>();
        listview3=(ListView)findViewById(R.id.listViewTatli);
        customListAdapter=new CustomListAdapter (RestaurantMenuActivity.this,urunler);
        listview3.setAdapter(customListAdapter);
    }
    private void fill(ArrayList<String>urunAd,ArrayList<String>urunFiyati ) {
        for (int i = 0; i < urunAd.size(); i++) {
            Urun person = new Urun(urunAd.get(i), urunFiyati.get(i) + " ₺");
            urunler.add(person);
        }
    }

    public void SiparisOnayla(View view)
    {
        Intent ıntent = new Intent(getApplicationContext(),SiparisOzetActivity.class);
        ıntent.putExtra("restoranid",restoranid);
        ıntent.putExtra("kAdi", kAdi);
        startActivity(ıntent);
    }




}
