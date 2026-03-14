# Système de gestion d'un refuge animalier — Java

Projet scolaire réalisé dans le cadre du cours **Java Programming (CEWP MOD4-GRA)** à Concordia University (Montréal, 2026).

---

## Contexte

Ce projet a été développé pour appliquer concrètement les concepts de la programmation orientée objet en Java, enseignés module par module tout au long du cours. L'application simule la gestion complète d'un refuge animalier : enregistrement des animaux, suivi médical, gestion des adoptions et des bénévoles, recherche et tri.

---

## Principe technique

Le projet repose sur une hiérarchie de classes abstraites et concrètes : `Animal` (abstraite) est étendue par `Dog`, `Cat` et `Bird`. Chaque sous-classe implémente sa propre version de `afficherDetails()` : c'est le polymorphisme en action. `Animal` implémente également l'interface `Adoptable`, qui définit le contrat `adopt()` / `returnToShelter()` et lève une `ShelterException` personnalisée en cas d'opération invalide.

Le suivi médical repose sur la composition : chaque animal *possède* un `MedicalRecord`, qui calcule récursivement le coût total des soins. La recherche et le tri sont délégués à `SearchUtils` (linéaire et binaire) et `SortUtils` (bulles, sélection, insertion).

---

## Fichiers clés

| Fichier | Rôle |
|---|---|
| `model/Animal.java` | Classe abstraite - attributs communs, interface `Adoptable` |
| `service/Shelter.java` | Gestionnaire principal - collections, logique métier |
| `service/SearchUtils.java` / `SortUtils.java` | Algorithmes de recherche et de tri |
| `exceptions/ShelterException.java` | Exception personnalisée |

---

## Utilisation

```bash
# Compiler
javac -d . model/*.java interfaces/*.java exceptions/*.java service/*.java ui/*.java Main.java

# Exécuter
java Main
```

Exemple d'interaction :

```
Type d'animal : 1. Chien  2. Chat  3. Oiseau
Votre choix : 1
Nom : Rex | Âge : 2 | Race : Labrador | Dressé : oui
✓ Animal ajouté : Rex (ID: 6)
```

---

## Technologies

- Java 17
- POO : héritage, polymorphisme, composition, interfaces
- Collections Java (`ArrayList`)
- Algorithmes de recherche et de tri
- Exceptions personnalisées, récursion

---

## Ce que j'ai appris

**La différence entre héritage et composition n'est pas qu'un détail de syntaxe.** Associer `MedicalRecord` à `Animal` par composition m'a obligée à réfléchir à la sémantique : un animal *a* un dossier médical, il n'*est* pas un dossier médical. Cette distinction, que j'avais lue en cours, n'est devenue claire qu'en l'appliquant.

**Une interface, c'est un contrat.** Implémenter `Adoptable` dans `Animal` m'a montré pourquoi les interfaces existent : elles permettent de garantir qu'un objet dispose de certaines méthodes sans contraindre sa hiérarchie. Si demain on ajoute une classe `Toy` adoptable, elle n'a pas à hériter d'`Animal`.

**Comprendre un algorithme de tri, c'est comprendre ses compromis.** Implémenter les trois algorithmes côte à côte m'a permis de voir concrètement leurs différences en termes de comparaisons et d'échanges - ce que les notations O() résument mais ne montrent pas.