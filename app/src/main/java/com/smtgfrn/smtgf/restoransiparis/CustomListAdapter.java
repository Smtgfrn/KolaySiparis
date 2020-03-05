package com.smtgfrn.smtgf.restoransiparis;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<Urun> implements View.OnClickListener {

    private Context myCtx;
    int resource;
    ArrayList<Urun> urunler;

    public CustomListAdapter(Context myCtx, ArrayList<Urun> urunler)
    {
        super(myCtx,0,urunler);

        this.myCtx = myCtx;
        this.urunler = urunler;
    }

    private static class ViewHolder{
        Button b_arti,b_eksi;
        EditText siparis_miktari;
        TextView urun_ismi,urun_fiyati;
    }

    public int getCount() {
        return urunler.size();
    }

    @Override
    public Urun getItem(int position) {
        return urunler.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onClick(View v) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.siparis_miktari = (EditText)v.findViewById (R.id.editText2_urun_miktari);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        View row;



        if (convertView ==null){
            viewHolder=new ViewHolder();
            LayoutInflater layoutInflater=(LayoutInflater)myCtx.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate (R.layout.menu_urunler,parent,false);

            viewHolder.b_arti=row.findViewById(R.id.buton_arti);
            viewHolder.b_eksi=row.findViewById(R.id.buton_eksi);
            viewHolder.siparis_miktari= row.findViewById(R.id.editText2_urun_miktari);
            viewHolder.urun_ismi=row.findViewById (R.id.textview_urun_isim);
            viewHolder.urun_fiyati = row.findViewById(R.id.textview_urun_fiyat);
            row.setTag(viewHolder);
        }else{
            row=convertView;
            viewHolder=(ViewHolder) row.getTag();
        }

        final Urun urunler=getItem(position);
        viewHolder.urun_ismi.setText (urunler.getUrunAd());
        viewHolder.urun_fiyati.setText (urunler.getUrunFiyat());
        viewHolder.siparis_miktari.setText (urunler.getAdet()+"");

       viewHolder.b_eksi.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               updateEdittext(position,viewHolder.siparis_miktari,-1);
           }
       });

        viewHolder.b_arti.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEdittext (position,viewHolder.siparis_miktari,+1);
            }
        });


       return row;
    }
    private void updateEdittext(int position, EditText t_siparis_mikatari, int value) {

        Urun urunler =getItem(position);

        if (value>0){
            urunler.setAdet (urunler.getAdet()+1);
        }else{
            if (urunler.getAdet()>0){
                urunler.setAdet (urunler.getAdet()-1);
            }
        }
        // Burada +"" kullanmadığımız zaman hata almamız muhtemel. Zira edittext
        // string bir değer almak zorunda.
        t_siparis_mikatari.setText(urunler.getAdet()+"");
    }



}
