# Système de Gestion de Bibliothèque

> Application console Java pour gérer une bibliothèque avec système d'emprunts, recherche avancée et persistance des données.

## Table des matières

- [À propos](#à-propos)
- [Fonctionnalités](#fonctionnalités)
- [Technologies utilisées](#technologies-utilisées)
- [Installation](#installation)
- [Utilisation](#utilisation)
- [Structure du projet](#structure-du-projet)
- [Concepts Java appliqués](#concepts-java-appliqués)
- [Améliorations futures](#améliorations-futures)
- [Auteur](#auteur)

## À propos

Ce projet a été développé pour consolider mes compétences en **programmation orientée objet** et en **gestion de données**. 

Il s'agit d'une application console complète permettant de gérer une bibliothèque : catalogue de livres, gestion des emprunteurs, suivi des emprunts avec calcul automatique des retards.

## Fonctionnalités

### Gestion des livres
- Ajouter des livres avec ISBN, titre et auteur
- Rechercher par ISBN, titre ou auteur
- Afficher le catalogue complet avec statut de disponibilité

### Gestion des emprunteurs
- Enregistrer de nouveaux emprunteurs
- Validation automatique des emails
- Liste complète des emprunteurs

### Gestion des emprunts
- Créer un emprunt avec durée personnalisable
- Retourner un livre
- Détection automatique des retards
- Historique complet des emprunts

### Persistance des données
- Sauvegarde automatique à la fermeture
- Chargement automatique au démarrage
- Jeu de données de test (25 livres, 10 emprunteurs)
- Option de réinitialisation

## Technologies utilisées

- **Java 17** - Langage de programmation
- **LocalDate** - Gestion moderne des dates (java.time)
- **Sérialisation Java** - Persistance des données
- **HashMap** - Stockage optimisé avec recherche O(1)
- **Git** - Contrôle de version

## Installation

### Prérequis
- Java JDK 17 ou supérieur
- Un IDE Java (IntelliJ IDEA, Eclipse, VS Code)

### Étapes

1. **Cloner le repository**
```bash
git clone https://github.com/titus-79/bibliotheque.git
cd bibliotheque
```

2. **Compiler le projet**
```bash
javac -d bin src/**/*.java src/Main.java
```

3. **Lancer l'application**
```bash
java -cp bin Main
```

Ou utilisez votre IDE pour exécuter `Main.java` directement.

## 🚀 Utilisation

### Menu principal
```
========== MENU PRINCIPAL ==========
1. Gestion des livres
2. Gestion des emprunteurs
3. Gestion des emprunts
8. Reinitialiser avec jeu de test
9. Sauvegarder maintenant
0. Quitter
```

### Exemple d'utilisation

#### 1️ Ajouter un livre
```
Menu > 1 > 1
ISBN : 978-1234567890
Titre : Mon Nouveau Livre
Auteur : Jean Dupont
```

#### 2️ Emprunter un livre
```
Menu > 3 > 1
ISBN du livre : 978-0132350884
ID de l'emprunteur : 1
Durée de l'emprunt (en jours) : 14
```

#### 3️ Consulter les emprunts en cours
```
Menu > 3 > 3

=== EMPRUNTS EN COURS ===
- [1] Clean Code : Jean Dupont (Retour: 2025-11-29)
- [2] Le Petit Prince : Sophie Martin (Retour: 2025-11-22) [EN RETARD]
```

## Structure du projet
```
bibliotheque/
├── src/
│   ├── model/
│   │   ├── Livre.java           # Classe représentant un livre
│   │   ├── Emprunteur.java      # Classe représentant un emprunteur
│   │   └── Emprunt.java         # Classe gérant les emprunts avec dates
│   ├── service/
│   │   ├── BibliothequeService.java  # Logique métier
│   │   └── FichierService.java       # Gestion de la persistance
│   └── Main.java                # Point d'entrée avec menu interactif
├── data/
│   └── bibliotheque.dat         # Fichier de sauvegarde (généré auto)
├── .gitignore
└── README.md
```

## Concepts Java appliqués

Ce projet m'a permis de mettre en pratique :

### Programmation Orientée Objet
- Encapsulation (getters/setters)
- Relations entre objets (composition)
- Interfaces (`Serializable`)

### Collections Java
- `HashMap<K, V>` pour les recherches optimisées
- `ArrayList<T>` pour les listes dynamiques
- Itération sur les collections

### Gestion des dates
- `LocalDate` pour les dates modernes
- `ChronoUnit` pour calculer les différences
- Méthodes `plusDays()`, `isAfter()`, `isBefore()`

### Gestion des fichiers
- `ObjectOutputStream` / `ObjectInputStream`
- Sérialisation d'objets Java
- Gestion des exceptions (`try-catch-finally`)

### Bonnes pratiques
- Validation des données (emails, ISBN)
- Gestion des erreurs
- Code modulaire et réutilisable
- Commits Git avec conventions

##  Auteur

**Rodolphe**  
Développeur Java Junior

