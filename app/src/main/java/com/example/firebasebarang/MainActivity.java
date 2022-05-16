package com.example.firebasebarang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.firebasebarang.Adapter.BarangAdapter;
import com.example.firebasebarang.Model.BarangModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton tblData;
    RecyclerView recyclerView;
    SearchView searchView;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("barangModel");

    List<BarangModel> list = new ArrayList<>();

    BarangAdapter barangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fungsi untuk menyembunyikan navbar

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        tblData =    findViewById(R.id.tbl_data);
        recyclerView = findViewById(R.id.rcylrBarang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tblData.setOnClickListener(v -> showDialogTambahData());
        bacaData();

        // Inisialisasi searchView

        searchView = (SearchView) findViewById(R.id.search_barr);


        // Fungsi saat memasukkan kata ke dalam searchview

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String querry) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });



    }

    private void bacaData() {

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BarangModel barangModel = snapshot.getValue(BarangModel.class);
                    list.add(barangModel);
                }

                barangAdapter = new BarangAdapter(MainActivity.this, list);
                recyclerView.setAdapter(barangAdapter);
                setClick();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Log.w("TAG", "Failed to read value.", error.toException());

            }
        });

    }

    private void setClick() {
        barangAdapter.setOnCallBack(barangModel -> hapusData(barangModel));

    }

    private void hapusData(BarangModel barangModel) {
        myRef.child(barangModel.getKey()).removeValue((error, ref) -> Toast.makeText(getApplicationContext(), " Kode barang " + barangModel.getKdbrg() + " telah dihapus", Toast.LENGTH_SHORT).show());
    }

    private void showDialogTambahData() {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tambah_data_barang);

//        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        EditText etKdbrg = dialog.findViewById(R.id.et_kdbrg);
        EditText etNmBrg = dialog.findViewById(R.id.et_nmbrg);
        EditText etSatuan = dialog.findViewById(R.id.et_satuan);
        EditText etHrgBeli = dialog.findViewById(R.id.et_hrgbeli);
        EditText etHrgJual = dialog.findViewById(R.id.et_hrgjual);
        EditText etStok = dialog.findViewById(R.id.et_stok);
        EditText etStokMin = dialog.findViewById(R.id.et_stokmin);
        Button tbltambah = dialog.findViewById(R.id.tbl_tambah);

        ImageButton tblKeluar = dialog.findViewById(R.id.tbl_Keluar);
        tblKeluar.setOnClickListener(v -> dialog.dismiss());

        tbltambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etKdbrg.getText())) {
                    errorMsg();
                }
                else if (TextUtils.isEmpty(etNmBrg.getText())) {
                    errorMsg();
                }
                else if (TextUtils.isEmpty(etSatuan.getText())) {
                    errorMsg();
                }
                else if (TextUtils.isEmpty(etHrgBeli.getText())) {
                    errorMsg();
                }
                else if (TextUtils.isEmpty(etHrgJual.getText())) {
                    errorMsg();
                }else if (TextUtils.isEmpty(etStok.getText())) {
                    errorMsg();
                }else if (TextUtils.isEmpty(etStokMin.getText())) {
                    errorMsg();
                }

                else {
                    createData(etKdbrg.getText().toString(), etNmBrg.getText().toString(), etSatuan.getText().toString(), etHrgBeli.getText().toString(), etHrgJual.getText().toString(), etStok.getText().toString(), etStokMin.getText().toString());

                    dialog.dismiss();
                }
            }

            private void errorMsg() {
                Toast.makeText(getApplicationContext(), "Form Harus Di Isi", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void createData(String kdBrg, String nmBrg, String satuan, String hrgBeli, String hrgJual, String stok, String stokMin) {
        String key = myRef.push().getKey();
        BarangModel barangModel = new BarangModel(key, kdBrg, nmBrg, satuan, hrgBeli, hrgJual, stok, stokMin );

        myRef.child(key).setValue(barangModel).addOnSuccessListener(unused -> Toast.makeText(getApplicationContext(), "Berhasil menambahkan barang baru ", Toast.LENGTH_SHORT).show());
    }

    // Method untuk realtime searchview

    private void filter(String newText) {

        ArrayList<BarangModel> filteredList = new ArrayList<>();

        for (BarangModel item : list) {
            if (item.getNmbrg().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(item);

            }
        }


        barangAdapter.filterList(filteredList);

//        mItems.clear();
//        mItems.addAll(filteredList);




        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        } else {
            barangAdapter.filterList(filteredList);
        }



    }
}