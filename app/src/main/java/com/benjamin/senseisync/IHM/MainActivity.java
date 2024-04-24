package com.benjamin.senseisync.IHM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.benjamin.senseisync.R;

public class MainActivity extends AppCompatActivity {
    private Button bQuitter;
    private Button bCategorie;
    private Button bJudoka;
    private Button bCours;
    private Button bOther;

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