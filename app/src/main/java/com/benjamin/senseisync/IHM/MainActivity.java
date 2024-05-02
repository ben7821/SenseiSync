package com.benjamin.senseisync.IHM;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.benjamin.senseisync.DAO.CoursDAO;
import com.benjamin.senseisync.METIER.Cours;
import com.benjamin.senseisync.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private Button bQuitter;
    private Button bCategorie;
    private Button bJudoka;
    private Button bCours;
    private Button bOther;
    private CoursDAO coursDAO;
    private RecyclerView recyclerViewCours;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constructeurGraphique();
        bQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitter();
            }
        });
        bCategorie.setOnClickListener(buttonClickListener);
        bJudoka.setOnClickListener(buttonClickListener);
        bCours.setOnClickListener(buttonClickListener);
        bOther.setOnClickListener(buttonClickListener);

        // Initialize the CoursDAO
        coursDAO = new CoursDAO(this);

        // Get the list of courses
        ArrayList<Cours> coursList = coursDAO.read();

        // Sort the list by date
        Collections.sort(coursList, new Comparator<Cours>() {
            @Override
            public int compare(Cours c1, Cours c2) {
                return c1.getDate().compareTo(c2.getDate());
            }
        });

        // Initialize the RecyclerView and its adapter
        recyclerViewCours = findViewById(R.id.lvCours);
        CoursAdapter coursAdapter = new CoursAdapter(coursList);

        // Set the adapter to the RecyclerView
        recyclerViewCours.setAdapter(coursAdapter);
    }

    private void constructeurGraphique(){
        bQuitter = (Button) findViewById(R.id.bQuitter);
        bCategorie = (Button) findViewById(R.id.bCategorie);
        bJudoka = (Button) findViewById(R.id.bJudoka);
        bCours = (Button) findViewById(R.id.BCours);
        bOther = (Button) findViewById(R.id.bOther);

        bCategorie.setTag("Categorie");
        bJudoka.setTag("Judoka");
        bCours.setTag("Cours");
        bOther.setTag("Other");
    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            switch (tag) {
                case "Categorie":
                    openActivity(CategorieActivity.class);
                    break;
                case "Judoka":
                    // Remplacez JudokaActivity par la classe réelle de votre activité
                    openActivity(JudokaActivity.class);
                    break;
                case "Cours":
                    // Remplacez CoursActivity par la classe réelle de votre activité
                    // openActivity(CoursActivity.class);
                    break;
                case "Other":
                    // Remplacez OtherActivity par la classe réelle de votre activité
                    // openActivity(OtherActivity.class);
                    break;
            }
        }
    };

    private void quitter(){
        finish();
    }

    private void openActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}