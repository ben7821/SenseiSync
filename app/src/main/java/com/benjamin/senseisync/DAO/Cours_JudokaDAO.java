package com.benjamin.senseisync.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.benjamin.senseisync.DAO.MainDAO;
import com.benjamin.senseisync.IHM.SQLiteSenseisync;
import com.benjamin.senseisync.METIER.Cours;
import com.benjamin.senseisync.METIER.Cours_Judoka;
import com.benjamin.senseisync.METIER.Judoka;

import java.util.ArrayList;
import java.util.Date;

public class Cours_JudokaDAO extends MainDAO<Cours_Judoka> {
    private SQLiteSenseisync dbs;
    //déclaration des outils nécessaires à la base
    private static final String TABLE_JUDOKA = "COURS_JUDOKA";
    private static final String COL_JUDOKA = "JUDOKA";
    private static final String COL_COURS = "COURS";

    private SQLiteDatabase db;
    public Cours_JudokaDAO(Context context){
        dbs = new SQLiteSenseisync(context);
    }
    public void open(){
        db = dbs.getReadableDatabase();
    }
    public void close(){
        dbs.close();
    }

    public ArrayList<Cours> getCours(Judoka judoka) {
        ArrayList<Cours> coursList = new ArrayList<>();

        // Ouverture de la base en lecture
        open();

        // Exécution de la requête SQL pour obtenir tous les cours du judoka
        Cursor cursor = db.rawQuery("SELECT * FROM COURS INNER JOIN COURS_JUDOKA ON COURS.IDCOURS = COURS_JUDOKA.IDCOURS WHERE COURS_JUDOKA.IDJUDOKA = ?", new String[]{String.valueOf(judoka.getIdjudoka())});

        // Parcours du curseur
        if (cursor.moveToFirst()) {
            do {
                // Récupération des données de l'enregistrement
                @SuppressLint("Range") int idCours = cursor.getInt(cursor.getColumnIndex("IDCOURS"));
                @SuppressLint("Range") Date date = new Date(cursor.getLong(cursor.getColumnIndex("DATE")));

                // Ajout du cours à la liste
                Cours cours = new Cours(idCours, date);
                coursList.add(cours);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();

        return coursList;
    }

    @Override
    public void insert(Cours_Judoka obj) {

    }

    @Override
    public void update(Cours_Judoka obj) {

    }

    @Override
    public void delete(Cours_Judoka obj) {

    }
}