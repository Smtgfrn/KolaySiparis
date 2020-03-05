package com.smtgfrn.smtgf.restoransiparis;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminLoginActivity extends AppCompatActivity {
    RelativeLayout rlt;
    EditText etUser, etPass;
    CheckBox chkBeniHatirla;
    Context context = this;
    Connection con;
    Baglanti baglanti = new Baglanti();
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        rlt = findViewById(R.id.rlt);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        chkBeniHatirla = findViewById(R.id.chkBeniHatirla);

        rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GirisKontrol baglan = new GirisKontrol();
                baglan.execute();
            }
        });
    }

    public class GirisKontrol extends AsyncTask<String,String,String> {
        String mesaj = "";
        Boolean basarilimi = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(AdminLoginActivity.this, s, Toast.LENGTH_LONG).show();
            if (basarilimi) {
                Toast.makeText(AdminLoginActivity.this, "Giris Basarili", Toast.LENGTH_LONG).show();
            }
        }

        @Override

        protected String doInBackground(String... strings) {

            String kadi = etUser.getText().toString();
            String sifre = etPass.getText().toString();
            if (kadi.trim().equals("") || sifre.trim().equals(""))
                mesaj = "Kullanici adi ve şifre girin!";
            else {
                try {
                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        mesaj = "Bağlantınızı kontrol edin.";
                    } else {
                        String sorgu = "select * from RestoranSahibi where kullaniciAdi = '" + kadi.toString() + "' and Sifre = '" + sifre.toString() + "'";
                        Statement durum = con.createStatement();
                        ResultSet rs = durum.executeQuery(sorgu);
                        if (rs.next()) {
                            basarilimi = true;
                            mesaj = "Giriş Başarılı!";
                            Intent intent = new Intent(getApplicationContext(), AdminMainPageActivity.class);
                            intent.putExtra("restoranid", rs.getInt("restoranid"));
                            startActivity(intent);
                            con.close();
                        }
                        else{
                            mesaj = "Kullanıcı Adı veya Şifreniz hatalı!";
                        }

                    }
                } catch (Exception ex) {
                    basarilimi = false;
                    mesaj = ex.getMessage();
                }
            }
            return mesaj;
        }
    }
}
