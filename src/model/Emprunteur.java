package model;

import java.io.Serializable;

public class Emprunteur implements Serializable{
    private int id;
    private String nom;
    private String prenom;
    private String email;

    public Emprunteur (int id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        if (email != null && email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email invalide : " + email);
        }
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (email!= null && email.contains("@")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email invalide : " + email);

        }
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    @Override
    public String toString() {
        return "Emprunteur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    
}
