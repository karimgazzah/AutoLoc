# Atelier 4 - IoC, injection et couche Service

## A. Modes d'injection comparés

| Mode | Champ `final` possible | Dépendance visible | Utilisable hors conteneur | SonarQube |
|---|---|---|---|---|
| Constructeur (`@RequiredArgsConstructor`) | Oui | Oui, dans le constructeur | Oui, si la dépendance est fournie | Recommandé |
| Attribut (`@Autowired`) | Non | Non, cachée dans la classe | Non, l'attribut reste `null` | Signalé par la règle S6813 indiquée dans le support |

Tous les services utilisent l'injection par constructeur : leur repository est un champ `final`, et Spring utilise le constructeur unique généré par Lombok. L'injection par attribut n'est pas conservée.

## B. Services et dépendances

| Service | Dépendance injectée | Mode | Justification |
|---|---|---|---|
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | CRUD des agences et contrôle du nom obligatoire |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | CRUD des clients et contrôle du nom et de l'e-mail |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | CRUD des contrats et refus d'un montant négatif |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | CRUD des employés et contrôle du nom |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | CRUD des équipements et contrôle du libellé |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | CRUD et contrôle de l'ordre des dates |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Lecture seule ; les paiements sont gérés avec leur contrat |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | CRUD et contrôle de l'ordre des dates |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | CRUD et refus d'un tarif journalier nul ou négatif |

Les méthodes d'écriture sont transactionnelles afin que les modifications de l'entité chargée restent dans la même unité de travail. Le service de paiement est en lecture seule. Les mises à jour chargent l'entité existante et recopient uniquement ses champs scalaires modifiables ; elles ne remplacent ni l'identifiant ni les associations.

Les interfaces Repository de l'Atelier 3 étaient absentes de cette version du projet. Les neuf interfaces `JpaRepository<Entité, Long>` ont donc été ajoutées comme prérequis techniques aux services et à leur injection.

Le modèle actuel ne comporte ni quantité dans `Equipement`, ni coût dans `Maintenance` ; ces règles de gestion ne peuvent donc pas être appliquées sans modifier le modèle hors du périmètre de cet atelier.

## C. Diagnostic des erreurs du conteneur

| Message | Cause probable | Correction |
|---|---|---|
| Aucun bean `IContratService` trouvé | `ContratServiceImpl` n'est pas annoté `@Service`, ou est placé hors du package scanné | Annoter l'implémentation et la placer sous `tn.esprit.autoloc` |
| Deux beans candidats, par exemple `emailNotificateur` et `smsNotificateur` | Plusieurs implémentations correspondent au même type demandé | Marquer l'implémentation par défaut `@Primary`, ou choisir explicitement avec `@Qualifier` sur le paramètre du constructeur |
| Cycle entre `clientServiceImpl` et `reservationServiceImpl` | Chaque service dépend directement de l'autre | Extraire la responsabilité commune dans un composant distinct et éviter les dépendances bidirectionnelles |

`UnsatisfiedDependencyException` peut envelopper ces causes : il faut examiner le message racine (`Caused by`).

## D. Analyse de qualité

| Point | Règle ou constat | Correction |
|---|---|---|
| Injection par attribut, présentée comme expérience dans le support | S6813, règle indiquée dans l'atelier | Non retenue ; dépendances finales injectées par constructeur |
| Exception générique, présentée comme expérience dans le support | S112, règle indiquée dans l'atelier | Non retenue ; les absences utilisent `ResourceNotFoundException` |
| Interface véhicule initiale vide et nommée `IVehiculeServices` | Écart de nommage et interface sans contrat métier utile | Remplacée par `IVehiculeService` avec les méthodes attendues |

Les règles S6813 et S112 sont celles indiquées par le support pédagogique ; elles ne sont pas présentées comme un rapport d'exécution du plugin SonarQube. Le contrôle final dans SonarQube for IDE reste à réaliser dans IntelliJ.

## E. Questions de compréhension

1. Le code applicatif ne fait pas de `new ContratServiceImpl`. Spring détecte `@Service`, crée le bean, puis lui fournit `IContratRepository` par le constructeur.
2. Le contrôleur dépend de `IContratService` pour rester découplé de l'implémentation, faciliter son remplacement et permettre de fournir une implémentation de test.
3. Un singleton est partagé entre les requêtes et les threads. Un champ mutable tel qu'un « contrat courant » pourrait être écrasé par une autre requête et provoquer une fuite de données entre utilisateurs.
4. Charger l'entité existante puis recopier les champs modifiables évite d'écraser des colonnes ou associations non fournies. C'est particulièrement important pour la collection de paiements du contrat, qui utilise `orphanRemoval`.
5. `@Component` permet la détection automatique d'une classe par scan, tandis que `@Bean` déclare explicitement le résultat d'une méthode d'une classe `@Configuration`. `@Primary`, placé sur un bean, le rend candidat par défaut ; `@Qualifier`, placé au point d'injection, sélectionne un bean précis. `IPaiementService` est en lecture seule car les paiements sont créés, modifiés et supprimés via leur contrat.
