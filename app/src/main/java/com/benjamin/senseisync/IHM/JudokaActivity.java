package com.benjamin.senseisync.IHM;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benjamin.senseisync.DAO.CategorieDAO;
import com.benjamin.senseisync.DAO.Cours_JudokaDAO;
import com.benjamin.senseisync.DAO.JudokaDAO;
import com.benjamin.senseisync.METIER.Categorie;
import com.benjamin.senseisync.METIER.Cours;
import com.benjamin.senseisync.METIER.Judoka;
import com.benjamin.senseisync.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JudokaActivity extends AppCompatActivity {

    private JudokaDAO judokaDAO;
    private RecyclerView recyclerViewJudoka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judoka);

        recyclerViewJudoka = findViewById(R.id.recyclerViewJudoka);
        recyclerViewJudoka.setLayoutManager(new LinearLayoutManager(this));

        Button btnHome = findViewById(R.id.btnHome);
        Button btncCreateJudoka = findViewById(R.id.bCreateJudoka);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(JudokaActivity.this, MainActivity.class);
            startActivity(intent);
        });
        judokaDAO = new JudokaDAO(this);
        ArrayList<Judoka> judokas = judokaDAO.read();

        JudokaAdapter adapter = new JudokaAdapter(judokas);
        recyclerViewJudoka.setAdapter(adapter);

      btncCreateJudoka.setOnClickListener(v -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(JudokaActivity.this);
        builder.setTitle("Create new Judoka");

        // Create a layout for the dialog
        LinearLayout layout = new LinearLayout(JudokaActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create input fields for the Judoka details

        final EditText nameInput = new EditText(JudokaActivity.this);
        nameInput.setHint("Name");
        layout.addView(nameInput);

        final EditText prenomInput = new EditText(JudokaActivity.this);
        prenomInput.setHint("Prenom");
        layout.addView(prenomInput);

        final EditText telInput = new EditText(JudokaActivity.this);
        telInput.setHint("Tel");
        layout.addView(telInput);

        final EditText dateNaissanceInput = new EditText(JudokaActivity.this);
        dateNaissanceInput.setHint("Date de naissance (yyyy-mm-dd)");
        layout.addView(dateNaissanceInput);

        // Create a spinner for the categories
        Spinner categorieSpinner = new Spinner(JudokaActivity.this);
        layout.addView(categorieSpinner);

        // Get all categories
        CategorieDAO categorieDAO = new CategorieDAO(JudokaActivity.this);
        ArrayList<Categorie> allCategories = categorieDAO.read();
        // Create an ArrayAdapter using the categories list
        ArrayAdapter<Categorie> adapterC = new ArrayAdapter<>(JudokaActivity.this, android.R.layout.simple_spinner_item, allCategories);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorieSpinner.setAdapter(adapterC);

        // Add the layout to the dialog
        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String name = nameInput.getText().toString();
            String prenom = prenomInput.getText().toString();
            String tel = telInput.getText().toString();
            String dateNaissance = dateNaissanceInput.getText().toString();

            // Create a new Judoka with the entered details
            Judoka newJudoka = new Judoka();
            newJudoka.setNom(name);
            newJudoka.setPrenom(prenom);
            newJudoka.setTel(tel);
            // Parse the date from string to Date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dob = sdf.parse(dateNaissance);
                newJudoka.setDateNaissance(dob);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Set the category
            Categorie selectedCategory = (Categorie) categorieSpinner.getSelectedItem();
            newJudoka.setCat(selectedCategory);

            // Add the new Judoka to the database
            judokaDAO.CreateJudoka(newJudoka);

            // Update the RecyclerView
            updateRecyclerView();
        });
        builder.setNegativeButton("Cancel", null);

        builder.show();
    });
    }

    // Implement the updateRecyclerView method here
    @SuppressLint("NotifyDataSetChanged")
    private void updateRecyclerView() {
        // Get the updated list of judokas from the database
        ArrayList<Judoka> updatedJudokas = judokaDAO.read();

        // Create a new adapter with the updated list
        JudokaAdapter updatedAdapter = new JudokaAdapter(updatedJudokas);

        // Set the new adapter on the RecyclerView
        recyclerViewJudoka.setAdapter(updatedAdapter);

        // Notify the adapter that the data set has changed
        updatedAdapter.notifyDataSetChanged();
    }

   public void showJudokaOptionsDialog(Judoka judoka) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Options for " + judoka.getNom());

    builder.setPositiveButton("Delete", (dialog, which) -> {
        // Affichez un autre AlertDialog pour confirmer la suppression
        showDeleteConfirmationDialog(judoka);
    });

    builder.setNegativeButton("Show Courses", (dialog, which) -> {
        // Affichez la liste des cours auxquels le judoka participe
        showCours(judoka);
    });

    builder.show();
}

public void showDeleteConfirmationDialog(Judoka judoka) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Delete Judoka" + " " + judoka.getNom());
    builder.setMessage("Are you sure you want to delete this judoka?");

    builder.setPositiveButton("Yes", (dialog, which) -> {
        judokaDAO.delete(judoka);
        updateRecyclerView();
    });

    builder.setNegativeButton("No", null);

    builder.show();
}

public void showCours(Judoka judoka) {
    Cours_JudokaDAO cours_judokaDAO = new Cours_JudokaDAO(this);
    ArrayList<Cours> cours = cours_judokaDAO.getCours(judoka);

    // Créez un StringBuilder pour construire le message de l'AlertDialog
    StringBuilder message = new StringBuilder();
    for (Cours c : cours) {
        message.append(c.getIdCour()).append("\n");
        message.append(c.getDate()).append("\n");
    }

    // Créez et affichez l'AlertDialog
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Cours for " + judoka.getNom());
    builder.setMessage(message.toString());
    builder.setPositiveButton("OK", null);
    builder.show();
}

}