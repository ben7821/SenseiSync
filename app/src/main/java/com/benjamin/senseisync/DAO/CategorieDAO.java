package com.benjamin.senseisync.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.benjamin.senseisync.IHM.SQLiteSenseisync;
import com.benjamin.senseisync.METIER.Categorie;

import java.util.ArrayList;

public class CategorieDAO extends MainDAO<Categorie> {
    private SQLiteSenseisync dbs;
    //déclaration des outils nécessaires à la base
    private static final String TABLE_CATEGORIE = "CATEGORIE";
    private static final String COL_ID_CATEGORIE = "IDCATEGORIE";
    private static final String COL_LIB = "LIBELLE";
    private SQLiteDatabase db;
    public CategorieDAO(Context context){
        dbs = new SQLiteSenseisync(context);
    }
    public void open(){
        db = dbs.getReadableDatabase();
    }
    public void close(){
        dbs.close();
    }
    public void insert(Categorie ma) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_LIB,ma.getLibelle());
        open();
        db.insert(TABLE_CATEGORIE,null,ctv);
        close();
    }
    //insertion de la matière dans la base
    public void update(Categorie obj) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_LIB,obj.getLibelle());
        open();
        db.update(TABLE_CATEGORIE,ctv,COL_ID_CATEGORIE + " = " + obj.getIdCategorie(),null);
        close();
    }
    //modification du nom et coefficient de la matière en fonction du numéro
    public void delete(Categorie obj) {
        open();
        db.delete(TABLE_CATEGORIE,COL_ID_CATEGORIE + " = " + obj.getIdCategorie(),null);
        close();
    }
    //suppression de la matière en fonction de son numéro
    public Categorie read(long id){return null;}
    //Recherche le numéro de matière dans la base et la retourne

    public ArrayList<Categorie> read() {
        Cursor curseurCategorie;
        ArrayList<Categorie> listeDesCategorie;
        Categorie uneCategorie;
        int idJudoka;
        String Lib;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurCategorie = db.query(TABLE_CATEGORIE, null,null,null,null,null,
                COL_LIB);
        //Initialisation de la liste des matières
        listeDesCategorie = new ArrayList<Categorie>();
        //Parcours du curseur
        curseurCategorie.moveToFirst();
        while (!curseurCategorie.isAfterLast()){

            //Récupération des données de l'enregistrement

            idJudoka = curseurCategorie.getInt(0);
            Lib = curseurCategorie.getString(1);

            //Ajout de la matière dans la liste
            uneCategorie = new Categorie(idJudoka,Lib);
            listeDesCategorie.add(uneCategorie);
            curseurCategorie.moveToNext();
        }
        curseurCategorie.close();
        close();
        return listeDesCategorie;
    }

    //Retourne la liste de toutes les matières enregistrées dans la base.
}