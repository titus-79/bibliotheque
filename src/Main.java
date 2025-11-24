import model.*;
import service.BibliothequeService;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static BibliothequeService bibliotheque = new BibliothequeService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   SYSTÈME DE GESTION DE BIBLIOTHÈQUE   ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        System.out.println("\n[CHARGEMENT] Recuperation des donnees...");
        boolean donneesChargees = bibliotheque.chargerDonnees();
       
        if (!donneesChargees) {
            System.out.println("\n[INIT] Premier demarrage - Chargement du jeu de test...");
            initialiserDonneesTest();
        }
        
        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            int choix = lireChoix();
            continuer = traiterChoix(choix);
        }
        
        scanner.close();
        System.out.println("\n👋 Au revoir !");
    }

    private static void afficherMenu() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1.  Gestion des livres");
        System.out.println("2.  Gestion des emprunteurs");
        System.out.println("3.  Gestion des emprunts");
        System.out.println("8.  Reinitialiser avec jeu de test");
        System.out.println("9.  Sauvegarder maintenant");
        System.out.println("0.  Quitter");
        System.out.print("Votre choix : ");
    }

    private static int lireChoix() {
        try {
            int choix = Integer.parseInt(scanner.nextLine());
            return choix;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean traiterChoix(int choix) {
        switch (choix) {
            case 1:
                menuLivres();
                break;
            case 2:
                menuEmprunteurs();
                break;
            case 3:
                menuEmprunts();
                break;
            case 8:
                reinitialiserAvecTest();
                break;
            case 9:
                System.out.println("\nEnregistrement des donnees...");
                bibliotheque.sauvegarderDonnees();
                break;
            case 0:
                System.out.println("\nEnregistrement des donnees...");
                bibliotheque.sauvegarderDonnees();
                return false;
            default:
                System.out.println("Choix invalide !");
        }
        return true;
    }

    // ========== MENU LIVRES ==========
    
    private static void menuLivres() {
        System.out.println("\n---  GESTION DES LIVRES ---");
        System.out.println("1. Ajouter un livre");
        System.out.println("2. Rechercher par ISBN");
        System.out.println("3. Rechercher par titre");
        System.out.println("4. Rechercher par auteur");
        System.out.println("5. Afficher tous les livres");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");
        
        int choix = lireChoix();
        
        switch (choix) {
            case 1:
                ajouterLivre();
                break;
            case 2:
                rechercherParISBN();
                break;
            case 3:
                rechercherParTitre();
                break;
            case 4:
                rechercherParAuteur();
                break;
            case 5:
                bibliotheque.afficherTousLesLivres();
                break;
            case 0:
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }

    private static void ajouterLivre() {
        System.out.print("ISBN : ");
        String isbn = scanner.nextLine();
        
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        
        System.out.print("Auteur : ");
        String auteur = scanner.nextLine();
        
        bibliotheque.ajouterLivre(isbn, titre, auteur);
    }

    private static void rechercherParISBN() {
        System.out.print("ISBN à rechercher : ");
        String isbn = scanner.nextLine();
        
        Livre livre = bibliotheque.rechercherLivreParISBN(isbn);
        if (livre != null) {
            String statut = livre.isDisponible() ? "Disponible" : "Emprunté";
            System.out.println("\n Livre trouvé :");
            System.out.println("   Titre: " + livre.getTitre());
            System.out.println("   Auteur: " + livre.getAuteur());
            System.out.println("   ISBN: " + livre.getIsbn());
            System.out.println("   Statut: " + statut);
        } else {
            System.out.println("Aucun livre trouvé avec cet ISBN.");
        }
    }

    private static void rechercherParTitre() {
        System.out.print("Titre à rechercher : ");
        String titre = scanner.nextLine();
        
        List<Livre> resultats = bibliotheque.rechercherLivresParTitre(titre);
        afficherResultatsRecherche(resultats);
    }

    private static void rechercherParAuteur() {
        System.out.print("Auteur à rechercher : ");
        String auteur = scanner.nextLine();
        
        List<Livre> resultats = bibliotheque.rechercherLivresParAuteur(auteur);
        afficherResultatsRecherche(resultats);
    }

    private static void afficherResultatsRecherche(List<Livre> resultats) {
        if (resultats.isEmpty()) {
            System.out.println("Aucun résultat trouvé.");
        } else {
            System.out.println("\n " + resultats.size() + " résultat(s) trouvé(s) :");
            for (Livre livre : resultats) {
                String statut = livre.isDisponible() ? "oui" : "non";
                System.out.println("   " + statut + " [" + livre.getIsbn() + "] " 
                    + livre.getTitre() + " - " + livre.getAuteur());
            }
        }
    }

    // ========== MENU EMPRUNTEURS ==========
    
    private static void menuEmprunteurs() {
        System.out.println("\n---  GESTION DES EMPRUNTEURS ---");
        System.out.println("1. Ajouter un emprunteur");
        System.out.println("2. Afficher tous les emprunteurs");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");
        
        int choix = lireChoix();
        
        switch (choix) {
            case 1:
                ajouterEmprunteur();
                break;
            case 2:
                bibliotheque.afficherTousLesEmprunteurs();
                break;
            case 0:
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }

    private static void ajouterEmprunteur() {
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        
        System.out.print("Email : ");
        String email = scanner.nextLine();
        
        bibliotheque.ajouterEmprunteur(nom, prenom, email);
    }

    // ========== MENU EMPRUNTS ==========
    
    private static void menuEmprunts() {
        System.out.println("\n---  GESTION DES EMPRUNTS ---");
        System.out.println("1. Emprunter un livre");
        System.out.println("2. Retourner un livre");
        System.out.println("3. Afficher les emprunts en cours");
        System.out.println("4. Afficher l'historique complet");
        System.out.println("0. Retour");
        System.out.print("Votre choix : ");
        
        int choix = lireChoix();
        
        switch (choix) {
            case 1:
                emprunterLivre();
                break;
            case 2:
                retournerLivre();
                break;
            case 3:
                bibliotheque.afficherEmpruntsActifs();
                break;
            case 4:
                bibliotheque.afficherHistorique();
                break;
            case 0:
                break;
            default:
                System.out.println("Choix invalide !");
        }
    }

    private static void emprunterLivre() {
        System.out.print("ISBN du livre : ");
        String isbn = scanner.nextLine();
        
        System.out.print("ID de l'emprunteur : ");
        int idEmprunteur = lireChoix();
        
        System.out.print("Durée de l'emprunt (en jours) : ");
        int duree = lireChoix();
        
        bibliotheque.emprunterLivre(isbn, idEmprunteur, duree);
    }

    private static void retournerLivre() {
        System.out.print("ID de l'emprunt : ");
        int idEmprunt = lireChoix();
        
        bibliotheque.retournerLivre(idEmprunt);
    }

    private static void reinitialiserAvecTest() {
    System.out.print("\nToutes les donnees seront effacees ! Confirmer (O/N) ? ");
    String reponse = scanner.nextLine();
    
    if (reponse.equalsIgnoreCase("O") || reponse.equalsIgnoreCase("OUI")) {
        bibliotheque = new BibliothequeService();
        initialiserDonneesTest();
        System.out.println("Donnees reinitialisees avec le jeu de test !");
    } else {
        System.out.println("Operation annulee.");
    }
}

    // ========== DONNÉES DE TEST ==========
    
    private static void initialiserDonneesTest() {
        System.out.println("\n🔧 Initialisation des données de test...");
        
    // ========== LIVRES DE PROGRAMMATION ==========
    bibliotheque.ajouterLivre("978-0132350884", "Clean Code", "Robert C. Martin");
    bibliotheque.ajouterLivre("978-0134685991", "Effective Java", "Joshua Bloch");
    bibliotheque.ajouterLivre("978-0596009205", "Head First Java", "Kathy Sierra");
    bibliotheque.ajouterLivre("978-0201633610", "Design Patterns", "Gang of Four");
    bibliotheque.ajouterLivre("978-0135957059", "The Pragmatic Programmer", "David Thomas");
    bibliotheque.ajouterLivre("978-0134494166", "Clean Architecture", "Robert C. Martin");
    bibliotheque.ajouterLivre("978-1449355739", "Learning Python", "Mark Lutz");
    bibliotheque.ajouterLivre("978-1617294945", "Spring in Action", "Craig Walls");
    bibliotheque.ajouterLivre("978-0137081073", "The Clean Coder", "Robert C. Martin");
    bibliotheque.ajouterLivre("978-0596517748", "JavaScript: The Good Parts", "Douglas Crockford");
    
    // ========== LITTÉRATURE CLASSIQUE ==========
    bibliotheque.ajouterLivre("978-2070360024", "Le Petit Prince", "Antoine de Saint-Exupéry");
    bibliotheque.ajouterLivre("978-2253002864", "Les Misérables", "Victor Hugo");
    bibliotheque.ajouterLivre("978-2070360079", "L'Étranger", "Albert Camus");
    bibliotheque.ajouterLivre("978-2253004226", "Le Comte de Monte-Cristo", "Alexandre Dumas");
    bibliotheque.ajouterLivre("978-2070368228", "1984", "George Orwell");
    
    // ========== SCIENCE-FICTION & FANTASY ==========
    bibliotheque.ajouterLivre("978-0345339706", "Le Seigneur des Anneaux", "J.R.R. Tolkien");
    bibliotheque.ajouterLivre("978-0439708180", "Harry Potter à l'école des sorciers", "J.K. Rowling");
    bibliotheque.ajouterLivre("978-0553293357", "Foundation", "Isaac Asimov");
    bibliotheque.ajouterLivre("978-0441172719", "Dune", "Frank Herbert");
    bibliotheque.ajouterLivre("978-0553380163", "Snow Crash", "Neal Stephenson");
    
    // ========== DÉVELOPPEMENT PERSONNEL ==========
    bibliotheque.ajouterLivre("978-2081491458", "Sapiens", "Yuval Noah Harari");
    bibliotheque.ajouterLivre("978-0143127741", "Thinking, Fast and Slow", "Daniel Kahneman");
    bibliotheque.ajouterLivre("978-1501124020", "Deep Work", "Cal Newport");
    bibliotheque.ajouterLivre("978-0062315007", "The Lean Startup", "Eric Ries");
    bibliotheque.ajouterLivre("978-1591847786", "Atomic Habits", "James Clear");
    
    // ========== EMPRUNTEURS ==========
    bibliotheque.ajouterEmprunteur("Dupont", "Jean", "jean.dupont@mail.com");
    bibliotheque.ajouterEmprunteur("Martin", "Sophie", "sophie.martin@mail.com");
    bibliotheque.ajouterEmprunteur("Bernard", "Luc", "luc.bernard@mail.com");
    bibliotheque.ajouterEmprunteur("Dubois", "Marie", "marie.dubois@mail.com");
    bibliotheque.ajouterEmprunteur("Moreau", "Pierre", "pierre.moreau@mail.com");
    bibliotheque.ajouterEmprunteur("Laurent", "Emma", "emma.laurent@mail.com");
    bibliotheque.ajouterEmprunteur("Simon", "Thomas", "thomas.simon@mail.com");
    bibliotheque.ajouterEmprunteur("Michel", "Claire", "claire.michel@mail.com");
    bibliotheque.ajouterEmprunteur("Leroy", "Antoine", "antoine.leroy@mail.com");
    bibliotheque.ajouterEmprunteur("Garcia", "Julie", "julie.garcia@mail.com");
    
    // ========== EMPRUNTS DE TEST ==========
    // Quelques emprunts actifs pour tester
    bibliotheque.emprunterLivre("978-0132350884", 1, 14);  // Jean emprunte Clean Code
    bibliotheque.emprunterLivre("978-2070360024", 2, 7);   // Sophie emprunte Le Petit Prince
    bibliotheque.emprunterLivre("978-0345339706", 3, 21);  // Luc emprunte Le Seigneur des Anneaux
    
    System.out.println("Données de test chargées !");
    System.out.println("    25 livres ajoutés");
    System.out.println("    10 emprunteurs enregistrés");
    System.out.println("    3 emprunts actifs\n");
    }
}