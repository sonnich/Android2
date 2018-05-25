package com.example.sonnich.skemaplan.Model;

import java.io.Serializable;

public class Blok implements Serializable {



    private int dbID;
    private int uge;
    private String dag;
    private int blokNr;
    private int fag;
    private int klasse;
    private String fagnavn;
    private String klasseNavn;
    private String undervisernavn;

    public String getKlasseNavn() {
        return klasseNavn;
    }

    public void setKlasseNavn(String klasseNavn) {
        this.klasseNavn = klasseNavn;
    }

    public String getUndervisernavn() {
        return undervisernavn;
    }

    public void setUndervisernavn(String undervisernavn) {
        this.undervisernavn = undervisernavn;
    }

    private boolean undervisningsfri;


    public String getFagnavn() {
        return fagnavn;
    }

    public void setFagnavn(String fagnavn) {
        this.fagnavn = fagnavn;
    }

    public Blok(int blokNr){
        this.blokNr=blokNr;
    }

    public Blok(int dbID, int uge, String dag, int blokNr, int fag, int klasse, boolean undervisningsfri) {
        this.dbID = dbID;
        this.uge = uge;
        this.dag = dag;
        this.blokNr = blokNr;
        this.fag = fag;
        this.klasse = klasse;
        this.undervisningsfri = undervisningsfri;
    }



    public int getDbID() {
        return dbID;
    }

    public int getUge() {
        return uge;
    }

    public void setUge(int uge) {
        this.uge = uge;
    }

    public String getDag() {
        return dag;
    }

    public void setDag(String dag) {
        this.dag = dag;
    }

    public int getBlokNr() {
        return blokNr;
    }

    public void setBlokNr(int blokNr) {
        this.blokNr = blokNr;
    }

    public int getFag() {
        return fag;
    }

    public void setFag(int fag) {
        this.fag = fag;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public boolean isUndervisningsfri() {
        return undervisningsfri;
    }

    public void setUndervisningsfri(boolean undervisningsfri) {
        this.undervisningsfri = undervisningsfri;
    }

}
