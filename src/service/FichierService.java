package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import model.Emprunt;
import model.Emprunteur;
import model.Livre;

public class FichierService {
    private static final String FICHIER_DONNEES = "data/bibliotheque.dat";

    public static void sauvegarder (
        HashMap<String, Livre> livres,
        HashMap<Integer, Emprunteur> emprunteurs,
        HashMap<Integer, Emprunt> emprunts,
        int prochainIdEmprunteur,
        int prochainIdEmprunt) {
            File dossier = new File("data");
            if (!dossier.exists()) {
                dossier.mkdir();
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHIER_DONNEES))) {
            oos.writeObject(livres);
            oos.writeObject(emprunteurs);
            oos.writeObject(emprunts);
            oos.writeInt(prochainIdEmprunteur);
            oos.writeInt(prochainIdEmprunt);
            System.out.println("Données sauvegardées avec succès.");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde des données : " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static DonneesBibliotheque charger() {
        File fichier = new File(FICHIER_DONNEES);
        if (!fichier.exists()) {
            System.out.println("Aucun fichier de données trouvé. Démarrage avec une bibliothèque vide.");
            return new DonneesBibliotheque();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHIER_DONNEES))) {
            HashMap<String, Livre> livres = (HashMap<String, Livre>) ois.readObject();
            HashMap<Integer, Emprunteur> emprunteurs = (HashMap<Integer, Emprunteur>) ois.readObject();
            HashMap<Integer, Emprunt> emprunts = (HashMap<Integer, Emprunt>) ois.readObject();
            int prochainIdEmprunteur = ois.readInt();
            int prochainIdEmprunt = ois.readInt();
            System.out.println("[OK] Donnees chargees avec succes !");
            System.out.println("     - " + livres.size() + " livre(s)");
            System.out.println("     - " + emprunteurs.size() + " emprunteur(s)");
            System.out.println("     - " + emprunts.size() + " emprunt(s)");
            return new DonneesBibliotheque(livres, emprunteurs, emprunts, prochainIdEmprunteur, prochainIdEmprunt);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement des données : " + e.getMessage());
            return new DonneesBibliotheque();
        }
    }

     public static class DonneesBibliotheque {
        public HashMap<String, Livre> livres;
        public HashMap<Integer, Emprunteur> emprunteurs;
        public HashMap<Integer, Emprunt> emprunts;
        public int prochainIdEmprunteur;
        public int prochainIdEmprunt;

        public DonneesBibliotheque() {
            this.livres = new HashMap<>();
            this.emprunteurs = new HashMap<>();
            this.emprunts = new HashMap<>();
            this.prochainIdEmprunteur = 1;
            this.prochainIdEmprunt = 1;
        }

        public DonneesBibliotheque(HashMap<String, Livre> livres,
                                  HashMap<Integer, Emprunteur> emprunteurs,
                                  HashMap<Integer, Emprunt> emprunts,
                                  int prochainIdEmprunteur,
                                  int prochainIdEmprunt) {
            this.livres = livres;
            this.emprunteurs = emprunteurs;
            this.emprunts = emprunts;
            this.prochainIdEmprunteur = prochainIdEmprunteur;
            this.prochainIdEmprunt = prochainIdEmprunt;
        }
     }
}
