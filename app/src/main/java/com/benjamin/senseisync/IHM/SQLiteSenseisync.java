package com.benjamin.senseisync.IHM;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteSenseisync extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "BDDSync";
    private Context context=null;

    public SQLiteSenseisync(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    public void onCreate(SQLiteDatabase db) {
    try {
        // Ajout de la tables Categorie
        db.execSQL("DROP TABLE IF EXISTS CATEGORIE");
        db.execSQL("CREATE TABLE CATEGORIE (" +
                "idCategorie INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Libelle VARCHAR(100))");
        db.execSQL("INSERT INTO Categorie VALUES(0,'Non défini')");
        db.execSQL("INSERT INTO Categorie VALUES(1,'Eveils')");
        db.execSQL("INSERT INTO Categorie VALUES(2,'Poussinets')");
        db.execSQL("INSERT INTO Categorie VALUES(3,'Poussins')");
        db.execSQL("INSERT INTO Categorie VALUES(4,'Benjamins')");
        db.execSQL("INSERT INTO Categorie VALUES(5,'Minimes')");
        db.execSQL("INSERT INTO Categorie VALUES(6,'Cadets')");
        db.execSQL("INSERT INTO Categorie VALUES(7,'Juniors')");
        db.execSQL("INSERT INTO Categorie VALUES(8,'Seniors')");
        // Ajout de la tables Judoka
        db.execSQL("DROP TABLE IF EXISTS JUDOKA");
        db.execSQL("CREATE TABLE JUDOKA (" +
                "idJudoka INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nom VARCHAR(100), " +
                "Prenom VARCHAR(100), " +
                "Tel VARCHAR(100), " +
                "DateNaissance DATE, " +
                "idCategorie INTEGER, " +
                "FOREIGN KEY(idCategorie) REFERENCES Categorie(idCategorie))");
        db.execSQL("INSERT INTO Judoka VALUES(1,'Toto','Tata',0488127318,'2001-01-01',1)");
        db.execSQL("INSERT INTO Judoka VALUES(2,'Titi','Tutu',0488127318,'2002-01-01',2)");
        db.execSQL("INSERT INTO Judoka VALUES(3,'Tata','Titi',0488127318,'2003-01-01',3)");
        db.execSQL("INSERT INTO Judoka VALUES(4,'Tutu','Toto',0488127318,'2004-01-01',4)");
        db.execSQL("INSERT INTO Judoka VALUES(5,'RaRa','Titi',0488127318,'2005-01-01',5)");
        db.execSQL("INSERT INTO Judoka VALUES(6,'RiRi','Toto',0488127318,'2006-01-01',6)");
        db.execSQL("INSERT INTO Judoka VALUES(7,'RuRu','Tata',0488127318,'2007-01-01',7)");
        db.execSQL("INSERT INTO Judoka VALUES(8,'ReRe','Tutu',0488127318,'2007-01-01','8')");

        // Ajout de la tables Cours
        db.execSQL("DROP TABLE IF EXISTS COURS");
        db.execSQL("CREATE TABLE COURS (" +
                "idCours INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Date DATE, " +
                "idJudoka INTEGER, " +
                "FOREIGN KEY(idJudoka) REFERENCES Judoka(idJudoka))");
        db.execSQL("INSERT INTO Cours VALUES(1,'2020-01-01',1)");
        db.execSQL("INSERT INTO Cours VALUES(2,'2020-01-02',2)");
        db.execSQL("INSERT INTO Cours VALUES(3,'2020-01-03',3)");
        db.execSQL("INSERT INTO Cours VALUES(4,'2020-01-04',4)");

        // Ajout de la tables agregat Cours_Judoka
        db.execSQL("DROP TABLE IF EXISTS COURS_JUDOKA");
        db.execSQL("CREATE TABLE COURS_JUDOKA (" +
                "idCours INTEGER, " +
                "idJudoka INTEGER, " +
                "FOREIGN KEY(idCours) REFERENCES Cours(idCours), " +
                "FOREIGN KEY(idJudoka) REFERENCES Judoka(idJudoka))");
        db.execSQL("INSERT INTO Cours_Judoka VALUES(1,1)");
        db.execSQL("INSERT INTO Cours_Judoka VALUES(2,2)");
        db.execSQL("INSERT INTO Cours_Judoka VALUES(3,3)");
        db.execSQL("INSERT INTO Cours_Judoka VALUES(4,4)");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        onCreate(db);
    }
}