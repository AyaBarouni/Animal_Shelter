# Animal Shelter Management System — Java

Academic project completed as part of the **Java Programming (CEWP MOD4-GRA)** course at Concordia University (Montreal, 2026).

---

## Context

This project was developed to apply object-oriented programming concepts in Java, taught module by module throughout the course. The application simulates the full management of an animal shelter: registering animals, tracking medical records, managing adoptions and volunteers, searching and sorting.

---

## Technical overview

The project is built around a hierarchy of abstract and concrete classes: `Animal` (abstract) is extended by `Dog`, `Cat` and `Bird`. Each subclass implements its own version of `displayDetails()` - this is polymorphism in action. `Animal` also implements the `Adoptable` interface, which defines the contract `adopt()` / `returnToShelter()` and throws a custom `ShelterException` when an invalid operation is attempted.

Medical tracking relies on composition: each animal *has* a `MedicalRecord`, which recursively calculates the total cost of care. Search and sort logic is handled by `SearchUtils` (linear and binary) and `SortUtils` (bubble, selection, insertion).

---

## Key files

| File | Role |
|---|---|
| `model/Animal.java` | Abstract class - shared attributes, `Adoptable` interface |
| `service/Shelter.java` | Main manager - collections, business logic |
| `service/SearchUtils.java` / `SortUtils.java` | Search and sorting algorithms |
| `exceptions/ShelterException.java` | Custom exception |

---

## Usage

```bash
# Compile
javac -d . model/*.java interfaces/*.java exceptions/*.java service/*.java ui/*.java Main.java

# Run
java Main
```

Sample interaction:

```
Animal type: 1. Dog  2. Cat  3. Bird
Your choice: 1
Name: Rex | Age: 2 | Breed: Labrador | Trained: yes
✓ Animal added: Rex (ID: 6)
```

---

## Technologies

- Java 17
- OOP: inheritance, polymorphism, composition, interfaces
- Java collections (`ArrayList`)
- Search and sorting algorithms
- Custom exceptions, recursion

---

## What I learned

**The difference between inheritance and composition is not just a syntax detail.** Linking `MedicalRecord` to `Animal` through composition made me think carefully about semantics: an animal *has* a medical record, it is not a medical record. This distinction only became clear to me once I had to apply it.

**An interface is a contract.** Implementing `Adoptable` in `Animal` showed me why interfaces exist: they guarantee that an object exposes certain methods without constraining its class hierarchy. If a `Toy` class needed to be adoptable tomorrow, it would not have to inherit from `Animal`.

**Understanding a sorting algorithm means understanding its trade-offs.** Implementing all three algorithms side by side let me see their differences in terms of comparisons and swaps - something that Big O notation summarizes but does not show.