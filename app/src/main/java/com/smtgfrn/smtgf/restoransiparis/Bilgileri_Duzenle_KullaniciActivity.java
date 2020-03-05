package com.smtgfrn.smtgf.restoransiparis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Bilgileri_Duzenle_KullaniciActivity extends AppCompatActivity {

    EditText et_isim,et_soyisim,et_telefon,et_mail;
    RelativeLayout rlt;
    Baglanti baglanti = new Baglanti();
    String isim,soyisim,mail,telefon;
    String kAdi;
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        kAdi = extras.getString("kAdi");
        setContentView(R.layout.activity_bilgileri__duzenle__kullanici);
        et_isim  = findViewById(R.id.et_Name);
        et_soyisim = findViewById(R.id.et_Surname);
        et_mail = findViewById(R.id.et_Mail);
        et_telefon = findViewById(R.id.et_Tel);
        rlt = findViewById(R.id.rlt);

        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(Bilgileri_Duzenle_KullaniciActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "select * from Kullanıcılar where KullaniciAdi='" + kAdi + "'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if (rs.next()) {
                    isim = rs.getString("Adi");
                    soyisim = rs.getString("Soyadi");
                    telefon = rs.getString("Telefon");
                    mail = rs.getString("Mail");
                }

            }
        } catch (Exception ex) {
            Toast.makeText(Bilgileri_Duzenle_KullaniciActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        et_isim.setText(isim, EditText.BufferType.EDITABLE);
        et_soyisim.setText(soyisim, EditText.BufferType.EDITABLE);
        et_telefon.setText(telefon, EditText.BufferType.EDITABLE);
        et_mail.setText(mail, EditText.BufferType.EDITABLE);



        rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yeniAd = et_isim.getText().toString();
                String yeniSoyad = et_soyisim.getText().toString();
                String yeniTel = et_telefon.getText().toString();
                String yeniMail = et_mail.getText().toString();
                if (yeniAd.equals("")||yeniSoyad.equals("") ||yeniTel.equals("") ||yeniMail.equals("")){
                    Toast.makeText(Bilgileri_Duzenle_KullaniciActivity.this, "Lütfen Boş Alan Bırakmayınız", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {

                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(Bilgileri_Duzenle_KullaniciActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "UPDATE Kullanıcılar SET Adi='" + yeniAd + "',Soyadi='" + yeniSoyad + "', Telefon = '"+yeniTel+"', Mail = '"+yeniMail+"' Where KullaniciAdi='"+ kAdi+ "'";
                            PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                            Toast.makeText(Bilgileri_Duzenle_KullaniciActivity.this, "Bilgiler Başarıyla Güncellendi!", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception ex) {
                        Toast.makeText(Bilgileri_Duzenle_KullaniciActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(getApplicationContext(),Main_PageActivity.class);
                    intent.putExtra("kAdi",kAdi);
                    startActivity(intent);
                }

            }
        });
    }
}
