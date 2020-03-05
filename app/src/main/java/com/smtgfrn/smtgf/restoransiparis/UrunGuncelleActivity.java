package com.smtgfrn.smtgf.restoransiparis;

import android.content.Intent;
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UrunGuncelleActivity extends AppCompatActivity {

    private String[] Kategoriler={"Yiyecek","İçecek","Tatlı"};
    private Spinner spinnerKategoriler;
    private Spinner spinnerUrunler;
    private ArrayList<String> urunler= new ArrayList<>();
    private ArrayAdapter<String> dataAdapterForKategoriler,dataAdapterForUrunler;
    String Kategori;
    int menuid,restoranid;
    EditText UrunlerinAdi,UrunlerinFiyati;
    String urunAd,urunFiyat;
    Baglanti baglanti=new Baglanti();
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_guncelle);

        spinnerKategoriler = findViewById(R.id.spnUrunGuncelleKategori);
        spinnerUrunler = findViewById(R.id.spnUrunGuncelleUrunler);
        UrunlerinAdi=findViewById(R.id.txtYeniAd);
        UrunlerinFiyati=findViewById(R.id.txtYeniFiyat);
        dataAdapterForKategoriler=new ArrayAdapter<String >(this,android.R.layout.simple_spinner_item,Kategoriler);
        dataAdapterForKategoriler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategoriler.setAdapter(dataAdapterForKategoriler);
        Bundle extras = getIntent().getExtras();
        restoranid= extras.getInt("restoranid");
        menuid=extras.getInt("menuId");

        spinnerKategoriler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                Kategori = spinnerKategoriler.getSelectedItem().toString();
                urunler.clear();
                if (Kategori.equals("Yiyecek")) {
                    try {
                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(UrunGuncelleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "select * from Yiyecek where menuid='" + menuid + "'";
                            Statement durum = con.createStatement();
                            ResultSet rs = durum.executeQuery(sorgu);
                            while (rs.next()) {
                                urunler.add(rs.getString("yiyecekAdı"));
                            }

                        }
                    } catch (Exception ex) {
                        Toast.makeText(UrunGuncelleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                else if(Kategori.equals("İçecek")){
                    try {
                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(UrunGuncelleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "select * from İcecek where menuid='" + menuid + "'";
                            Statement durum = con.createStatement();
                            ResultSet rs = durum.executeQuery(sorgu);
                            while (rs.next()) {
                                urunler.add(rs.getString("icecekAdi"));
                            }

                        }
                    } catch (Exception ex) {
                        Toast.makeText(UrunGuncelleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    try {
                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(UrunGuncelleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "select * from Tatli where menuid='" + menuid + "'";
                            Statement durum = con.createStatement();
                            ResultSet rs = durum.executeQuery(sorgu);
                            while (rs.next()) {
                                urunler.add(rs.getString("tatliAdi"));
                            }

                        }
                    } catch (Exception ex) {
                        Toast.makeText(UrunGuncelleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                dataAdapterForUrunler = new ArrayAdapter<String>(UrunGuncelleActivity.this, android.R.layout.simple_spinner_item, urunler);
                spinnerUrunler.setAdapter(dataAdapterForUrunler);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerUrunler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.WHITE);
                urunAd = spinnerUrunler.getSelectedItem().toString();
                UrunlerinAdi.setText(urunAd, EditText.BufferType.EDITABLE);
                if (Kategori.equals("Yiyecek")) {
                    try {

                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(UrunGuncelleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "Select * FROM Yiyecek WHERE yiyecekAdı='" + urunAd + "'and menuid='" + menuid + "'";
                            Statement durum = con.createStatement();
                            ResultSet rs = durum.executeQuery(sorgu);
                            if (rs.next()) {
                                urunFiyat = rs.getString("Fiyat");
                            }


                        }
                    } catch (Exception ex) {
                        Toast.makeText(UrunGuncelleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else if (Kategori.equals("İçecek")) {
                    try {

                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(UrunGuncelleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "Select * FROM İcecek WHERE icecekAdi='" + urunAd + "'and menuid='" + menuid + "'";
                            Statement durum = con.createStatement();
                            ResultSet rs = durum.executeQuery(sorgu);
                            if (rs.next()) {
                                urunFiyat = rs.getString("Fiyat");
                            }


                        }
                    } catch (Exception ex) {
                        Toast.makeText(UrunGuncelleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    try {

                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(UrunGuncelleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                          String sorgu = "Select * FROM Tatli WHERE tatliAdi='" + urunAd + "'and menuid='" + menuid + "'";
                            Statement durum = con.createStatement();
                            ResultSet rs = durum.executeQuery(sorgu);
                            if (rs.next()) {
                                urunFiyat = rs.getString("Fiyat");
                            }


                        }
                    } catch (Exception ex) {
                        Toast.makeText(UrunGuncelleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                UrunlerinFiyati.setText(urunFiyat, EditText.BufferType.EDITABLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void btnUrunGuncelleGuncelle(View view) {

        String YeniUrunAd= UrunlerinAdi.getText().toString();
        String YeniUrunFiyat= UrunlerinFiyati.getText().toString();
        if (YeniUrunAd.equals("")||YeniUrunFiyat.equals("")){
            Toast.makeText(UrunGuncelleActivity.this, "Lütfen Boş Alan Bırakmayınız", Toast.LENGTH_SHORT).show();
        }
        else {
            if (Kategori.equals("Yiyecek")) {

                try {

                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(UrunGuncelleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        String sorgu = "UPDATE Yiyecek SET yiyecekAdı='" + YeniUrunAd + "',Fiyat='" + YeniUrunFiyat + "' Where yiyecekAdı='" + urunAd + "'and menuid='" + menuid + "'";
                        PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                        Toast.makeText(UrunGuncelleActivity.this, "Ürün Başarıyla Güncellendi!", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception ex) {
                    Toast.makeText(UrunGuncelleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (Kategori.equals("İçecek")) {

                try {

                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(UrunGuncelleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        String sorgu = "UPDATE İcecek SET icecekAdi='" + YeniUrunAd + "',Fiyat='" + YeniUrunFiyat + "' Where icecekAdi='" + urunAd + "'and menuid='" + menuid + "'";
                        PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();


                    }
                } catch (Exception ex) {
                    Toast.makeText(UrunGuncelleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {

                try {

                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(UrunGuncelleActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        String sorgu = "UPDATE Tatli SET tatliAdi='" + YeniUrunAd + "',Fiyat='" + YeniUrunFiyat + "' Where tatliAdi='" + urunAd + "'and menuid='" + menuid + "'";
                        PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();


                    }
                } catch (Exception ex) {
                    Toast.makeText(UrunGuncelleActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            Intent intent = new Intent(getApplicationContext(),AdminMainPageActivity.class);
            intent.putExtra("restoranid",restoranid);
            startActivity(intent);
        }

    }
}
