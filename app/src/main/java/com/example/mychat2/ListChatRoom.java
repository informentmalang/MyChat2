package com.example.mychat2;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.mychat2.Util.LinkData;



import android.content.DialogInterface;
import android.content.Intent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
//import com.example.marketplaceinforment.Adapter.AdapterKeranjang;
//import com.example.marketplaceinforment.Model.ModelKeranjang;
import com.example.mychat2.Util.AppController;
import com.example.mychat2.Util.LinkData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListChatRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chat_room);
    }

    public void loadKeranjang()
    {
        StringRequest loadKeranjang = new StringRequest(Request.Method.POST, LinkData.VIEW_KERANJANG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        DecimalFormat formatRupiah = (DecimalFormat) NumberFormat.getInstance();
                        formatRupiah.setPositivePrefix("Rp. ");
                        formatRupiah.setMinimumFractionDigits(0);
                        formatRupiah.setMaximumFractionDigits(0);

                        try {
                            mItems.clear();
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ModelKeranjang mk = new ModelKeranjang();
                                mk.setId_akun(String.valueOf(jsonObject.getInt("id_akun")));
                                mk.setId_barang(String.valueOf(jsonObject.getInt("id_barang")));
                                mk.setId_keranjang(String.valueOf(jsonObject.getInt("id_keranjang")));
                                mk.setId_toko(String.valueOf(jsonObject.getInt("id_toko")));
                                mk.setNama_barang(jsonObject.getString("nama_barang"));
                                mk.setHarga_keranjang(formatRupiah.format((double)jsonObject.getInt("harga_barang")));
                                mk.setJumlah_barang(String.valueOf(jsonObject.getInt("jumlah_barang")));
                                mk.setFoto_barang(jsonObject.getString("foto_barang"));
                                //mp.setFoto_barang(jsonObject.getString("foto_barang"));
                                mk.setCatatan(jsonObject.getString("catatan_keranjang"));
                                IdData.INVOICE = jsonObject.getString("invoice_keranjang");
                                mItems.add(mk);
                            }
                            adapterKeranjang.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(KeranjangActivity.this, "Keranjang Masih Kosong", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(KeranjangActivity.this, "Er : " + error, Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_akun", IdData.ID_AKUN);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(loadKeranjang);
    }
}