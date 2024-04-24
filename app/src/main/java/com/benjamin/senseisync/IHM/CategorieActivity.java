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

public class CategorieActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategorie;
    private CategorieDAO categorieDAO;
    private ArrayList<Categorie> categories;
    private JudokaDAO judokaDAO;
    private Button btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategorieActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerViewCategorie = findViewById(R.id.recyclerViewCategorie);
        recyclerViewCategorie.setLayoutManager(new LinearLayoutManager(this));

        categorieDAO = new CategorieDAO(this);
        judokaDAO = new JudokaDAO(this);
        categories = categorieDAO.read();

        CategorieAdapter adapter = new CategorieAdapter(categories);
        recyclerViewCategorie.setAdapter(adapter);

        // Ajoutez un OnClickListener à votre RecyclerView
        recyclerViewCategorie.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerViewCategorie ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // Lorsqu'un élément est cliqué, affichez l'AlertDialog
                        showJudokaDialog(categories.get(position));
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // Do something on long click if needed
                    }
                })
        );
    }




    private void showJudokaDialog(Categorie categorie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Judokas in " + categorie.getLibelle());

        // Obtenez la liste des judokas dans cette catégorie
        ArrayList<Judoka> judokas = judokaDAO.read();

        // Filtrez la liste des judokas pour obtenir uniquement ceux de la catégorie spécifique
        int idCategorie = categorie.getIdCategorie();
        ArrayList<Judoka> judokasInCategory = new ArrayList<>();
        for (Judoka judoka : judokas) {
            if (judoka.getCat().getIdCategorie() == idCategorie) {
                judokasInCategory.add(judoka);
            }
        }

        // Créez un ArrayAdapter pour afficher les judokas
        ArrayAdapter<Judoka> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, judokasInCategory);
        builder.setAdapter(adapter, (dialog, which) -> {
            // Lorsqu'un judoka est sélectionné, affichez un autre AlertDialog pour changer la catégorie ou supprimer le judoka
            showJudokaOptionsDialog(judokasInCategory.get(which));
        });

        // Affichez l'AlertDialog
        builder.show();
    }

    private void showJudokaOptionsDialog(Judoka judoka) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options for " + judoka.getNom());

        // Ajoutez des boutons pour changer la catégorie ou supprimer le judoka
        builder.setPositiveButton("Change Category", (dialog, which) -> {
            // Changez la catégorie du judoka
            changeJudokaCategory(judoka);
        });
        builder.setNegativeButton("Remove from Category", (dialog, which) -> {
            // Supprimez le judoka de la catégorie
            removeJudokaFromCategory(judoka);
        });

        // Affichez l'AlertDialog
        builder.show();
    }

    private void changeJudokaCategory(Judoka judoka) {
        // Affichez un dialogue pour sélectionner la nouvelle catégorie, puis appelez updateJudokaCategory
        int newCategoryId = judoka.getIdjudoka(); // Obtenez l'ID de la nouvelle catégorie
        judokaDAO.updateJudokaCategory(judoka, newCategoryId);
    }

    private void removeJudokaFromCategory(Judoka judoka) {
        judokaDAO.removeJudokaFromCategory(judoka);
    }


}
