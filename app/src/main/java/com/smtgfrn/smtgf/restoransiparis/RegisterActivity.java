package com.smtgfrn.smtgf.restoransiparis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    EditText isim,soyisim,telefon,kAdi,sifre,sifreTekrar;
    RelativeLayout rlt;
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rlt = findViewById(R.id.rlt);
        isim = findViewById(R.id.etName);
        soyisim = findViewById(R.id.etSurname);
        telefon = findViewById(R.id.etTel);
        kAdi = findViewById(R.id.etUser);
        sifre = findViewById(R.id.etPass);
        sifreTekrar = findViewById(R.id.etPassVal);

        rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GirisKontrol baglan = new GirisKontrol();
                baglan.execute();

            }
        });
    }
    public class GirisKontrol extends AsyncTask<String,String,String>
    {
        String mesaj = "";
        Boolean basarilimi = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {

            Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_LONG).show();
            if(basarilimi)
            {
                Toast.makeText(RegisterActivity.this,"Giris Basarili",Toast.LENGTH_LONG).show();
            }
        }

        @Override

        protected String doInBackground(String... strings) {

            String ad = isim.getText().toString();
            String soyad = soyisim.getText().toString();
            String tel = telefon.getText().toString();
            String kadi = kAdi.getText().toString();
            String pass = sifre.getText().toString();
            String passVal = sifreTekrar.getText().toString();
            if(kadi.trim().equals("") || pass.trim().equals("") || passVal.trim().equals("") || ad.trim().equals("") || soyad.trim().equals("") || tel.trim().equals(""))
                mesaj = "Boş Alan Bırakmayınız!";
            else
            {

                if(pass.equals(passVal)) {
                    try {
                        con = Baglanti(vtkuladi, vtsifre);

                        if (con == null) {
                            mesaj = "Bağlantınızı kontrol edin.";
                        } else {
                            try {
                                String sorgu2 = "select * from Kullanıcılar where KullaniciAdi = '"+kadi+"'";
                                Statement durum2 = con.createStatement();
                                ResultSet rs2 = durum2.executeQuery(sorgu2);
                                if(rs2.next())
                                {
                                    mesaj = "Bu Kullanıcı Adı Daha Önceden Alınmış";
                                }
                                else{
                                    String sorgu = "Insert Into Kullanıcılar(Adi,Soyadi,KullaniciAdi,Sifre,Telefon) Values('" + ad.toString() + "','" + soyad.toString() + "','" + kadi.toString() + "','" + pass.toString() + "','" + tel.toString() + "')";
                                    PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                                    preparedStatement.executeUpdate();
                                    preparedStatement.close();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    return "Kayıt Başarılı";
                                }


                            } catch (SQLException se) {
                                Log.e("Error", se.getMessage());
                            }
                        }
                    } catch (Exception ex) {
                        basarilimi = false;
                        mesaj = ex.getMessage();
                    }
                }
                else
                {
                    mesaj = "Şifreler uyuşmuyor!";
                }
            }
            return mesaj;
        }

        @SuppressLint("NewApi")
        public Connection Baglanti(String user, String password)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection connection = null;
            String ConnectionURL = null;
            try {
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                ConnectionURL = "jdbc:jtds:sqlserver://192.168.43.103/KolaySiparis:1433";
                connection = DriverManager.getConnection(ConnectionURL,user,password);

            } catch (SQLException se) {
                Log.e("error here 1:", se.getMessage());
            } catch (ClassNotFoundException e) {
                Log.e("error here 2:", e.getMessage());
            } catch (Exception e) {
                Log.e("error here 3:", e.getMessage());
            }
            return  connection;
        }

    }
}
