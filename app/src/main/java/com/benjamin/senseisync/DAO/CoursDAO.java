package com.benjamin.senseisync.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.benjamin.senseisync.IHM.SQLiteSenseisync;
import com.benjamin.senseisync.METIER.Categorie;
import com.benjamin.senseisync.METIER.Cours;
import com.benjamin.senseisync.METIER.Judoka;

import java.util.ArrayList;
import java.util.Date;

public class CoursDAO extends MainDAO<Cours> {
    private SQLiteSenseisync dbs;
    //déclaration des outils nécessaires à la base
    private static final String TABLE_CATEGORIE = "COURS";
    private static final String COL_ID_COURS = "IDCOURS";
    private static final String COL_DATE = "DATE";

    private SQLiteDatabase db;
    public CoursDAO(Context context){
        dbs = new SQLiteSenseisync(context);
    }
    public void open(){
        db = dbs.getReadableDatabase();
    }
    public void close(){
        dbs.close();
    }
    public void insert(Cours ma) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_DATE,ma.getDate().getTime());
        open();
        db.insert(TABLE_CATEGORIE,null,ctv);
        close();
    }
    //insertion de la matière dans la base
    public void update(Cours obj) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_DATE,obj.getDate().getTime());
        open();
        db.update(TABLE_CATEGORIE,ctv,COL_ID_COURS + " = " + obj.getIdCour(),null);
        close();
    }
    //modification du nom et coefficient de la matière en fonction du numéro
    public void delete(Cours obj) {
        open();
        db.delete(TABLE_CATEGORIE,COL_ID_COURS + " = " + obj.getIdCour(),null);
        close();
    }
    //suppression de la matière en fonction de son numéro
    public Categorie read(long id){
        Cursor curseurCategorie;
        Categorie uneCategorie;
        String Lib;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurCategorie = db.query(TABLE_CATEGORIE,null,COL_ID_COURS + " = " + id,null,null,null,null);
        //Initialisation de la liste des matières
        curseurCategorie.moveToFirst();
        //Récupération des données de l'enregistrement
        Lib = curseurCategorie.getString(1);
        uneCategorie = new Categorie((int)id,Lib);
        curseurCategorie.close();
        close();
        return uneCategorie;
    }
    //Recherche le numéro de matière dans la base et la retourne

    public ArrayList<Cours> read() {
        Cursor curseurCours;
        ArrayList<Cours> listeDesCours;
        Cours unCours;
        int idcours;
        Date date;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurCours = db.query(TABLE_CATEGORIE, null,null,null,null,null,
                COL_ID_COURS);
        //Initialisation de la liste des matières
        listeDesCours = new ArrayList<Cours>();
        //Parcours du curseur
        curseurCours.moveToFirst();
        while (!curseurCours.isAfterLast()){

            //Récupération des données de l'enregistrement

            idcours = curseurCours.getInt(0);
            date = new Date(curseurCours.getLong(1));

            //Ajout de la matière dans la liste
            unCours = new Cours(idcours,date);
            listeDesCours.add(unCours);
            curseurCours.moveToNext();
        }
        curseurCours.close();
        close();
        return listeDesCours;
    }
    //Retourne la liste de toutes les matières enregistrées dans la base.
}
