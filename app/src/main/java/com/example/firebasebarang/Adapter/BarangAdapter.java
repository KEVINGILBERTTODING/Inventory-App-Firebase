package com.example.firebasebarang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasebarang.Model.BarangModel;
import com.example.firebasebarang.R;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder>{

    Context context;
    List<BarangModel> list;

    BarangModel barangModel;

    onCallBack onCallBack;

    public void setOnCallBack(BarangAdapter.onCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }



    public BarangAdapter(Context context, List<BarangModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_data_barang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {





        holder.tvKdBrg.setText(list.get(position).getKdbrg());
        holder.tvNmBrg.setText(list.get(position).getNmbrg());
        holder.tvSatuan.setText(list.get(position).getSatuan());
        holder.tvHrgBeli.setText(list.get(position).getHrgbeli());
        holder.tvHrgJual.setText(list.get(position).getHrgjual());
        holder.tvStok.setText(list.get(position).getStok());
        holder.tvStokMin.setText(list.get(position).getStokmin());

        holder.tblHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onCallBack.onTblHapus(list.get(position));

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

       BarangModel barangModel;

        TextView tvKdBrg, tvNmBrg, tvSatuan, tvHrgBeli, tvHrgJual, tvStok, tvStokMin;
        ImageButton tblHapus, tblEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            tvKdBrg =   itemView.findViewById(R.id.tv_kdbrg);
            tvNmBrg =   itemView.findViewById(R.id.tv_nmbrg);
            tvSatuan =   itemView.findViewById(R.id.tv_satuanbrg);
            tvHrgBeli =   itemView.findViewById(R.id.tv_hrgbeli);
            tvHrgJual =   itemView.findViewById(R.id.tv_hrgjual);
            tvStok =   itemView.findViewById(R.id.tv_stok);
            tvStokMin =   itemView.findViewById(R.id.tv_stokmin);

            tblHapus = itemView.findViewById(R.id.tbl_hapus);


        }
    }

    public interface onCallBack {
        void onTblHapus(BarangModel brgModel);


    }


    public void filterList(List<BarangModel> filteredList) {

        list = filteredList;
        notifyDataSetChanged();


    }


}
