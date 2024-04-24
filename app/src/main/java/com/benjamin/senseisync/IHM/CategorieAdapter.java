package com.benjamin.senseisync.IHM;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.benjamin.senseisync.METIER.Categorie;
import com.benjamin.senseisync.R;

import java.util.ArrayList;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.CategorieViewHolder> {

    private ArrayList<Categorie> categories;

    public CategorieAdapter(ArrayList<Categorie> categories) {
        this.categories = categories;
    }

    @Override
    public CategorieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorie, parent, false);
        return new CategorieViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(CategorieViewHolder holder, int position) {
        Categorie categorie = categories.get(position);
        holder.textViewLibelle.setText(categorie.getLibelle());
        int judokaCount = 0;
        if (categorie.getListeJudoka() != null) {
            judokaCount = categorie.getListeJudoka().size();
        }
        holder.textViewNumberJudoka.setText(judokaCount + " judokas");
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategorieViewHolder extends RecyclerView.ViewHolder {

        TextView textViewLibelle;
        TextView textViewNumberJudoka;


        public CategorieViewHolder(View itemView) {
            super(itemView);
            textViewLibelle = itemView.findViewById(R.id.textViewLibelle);
            textViewNumberJudoka = itemView.findViewById(R.id.textViewNumberJudoka);

        }
    }
}