# Stratégies de fetch et de cascade

Les associations utilisent `FetchType.LAZY` pour ne charger les entités liées que lorsqu'elles sont demandées. Cela évite les chargements en chaîne et garde les lectures courantes plus légères. Une association `LAZY` doit être consultée dans une transaction ou chargée explicitement par une requête dédiée.

| Association | Fetch | Cascade / orphanRemoval | Justification |
|---|---|---|---|
| Agence → Véhicule | `LAZY` | Aucune | Un véhicule peut survivre à la suppression de son agence ; ne pas propager la suppression. |
| Agence → Employé | `LAZY` | Aucune | Les employés ont un cycle de vie indépendant de l'agence. |
| Véhicule ↔ Équipement | `LAZY` | Aucune | Les équipements peuvent être partagés entre véhicules ; le côté propriétaire `Vehicule` utilise un `Set` et la table `vehicule_equipement`. |
| Client → Réservation | `LAZY` | `PERSIST` | Enregistrer un client peut enregistrer ses nouvelles réservations, sans propager les mises à jour ni les suppressions. |
| Réservation → Véhicule | `LAZY` | Aucune | Une réservation référence un véhicule existant ; elle ne possède pas son cycle de vie. |
| Réservation ↔ Contrat | `LAZY` | `ALL` côté `Reservation` | Le contrat est créé, modifié et supprimé avec sa réservation. `Contrat` est le propriétaire SQL via `id_reservation`. |
| Véhicule → Maintenance | `LAZY` | `PERSIST` | Enregistrer un véhicule peut enregistrer ses nouvelles maintenances, sans cascade de suppression du véhicule vers les maintenances. |
| Contrat → Paiement | `LAZY` | `ALL` et `orphanRemoval = true` | Un paiement dépend de son contrat : les opérations du contrat sont propagées, et retirer un paiement de la collection le supprime. |

`mappedBy` est déclaré sur les côtés inverses (`Agence`, `Client`, `Vehicule`, `Reservation` et `Contrat`) ; les clés étrangères sont portées par les côtés propriétaires. Aucun `@Data` n'est utilisé sur les entités afin d'éviter les parcours récursifs dans `toString`, `equals` et `hashCode`.
