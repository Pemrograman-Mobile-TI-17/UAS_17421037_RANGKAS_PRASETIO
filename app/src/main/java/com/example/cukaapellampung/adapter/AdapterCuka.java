package com.example.cukaapellampung.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cukaapellampung.model.ModelCuka;
import com.example.cukaapellampung.R;
import com.example.cukaapellampung.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterCuka extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelCuka> item;

    public AdapterCuka(Activity activity, List<ModelCuka> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_cuka, null);


        TextView ukuran = (TextView) convertView.findViewById(R.id.txtUkuran);
        TextView harga         = (TextView) convertView.findViewById(R.id.txtHarga);
        ImageView gambar         = (ImageView) convertView.findViewById(R.id.gambarCuka);

        ukuran.setText(item.get(position).getukuran());
        harga.setText("Rp." + item.get(position).getharga());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getgambar())
                .resize(40, 40)
                .centerCrop()
                .into(gambar);
        return convertView;
    }

}