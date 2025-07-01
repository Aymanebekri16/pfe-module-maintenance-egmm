PFE : Module de Gestion de la Maintenance (Backend)
1. À propos du projet
Ce projet est le backend que j'ai développé pour mon Projet de Fin d'Études. Il s'agit d'un module de gestion de la maintenancepour les  besoins de l'EGMM, qui digitalise le suivi des interventions préventives et correctives.

L'application a été construite avec Spring Boot et expose une API REST pour gérer le cycle de vie complet d'une intervention :

Gestion des équipements du parc

Signalement d'une anomalie sur un équipement

Création d'un bon de travaux suite à une anomalie

Suivi des différentes interventions

2. Stack Technique
Langage : Java 17

Framework : Spring Boot 3

Accès aux données : Spring Data JPA (Hibernate)

Base de données (test) : H2 In-Memory

API : RESTful

Build : Maven

3. Lancement du projet
Pour lancer le serveur en local :

Clonez ce dépôt (git clone ...).

Prérequis : Assurez-vous que Java 17 et Maven sont bien installés sur votre machine.

Ouvrez un terminal à la racine du projet.

Lancez la commande : mvn spring-boot:run

Le serveur démarrera sur http://localhost:8080.

4. Tester l'API
Pour vérifier que la logique métier fonctionne correctement, voici un scénario de test simple à réaliser avec Postman. Il simule un cycle de maintenance de A à Z.

a. Créer un équipement
Méthode : POST

URL : http://localhost:8080/api/equipements

Exemple de Body (JSON) :

{
    "nom": "Concasseur B-101",
    "code": "CNC-B101"
}

b. Signaler une anomalie sur cet équipement
Méthode : POST

URL : http://localhost:8080/api/anomalies?equipementId=1

Exemple de Body (JSON) :

{
    "numPVCA": "PVCA-TEST-001",
    "dateConstat": "2025-07-01",
    "description": "Bruit de grincement excessif",
    "degreUrgence": 1
}

c. Créer le bon de travaux correspondant
Méthode : POST

URL : http://localhost:8080/api/bons-de-travaux?anomalieId=1

Exemple de Body (JSON) :

{
    "numFiche": "BT-2025-001",
    "description": "Investigation du bruit"
}

d. Vérifier la mise à jour automatique
Méthode : GET

URL : http://localhost:8080/api/anomalies/1

Résultat : Le statut de l'anomalie doit automatiquement passer à EN_TRAITEMENT, ce qui confirme que la logique de workflow est bien implémentée.