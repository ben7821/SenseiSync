package com.benjamin.senseisync.IHM;

import com.benjamin.senseisync.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benjamin.senseisync.METIER.Cours;
import com.benjamin.senseisync.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CoursAdapter extends RecyclerView.Adapter<CoursAdapter.CoursViewHolder> {

    private ArrayList<Cours> coursList;

    public CoursAdapter(ArrayList<Cours> coursList) {
        this.coursList = coursList;
    }

    @NonNull
    @Override
    public CoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cours, parent, false);
        return new CoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursViewHolder holder, int position) {
        Cours cours = coursList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.textViewDate.setText(sdf.format(cours.getDate()));
    }

    @Override
    public int getItemCount() {
        return coursList.size();
    }

    public static class CoursViewHolder extends RecyclerView.ViewHolder {
        TextView textViewDate;

        public CoursViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}