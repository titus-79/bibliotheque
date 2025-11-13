package model;

import java.io.Serializable;

public class Livre implements Serializable {
    private String isbn;
    private String titre;
    private String auteur;
    private boolean disponible;

    public Livre(String isbn, String titre, String auteur) {
        this.isbn = isbn;
        this.titre = titre;
        this.auteur = auteur;
        this.disponible = true;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // La classe Object (parent de TOUTES les classes Java) a déjà une méthode toString()
    // Par défaut elle affiche : Livre@15db9742 (le nom de la classe + l'adresse mémoire de l'objet)
    @Override
    public String toString() {
        // On REDÉFINIT la méthode pour afficher quelque chose d'utile
        return "Livre{" +
                "isbn='" + isbn + '\'' +
                ", titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", disponible=" + disponible +
                '}';
            }
    
    
}
