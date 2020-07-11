package com.example.cukaapellampung.pembeli;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cukaapellampung.R;
import com.example.cukaapellampung.server.BaseURL;
import com.squareup.picasso.Picasso;

public class DetailPembeli extends AppCompatActivity {

    EditText edtKodeCuka, edtUkuran, edtHarga;
    ImageView imgGambarCuka;

    String strKodeCuka, strUkuran, strHarga, strGamabr, _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembeli);

        edtKodeCuka = (EditText) findViewById(R.id.edtKodeCuka);
        edtUkuran = (EditText) findViewById(R.id.edtUkuran);
        edtHarga = (EditText) findViewById(R.id.edtHarga);

        imgGambarCuka = (ImageView) findViewById(R.id.gambar);

        Intent i = getIntent();
        strKodeCuka = i.getStringExtra("kodecuka");
        strUkuran = i.getStringExtra("ukuran");
        strHarga = i.getStringExtra("harga");
        strGamabr = i.getStringExtra("gambar");
        _id = i.getStringExtra("_id");

        edtKodeCuka.setText(strKodeCuka);
        edtUkuran.setText(strUkuran);
        edtHarga.setText(strHarga);
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + strGamabr)
                .into(imgGambarCuka);
    }
}
