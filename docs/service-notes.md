# Notes Atelier 4

## A. Modes d'injection
Avec le constructeur le champ est final et la classe ne peut pas être créée sans son repository. Avec l'attribut le champ n'est pas final, la dépendance est cachée et elle vaut null si on utilise new hors de Spring. SonarQube a signalé l'injection par attribut avec la règle S6813.

## B. Services
Les neuf services (Vehicule, Agence, Employe, Equipement, Client, Reservation, Contrat, Maintenance et Paiement) reçoivent leur repository par le constructeur. Le constructeur est généré par @RequiredArgsConstructor et le champ est final, donc le service est toujours complet.

## C. Messages d'erreur
Message 1 : Spring ne trouve pas de bean IContratService, car @Service manque sur ContratServiceImpl ou la classe est hors du package de scan. Il faut ajouter @Service ou la déplacer.
Message 2 : deux beans du même type. On peut mettre @Primary sur la classe choisie par défaut ou @Qualifier sur le paramètre du constructeur.
Message 3 : dépendance circulaire. Avec le constructeur, chaque bean a besoin de l'autre pour être créé, donc aucun ne démarre. Il faut déplacer la logique commune dans un troisième service.

## D. Anomalies SonarQube
1. La règle S112 signalait l'exception générique RuntimeException dans findById, je l'ai remplacée par ResourceNotFoundException.
2. La règle S6813 signalait l'injection par attribut (@Autowired sur le champ), je suis revenu à l'injection par constructeur avec @RequiredArgsConstructor et un champ final.
3. La règle S1128 signalait un import inutile (java.util.Map), je l'ai supprimé.

## E. Questions
1. Le new est exécuté par Spring au démarrage, grâce à @Service et au scan.
2. Le contrôleur dépend de l'interface pour qu'on puisse changer l'implémentation sans le modifier.
3. Un service est partagé par toutes les requêtes. S'il garde un attribut, deux requêtes en même temps s'écrasent.
4. Si on passait l'objet reçu à save, les colonnes non envoyées deviendraient null. On charge donc l'existant, on recopie, puis on sauvegarde.
5. @Component se met sur une classe, @Bean sur une méthode de configuration. @Primary se met sur la classe, @Qualifier à l'injection. Les paiements passent par le contrat, donc IPaiementService est en lecture seule.