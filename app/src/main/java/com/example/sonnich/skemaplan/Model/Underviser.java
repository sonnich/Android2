package com.example.sonnich.skemaplan.Model;

import java.util.ArrayList;

public class Underviser {
    private int dbID;
    private String navn;
    private ArrayList<Fag> fag;

    public Underviser(int dbID,String navn) {
        this.dbID=dbID;
        this.navn = navn;
        this.fag = new ArrayList<>();
    }

    public int getDbID() {
        return dbID;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public ArrayList<Fag> getFag() {
        return new ArrayList<Fag>(fag);
    }

    public void addFag(Fag f) {
        fag.add(f);
    }

    public void removeFag(Fag f){
        fag.remove(f);
    }
}
