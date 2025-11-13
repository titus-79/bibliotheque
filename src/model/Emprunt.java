package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprunt implements Serializable {
    private int id;
    private Livre livre;
    private Emprunteur emprunteur;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourEffective;
    private boolean actif;

    public Emprunt(int id, Livre livre, Emprunteur emprunteur, int dureeJours) {
        this.id = id;
        this.livre = livre;
        this.emprunteur = emprunteur;
        this.dateEmprunt = LocalDate.now();
        this.dateRetourPrevue = dateEmprunt.plusDays(dureeJours);
        this.dateRetourEffective = null;
        this.actif = true;
        livre.setDisponible(false);
    }

    public void retournerLivre() {
        this.dateRetourEffective = LocalDate.now();
        this.actif = false;
        this.livre.setDisponible(true);
    }

    public long calculerRetard() {
        LocalDate dateReference = (dateRetourEffective != null) ? dateRetourEffective : LocalDate.now();
        if (dateReference.isAfter(dateRetourPrevue)) {
             return ChronoUnit.DAYS.between(dateRetourPrevue, dateReference);
        }
        return 0;
    }

    public boolean estEnRetard() {
        return actif && LocalDate.now().isAfter(dateRetourPrevue);
    }

    public int getId() {
        return id;
    }
    public Livre getLivre() {
        return livre;
    }
    public Emprunteur getEmprunteur() {
        return emprunteur;
    }
    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }
    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }
    public LocalDate getDateRetourEffective() {
        return dateRetourEffective;
    }
    public boolean isActif() {
        return actif;
    }

    @Override
    public String toString() {
        String retard = estEnRetard() ? "EN RETARD" : "";
        String statut = actif ? "En cours" : "Terminé";

        return "Emprunt{" +
                "id=" + id +
                ", livre=" + livre.getTitre() +
                ", emprunteur=" + emprunteur.getPrenom() + " " + emprunteur.getNom() +
                ", dateEmprunt=" + dateEmprunt +
                ", retourPrévu=" + dateRetourPrevue +
                ", statut=" + statut +
                retard +
                '}';
    }
}

