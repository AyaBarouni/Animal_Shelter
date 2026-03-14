package ui;

import service.*;
import model.*;
import exceptions.ShelterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe MainMenu
 * 
 * Interface utilisateur en ligne de commande (menu interactif).
 * Gère l'interaction avec l'utilisateur et affiche le menu principal.
 */
public class MainMenu {
    private Shelter shelter;
    private Scanner scanner;

    /**
     * Constructeur
     */
    public MainMenu() {
        this.shelter = new Shelter("Refuge Animalier de Paris");
        this.scanner = new Scanner(System.in);

        // Charger les données depuis les fichiers
        shelter.loadData();

        // Si aucune donnée n'a été chargée, initialiser avec des données d'exemple
        if (shelter.getAnimals().isEmpty()) {
            initializeSampleData();
            shelter.saveAllData();
        }
    }

    /**
     * Initialise des données d'exemple
     */
    private void initializeSampleData() {
        // Ajouter quelques animaux d'exemple
        Dog dog1 = new Dog(shelter.getNextAnimalId(), "Max", 3, LocalDate.now().minusDays(30),
                "Labrador", true);
        Dog dog2 = new Dog(shelter.getNextAnimalId(), "Bella", 2, LocalDate.now().minusDays(15),
                "Golden Retriever", false);
        Cat cat1 = new Cat(shelter.getNextAnimalId(), "Mimi", 1, LocalDate.now().minusDays(10),
                "Noir", true);
        Cat cat2 = new Cat(shelter.getNextAnimalId(), "Felix", 5, LocalDate.now().minusDays(45),
                "Gris", false);
        Bird bird1 = new Bird(shelter.getNextAnimalId(), "Tweety", 1, LocalDate.now().minusDays(5),
                25.5, "Canari");

        shelter.addAnimal(dog1);
        shelter.addAnimal(dog2);
        shelter.addAnimal(cat1);
        shelter.addAnimal(cat2);
        shelter.addAnimal(bird1);

        // Ajouter des données médicales pour démontrer la récursion
        dog1.getMedicalRecord().addVaccination("Rage");
        dog1.getMedicalRecord().addVaccination("Parvovirus");
        dog1.getMedicalRecord().addTreatment("Antiparasitaire");
        dog1.getMedicalRecord().addTreatment("Antibiotique pour infection");

        cat1.getMedicalRecord().addVaccination("Typhus");
        cat1.getMedicalRecord().addVaccination("Leucose");
        cat1.getMedicalRecord().addTreatment("Vaccin contre la leucose");

        bird1.getMedicalRecord().addVaccination("Hépatite");
        bird1.getMedicalRecord().addTreatment("Radiographie des ailes");

        // Ajouter quelques bénévoles
        Volunteer vol1 = new Volunteer(shelter.getNextVolunteerId(), "Jean Dupont",
                "jean@email.com", "0612345678");
        vol1.addSchedule("Lundi 9h-12h");
        vol1.addTask("Nourrir les animaux");

        Volunteer vol2 = new Volunteer(shelter.getNextVolunteerId(), "Marie Martin",
                "marie@email.com", "0698765432");
        vol2.addSchedule("Mercredi 14h-18h");
        vol2.addTask("Promenade des chiens");

        shelter.addVolunteer(vol1);
        shelter.addVolunteer(vol2);
    }

    /**
     * Lance le menu principal
     */
    public void start() {
        displayWelcome();
        boolean running = true;

        while (running) {
            displayMainMenu();
            int choice = getIntInput("Votre choix: ");

            switch (choice) {
                case 1:
                    gestionAnimaux();
                    break;
                case 2:
                    gestionAdoptions();
                    break;
                case 3:
                    gestionBenevoles();
                    break;
                case 4:
                    rechercheEtTri();
                    break;
                case 5:
                    shelter.displayStatistics();
                    break;
                case 6:
                    navigationCategoriesRecursive();
                    break;
                case 7:
                    calculCoutsMedicauxRecursive();
                    break;
                case 8:
                    verificationMedicaleRecursive();
                    break;
                case 0:
                    running = false;
                    shelter.saveAllData();
                    System.out.println("\nDonnées sauvegardées. Merci d'avoir utilisé notre système. Au revoir!");
                    break;
                default:
                    System.out.println("❌ Choix invalide. Veuillez réessayer.");
            }
        }
        scanner.close();
    }

    /**
     * Affiche le message de bienvenue
     */
    private void displayWelcome() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   SYSTÈME DE GESTION DU REFUGE ANIMALIER   ║");
        System.out.println("║              Refuge Animalier              ║");
        System.out.println("╚════════════════════════════════════════════╝\n");
    }

    /**
     * Affiche le menu principal
     */
    private void displayMainMenu() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║            MENU PRINCIPAL                  ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║  1. Gestion des animaux                    ║");
        System.out.println("║  2. Gestion des adoptions                  ║");
        System.out.println("║  3. Gestion des bénévoles                  ║");
        System.out.println("║  4. Recherche et tri                       ║");
        System.out.println("║  5. Statistiques du refuge                 ║");
        System.out.println("║  6. Navigation par catégories              ║");
        System.out.println("║  7. Calcul des coûts médicaux              ║");
        System.out.println("║  8. Vérification médicale                  ║");
        System.out.println("║  0. Quitter                                ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }

    /**
     * Menu de gestion des animaux
     */
    private void gestionAnimaux() {
        boolean back = false;
        while (!back) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║        GESTION DES ANIMAUX                 ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║  1. Afficher tous les animaux              ║");
            System.out.println("║  2. Afficher les animaux disponibles       ║");
            System.out.println("║  3. Afficher les animaux adoptés           ║");
            System.out.println("║  4. Ajouter un animal                      ║");
            System.out.println("║  5. Voir détails d'un animal               ║");
            System.out.println("║  6. Supprimer un animal                    ║");
            System.out.println("║  7. Modifier un animal                     ║");
            System.out.println("║  0. Retour                                 ║");
            System.out.println("╚════════════════════════════════════════════╝");

            int choice = getIntInput("Votre choix: ");
            switch (choice) {
                case 1:
                    shelter.displayAllAnimals();
                    break;
                case 2:
                    shelter.displayAvailableAnimals();
                    break;
                case 3:
                    shelter.displayAdoptedAnimals();
                    break;
                case 4:
                    ajouterAnimal();
                    break;
                case 5:
                    voirDetailsAnimal();
                    break;
                case 6:
                    supprimerAnimal();
                    break;
                case 7:
                    editerAnimal();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide.");
            }
        }
    }

    /**
     * Menu de gestion des adoptions
     */
    private void gestionAdoptions() {
        boolean back = false;
        while (!back) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║        GESTION DES ADOPTIONS               ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║  1. Adopter un animal                      ║");
            System.out.println("║  2. Retourner un animal                    ║");
            System.out.println("║  3. Afficher les formulaires d'adoption    ║");
            System.out.println("║  0. Retour                                 ║");
            System.out.println("╚════════════════════════════════════════════╝");

            int choice = getIntInput("Votre choix: ");
            switch (choice) {
                case 1:
                    adopterAnimal();
                    break;
                case 2:
                    retournerAnimal();
                    break;
                case 3:
                    shelter.displayAllAdoptionForms();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide.");
            }
        }
    }

    /**
     * Menu de gestion des bénévoles
     */
    private void gestionBenevoles() {
        boolean back = false;
        while (!back) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║        GESTION DES BÉNÉVOLES               ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║  1. Afficher tous les bénévoles            ║");
            System.out.println("║  2. Afficher les bénévoles actifs          ║");
            System.out.println("║  3. Ajouter un bénévole                    ║");
            System.out.println("║  4. Voir détails d'un bénévole             ║");
            System.out.println("║  5. Assigner une tâche                     ║");
            System.out.println("║  6. Trier par heures travaillées           ║");
            System.out.println("║  7. Trier par nom                          ║");
            System.out.println("║  0. Retour                                 ║");
            System.out.println("╚════════════════════════════════════════════╝");

            int choice = getIntInput("Votre choix: ");
            switch (choice) {
                case 1:
                    shelter.displayAllVolunteers();
                    break;
                case 2:
                    shelter.displayActiveVolunteers();
                    break;
                case 3:
                    ajouterBenevole();
                    break;
                case 4:
                    voirDetailsBenevole();
                    break;
                case 5:
                    assignerTache();
                    break;
                case 6:
                    trierBenevolesParHeures();
                    break;
                case 7:
                    trierBenevolesParNom();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide.");
            }
        }
    }

    /**
     * Menu de recherche et tri
     */
    private void rechercheEtTri() {
        boolean back = false;
        while (!back) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║        RECHERCHE ET TRI                    ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║  1. Rechercher par nom                     ║");
            System.out.println("║  2. Rechercher par type                    ║");
            System.out.println("║  3. Rechercher par tranche d'âge           ║");
            System.out.println("║  4. Trier par ID                           ║");
            System.out.println("║  5. Trier par âge                          ║");
            System.out.println("║  6. Trier par nom                          ║");
            System.out.println("║  7. Trier par date d'arrivée               ║");
            System.out.println("║  8. Trier par ID (Comparator)              ║");
            System.out.println("║  9. Trier par âge (Comparator)             ║");
            System.out.println("║ 10. Trier par nom (Comparator)             ║");
            System.out.println("║ 11. Trier par date (Comparator)            ║");
            System.out.println("║  0. Retour                                 ║");
            System.out.println("╚════════════════════════════════════════════╝");

            int choice = getIntInput("Votre choix: ");
            switch (choice) {
                case 1:
                    rechercherParNom();
                    break;
                case 2:
                    rechercherParType();
                    break;
                case 3:
                    rechercherParAge();
                    break;
                case 4:
                    trierParId();
                    break;
                case 5:
                    trierParAge();
                    break;
                case 6:
                    trierParNom();
                    break;
                case 7:
                    trierParDateArrivee();
                    break;
                case 8:
                    trierParIdComparator();
                    break;
                case 9:
                    trierParAgeComparator();
                    break;
                case 10:
                    trierParNomComparator();
                    break;
                case 11:
                    trierParDateComparator();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide.");
            }
        }
    }

    // ==================== MÉTHODES D'AJOUT ====================
    /**
     * Ajoute un animal
     */
    private void ajouterAnimal() {
        System.out.println("\nType d'animal: 1. Chien  2. Chat  3. Oiseau");
        int type = getIntInput("Votre choix: ");

        System.out.print("Nom: ");
        String name = scanner.nextLine();
        int age = getIntInput("Âge: ");
        int id = shelter.getNextAnimalId();

        try {
            switch (type) {
                case 1:
                    System.out.print("Race: ");
                    String breed = scanner.nextLine();
                    System.out.print("Dressé (oui/non): ");
                    boolean trained = scanner.nextLine().equalsIgnoreCase("oui");
                    Dog dog = new Dog(id, name, age, LocalDate.now(), breed, trained);
                    shelter.addAnimal(dog);
                    shelter.saveAllData();
                    break;
                case 2:
                    System.out.print("Couleur: ");
                    String color = scanner.nextLine();
                    System.out.print("Intérieur seulement (oui/non): ");
                    boolean indoor = scanner.nextLine().equalsIgnoreCase("oui");
                    Cat cat = new Cat(id, name, age, LocalDate.now(), color, indoor);
                    shelter.addAnimal(cat);
                    shelter.saveAllData();
                    break;
                case 3:
                    System.out.print("Espèce: ");
                    String species = scanner.nextLine();
                    System.out.print("Envergure des ailes (cm): ");
                    double wingspan = Double.parseDouble(scanner.nextLine());
                    Bird bird = new Bird(id, name, age, LocalDate.now(), wingspan, species);
                    shelter.addAnimal(bird);
                    shelter.saveAllData();
                    break;
                default:
                    System.out.println("❌ Type invalide.");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de l'ajout: " + e.getMessage());
        }
    }

    /**
     * Ajoute un bénévole
     */
    private void ajouterBenevole() {
        System.out.print("Nom: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Téléphone: ");
        String phone = scanner.nextLine();

        Volunteer volunteer = new Volunteer(shelter.getNextVolunteerId(), name, email, phone);
        shelter.addVolunteer(volunteer);
        shelter.saveAllData();
    }

    // ==================== MÉTHODES D'AFFICHAGE ====================
    /**
     * Affiche les détails d'un animal
     */
    private void voirDetailsAnimal() {
        int id = getIntInput("ID de l'animal: ");
        Animal animal = shelter.findAnimalById(id);
        if (animal != null) {
            animal.afficherDetails();
            animal.afficherHistoriqueMedical();
        } else {
            System.out.println("❌ Animal non trouvé.");
        }
    }

    /**
     * Affiche les détails d'un bénévole
     */
    private void voirDetailsBenevole() {
        int id = getIntInput("ID du bénévole: ");
        Volunteer volunteer = shelter.findVolunteerById(id);
        if (volunteer != null) {
            volunteer.afficherDetails();
        } else {
            System.out.println("❌ Bénévole non trouvé.");
        }
    }

    // ==================== MÉTHODES D'ADOPTION ====================
    /**
     * Adopte un animal
     */
    private void adopterAnimal() {
        shelter.displayAvailableAnimals();
        int animalId = getIntInput("ID de l'animal à adopter: ");

        System.out.print("Nom de l'adoptant: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Téléphone: ");
        String phone = scanner.nextLine();
        System.out.print("Adresse: ");
        String address = scanner.nextLine();

        try {
            AdoptionForm form = shelter.createAdoptionForm(animalId, name, email, phone, address);
            shelter.adoptAnimal(animalId, form);
            shelter.saveAllData();
        } catch (ShelterException e) {
            System.out.println("❌ Erreur: " + e.getMessage());
        }
    }

    /**
     * Retourne un animal
     */
    private void retournerAnimal() {
        shelter.displayAdoptedAnimals();
        int animalId = getIntInput("ID de l'animal à retourner: ");

        try {
            shelter.returnAnimal(animalId);
            shelter.saveAllData();
        } catch (ShelterException e) {
            System.out.println("❌ Erreur: " + e.getMessage());
        }
    }

    // ==================== MÉTHODES DE SUPPRESSION ====================
    /**
     * Supprime un animal
     */
    private void supprimerAnimal() {
        int id = getIntInput("ID de l'animal à supprimer: ");
        shelter.removeAnimal(id);
        shelter.saveAllData();
    }

    /**
     * Assigne une tâche à un bénévole
     */
    private void assignerTache() {
        int id = getIntInput("ID du bénévole: ");
        Volunteer volunteer = shelter.findVolunteerById(id);
        if (volunteer != null) {
            System.out.print("Tâche à assigner: ");
            String task = scanner.nextLine();
            volunteer.addTask(task);
            shelter.saveAllData();
        } else {
            System.out.println("❌ Bénévole non trouvé.");
        }
    }

    // ==================== MÉTHODES DE RECHERCHE ====================
    /**
     * Recherche par nom
     */
    private void rechercherParNom() {
        System.out.print("Nom à rechercher: ");
        String name = scanner.nextLine();
        Animal animal = SearchUtils.linearSearchByName(shelter.getAnimals(), name);
        if (animal != null) {
            animal.afficherDetails();
        } else {
            System.out.println("❌ Aucun animal trouvé avec ce nom.");
        }
    }

    /**
     * Recherche par type
     */
    private void rechercherParType() {
        System.out.print("Type (Dog/Cat/Bird): ");
        String type = scanner.nextLine();
        ArrayList<Animal> results = SearchUtils.searchByType(shelter.getAnimals(), type);
        SearchUtils.displaySearchResults(results, "Type: " + type);
    }

    /**
     * Recherche par tranche d'âge
     */
    private void rechercherParAge() {
        int minAge = getIntInput("Âge minimum: ");
        int maxAge = getIntInput("Âge maximum: ");
        ArrayList<Animal> results = SearchUtils.searchByAgeRange(shelter.getAnimals(), minAge, maxAge);
        SearchUtils.displaySearchResults(results, "Âge entre " + minAge + " et " + maxAge);
    }

    // ==================== MÉTHODES DE TRI ====================
    /**
     * Trie par ID
     */
    private void trierParId() {
        ArrayList<Animal> animals = new ArrayList<>(shelter.getAnimals());
        SortUtils.bubbleSortById(animals);
        SortUtils.displaySortedList(animals, "ID");
    }

    /**
     * Trie par âge
     */
    private void trierParAge() {
        ArrayList<Animal> animals = new ArrayList<>(shelter.getAnimals());
        SortUtils.insertionSortByAge(animals);
        SortUtils.displaySortedList(animals, "Âge");
    }

    /**
     * Trie par nom
     */
    private void trierParNom() {
        ArrayList<Animal> animals = new ArrayList<>(shelter.getAnimals());
        SortUtils.sortByName(animals);
        SortUtils.displaySortedList(animals, "Nom");
    }

    /**
     * Trie par date d'arrivée
     */
    private void trierParDateArrivee() {
        ArrayList<Animal> animals = new ArrayList<>(shelter.getAnimals());
        SortUtils.sortByArrivalDate(animals);
        SortUtils.displaySortedList(animals, "Date d'arrivée");
    }

    // ==================== FONCTIONNALITÉS RÉCURSIVES ====================
    /**
     * Navigation récursive par catégories d'animaux
     */
    private void navigationCategoriesRecursive() {
        int categoryLevel = 0;
        String selectedType = null;
        String selectedSubtype = null;

        while (true) {
            // Afficher le niveau actuel
            shelter.browseCategoriesRecursive(categoryLevel, selectedType, selectedSubtype);

            if (categoryLevel == 0) {
                System.out.print("Choisissez un type (1-3) ou 0 pour retour: ");
            } else if (categoryLevel == 1) {
                System.out.print("Choisissez une sous-catégorie (1-5) ou 0 pour retour: ");
            } else if (categoryLevel == 2) {
                System.out.print("Entrez l'ID d'un animal pour détails ou 0 pour retour: ");
            }

            int choice = getIntInput("");

            if (choice == 0) {
                if (categoryLevel == 0) {
                    break; // Retour au menu principal
                } else {
                    categoryLevel--; // Remonter d'un niveau
                    if (categoryLevel == 1)
                        selectedSubtype = null;
                    if (categoryLevel == 0)
                        selectedType = null;
                }
            } else {
                switch (categoryLevel) {
                    case 0: // Sélection du type
                        switch (choice) {
                            case 1:
                                selectedType = "Dogs";
                                categoryLevel = 1;
                                break;
                            case 2:
                                selectedType = "Cats";
                                categoryLevel = 1;
                                break;
                            case 3:
                                selectedType = "Birds";
                                categoryLevel = 1;
                                break;
                            default:
                                System.out.println("❌ Choix invalide.");
                        }
                        break;

                    case 1: // Sélection de la sous-catégorie
                        ArrayList<String> subcategories = getSubcategoriesForNavigation(selectedType);
                        if (choice >= 1 && choice <= subcategories.size()) {
                            selectedSubtype = subcategories.get(choice - 1);
                            categoryLevel = 2;
                        } else {
                            System.out.println("❌ Choix invalide.");
                        }
                        break;

                    case 2: // Affichage des détails d'un animal
                        Animal animal = shelter.findAnimalById(choice);
                        if (animal != null) {
                            animal.afficherDetails();
                            animal.afficherHistoriqueMedical();
                            System.out.println("\nAppuyez sur Entrée pour continuer...");
                            scanner.nextLine();
                        } else {
                            System.out.println("❌ Animal non trouvé.");
                        }
                        break;
                }
            }
        }
    }

    /**
     * Calcul récursif des coûts médicaux
     */
    private void calculCoutsMedicauxRecursive() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    CALCUL RÉCURSIF DES COÛTS MÉDICAUX");
        System.out.println("══════════════════════════════════════════");

        shelter.displayAllAnimals();
        int animalId = getIntInput("Entrez l'ID de l'animal pour calculer ses coûts médicaux: ");

        Animal animal = shelter.findAnimalById(animalId);
        if (animal != null) {
            System.out.println("\nCalcul des coûts pour " + animal.getName() + "...");
            animal.getMedicalRecord().displayMedicalCost();

            // Afficher le détail des calculs récursifs
            displayRecursiveCostCalculation(animal.getMedicalRecord());
        } else {
            System.out.println("❌ Animal non trouvé.");
        }

        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    /**
     * Affiche le détail du calcul récursif des coûts
     */
    private void displayRecursiveCostCalculation(MedicalRecord record) {
        System.out.println("\n--- DÉTAIL DU CALCUL RÉCURSIF ---");
        System.out.println("La méthode calculateTotalMedicalCostRecursive() parcourt :");
        System.out.println("1. La liste des vaccins un par un (récursion sur indexVaccin)");
        System.out.println("2. La liste des traitements un par un (récursion sur indexTraitement)");
        System.out.println("3. Accumule les coûts à chaque étape récursive");
        System.out.println("4. Retourne 0.0 quand toutes les listes sont parcourues (cas de base)");
        System.out.println("----------------------------------");
    }

    /**
     * Méthode utilitaire pour obtenir les sous-catégories (duplication nécessaire
     * pour MainMenu)
     */
    private ArrayList<String> getSubcategoriesForNavigation(String type) {
        ArrayList<String> subcategories = new ArrayList<>();
        switch (type) {
            case "Dogs":
                subcategories.add("Labrador");
                subcategories.add("Golden Retriever");
                subcategories.add("Berger Allemand");
                subcategories.add("Bulldog");
                subcategories.add("Autres races");
                break;
            case "Cats":
                subcategories.add("Chat de maison");
                subcategories.add("Chat de gouttière");
                subcategories.add("Chat persan");
                subcategories.add("Chat siamois");
                subcategories.add("Autres races");
                break;
            case "Birds":
                subcategories.add("Canari");
                subcategories.add("Perroquet");
                subcategories.add("Colombe");
                subcategories.add("Perruche");
                subcategories.add("Autres espèces");
                break;
        }
        return subcategories;
    }

    // ==================== UTILITAIRES ====================
    /**
     * Lit un entier saisi par l'utilisateur
     */
    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("❌ Veuillez entrer un nombre: ");
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne
        return input;
    }

    /**
     * Lit un nombre décimal saisi par l'utilisateur
     */
    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.print("❌ Veuillez entrer un nombre valide: ");
        }
        double input = scanner.nextDouble();
        scanner.nextLine(); // Consommer le retour à la ligne
        return input;
    }

    // ==================== ÉDITION D'ANIMAL ====================
    /**
     * Permet de modifier les informations d'un animal existant
     */
    private void editerAnimal() {
        shelter.displayAllAnimals();
        int id = getIntInput("ID de l'animal à modifier: ");
        Animal animal = shelter.findAnimalById(id);

        if (animal == null) {
            System.out.println("❌ Animal non trouvé.");
            return;
        }

        boolean back = false;
        while (!back) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║   MODIFIER: " + animal.getName() + " (ID: " + animal.getId() + ")");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║  1. Modifier le nom                        ║");
            System.out.println("║  2. Modifier l'âge                         ║");
            System.out.println("║  3. Ajouter un vaccin                      ║");
            System.out.println("║  4. Ajouter un traitement                  ║");
            System.out.println("║  5. Ajouter une note médicale              ║");
            System.out.println("║  6. Modifier attribut spécifique            ║");
            System.out.println("║  0. Retour                                 ║");
            System.out.println("╚════════════════════════════════════════════╝");

            int choice = getIntInput("Votre choix: ");
            switch (choice) {
                case 1:
                    System.out.print("Nouveau nom: ");
                    String newName = scanner.nextLine();
                    animal.setName(newName);
                    System.out.println("✓ Nom modifié en: " + newName);
                    shelter.saveAllData();
                    break;
                case 2:
                    int newAge = getIntInput("Nouvel âge: ");
                    animal.setAge(newAge);
                    System.out.println("✓ Âge modifié: " + newAge + " ans");
                    shelter.saveAllData();
                    break;
                case 3:
                    System.out.print("Nom du vaccin: ");
                    String vaccine = scanner.nextLine();
                    animal.getMedicalRecord().addVaccination(vaccine);
                    shelter.saveAllData();
                    break;
                case 4:
                    System.out.print("Nom du traitement: ");
                    String treatment = scanner.nextLine();
                    animal.getMedicalRecord().addTreatment(treatment);
                    shelter.saveAllData();
                    break;
                case 5:
                    System.out.print("Note médicale: ");
                    String note = scanner.nextLine();
                    animal.getMedicalRecord().appendNote(note);
                    shelter.saveAllData();
                    break;
                case 6:
                    editerAttributSpecifique(animal);
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("❌ Choix invalide.");
            }
        }
    }

    /**
     * Modifie les attributs spécifiques selon le type d'animal
     */
    private void editerAttributSpecifique(Animal animal) {
        if (animal instanceof Dog) {
            Dog dog = (Dog) animal;
            System.out.println("1. Modifier la race (actuelle: " + dog.getBreed() + ")");
            System.out.println("2. Modifier le dressage (actuel: " + (dog.isTrained() ? "OUI" : "NON") + ")");
            int choice = getIntInput("Votre choix: ");
            if (choice == 1) {
                System.out.print("Nouvelle race: ");
                dog.setBreed(scanner.nextLine());
                System.out.println("✓ Race modifiée.");
            } else if (choice == 2) {
                System.out.print("Dressé (oui/non): ");
                dog.setTrained(scanner.nextLine().equalsIgnoreCase("oui"));
                System.out.println("✓ Dressage modifié.");
            }
        } else if (animal instanceof Cat) {
            Cat cat = (Cat) animal;
            System.out.println("1. Modifier la couleur (actuelle: " + cat.getColor() + ")");
            System.out
                    .println("2. Modifier intérieur seulement (actuel: " + (cat.isIndoorOnly() ? "OUI" : "NON") + ")");
            int choice = getIntInput("Votre choix: ");
            if (choice == 1) {
                System.out.print("Nouvelle couleur: ");
                cat.setColor(scanner.nextLine());
                System.out.println("✓ Couleur modifiée.");
            } else if (choice == 2) {
                System.out.print("Intérieur seulement (oui/non): ");
                cat.setIndoorOnly(scanner.nextLine().equalsIgnoreCase("oui"));
                System.out.println("✓ Restriction d'habitat modifiée.");
            }
        } else if (animal instanceof Bird) {
            Bird bird = (Bird) animal;
            System.out.println("1. Modifier l'espèce (actuelle: " + bird.getSpecies() + ")");
            System.out.println("2. Modifier l'envergure (actuelle: " + bird.getWingSpan() + " cm)");
            int choice = getIntInput("Votre choix: ");
            if (choice == 1) {
                System.out.print("Nouvelle espèce: ");
                bird.setSpecies(scanner.nextLine());
                System.out.println("✓ Espèce modifiée.");
            } else if (choice == 2) {
                double newWingSpan = getDoubleInput("Nouvelle envergure (cm): ");
                bird.setWingSpan(newWingSpan);
                System.out.println("✓ Envergure modifiée.");
            }
        }
        shelter.saveAllData();
    }

    // ==================== VÉRIFICATION MÉDICALE RÉCURSIVE ====================
    /**
     * Vérifie récursivement quels animaux ont besoin d'un contrôle médical
     * et affiche les statistiques récursives
     */
    private void verificationMedicaleRecursive() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  VÉRIFICATION MÉDICALE RÉCURSIVE");
        System.out.println("══════════════════════════════════════════");

        // Statistiques récursives
        int totalAnimaux = shelter.countAnimalsRecursive(0);
        int totalAge = shelter.getTotalAgeRecursive(0);
        System.out.println("\n--- Statistiques récursives ---");
        System.out.println("Nombre total d'animaux (récursif): " + totalAnimaux);
        System.out.println("Somme des âges (récursif): " + totalAge + " ans");
        if (totalAnimaux > 0) {
            System.out.printf("Âge moyen (récursif): %.1f ans\n", (double) totalAge / totalAnimaux);
        }

        // Vérification des contrôles médicaux
        ArrayList<Animal> needCheckup = shelter.checkAnimalsNeedCheckupRecursive(0);
        System.out.println("\n--- Animaux nécessitant un contrôle médical ---");
        System.out.println("(Dernière visite > 6 mois)");
        if (needCheckup.isEmpty()) {
            System.out.println("✓ Tous les animaux sont à jour sur leurs contrôles médicaux!");
        } else {
            System.out.println("⚠ " + needCheckup.size() + " animaux ont besoin d'un contrôle:");
            for (Animal animal : needCheckup) {
                System.out.println("  - " + animal.getName() + " (ID: " + animal.getId()
                        + ") | Dernière visite: " + animal.getMedicalRecord().getLastCheckup());
            }
        }

        System.out.println("\n--- Détail de la récursion ---");
        System.out.println("countAnimalsRecursive(0) parcourt la liste index par index:");
        System.out.println("  Cas de base: index >= taille → retourne 0");
        System.out.println("  Récurrence: 1 + countAnimalsRecursive(index + 1)");
        System.out.println("getTotalAgeRecursive(0) accumule les âges:");
        System.out.println("  Cas de base: index >= taille → retourne 0");
        System.out.println("  Récurrence: age[index] + getTotalAgeRecursive(index + 1)");
        System.out.println("checkAnimalsNeedCheckupRecursive(0) construit la liste:");
        System.out.println("  Cas de base: index >= taille → retourne liste vide");
        System.out.println("  Récurrence: vérifier animal + résultat sous-liste");
        System.out.println("══════════════════════════════════════════");

        System.out.println("\nAppuyez sur Entrée pour continuer...");
        scanner.nextLine();
    }

    // ==================== TRI BÉNÉVOLES ====================
    /**
     * Trie les bénévoles par heures travaillées (utilise Comparable)
     */
    private void trierBenevolesParHeures() {
        ArrayList<Volunteer> volunteers = new ArrayList<>(shelter.getVolunteers());
        SortUtils.sortVolunteersByHours(volunteers);
        SortUtils.displaySortedVolunteerList(volunteers, "Heures travaillées");
    }

    /**
     * Trie les bénévoles par nom (utilise Comparator)
     */
    private void trierBenevolesParNom() {
        ArrayList<Volunteer> volunteers = new ArrayList<>(shelter.getVolunteers());
        SortUtils.sortVolunteersByName(volunteers);
        SortUtils.displaySortedVolunteerList(volunteers, "Nom");
    }

    // ==================== TRI AVEC COMPARATOR ====================
    /**
     * Trie par ID avec Collections.sort et Comparator
     */
    private void trierParIdComparator() {
        ArrayList<Animal> animals = new ArrayList<>(shelter.getAnimals());
        SortUtils.sortByIdWithComparator(animals);
        SortUtils.displaySortedList(animals, "ID (Comparator)");
    }

    /**
     * Trie par âge avec Collections.sort et Comparator
     */
    private void trierParAgeComparator() {
        ArrayList<Animal> animals = new ArrayList<>(shelter.getAnimals());
        SortUtils.sortByAgeWithComparator(animals);
        SortUtils.displaySortedList(animals, "Âge (Comparator)");
    }

    /**
     * Trie par nom avec Collections.sort et Comparator
     */
    private void trierParNomComparator() {
        ArrayList<Animal> animals = new ArrayList<>(shelter.getAnimals());
        SortUtils.sortByNameWithComparator(animals);
        SortUtils.displaySortedList(animals, "Nom (Comparator)");
    }

    /**
     * Trie par date d'arrivée avec Collections.sort et Comparator
     */
    private void trierParDateComparator() {
        ArrayList<Animal> animals = new ArrayList<>(shelter.getAnimals());
        SortUtils.sortByArrivalDateWithComparator(animals);
        SortUtils.displaySortedList(animals, "Date d'arrivée (Comparator)");
    }
}
