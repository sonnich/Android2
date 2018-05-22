package com.example.sonnich.skemaplan.Model;

public class Fag {
    private int dbID;
    private String navn;
    private int semester;
    private int Blokke;


    public Fag(int dbID,String navn, int semester, int blokke) {
        this.dbID=dbID;
        this.navn = navn;
        this.semester = semester;
        Blokke = blokke;
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

    public int getBlokke() {
        return Blokke;
    }

    public void setBlokke(int blokke) {
        Blokke = blokke;
    }
}
