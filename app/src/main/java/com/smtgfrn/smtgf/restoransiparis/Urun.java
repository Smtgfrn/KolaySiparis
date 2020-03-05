package com.smtgfrn.smtgf.restoransiparis;

public class Urun {

    private String urunAd;
    private String urunFiyat;
    private int adet = 0;

    public Urun(String urunAd,String urunFiyat) {
        this.urunAd = urunAd;
        this.urunFiyat = urunFiyat;
    }

    public String getUrunAd() {
        return urunAd;
    }

    public void setUrunAd(String urunAd) {
        this.urunAd = urunAd;
    }

    public void setUrunFiyat(String urunFiyat) {
        this.urunFiyat = urunFiyat;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public String getUrunFiyat() {
        return urunFiyat;
    }

    public int getAdet() {
        return adet;
    }
}
