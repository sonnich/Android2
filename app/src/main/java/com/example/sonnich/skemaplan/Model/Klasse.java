package com.example.sonnich.skemaplan.Model;

import java.util.ArrayList;

public class Klasse {
    private int dbID;
    private String navn;
    private int semester;
    private ArrayList<Fag> fag;

    public Klasse(int dbID,String navn, int semester) {
        this.dbID=dbID;
        this.navn = navn;
        this.semester = semester;
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

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public ArrayList<Fag> getFag() {
        return new ArrayList<Fag>(fag);
    }

    public void removeFag(Fag f){
        fag.remove(f);
    }

    public void addFag(Fag f){
        fag.remove(f);
    }

}
