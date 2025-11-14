package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Emprunt;
import model.Emprunteur;
import model.Livre;

public class BibliothequeService {
    private HashMap<String, Livre> livres;
    private HashMap<Integer, Emprunteur> emprunteurs;
    private HashMap<Integer, Emprunt> emprunts;
    private int prochainIdEmprunteur = 1;
    private int prochainIdEmprunt = 1;

    public BibliothequeService() {
        this.livres = new HashMap<>();
        this.emprunteurs = new HashMap<>();
        this.emprunts = new HashMap<>();
    }

    public void ajouterLivre(String isbn, String titre, String auteur) {
        if (livres.containsKey(isbn)) {
            System.out.println("Le livre avec l'ISBN " + isbn + " existe déjà.");
            return;
        }
        Livre livre = new Livre(isbn, titre, auteur);
        livres.put(isbn, livre);
        System.out.println("Livre ajouté : " + titre);
    }

    public Livre rechercherLivreParISBN(String isbn) {
        return livres.get(isbn);
    }

    public List<Livre> rechercherLivresParTitre(String titre) {
        List<Livre> resultats = new ArrayList<>();
        for(Livre livre : livres.values()) {
            if (livre.getTitre().toLowerCase().contains(titre.toLowerCase())) {
                resultats.add(livre);
            }
        }
        return resultats;
    }

    public List<Livre> rechercherLivresParAuteur(String auteur) {
        List<Livre> resultats = new ArrayList<>();
        for(Livre livre : livres.values()) {
            if (livre.getAuteur().toLowerCase().contains(auteur.toLowerCase())) {
                resultats.add(livre);
            }
        }
        return resultats;
    }

    public void afficherTousLesLivres() {
        if (livres.isEmpty()) {
            System.out.println("Aucun livre dans la bibliothèque.");
            return;
        }
        System.out.println("\n===CATALOGUE DE LA BIBLIOTHÈQUE===");
        for (Livre livre : livres.values()) {
            String statut = livre.isDisponible() ? "Disponible" : "Emprunté";
            System.out.println("-[" + livre.getIsbn() + "] " + livre.getTitre() + " par " + livre.getAuteur() + " - " + statut);
        }
    }

    public void ajouterEmprunteur(String nom, String prenom, String email) {
        try {
            Emprunteur emprunteur = new Emprunteur(prochainIdEmprunteur, nom, prenom, email);
            emprunteurs.put(prochainIdEmprunteur, emprunteur);
            System.out.println(" Emprunteur ajouté : " + prenom + " " + nom + " (ID: " + prochainIdEmprunteur + ")");
            prochainIdEmprunteur++;
        } catch (IllegalArgumentException e) {
            System.out.println("X " + e.getMessage());
        }
    }

    public Emprunteur rechercherEmprunteurParId(int id) {
        return emprunteurs.get(id);
    }

    public void afficherTousLesEmprunteurs() {
        if (emprunteurs.isEmpty()) {
            System.out.println(" Aucun emprunteur enregistré.");
            return;
        }
        System.out.println("\n === LISTE DES EMPRUNTEURS ===");
        for (Emprunteur emp : emprunteurs.values()) {
            System.out.println("- [" + emp.getId() + "] " + emp.getPrenom() + " " 
                + emp.getNom() + " (" + emp.getEmail() + ")");
        }
    }

     public void emprunterLivre(String isbn, int idEmprunteur, int dureeJours) {
        Livre livre = livres.get(isbn);
        if (livre == null) {
            System.out.println("X Livre non trouvé (ISBN: " + isbn + ")");
            return;
        }
        
        if (!livre.isDisponible()) {
            System.out.println("X Ce livre est déjà emprunté !");
            return;
        }
        
        Emprunteur emprunteur = emprunteurs.get(idEmprunteur);
        if (emprunteur == null) {
            System.out.println("X Emprunteur non trouvé (ID: " + idEmprunteur + ")");
            return;
        }

         Emprunt emprunt = new Emprunt(prochainIdEmprunt, livre, emprunteur, dureeJours);
        emprunts.put(prochainIdEmprunt, emprunt);
        
        System.out.println("Emprunt créé (ID: " + prochainIdEmprunt + ")");
        System.out.println("Livre: " + livre.getTitre());
        System.out.println("Emprunteur: " + emprunteur.getPrenom() + " " + emprunteur.getNom());
        System.out.println("Retour prévu: " + emprunt.getDateRetourPrevue());
        
        prochainIdEmprunt++;
    }

    public void retournerLivre(int idEmprunt) {
        Emprunt emprunt = emprunts.get(idEmprunt);
        
        if (emprunt == null) {
            System.out.println("X Emprunt non trouvé (ID: " + idEmprunt + ")");
            return;
        }
        
        if (!emprunt.isActif()) {
            System.out.println("Ce livre a déjà été retourné !");
            return;
        }
        
        emprunt.retournerLivre();
        
        long retard = emprunt.calculerRetard();
        System.out.println("Livre retourné : " + emprunt.getLivre().getTitre());
        
        if (retard > 0) {
            System.out.println("Retard de " + retard + " jour(s)");
        } else {
            System.out.println("Retour dans les temps !");
        }
    }

    public void afficherEmpruntsActifs() {
        List<Emprunt> actifs = new ArrayList<>();
        for (Emprunt emprunt : emprunts.values()) {
            if (emprunt.isActif()) {
                actifs.add(emprunt);
            }
        }
        
        if (actifs.isEmpty()) {
            System.out.println("Aucun emprunt en cours.");
            return;
        }
        
        System.out.println("\n=== EMPRUNTS EN COURS ===");
        for (Emprunt emprunt : actifs) {
            String retard = emprunt.estEnRetard() ? "EN RETARD" : "";
            System.out.println("- [" + emprunt.getId() + "] " 
                + emprunt.getLivre().getTitre() 
                + " → " + emprunt.getEmprunteur().getPrenom() + " " + emprunt.getEmprunteur().getNom()
                + " (Retour: " + emprunt.getDateRetourPrevue() + ")" + retard);
        }
    }

    public void afficherHistorique() {
        if (emprunts.isEmpty()) {
            System.out.println("Aucun historique d'emprunt.");
            return;
        }
        
        System.out.println("\n=== HISTORIQUE COMPLET DES EMPRUNTS ===");
        for (Emprunt emprunt : emprunts.values()) {
            System.out.println(emprunt);
        }
    }
}



