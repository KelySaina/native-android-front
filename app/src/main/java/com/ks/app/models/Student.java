package com.ks.app.models;

public class Student {
    private String numEt;
    private String nom;
    private double noteMath;
    private double notePhy;

    // Constructors
    public Student() {

    }
    public Student(String numEt, String nom, int noteMath, int notePhy) {
        this.numEt = numEt;
        this.nom = nom;
        this.noteMath = noteMath;
        this.notePhy = notePhy;
    }

    // Getter and Setter methods

    public String getNumEt() {
        return numEt;
    }

    public void setNumEt(String numEt) {
        this.numEt = numEt;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getNoteMath() {
        return noteMath;
    }

    public void setNoteMath(int noteMath) {
        this.noteMath = noteMath;
    }

    public double getNotePhy() {
        return notePhy;
    }

    public void setNotePhy(int notePhy) {
        this.notePhy = notePhy;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Student{" +
                "numEt='" + numEt + '\'' +
                ", nom='" + nom + '\'' +
                ", noteMath=" + noteMath +
                ", notePhy=" + notePhy +
                '}';
    }
}
