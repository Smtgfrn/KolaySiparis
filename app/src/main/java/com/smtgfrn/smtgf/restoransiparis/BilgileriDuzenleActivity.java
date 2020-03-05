package com.smtgfrn.smtgf.restoransiparis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BilgileriDuzenleActivity extends AppCompatActivity {

    EditText RestoranSahibiAdi,RestoranSahibiSoyadi,RestoranSahibiMail,RestoranAdi,RestoranAdresi;
    Baglanti baglanti=new Baglanti();
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    int restoranid;
    String Adi,Soyadi,Mail,RestoraninAdi,RestoraninAdresi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bilgileri_duzenle);
        RestoranSahibiAdi=findViewById(R.id.etRestoranSahibiAdi);
        RestoranSahibiSoyadi=findViewById(R.id.etRestoranSahibiSoyadi);
        RestoranSahibiMail=findViewById(R.id.etRestoranSahibiMail);
        RestoranAdi=findViewById(R.id.etRestoranAdi);
        RestoranAdresi=findViewById(R.id.etRestoranAdresi);
        Bundle extras = getIntent().getExtras();
        restoranid=extras.getInt("restoranid");

        try {

            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(BilgileriDuzenleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "Select * FROM RestoranSahibi WHERE restoranid='" + restoranid + "'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if (rs.next()) {
                    Adi = rs.getString("Adı");
                    RestoranSahibiAdi.setText(Adi, EditText.BufferType.EDITABLE);
                    Soyadi=rs.getString("Soyadı");
                    RestoranSahibiSoyadi.setText(Soyadi, EditText.BufferType.EDITABLE);
                    Mail=rs.getString("mail");
                    RestoranSahibiMail.setText(Mail, EditText.BufferType.EDITABLE);
                }


            }
        } catch (Exception ex) {
            Toast.makeText(BilgileriDuzenleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        try {

            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(BilgileriDuzenleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "Select * FROM Restoran WHERE restoranid='" + restoranid + "'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if (rs.next()) {
                    RestoraninAdi = rs.getString("Adı");
                    RestoranAdi.setText(RestoraninAdi, EditText.BufferType.EDITABLE);
                    RestoraninAdresi=rs.getString("Adres");
                    RestoranAdresi.setText(RestoraninAdresi, EditText.BufferType.EDITABLE);

                }


            }
        } catch (Exception ex) {
            Toast.makeText(BilgileriDuzenleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void btnBilgileriDuzenleKaydet(View view) {

        String YeniRestoranSahibiAdi = RestoranSahibiAdi.getText().toString();
        String YeniRestoranSahibiSoyadi = RestoranSahibiSoyadi.getText().toString();
        String YeniRestoranSahibiMail = RestoranSahibiMail.getText().toString();
        String YeniRestoranAdi = RestoranAdi.getText().toString();
        String YeniRestoranAdresi = RestoranAdresi.getText().toString();
        if (YeniRestoranSahibiAdi.equals("") || YeniRestoranSahibiSoyadi.equals("") || YeniRestoranSahibiMail.equals("") || YeniRestoranAdi.equals("") || YeniRestoranAdresi.equals("")) {
            Toast.makeText(BilgileriDuzenleActivity.this, "Lütfen Boş Alan Bırakmayınız", Toast.LENGTH_SHORT).show();
        } else {
            try {

                con = baglanti.Baglan(vtkuladi, vtsifre);

                if (con == null) {
                    Toast.makeText(BilgileriDuzenleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                } else {
                    String sorgu = "UPDATE RestoranSahibi SET Adı='" + YeniRestoranSahibiAdi + "',Soyadı='" + YeniRestoranSahibiSoyadi + "',mail='" + YeniRestoranSahibiMail + "' Where restoranid='" + restoranid + "'";
                    PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    Toast.makeText(BilgileriDuzenleActivity.this, "Bilgileriniz Güncellendi!", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception ex) {
                Toast.makeText(BilgileriDuzenleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }

            try {

                con = baglanti.Baglan(vtkuladi, vtsifre);

                if (con == null) {
                    Toast.makeText(BilgileriDuzenleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                } else {
                    String sorgu = "UPDATE Restoran SET Adı='" + YeniRestoranAdi + "',Adres='" + YeniRestoranAdresi + "' Where restoranid='" + restoranid + "'";
                    PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                    Toast.makeText(BilgileriDuzenleActivity.this, "Bilgileriniz Güncellendi!", Toast.LENGTH_SHORT).show();

                }
            } catch (Exception ex) {
                Toast.makeText(BilgileriDuzenleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getApplicationContext(), AdminMainPageActivity.class);
            intent.putExtra("restoranid", restoranid);
            startActivity(intent);
        }

    }
}
