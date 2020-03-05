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

public class Sifre_DegistirActivity extends AppCompatActivity {

    EditText et_Oldpass,et_Newpass;
    RelativeLayout rlt;
    Baglanti baglanti = new Baglanti();
    String eskiSifre,yeniSifre,kayıtlıEskiSifre;
    String kAdi;
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre__degistir);
        Bundle extras = getIntent().getExtras();
        kAdi = extras.getString("kAdi");
        et_Newpass = findViewById(R.id.et_NewPass);
        et_Oldpass = findViewById(R.id.et_OldPass);
        rlt = findViewById(R.id.rlt);

        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(Sifre_DegistirActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "select * from Kullanıcılar where KullaniciAdi='" + kAdi + "'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if (rs.next()) {
                    kayıtlıEskiSifre = rs.getString("Sifre");
                }

            }
        } catch (Exception ex) {
            Toast.makeText(Sifre_DegistirActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        rlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eskiSifre = et_Oldpass.getText().toString();
                yeniSifre = et_Newpass.getText().toString();

                if(eskiSifre.equals(kayıtlıEskiSifre))
                {
                    try {

                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(Sifre_DegistirActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "UPDATE Kullanıcılar SET Sifre='" + yeniSifre + "' Where KullaniciAdi='"+ kAdi+ "'";
                            PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                            Toast.makeText(Sifre_DegistirActivity.this, "Şifre Başarıyla Değiştirildi!", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception ex) {
                        Toast.makeText(Sifre_DegistirActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    Intent ıntent = new Intent(getApplicationContext(),LoginActivity.class);
                    ıntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(ıntent);
                }
                else
                {
                    Toast.makeText(Sifre_DegistirActivity.this, "Eski Şifrenizi Yanlış Girdiniz!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
