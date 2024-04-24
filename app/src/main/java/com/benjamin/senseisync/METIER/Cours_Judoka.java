package com.benjamin.senseisync.METIER;

public class Cours_Judoka {
    private Judoka Judoka;
    private Cours Cours;

    public Cours_Judoka(com.benjamin.senseisync.METIER.Judoka judoka, Cours cours) {
        Judoka = judoka;
        Cours = cours;
    }

    public com.benjamin.senseisync.METIER.Judoka getJudoka() {
        return Judoka;
    }

    public void setJudoka(com.benjamin.senseisync.METIER.Judoka judoka) {
        Judoka = judoka;
    }

    public Cours getCour() {
        return Cours;
    }

    public void setCour(Cours cours) {
        Cours = cours;
    }

    @Override
    public String toString() {
        return "Cour_Judoka{" +
                "Judoka=" + Judoka.toString() +
                ", Cour=" + Cours.toString() +
                '}';
    }
}
