package com.smtgfrn.smtgf.restoransiparis;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UrunEkleActivity extends AppCompatActivity {

    private String[] Kategoriler={"Yiyecek","İçecek","Tatlı"};

    private Spinner spinnerKategoriler;
    private ArrayAdapter<String> dataAdapterForKategoriler;
    int menuid,restoranid;
    String Kategori;
    Baglanti baglanti=new Baglanti();
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    String Adi,Fiyat;
    String urunAd="";
    String urunFiyat="";
    EditText UrunAdi,UrunFiyat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ekle);

        UrunAdi=findViewById(R.id.txtUrunAd);
        UrunFiyat=findViewById(R.id.txtUrunFiyat);

        Bundle extras = getIntent().getExtras();
        restoranid=extras.getInt("restoranid");
        menuid=extras.getInt("menuid");
        spinnerKategoriler= (Spinner) findViewById(R.id.spnUrunEkleKategori);
        dataAdapterForKategoriler=new ArrayAdapter<String >(this,android.R.layout.simple_spinner_item,Kategoriler);
        dataAdapterForKategoriler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategoriler.setAdapter(dataAdapterForKategoriler);

        spinnerKategoriler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                Kategori=spinnerKategoriler.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void UrunEkle(View view){
        urunAd=UrunAdi.getText().toString();
        urunFiyat=UrunFiyat.getText().toString();
        if (urunAd.trim().equals("") || urunFiyat.trim().equals("")){
            Toast.makeText(UrunEkleActivity.this, "Boş Alan Bırakmayınız!", Toast.LENGTH_SHORT).show();

        }
        else {
            if (Kategori.equals("Yiyecek")) {
                try {

                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(UrunEkleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        String sorgu = "Insert Into Yiyecek(yiyecekAdı,Fiyat,menuid) Values('" + urunAd.toString() + "','" + urunFiyat.toString() + "','" + menuid + "')";
                        PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        UrunAdi.setText("");
                        UrunFiyat.setText("");
                        Toast.makeText(UrunEkleActivity.this, "Ürün Başarıyla Eklendi!", Toast.LENGTH_SHORT).show();


                    }
                } catch (Exception ex) {
                    Toast.makeText(UrunEkleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (Kategori.equals("İçecek")) {
                try {

                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(UrunEkleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        String sorgu = "Insert Into İcecek(icecekAdi,Fiyat,menuid) Values('" + urunAd.toString() + "','" + urunFiyat.toString() + "','" + menuid + "')";
                        PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        UrunAdi.setText("");
                        UrunFiyat.setText("");
                        Toast.makeText(UrunEkleActivity.this, "Ürün Başarıyla Eklendi!", Toast.LENGTH_SHORT).show();


                    }
                } catch (Exception ex) {
                    Toast.makeText(UrunEkleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            if (Kategori.equals("Tatlı")) {
                try {

                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(UrunEkleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        String sorgu = "Insert Into Tatli(tatliAdi,Fiyat,menuid) Values('" + urunAd.toString() + "','" + urunFiyat.toString() + "','" + menuid + "')";
                        PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        UrunAdi.setText("");
                        UrunFiyat.setText("");
                        Toast.makeText(UrunEkleActivity.this, "Ürün Başarıyla Eklendi!", Toast.LENGTH_SHORT).show();


                    }
                } catch (Exception ex) {
                    Toast.makeText(UrunEkleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        }


    }
}
