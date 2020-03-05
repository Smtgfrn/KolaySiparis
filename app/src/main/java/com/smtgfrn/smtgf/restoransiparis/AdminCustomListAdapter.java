package com.smtgfrn.smtgf.restoransiparis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdminCustomListAdapter extends ArrayAdapter<Urun> implements View.OnClickListener {
    private Context myCtx;
    int resource;
    ArrayList<Urun> urunler;

    public AdminCustomListAdapter(Context myCtx, ArrayList<Urun> urunler)
    {
        super(myCtx,0,urunler);

        this.myCtx = myCtx;
        this.urunler = urunler;
    }

    private static class ViewHolder{
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
        AdminCustomListAdapter.ViewHolder viewHolder = new AdminCustomListAdapter.ViewHolder();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final AdminCustomListAdapter.ViewHolder viewHolder;
        View row;



        if (convertView ==null){
            viewHolder=new AdminCustomListAdapter.ViewHolder();
            LayoutInflater layoutInflater=(LayoutInflater)myCtx.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate (R.layout.menu_gor,parent,false);

            viewHolder.urun_ismi=row.findViewById (R.id.textview_urun_gor_isim);
            viewHolder.urun_fiyati = row.findViewById(R.id.textview_urun_gor_fiyat);
            row.setTag(viewHolder);
        }else{
            row=convertView;
            viewHolder=(AdminCustomListAdapter.ViewHolder) row.getTag();
        }

        final Urun urunler=getItem(position);
        viewHolder.urun_ismi.setText (urunler.getUrunAd());
        viewHolder.urun_fiyati.setText (urunler.getUrunFiyat());


        return row;
    }
}
