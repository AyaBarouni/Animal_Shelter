package service;

import model.*;
import exceptions.ShelterException;
import java.util.ArrayList;

/**
 * Classe Shelter
 * 
 * Classe principale de gestion du refuge.
 * Gère les animaux, les bénévoles, les adoptions et l'historique.
 */
public class Shelter {
    // Attributs
    private String shelterName;
    private ArrayList<Animal> animals;
    private ArrayList<Volunteer> volunteers;
    private ArrayList<AdoptionForm> adoptionForms;
    private int nextAnimalId;
    private int nextVolunteerId;
    private int nextFormId;

    /**
     * Constructeur
     * 
     * @param shelterName Nom du refuge
     */
    public Shelter(String shelterName) {
        this.shelterName = shelterName;
        this.animals = new ArrayList<>();
        this.volunteers = new ArrayList<>();
        this.adoptionForms = new ArrayList<>();
        this.nextAnimalId = 1;
        this.nextVolunteerId = 1;
        this.nextFormId = 1;
    }

    // ==================== GETTERS ====================
    public String getShelterName() {
        return shelterName;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<Volunteer> getVolunteers() {
        return volunteers;
    }

    public ArrayList<AdoptionForm> getAdoptionForms() {
        return adoptionForms;
    }

    // ==================== GESTION DES ANIMAUX ====================
    /**
     * Ajoute un animal au refuge
     * 
     * @param animal Animal à ajouter
     */
    public void addAnimal(Animal animal) {
        animals.add(animal);
        System.out.println("✓ Animal ajouté: " + animal.getName() + " (ID: " + animal.getId() + ")");
    }

    /**
     * Retire un animal du refuge par ID
     * Supprime en cascade les formulaires d'adoption liés
     *
     * @param animalId ID de l'animal à retirer
     * @return True si retiré, false sinon
     */
    public boolean removeAnimal(int animalId) {
        Animal animal = findAnimalById(animalId);
        if (animal != null) {
            animals.remove(animal);
            // Suppression en cascade des formulaires d'adoption liés
            ArrayList<AdoptionForm> formsToRemove = new ArrayList<>();
            for (AdoptionForm form : adoptionForms) {
                if (form.getAnimalId() == animalId) {
                    formsToRemove.add(form);
                }
            }
            adoptionForms.removeAll(formsToRemove);
            if (!formsToRemove.isEmpty()) {
                System.out.println("✓ " + formsToRemove.size() + " formulaire(s) d'adoption supprimé(s) en cascade.");
            }
            System.out.println("✓ Animal retiré: " + animal.getName());
            return true;
        }
        System.out.println("✗ Animal non trouvé.");
        return false;
    }

    /**
     * Trouve un animal par son ID
     * 
     * @param animalId ID à rechercher
     * @return Animal trouvé ou null
     */
    public Animal findAnimalById(int animalId) {
        for (Animal animal : animals) {
            if (animal.getId() == animalId) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Affiche tous les animaux du refuge
     */
    public void displayAllAnimals() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    ANIMAUX DU REFUGE: " + shelterName);
        System.out.println("══════════════════════════════════════════");
        if (animals.isEmpty()) {
            System.out.println("Aucun animal dans le refuge.");
        } else {
            for (int i = 0; i < animals.size(); i++) {
                System.out.println((i + 1) + ". " + animals.get(i).toString());
            }
        }
        System.out.println("══════════════════════════════════════════\n");
    }

    /**
     * Affiche les animaux disponibles à l'adoption
     */
    public void displayAvailableAnimals() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    ANIMAUX DISPONIBLES À L'ADOPTION");
        System.out.println("══════════════════════════════════════════");
        boolean found = false;
        for (Animal animal : animals) {
            if (!animal.isAdopted()) {
                System.out.println("  " + animal.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Aucun animal disponible.");
        }
        System.out.println("══════════════════════════════════════════\n");
    }

    /**
     * Affiche les animaux déjà adoptés
     */
    public void displayAdoptedAnimals() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    ANIMAUX ADOPTÉS");
        System.out.println("══════════════════════════════════════════");
        boolean found = false;
        for (Animal animal : animals) {
            if (animal.isAdopted()) {
                System.out.println("  " + animal.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Aucun animal adopté.");
        }
        System.out.println("══════════════════════════════════════════\n");
    }

    /**
     * Compte le nombre d'animaux disponibles
     * 
     * @return Nombre d'animaux disponibles
     */
    public int countAvailableAnimals() {
        int count = 0;
        for (Animal animal : animals) {
            if (!animal.isAdopted()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Compte le nombre d'animaux adoptés
     * 
     * @return Nombre d'animaux adoptés
     */
    public int countAdoptedAnimals() {
        int count = 0;
        for (Animal animal : animals) {
            if (animal.isAdopted()) {
                count++;
            }
        }
        return count;
    }

    // ==================== GESTION DES ADOPTIONS ====================
    /**
     * Adopte un animal avec un formulaire d'adoption
     * 
     * @param animalId ID de l'animal à adopter
     * @param form     Formulaire d'adoption
     * @throws ShelterException Si adoption impossible
     */
    public void adoptAnimal(int animalId, AdoptionForm form) throws ShelterException {
        Animal animal = findAnimalById(animalId);
        if (animal == null) {
            throw new ShelterException("Animal avec ID " + animalId + " non trouvé.");
        }
        if (animal.isAdopted()) {
            throw new ShelterException("L'animal " + animal.getName() + " est déjà adopté.");
        }
        if (!form.isValid()) {
            throw new ShelterException("Formulaire invalide: " + form.getValidationError());
        }

        animal.adopt();
        form.approveAdoption();
        adoptionForms.add(form);
        System.out.println("✓ Adoption réussie de " + animal.getName() + " par " + form.getAdopterName());
    }

    /**
     * Retourne un animal au refuge
     * 
     * @param animalId ID de l'animal à retourner
     * @throws ShelterException Si retour impossible
     */
    public void returnAnimal(int animalId) throws ShelterException {
        Animal animal = findAnimalById(animalId);
        if (animal == null) {
            throw new ShelterException("Animal avec ID " + animalId + " non trouvé.");
        }
        animal.returnToShelter();
    }

    /**
     * Crée un formulaire d'adoption
     * 
     * @param animalId       ID de l'animal
     * @param adopterName    Nom de l'adoptant
     * @param adopterEmail   Email de l'adoptant
     * @param adopterPhone   Téléphone de l'adoptant
     * @param adopterAddress Adresse de l'adoptant
     * @return Formulaire créé
     */
    public AdoptionForm createAdoptionForm(int animalId, String adopterName,
            String adopterEmail, String adopterPhone, String adopterAddress) {
        AdoptionForm form = new AdoptionForm(nextFormId++, animalId, adopterName,
                adopterEmail, adopterPhone, adopterAddress);
        return form;
    }

    /**
     * Affiche tous les formulaires d'adoption
     */
    public void displayAllAdoptionForms() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    FORMULAIRES D'ADOPTION");
        System.out.println("══════════════════════════════════════════");
        if (adoptionForms.isEmpty()) {
            System.out.println("Aucun formulaire d'adoption.");
        } else {
            for (AdoptionForm form : adoptionForms) {
                form.afficherResume();
            }
        }
        System.out.println("══════════════════════════════════════════\n");
    }

    // ==================== GESTION DES BÉNÉVOLES ====================
    /**
     * Ajoute un bénévole
     * 
     * @param volunteer Bénévole à ajouter
     */
    public void addVolunteer(Volunteer volunteer) {
        volunteers.add(volunteer);
        System.out.println("✓ Bénévole ajouté: " + volunteer.getName());
    }

    /**
     * Retire un bénévole par ID
     * 
     * @param volunteerId ID du bénévole
     * @return True si retiré, false sinon
     */
    public boolean removeVolunteer(int volunteerId) {
        Volunteer volunteer = findVolunteerById(volunteerId);
        if (volunteer != null) {
            volunteers.remove(volunteer);
            System.out.println("✓ Bénévole retiré: " + volunteer.getName());
            return true;
        }
        System.out.println("✗ Bénévole non trouvé.");
        return false;
    }

    /**
     * Trouve un bénévole par ID
     * 
     * @param volunteerId ID à rechercher
     * @return Bénévole trouvé ou null
     */
    public Volunteer findVolunteerById(int volunteerId) {
        for (Volunteer volunteer : volunteers) {
            if (volunteer.getVolunteerId() == volunteerId) {
                return volunteer;
            }
        }
        return null;
    }

    /**
     * Affiche tous les bénévoles
     */
    public void displayAllVolunteers() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    BÉNÉVOLES DU REFUGE");
        System.out.println("══════════════════════════════════════════");
        if (volunteers.isEmpty()) {
            System.out.println("Aucun bénévole.");
        } else {
            for (Volunteer volunteer : volunteers) {
                volunteer.afficherResume();
            }
        }
        System.out.println("══════════════════════════════════════════\n");
    }

    /**
     * Affiche les bénévoles actifs
     */
    public void displayActiveVolunteers() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    BÉNÉVOLES ACTIFS");
        System.out.println("══════════════════════════════════════════");
        boolean found = false;
        for (Volunteer volunteer : volunteers) {
            if (volunteer.isActive()) {
                volunteer.afficherResume();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Aucun bénévole actif.");
        }
        System.out.println("══════════════════════════════════════════\n");
    }

    // ==================== STATISTIQUES ====================
    /**
     * Affiche les statistiques détaillées du refuge
     */
    public void displayStatistics() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    STATISTIQUES DU REFUGE: " + shelterName);
        System.out.println("══════════════════════════════════════════");
        System.out.println("Total d'animaux: " + animals.size());
        System.out.println("Animaux disponibles: " + countAvailableAnimals());
        System.out.println("Animaux adoptés: " + countAdoptedAnimals());

        // Statistiques par type d'animal
        int dogs = 0, cats = 0, birds = 0;
        int totalAge = 0;
        for (Animal animal : animals) {
            totalAge += animal.getAge();
            if (animal instanceof Dog)
                dogs++;
            else if (animal instanceof Cat)
                cats++;
            else if (animal instanceof Bird)
                birds++;
        }
        System.out.println("\n--- Répartition par type ---");
        System.out.println("Chiens: " + dogs);
        System.out.println("Chats: " + cats);
        System.out.println("Oiseaux: " + birds);

        // Âge moyen
        if (!animals.isEmpty()) {
            double avgAge = (double) totalAge / animals.size();
            System.out.printf("Âge moyen: %.1f ans\n", avgAge);
        }

        // Taux d'adoption
        if (!animals.isEmpty()) {
            double adoptionRate = (double) countAdoptedAnimals() / animals.size() * 100;
            System.out.printf("Taux d'adoption: %.1f%%\n", adoptionRate);
        }

        // Statistiques des bénévoles
        int activeVolunteers = 0;
        int totalHours = 0;
        for (Volunteer v : volunteers) {
            if (v.isActive())
                activeVolunteers++;
            totalHours += v.getHoursWorked();
        }
        System.out.println("\n--- Bénévoles ---");
        System.out.println("Total: " + volunteers.size());
        System.out.println("Actifs: " + activeVolunteers);
        System.out.println("Inactifs: " + (volunteers.size() - activeVolunteers));
        System.out.println("Heures totales travaillées: " + totalHours + "h");

        // Statistiques des formulaires d'adoption
        int pending = 0, approved = 0, rejected = 0;
        for (AdoptionForm form : adoptionForms) {
            if (form.isPending())
                pending++;
            else if (form.isApproved())
                approved++;
            else if (form.isRejected())
                rejected++;
        }
        System.out.println("\n--- Formulaires d'adoption ---");
        System.out.println("Total: " + adoptionForms.size());
        System.out.println("Approuvés: " + approved);
        System.out.println("En attente: " + pending);
        System.out.println("Rejetés: " + rejected);

        System.out.println("══════════════════════════════════════════\n");
    }

    /**
     * Génère le prochain ID d'animal
     * 
     * @return ID suivant
     */
    public int getNextAnimalId() {
        return nextAnimalId++;
    }

    /**
     * Génère le prochain ID de bénévole
     *
     * @return ID suivant
     */
    public int getNextVolunteerId() {
        return nextVolunteerId++;
    }

    /**
     * Génère le prochain ID de formulaire
     *
     * @return ID suivant
     */
    public int getNextFormId() {
        return nextFormId++;
    }

    // ==================== SETTERS POUR IDS (CHARGEMENT) ====================
    /**
     * Définit le prochain ID d'animal (utilisé après chargement de fichier)
     */
    public void setNextAnimalId(int nextId) {
        this.nextAnimalId = nextId;
    }

    /**
     * Définit le prochain ID de bénévole (utilisé après chargement de fichier)
     */
    public void setNextVolunteerId(int nextId) {
        this.nextVolunteerId = nextId;
    }

    /**
     * Définit le prochain ID de formulaire (utilisé après chargement de fichier)
     */
    public void setNextFormId(int nextId) {
        this.nextFormId = nextId;
    }

    // ==================== PERSISTANCE DES DONNÉES ====================
    /**
     * Charge les données depuis les fichiers texte
     * Met à jour les compteurs d'ID en fonction des données chargées
     */
    public void loadData() {
        ArrayList<Animal> loadedAnimals = DataManager.loadAnimals();
        ArrayList<Volunteer> loadedVolunteers = DataManager.loadVolunteers();
        ArrayList<AdoptionForm> loadedAdoptions = DataManager.loadAdoptions();

        if (!loadedAnimals.isEmpty()) {
            this.animals = loadedAnimals;
            int maxId = 0;
            for (Animal a : animals) {
                if (a.getId() > maxId)
                    maxId = a.getId();
            }
            this.nextAnimalId = maxId + 1;
        }

        if (!loadedVolunteers.isEmpty()) {
            this.volunteers = loadedVolunteers;
            int maxId = 0;
            for (Volunteer v : volunteers) {
                if (v.getVolunteerId() > maxId)
                    maxId = v.getVolunteerId();
            }
            this.nextVolunteerId = maxId + 1;
        }

        if (!loadedAdoptions.isEmpty()) {
            this.adoptionForms = loadedAdoptions;
            int maxId = 0;
            for (AdoptionForm f : adoptionForms) {
                if (f.getFormId() > maxId)
                    maxId = f.getFormId();
            }
            this.nextFormId = maxId + 1;
        }
    }

    /**
     * Sauvegarde toutes les données dans les fichiers texte
     */
    public void saveAllData() {
        DataManager.saveAnimals(animals);
        DataManager.saveVolunteers(volunteers);
        DataManager.saveAdoptions(adoptionForms);
    }

    // ==================== MÉTHODES RÉCURSIVES BONUS ====================
    /**
     * Compte récursivement le nombre d'animaux dans la liste
     *
     * @param index Index actuel dans la liste
     * @return Nombre total d'animaux
     */
    public int countAnimalsRecursive(int index) {
        // Cas de base : on a dépassé la fin de la liste
        if (index >= animals.size())
            return 0;
        // Appel récursif : 1 (animal actuel) + count du reste
        return 1 + countAnimalsRecursive(index + 1);
    }

    /**
     * Calcule récursivement la somme des âges de tous les animaux
     *
     * @param index Index actuel dans la liste
     * @return Somme totale des âges
     */
    public int getTotalAgeRecursive(int index) {
        // Cas de base : fin de la liste
        if (index >= animals.size())
            return 0;
        // Appel récursif : âge actuel + somme des âges du reste
        return animals.get(index).getAge() + getTotalAgeRecursive(index + 1);
    }

    /**
     * Vérifie récursivement quels animaux ont besoin d'un contrôle médical
     * (dernière visite > 6 mois)
     *
     * @param index Index actuel dans la liste
     * @return Liste des animaux nécessitant un contrôle
     */
    public ArrayList<Animal> checkAnimalsNeedCheckupRecursive(int index) {
        // Cas de base : fin de la liste
        if (index >= animals.size())
            return new ArrayList<>();

        // Appel récursif pour le reste de la liste
        ArrayList<Animal> needCheckup = checkAnimalsNeedCheckupRecursive(index + 1);

        // Vérifier si l'animal actuel a besoin d'un contrôle
        if (animals.get(index).getMedicalRecord().needsCheckup()) {
            needCheckup.add(0, animals.get(index));
        }
        return needCheckup;
    }

    // ==================== NAVIGATION RÉCURSIVE PAR CATÉGORIES ====================
    /**
     * Affiche récursivement les animaux par catégories
     * Navigation hiérarchique : Type -> Sous-catégorie -> Animaux
     *
     * @param categoryLevel   Niveau actuel dans la hiérarchie (0=types,
     *                        1=sous-types, 2=animaux)
     * @param selectedType    Type sélectionné (null au niveau 0)
     * @param selectedSubtype Sous-type sélectionné (null au niveau 1)
     */
    public void browseCategoriesRecursive(int categoryLevel, String selectedType, String selectedSubtype) {
        switch (categoryLevel) {
            case 0: // Niveau 0 : Afficher les types principaux
                System.out.println("\n══════════════════════════════════════════");
                System.out.println("    NAVIGATION PAR CATÉGORIES");
                System.out.println("══════════════════════════════════════════");
                System.out.println("Types disponibles:");
                System.out.println("1. Chiens (Dogs)");
                System.out.println("2. Chats (Cats)");
                System.out.println("3. Oiseaux (Birds)");
                System.out.println("0. Retour");
                System.out.println("══════════════════════════════════════════");
                break;

            case 1: // Niveau 1 : Afficher les sous-catégories du type sélectionné
                System.out.println("\n--- " + selectedType.toUpperCase() + " ---");
                ArrayList<String> subcategories = getSubcategoriesForType(selectedType);
                for (int i = 0; i < subcategories.size(); i++) {
                    System.out.println((i + 1) + ". " + subcategories.get(i));
                }
                System.out.println("0. Retour au menu principal");
                break;

            case 2: // Niveau 2 : Afficher les animaux de la sous-catégorie
                System.out.println("\n--- " + selectedType + " > " + selectedSubtype + " ---");
                ArrayList<Animal> categoryAnimals = getAnimalsByCategory(selectedType, selectedSubtype);
                if (categoryAnimals.isEmpty()) {
                    System.out.println("Aucun animal dans cette catégorie.");
                } else {
                    for (int i = 0; i < categoryAnimals.size(); i++) {
                        Animal animal = categoryAnimals.get(i);
                        System.out.println((i + 1) + ". " + animal.getName() +
                                " (ID: " + animal.getId() + ") - " +
                                (animal.isAdopted() ? "Adopté" : "Disponible"));
                    }
                    System.out.println("\nOptions:");
                    System.out.println("0. Retour");
                    System.out.println("Ou entrez l'ID d'un animal pour voir ses détails");
                }
                break;
        }
    }

    /**
     * Retourne les sous-catégories disponibles pour un type d'animal
     *
     * @param type Type d'animal ("Dogs", "Cats", "Birds")
     * @return Liste des sous-catégories
     */
    private ArrayList<String> getSubcategoriesForType(String type) {
        ArrayList<String> subcategories = new ArrayList<>();
        switch (type) {
            case "Dogs":
                // Races de chiens populaires
                subcategories.add("Labrador");
                subcategories.add("Golden Retriever");
                subcategories.add("Berger Allemand");
                subcategories.add("Bulldog");
                subcategories.add("Autres races");
                break;
            case "Cats":
                // Types de chats
                subcategories.add("Chat de maison");
                subcategories.add("Chat de gouttière");
                subcategories.add("Chat persan");
                subcategories.add("Chat siamois");
                subcategories.add("Autres races");
                break;
            case "Birds":
                // Types d'oiseaux
                subcategories.add("Canari");
                subcategories.add("Perroquet");
                subcategories.add("Colombe");
                subcategories.add("Perruche");
                subcategories.add("Autres espèces");
                break;
        }
        return subcategories;
    }

    /**
     * Retourne les animaux d'une catégorie spécifique
     *
     * @param type    Type d'animal
     * @param subtype Sous-catégorie
     * @return Liste des animaux correspondants
     */
    private ArrayList<Animal> getAnimalsByCategory(String type, String subtype) {
        ArrayList<Animal> result = new ArrayList<>();
        for (Animal animal : animals) {
            if (matchesCategory(animal, type, subtype)) {
                result.add(animal);
            }
        }
        return result;
    }

    /**
     * Vérifie si un animal correspond à une catégorie
     *
     * @param animal  Animal à vérifier
     * @param type    Type attendu
     * @param subtype Sous-type attendu
     * @return True si correspondance
     */
    private boolean matchesCategory(Animal animal, String type, String subtype) {
        String animalType = animal.getClass().getSimpleName();

        // Vérifier le type principal
        if (!animalType.equalsIgnoreCase(type.replace("s", ""))) { // Dogs -> Dog
            return false;
        }

        // Vérifier la sous-catégorie
        if (animal instanceof model.Dog && type.equals("Dogs")) {
            model.Dog dog = (model.Dog) animal;
            return matchesDogCategory(dog.getBreed(), subtype);
        } else if (animal instanceof model.Cat && type.equals("Cats")) {
            model.Cat cat = (model.Cat) animal;
            return matchesCatCategory(cat.getColor(), subtype);
        } else if (animal instanceof model.Bird && type.equals("Birds")) {
            model.Bird bird = (model.Bird) animal;
            return matchesBirdCategory(bird.getSpecies(), subtype);
        }

        return false;
    }

    /**
     * Vérifie si une race de chien correspond à la sous-catégorie
     */
    private boolean matchesDogCategory(String breed, String subtype) {
        if (subtype.equals("Autres races")) {
            return !breed.equalsIgnoreCase("Labrador") &&
                    !breed.equalsIgnoreCase("Golden Retriever") &&
                    !breed.equalsIgnoreCase("Berger Allemand") &&
                    !breed.equalsIgnoreCase("Bulldog");
        }
        return breed.equalsIgnoreCase(subtype);
    }

    /**
     * Vérifie si une couleur de chat correspond à la sous-catégorie
     */
    private boolean matchesCatCategory(String color, String subtype) {
        switch (subtype) {
            case "Chat de maison":
                return color.equalsIgnoreCase("noir") ||
                        color.equalsIgnoreCase("blanc") ||
                        color.equalsIgnoreCase("gris");
            case "Chat de gouttière":
                return color.equalsIgnoreCase("roux") ||
                        color.equalsIgnoreCase("tigré");
            case "Chat persan":
                return color.equalsIgnoreCase("blanc") ||
                        color.equalsIgnoreCase("gris");
            case "Chat siamois":
                return color.equalsIgnoreCase("siamois");
            case "Autres races":
                return !color.equalsIgnoreCase("noir") &&
                        !color.equalsIgnoreCase("blanc") &&
                        !color.equalsIgnoreCase("gris") &&
                        !color.equalsIgnoreCase("roux") &&
                        !color.equalsIgnoreCase("tigré") &&
                        !color.equalsIgnoreCase("siamois");
            default:
                return false;
        }
    }

    /**
     * Vérifie si une espèce d'oiseau correspond à la sous-catégorie
     */
    private boolean matchesBirdCategory(String species, String subtype) {
        if (subtype.equals("Autres espèces")) {
            return !species.equalsIgnoreCase("Canari") &&
                    !species.equalsIgnoreCase("Perroquet") &&
                    !species.equalsIgnoreCase("Colombe") &&
                    !species.equalsIgnoreCase("Perruche");
        }
        return species.equalsIgnoreCase(subtype);
    }

    /**
     * Affiche les statistiques par catégorie de manière récursive
     *
     * @param categoryLevel Niveau de profondeur pour l'affichage
     */
    public void displayCategoryStatisticsRecursive(int categoryLevel) {
        if (categoryLevel == 0) {
            System.out.println("\n══════════════════════════════════════════");
            System.out.println("    STATISTIQUES PAR CATÉGORIE");
            System.out.println("══════════════════════════════════════════");
        }

        String[] types = { "Dogs", "Cats", "Birds" };
        for (String type : types) {
            ArrayList<String> subcategories = getSubcategoriesForType(type);
            int totalInType = 0;

            System.out.println("\n" + type + ":");
            for (String subtype : subcategories) {
                ArrayList<Animal> animalsInSubcategory = getAnimalsByCategory(type, subtype);
                int available = 0;
                for (Animal animal : animalsInSubcategory) {
                    if (!animal.isAdopted())
                        available++;
                }
                totalInType += animalsInSubcategory.size();

                if (categoryLevel >= 1) {
                    System.out.println("  " + subtype + ": " + animalsInSubcategory.size() +
                            " total (" + available + " disponibles)");
                }
            }
            System.out.println("  Total " + type + ": " + totalInType + " animaux");
        }

        if (categoryLevel == 0) {
            System.out.println("══════════════════════════════════════════\n");
        }
    }
}
