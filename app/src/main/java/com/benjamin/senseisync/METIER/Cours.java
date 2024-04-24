package com.benjamin.senseisync.METIER;

import java.util.Date;

public class Cours {
    private int idCour;
    private Date date;

    public Cours(int idCour, Date date) {
        this.idCour = idCour;
        this.date = date;
    }

    public int getIdCour() {
        return idCour;
    }

    public  Date getDate() {
        return date;
    }

    public void setDate( Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Cour{" +
                "idCour=" + idCour +
                ", date=" + date +
                '}';
    }
}
