package com.smtgfrn.smtgf.restoransiparis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SiparisOzetActivity extends AppCompatActivity {

    ArrayList<String> urunlerAdlari = new ArrayList<>();
    ArrayList<String> urunlerAdetleri = new ArrayList<>();
    ArrayList<String> urunlerFiyatlari = new ArrayList<>();
    TextView txtToplamTutar;
    ListView listViewAd,listViewFiyat,listViewAdet;
    Button scan_btn;
    Baglanti baglanti = new Baglanti();
    Connection con;
    String vtkuladi = "sa";
    String vtsifre = "bkareeksi3ac";
    int restoranid,kullaniciid;
    String kAdi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_ozet);
        listViewAd = findViewById(R.id.listViewUrunAd);
        listViewAdet = findViewById(R.id.listViewUrunAdet);
        listViewFiyat = findViewById(R.id.listViewUrunFiyat);
        scan_btn = findViewById(R.id.scan_btn);
        txtToplamTutar = findViewById(R.id.txtToplamTutar);
        Bundle extras = getIntent().getExtras();
        restoranid = extras.getInt("restoranid");
        kAdi = extras.getString("kAdi");
        final Activity activity = this;
        urunlerAdlari.add("Big King");
        urunlerAdlari.add("Coco Cola");
        urunlerAdlari.add("Sufle");
        urunlerFiyatlari.add("39 TL");
        urunlerFiyatlari.add("3,5 TL");
        urunlerFiyatlari.add("16 TL");
        urunlerAdetleri.add("2");
        urunlerAdetleri.add("1");
        urunlerAdetleri.add("2");

        try {
            con = baglanti.Baglan(vtkuladi, vtsifre);

            if (con == null) {
                Toast.makeText(SiparisOzetActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
            } else {
                String sorgu = "select * from Kullanıcılar where KullaniciAdi='"+kAdi+"'";
                Statement durum = con.createStatement();
                ResultSet rs = durum.executeQuery(sorgu);
                if(rs.next()) {
                    kullaniciid = rs.getInt("kullanıcıid");

                }

            }
        } catch (Exception ex) {
            Toast.makeText(SiparisOzetActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> veriAdaptoru = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,urunlerAdlari){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);

                return view;
            }
        };
        listViewAd.setAdapter(veriAdaptoru);
        ArrayAdapter<String> veriadaptoru2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,urunlerFiyatlari){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);

                return view;
            }
        };
        listViewFiyat.setAdapter(veriadaptoru2);
        ArrayAdapter<String> veriadaptoru3 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,urunlerAdetleri){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);

                return view;
            }
        };
        listViewAdet.setAdapter(veriadaptoru3);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                IntentIntegrator integrator=new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        String toplamTutar = txtToplamTutar.getText().toString();
        int siparisid = 29;
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents()== null){
                Toast.makeText(this,"Tarama iptal edildi!",Toast.LENGTH_LONG).show();
            }
            else{
                try {
                    int masaNo = Integer.parseInt(result.getContents());
                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(SiparisOzetActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        String sorgu = "Insert Into Siparis(restoranid,masaNo,ToplamTutar) Values('" + restoranid + "','" + masaNo + "','" + toplamTutar + "')";
                        PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                        preparedStatement.executeUpdate();
                        preparedStatement.close();
                    }
                } catch (Exception ex) {
                    Toast.makeText(SiparisOzetActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }

                try {

                    con = baglanti.Baglan(vtkuladi, vtsifre);

                    if (con == null) {
                        Toast.makeText(SiparisOzetActivity.this, "Bağlantınızı kontrol edin.", Toast.LENGTH_SHORT).show();
                    } else {
                        for(int i=0;i<3;i++) {
                            String sorgu = "Insert Into SiparistekiUrunler(UrunAd,siparisid,UrunFiyat,UrunAdet) Values('" + urunlerAdlari.get(i) + "','" + siparisid + "','" + urunlerFiyatlari.get(i) + "','" + urunlerAdetleri.get(i) +"')";
                            PreparedStatement preparedStatement = con.prepareStatement(sorgu);
                            preparedStatement.executeUpdate();
                            preparedStatement.close();
                        }
                    }
                } catch (Exception ex) {
                    Toast.makeText(SiparisOzetActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(SiparisOzetActivity.this,"Siparisiniz Alınmıstır!",Toast.LENGTH_LONG).show();
                Intent ıntent = new Intent(getApplicationContext(),Main_PageActivity.class);
                ıntent.putExtra("kAdi", kAdi);
                startActivity(ıntent);
            }

        }
        else{
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

}
