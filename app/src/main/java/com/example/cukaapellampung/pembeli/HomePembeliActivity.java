package com.example.cukaapellampung.pembeli;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.cukaapellampung.R;
import com.example.cukaapellampung.adapter.AdapterCuka;
import com.example.cukaapellampung.model.ModelCuka;
import com.example.cukaapellampung.server.BaseURL;
import com.example.cukaapellampung.session.PrefSetting;
import com.example.cukaapellampung.session.SessionManager;
import com.example.cukaapellampung.users.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomePembeliActivity extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterCuka adapter;
    ListView list;

    ArrayList<ModelCuka> newsList = new ArrayList<ModelCuka>();
    private RequestQueue mRequestQueue;

    FloatingActionButton floatingExit;

    SessionManager session;
    SharedPreferences prefs;
    PrefSetting prefSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pembeli);

        prefSetting = new PrefSetting(this);
        prefs = prefSetting.getSharePreference();

        session = new SessionManager(HomePembeliActivity.this);

        prefSetting.isLogin(session, prefs);

        getSupportActionBar().setTitle("Data Cuka Apel");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);

        floatingExit = (FloatingActionButton) findViewById(R.id.exit);

        newsList.clear();
        adapter = new AdapterCuka(HomePembeliActivity.this, newsList);
        list.setAdapter(adapter);
        getAllCuka();

        floatingExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.setLogin(false);
                session.setSessid(0);
                Intent i = new Intent(HomePembeliActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
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
                                    final String kodecuka = jsonObject.getString("kodecuka");
                                    final String harga = jsonObject.getString("harga");
                                    final String gamabr = jsonObject.getString("gambar");
                                    cuka.setkodecuka(kodecuka);
                                    cuka.setukuran(ukuran);
                                    cuka.setharga(harga);
                                    cuka.setgambar(gamabr);
                                    cuka.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(HomePembeliActivity.this, DetailPembeli.class);
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
