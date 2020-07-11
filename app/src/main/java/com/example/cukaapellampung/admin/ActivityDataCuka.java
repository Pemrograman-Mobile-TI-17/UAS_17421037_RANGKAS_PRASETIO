package com.example.cukaapellampung.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cukaapellampung.R;
import com.example.cukaapellampung.adapter.AdapterCuka;
import com.example.cukaapellampung.model.ModelCuka;
import com.example.cukaapellampung.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityDataCuka extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterCuka adapter;
    ListView list;

    ArrayList<ModelCuka> newsList = new ArrayList<ModelCuka>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_cuka);

        getSupportActionBar().setTitle("Data Cuka");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterCuka(ActivityDataCuka.this, newsList);
        list.setAdapter(adapter);
        getAllCuka();
    }

   

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityDataCuka.this, HomeAdminActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllCuka() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.datacuka, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");
                            if (status == false) {
                                Log.d("data cuka = ", response.toString());
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    final ModelCuka cuka = new ModelCuka();
                                    final String _id = jsonObject.getString("_id");
                                    final String ukuran = jsonObject.getString("ukuran");
                                    final String kodecuka= jsonObject.getString("kodecuka");
                                    final String harga = jsonObject.getString("harga");
                                    final String gambar = jsonObject.getString("gambar");
                                    cuka.setkodecuka(kodecuka);
                                    cuka.setukuran(ukuran);
                                    cuka.setharga(harga);
                                    cuka.setgambar(gambar);
                                    cuka.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(ActivityDataCuka.this, EditCukaDanHapusActivity.class);
                                            a.putExtra("kodecuka", newsList.get(position).getkodecuka());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("ukuran", newsList.get(position).getukuran());
                                            a.putExtra("harga", newsList.get(position).getharga());
                                            a.putExtra("gambar", newsList.get(position).getgambar());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(cuka);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
