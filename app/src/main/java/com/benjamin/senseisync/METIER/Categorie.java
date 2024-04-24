package com.benjamin.senseisync.METIER;

import java.util.Map;

public class Categorie {
    private int idCategorie;
    private String Libelle;
    private Map<String,Judoka> ListeJudoka;

    public Categorie(int idCategorie, String libelle) {
        this.idCategorie = idCategorie;
        Libelle = libelle;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getLibelle() {
        return Libelle;
    }

    public Map<String, Judoka> getListeJudoka() {
        return ListeJudoka;
    }

    public void setListeJudoka(Map<String, Judoka> listeJudoka) {
        ListeJudoka = listeJudoka;
    }

    public void setLibelle(String libelle) {
        Libelle = libelle;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "idCategorie=" + idCategorie +
                ", Libelle='" + Libelle + '\'' +
                '}';
    }
}
