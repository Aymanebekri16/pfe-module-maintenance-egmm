PFE : Module de Gestion de la Maintenance (Backend)
1. Description du Projet
Ce dépôt contient le code source du backend pour le module de gestion de la maintenance préventive et corrective, réalisé dans le cadre de mon projet de fin d'études pour l'EGMM.

L'application est développée en Java avec le framework Spring Boot et expose une API RESTful pour gérer le cycle de vie complet d'une intervention de maintenance :

Gestion des équipements

Signalement des anomalies

Création des bons de travaux

Suivi des interventions

2. Technologies Utilisées
Langage : Java 17

Framework : Spring Boot 3

Accès aux données : Spring Data JPA (avec Hibernate)

Base de données de test : H2 In-Memory

API : RESTful

Gestion des dépendances : Maven

3. Comment Lancer le Projet
Clonez ce dépôt sur votre machine locale.

Assurez-vous que Java 17 et Maven sont installés et configurés.

Ouvrez un terminal à la racine du projet.

Exécutez la commande : mvn spring-boot:run

Le serveur sera lancé et l'application sera accessible sur http://localhost:8080.

4. Guide de Test de l'API
Pour démontrer le bon fonctionnement de l'application, voici un scénario de test complet à effectuer avec un client API comme Postman.

a. Créer un Équipement
Méthode : POST

URL : http://localhost:8080/api/equipements

Body (JSON) :

{
    "nom": "Concasseur B-101",
    "code": "CNC-B101"
}

b. Signaler une Anomalie
Méthode : POST

URL : http://localhost:8080/api/anomalies?equipementId=1

Body (JSON) :

{
    "numPVCA": "PVCA-TEST-001",
    "dateConstat": "2025-07-01",
    "description": "Bruit de grincement excessif",
    "degreUrgence": 1
}

c. Créer un Bon de Travaux
Méthode : POST

URL : http://localhost:8080/api/bons-de-travaux?anomalieId=1

Body (JSON) :

{
    "numFiche": "BT-2025-001",
    "description": "Investigation du bruit"
}

d. Vérification de la Logique Métier
Méthode : GET

URL : http://localhost:8080/api/anomalies/1

Résultat attendu : Le statut de l'anomalie doit être automatiquement passé à EN_TRAITEMENT.