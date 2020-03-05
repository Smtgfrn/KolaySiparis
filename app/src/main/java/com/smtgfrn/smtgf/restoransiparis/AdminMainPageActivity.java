package com.smtgfrn.smtgf.restoransiparis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminMainPageActivity extends AppCompatActivity {
    Button btnUrunEkle;
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    Baglanti baglanti=new Baglanti();
    int restoranid,menuid;
    String restoranAdi;
    TextView restoranAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
        Bundle extras = getIntent().getExtras();
        restoranid = extras.getInt("restoranid");
        restoranAd = findViewById(R.id.txtRestoranAdi);
        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(AdminMainPageActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "select * from Menu where restoranid='"+restoranid + "'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if(rs.next()) {
                    menuid = rs.getInt("menuid");
                    con.close();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(AdminMainPageActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(AdminMainPageActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "select * from Restoran where restoranid='"+restoranid + "'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if(rs.next()) {
                    restoranAdi = rs.getString("Adı");
                    con.close();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(AdminMainPageActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        restoranAd.setText(restoranAdi);


    }

    public void UrunEkle(View view){
        Intent intent=new Intent(getApplicationContext(),UrunEkleActivity.class);
        intent.putExtra("restoranid",restoranid);
        intent.putExtra("menuid",menuid);
        startActivity(intent);
    }

    public void UrunSil(View view){
        Intent intent=new Intent(getApplicationContext(),UrunSilActivity.class);
        intent.putExtra("Menuid",menuid);
        intent.putExtra("restoranid",restoranid);
        startActivity(intent);
    }

    public void UrunGuncelle(View view){
        Intent intent=new Intent(getApplicationContext(),UrunGuncelleActivity.class);
        intent.putExtra("menuId",menuid);
        intent.putExtra("restoranid",restoranid);
        startActivity(intent);
    }
    public void MenuGor(View view){
        Intent intent=new Intent(getApplicationContext(),AdminMenuGorActivity.class);
        intent.putExtra("menuId",menuid);
        intent.putExtra("restoranid",restoranid);
        startActivity(intent);
    }
    public void BilgileriDuzenle(View view){
        Intent intent=new Intent(getApplicationContext(),BilgileriDuzenleActivity.class);
        intent.putExtra("menuId",menuid);
        intent.putExtra("restoranid",restoranid);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.lagout_admin,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout_item)
        {
            Intent ıntent = new Intent(getApplicationContext(),AdminLoginActivity.class);
            ıntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(ıntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
