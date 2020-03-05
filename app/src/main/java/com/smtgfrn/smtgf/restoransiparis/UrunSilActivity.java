package com.smtgfrn.smtgf.restoransiparis;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UrunSilActivity extends AppCompatActivity {

    private String[] Kategoriler={"Yiyecek","İçecek","Tatlı"};
    private Spinner spinnerKategoriler;
    private Spinner spinnerUrunler;
    private ArrayList<String> urunler= new ArrayList<>();
    private ArrayAdapter<String> dataAdapterForKategoriler,dataAdapterForUrunler;
    String Kategori;
    int menuid,restoranid;
    String urunAd;
    Baglanti baglanti=new Baglanti();
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_sil);

        spinnerKategoriler = findViewById(R.id.spnUrunSilKategori);
        spinnerUrunler = findViewById(R.id.spnUrunSilUrunler);
        dataAdapterForKategoriler=new ArrayAdapter<String >(this,android.R.layout.simple_spinner_item,Kategoriler);
        dataAdapterForKategoriler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategoriler.setAdapter(dataAdapterForKategoriler);
        Bundle extras = getIntent().getExtras();
        restoranid = extras.getInt("restoranid");
        menuid=extras.getInt("Menuid");
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
                            Toast.makeText(UrunSilActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "select * from Yiyecek where menuid='" + menuid + "'";
                            Statement durum = con.createStatement();
                            ResultSet rs = durum.executeQuery(sorgu);
                            while (rs.next()) {
                                urunler.add(rs.getString("yiyecekAdı"));
                            }

                        }
                    } catch (Exception ex) {
                        Toast.makeText(UrunSilActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else if(Kategori.equals("İçecek")){
                    try {
                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(UrunSilActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "select * from İcecek where menuid='" + menuid + "'";
                            Statement durum = con.createStatement();
                            ResultSet rs = durum.executeQuery(sorgu);
                            while (rs.next()) {
                                urunler.add(rs.getString("icecekAdi"));
                            }

                        }
                    } catch (Exception ex) {
                        Toast.makeText(UrunSilActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    try {
                        con = baglanti.Baglan(vtkuladi, vtsifre);

                        if (con == null) {
                            Toast.makeText(UrunSilActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                        } else {
                            String sorgu = "select * from Tatli where menuid='" + menuid + "'";
                            Statement durum = con.createStatement();
                            ResultSet rs = durum.executeQuery(sorgu);
                            while (rs.next()) {
                                urunler.add(rs.getString("tatliAdi"));
                            }

                        }
                    } catch (Exception ex) {
                        Toast.makeText(UrunSilActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
                dataAdapterForUrunler = new ArrayAdapter<String>(UrunSilActivity.this, android.R.layout.simple_spinner_item, urunler);
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
                urunAd=spinnerUrunler.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    public void btnUrunSil(View view){
        if (Kategori.equals("Yiyecek")) {
            try {

                con = baglanti.Baglan(vtkuladi, vtsifre);

                if (con == null) {
                    Toast.makeText(UrunSilActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                } else {
                    String sorgu = "DELETE FROM Yiyecek WHERE yiyecekAdı='"+urunAd+"'and menuid='"+menuid+"'";
                    PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();



                }
            } catch (Exception ex) {
                Toast.makeText(UrunSilActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (Kategori.equals("İçecek")) {
            try {

                con = baglanti.Baglan(vtkuladi, vtsifre);

                if (con == null) {
                    Toast.makeText(UrunSilActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                } else {
                    String sorgu = "DELETE FROM İcecek WHERE icecekAdi='"+urunAd+"'and menuid='"+menuid+"'";
                    PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();



                }
            } catch (Exception ex) {
                Toast.makeText(UrunSilActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            try {

                con = baglanti.Baglan(vtkuladi, vtsifre);

                if (con == null) {
                    Toast.makeText(UrunSilActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                } else {
                    String sorgu = "DELETE FROM Tatli WHERE tatliAdi='"+urunAd+"'and menuid='"+menuid+"'";
                    PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();





                }
            } catch (Exception ex) {
                Toast.makeText(UrunSilActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        Intent intent = new Intent(getApplicationContext(),AdminMainPageActivity.class);
        intent.putExtra("restoranid",restoranid);
        startActivity(intent);
    }
}
