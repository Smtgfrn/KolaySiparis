package com.smtgfrn.smtgf.restoransiparis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {
    RelativeLayout rlt;
    EditText etUser, etPass;
    CheckBox chkBeniHatirla;
    Context context = this;
    Connection con;
    String sunucuAdresi,veritabani, vtkuladi, vtsifre;
    ShredPref shredPref = new ShredPref();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rlt = findViewById(R.id.rlt);
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        chkBeniHatirla = findViewById(R.id.chkBeniHatirla);

        if(shredPref.getValueBoolean(context,"remember"))
        {
            etUser.setText(shredPref.getValue(context,"username"));
            chkBeniHatirla.setChecked(shredPref.getValueBoolean(context,"remember"));
        }

        rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chkBeniHatirla.isChecked())
                {
                    shredPref.save(context,"username",etUser.getText().toString());
                }
                else{
                    shredPref.save(context,"username","");
                }
                shredPref.saveBoolen(context,"remember",chkBeniHatirla.isChecked());
                   GirisKontrol baglan = new GirisKontrol();
                   baglan.execute();
            }
        });

        sunucuAdresi = "192.168.1.101";
        veritabani = "KolaySiparis";
        vtkuladi = "sa";
        vtsifre = "bkareeksi3ac";
    }
    public void Register(View view)
    {
        Intent ıntent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(ıntent);


    }

    public void RestoranSahibiYonlendir(View view){
        Intent intent=new Intent(getApplicationContext(),AdminLoginActivity.class);
        startActivity(intent);
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

            Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
            if(basarilimi)
            {
                Toast.makeText(LoginActivity.this,"Giris Basarili",Toast.LENGTH_LONG).show();
            }
        }

        @Override

        protected String doInBackground(String... strings) {

            String kadi = etUser.getText().toString();
            String sifre = etPass.getText().toString();
            if(kadi.trim().equals("") || sifre.trim().equals(""))
                mesaj = "Kullanici adi ve şifre girin!";
            else
            {
                try
                {
                    con = Baglanti(vtkuladi,vtsifre);

                    if(con == null)
                    {
                        mesaj = "Bağlantınızı kontrol edin.";
                    }
                    else
                    {
                        String sorgu = "select * from Kullanıcılar where KullaniciAdi = '"+kadi.toString()+"' and Sifre = '"+sifre.toString()+"'";
                        Statement durum = con.createStatement();
                        ResultSet rs = durum.executeQuery(sorgu);
                        if(rs.next())
                        {
                            basarilimi = true;

                            mesaj = "Giriş Başarılı!";
                            Intent intent = new Intent(getApplicationContext(),Main_PageActivity.class);
                            intent.putExtra("kAdi", rs.getString("KullaniciAdi"));
                            startActivity(intent);
                            con.close();
                        }
                        else
                        {
                            mesaj = "Kullanıcı Adı veya Şifreniz hatalı!";
                        }

                    }
                }
                catch (Exception ex)
                {
                    basarilimi = false;
                    mesaj = ex.getMessage();
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
