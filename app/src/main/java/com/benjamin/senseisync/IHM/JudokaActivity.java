package com.benjamin.senseisync.IHM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benjamin.senseisync.DAO.CategorieDAO;
import com.benjamin.senseisync.DAO.JudokaDAO;
import com.benjamin.senseisync.METIER.Categorie;
import com.benjamin.senseisync.METIER.Judoka;
import com.benjamin.senseisync.R;

import java.util.ArrayList;

public class JudokaActivity extends AppCompatActivity {

    private RecyclerView recyclerViewJudoka;
    private JudokaDAO judokaDAO;
    private ArrayList<Judoka> judokas;
    private Button btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judoka);

        recyclerViewJudoka = findViewById(R.id.recyclerViewJudoka);
        recyclerViewJudoka.setLayoutManager(new LinearLayoutManager(this));

        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JudokaActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        judokaDAO = new JudokaDAO(this);
        judokas = judokaDAO.read();

        JudokaAdapter adapter = new JudokaAdapter(judokas);
        recyclerViewJudoka.setAdapter(adapter);

    }

}
