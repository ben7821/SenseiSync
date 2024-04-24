package com.benjamin.senseisync.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.benjamin.senseisync.IHM.SQLiteSenseisync;
import com.benjamin.senseisync.METIER.Categorie;
import com.benjamin.senseisync.METIER.Judoka;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JudokaDAO extends MainDAO<Judoka> {
    private SQLiteSenseisync dbs;
    //déclaration des outils nécessaires à la base
    private static final String TABLE_JUDOKA = "JUDOKA";
    private static final String COL_ID_JUDOKA = "IDJUDOKA";
    private static final String COL_NOM = "NOM";
    private static final String COL_PRENOM = "PRENOM";
    private static final String COL_TEL = "TEL";
    private static final String COL_DATE_NAISSANCE = "DATE NAISSANCE";
    private static final String COL_CATEGORIE = "CATEGORIE";
    private SQLiteDatabase db;
    public JudokaDAO(Context context){
        dbs = new SQLiteSenseisync(context);
    }
    public void open(){
        db = dbs.getReadableDatabase();
    }
    public void close(){
        dbs.close();
    }
    public void insert(Judoka ma) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_NOM, ma.getNom());
        ctv.put(COL_PRENOM, ma.getPrenom());
        ctv.put(COL_TEL, ma.getTel());
        ctv.put(COL_DATE_NAISSANCE, ma.getDateNaissance().getTime());
        ctv.put(COL_CATEGORIE, ma.getCat().getLibelle());
        open();
        db.insert(TABLE_JUDOKA, null, ctv);
        close();
    }
    //insertion de la matière dans la base
    public void update(Judoka obj) {
        ContentValues ctv = new ContentValues();
        ctv.put(COL_NOM, obj.getNom());
        ctv.put(COL_PRENOM, obj.getPrenom());
        ctv.put(COL_TEL, obj.getTel());
        ctv.put(COL_DATE_NAISSANCE, obj.getDateNaissance().getTime());
        ctv.put(COL_CATEGORIE, obj.getCat().getLibelle());
        open();
        db.update(TABLE_JUDOKA, ctv, COL_ID_JUDOKA + " = " + obj.getIdjudoka(), null);
        close();
    }
    //modification de la catégorie du judoka si elle existe
    public void updateJudokaCategory(Judoka judoka, int newCategoryId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("idCategorie", newCategoryId); // Assurez-vous que "idCategorie" est le nom correct de la colonne

        String whereClause = "idJudoka = ?";
        String[] whereArgs = new String[] { String.valueOf(judoka.getIdjudoka()) };

        db.update("Judoka", contentValues, whereClause, whereArgs); // Assurez-vous que "Judoka" est le nom correct de la table
    }
    //suppression de la catégorie du judoka et le remet à 0
    public void removeJudokaFromCategory(Judoka judoka) {
        updateJudokaCategory(judoka, 0); // Met à jour la catégorie du judoka à 0 (pas de catégorie)
    }

    //modification du nom et coefficient de la matière en fonction du numéro
    public void delete(Judoka obj) {
        open();
        db.delete(TABLE_JUDOKA,COL_ID_JUDOKA + " = " + obj.getIdjudoka(),null);
        close();
    }
    //suppression de la matière en fonction de son numéro
    public Judoka read(long id){return null;}
    //Recherche le numéro de matière dans la base et la retourne

    public ArrayList<Judoka> read() {
        Cursor curseurJudoka;
        ArrayList<Judoka> listeDesJudoka;
        Judoka unJudoka;
        int idJudoka;
        String Nom;
        String Prenom;
        String Tel;
        Date DateNaissance;
        Categorie Categorie;
        //Ouverture de la base en lecture
        open();
        //Execution de la requête de selection avec tri par nom de matière
        curseurJudoka = db.query(TABLE_JUDOKA, null,null,null,null,null,
                COL_NOM);
        //Initialisation de la liste des matières
        listeDesJudoka = new ArrayList<Judoka>();
        //Parcours du curseur
        curseurJudoka.moveToFirst();
        while (!curseurJudoka.isAfterLast()){

            //Récupération des données de l'enregistrement
            idJudoka = curseurJudoka.getInt(0);
            Nom = curseurJudoka.getString(1);
            Prenom = curseurJudoka.getString(2);
            Tel = curseurJudoka.getString(3);
            DateNaissance = new Date(curseurJudoka.getLong(4));
            // ...
            int categoryId = curseurJudoka.getInt(5);
            Cursor categoryCursor = db.query("CATEGORIE", new String[]{"Libelle"}, "idCategorie = ?", new String[]{String.valueOf(categoryId)}, null, null, null);
            String categoryLabel = null;
            if (categoryCursor.moveToFirst()) {
                categoryLabel = categoryCursor.getString(0);
            }
            categoryCursor.close();
            Categorie = new Categorie(categoryId, categoryLabel);
            //Ajout de la matière dans la liste
            unJudoka = new Judoka(idJudoka,Nom,Prenom,Tel,DateNaissance,Categorie);
            listeDesJudoka.add(unJudoka);
            curseurJudoka.moveToNext();
        }
        curseurJudoka.close();
        close();
        return listeDesJudoka;
    }
    //Retourne la liste de toutes les matières enregistrées dans la base.
}