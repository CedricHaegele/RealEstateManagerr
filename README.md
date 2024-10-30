# Real Estate Manager

**Application Android pour la gestion de biens immobiliers dans une agence spécialisée dans les biens d’exception.**  
Dernière mise à jour : Jeudi 17 mars 2022  
Temps estimé : 100 heures

## Description

**Real Estate Manager** est une application Android conçue pour les agents immobiliers d’une grande agence new-yorkaise. Cette application permet de gérer et d’afficher des informations sur des biens immobiliers haut de gamme. L’application a été initialement développée par un stagiaire, mais des améliorations substantielles ont été apportées pour qu’elle devienne un outil fiable et convivial pour les agents sur le terrain, y compris le support du mode hors-ligne.

---

## Fonctionnalités

1. **Liste des biens immobiliers** : Consultation d'une liste de biens avec détails et informations importantes.
2. **Détails du bien** : Accéder aux détails d’un bien avec ses photos, description, localisation, et informations financières.
3. **Ajout/Édition de biens** : Création ou mise à jour des informations des biens, avec ajout de photos depuis la galerie ou via la caméra.
4. **Géo-localisation des biens** : Affichage des biens sur une carte dynamique.
5. **Mode hors-ligne** : Stockage de toutes les données en local pour un accès même sans connexion internet.
6. **Moteur de recherche multi-critères** : Recherche avancée selon des critères comme la localisation, le prix, le type de bien, etc.
7. **Notifications** : Confirmation d’ajout de bien avec message de notification.
8. **Conversion des devises** : Conversion de prix en dollars vers euros et vice-versa.
9. **Gestion du statut du bien** : Marquage des biens comme vendus avec date d’enregistrement.

---

## Technologies utilisées

- **Java** : Langage principal de développement.
- **SQLite** : Base de données locale pour un stockage persistant.
- **ContentProvider** : Couche d’abstraction pour un accès standardisé aux données.
- **Google Maps API** : Pour la gestion de la carte et la géo-localisation.
- **Architecture MVC/MVP** : Organisation de l’application.
- **Tests unitaires et d’intégration** : Pour garantir la qualité du code.
- **Material Design** : Interface respectant les recommandations UX de Google.

---

## Configuration requise

- **Android Studio** : Version Hedgehog (2023.1.1) ou plus récente.
- **SDK Android minimum** : API 21 (Android 5.0).
- **SDK cible** : API 34 (Android 14).

---

## Installation

1. **Cloner le dépôt** :
   ```bash
   [git clone https://github.com/CedricHaegele/RealEstateManagerr.git)
