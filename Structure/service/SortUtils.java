package service;

import model.Animal;
import model.Volunteer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Classe SortUtils
 * 
 * Utilitaires de tri pour les animaux.
 * Contient les algorithmes de tri: tri à bulles, sélection, insertion, etc.
 */
public class SortUtils {

    /**
     * Tri à bulles par ID
     * 
     * @param animals Liste des animaux à trier
     */
    public static void bubbleSortById(ArrayList<Animal> animals) {
        int n = animals.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (animals.get(j).getId() > animals.get(j + 1).getId()) {
                    // Échange
                    Animal temp = animals.get(j);
                    animals.set(j, animals.get(j + 1));
                    animals.set(j + 1, temp);
                }
            }
        }
        System.out.println("✓ Tri à bulles par ID effectué.");
    }

    /**
     * Tri à bulles par âge
     * 
     * @param animals Liste des animaux à trier
     */
    public static void bubbleSortByAge(ArrayList<Animal> animals) {
        int n = animals.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (animals.get(j).getAge() > animals.get(j + 1).getAge()) {
                    // Échange
                    Animal temp = animals.get(j);
                    animals.set(j, animals.get(j + 1));
                    animals.set(j + 1, temp);
                }
            }
        }
        System.out.println("✓ Tri à bulles par âge effectué.");
    }

    /**
     * Tri par sélection par ID
     * 
     * @param animals Liste des animaux à trier
     */
    public static void selectionSortById(ArrayList<Animal> animals) {
        int n = animals.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (animals.get(j).getId() < animals.get(minIndex).getId()) {
                    minIndex = j;
                }
            }
            // Échange
            Animal temp = animals.get(minIndex);
            animals.set(minIndex, animals.get(i));
            animals.set(i, temp);
        }
        System.out.println("✓ Tri par sélection par ID effectué.");
    }

    /**
     * Tri par sélection par âge
     * 
     * @param animals Liste des animaux à trier
     */
    public static void selectionSortByAge(ArrayList<Animal> animals) {
        int n = animals.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (animals.get(j).getAge() < animals.get(minIndex).getAge()) {
                    minIndex = j;
                }
            }
            // Échange
            Animal temp = animals.get(minIndex);
            animals.set(minIndex, animals.get(i));
            animals.set(i, temp);
        }
        System.out.println("✓ Tri par sélection par âge effectué.");
    }

    /**
     * Tri par insertion par ID
     * 
     * @param animals Liste des animaux à trier
     */
    public static void insertionSortById(ArrayList<Animal> animals) {
        int n = animals.size();
        for (int i = 1; i < n; i++) {
            Animal key = animals.get(i);
            int j = i - 1;
            while (j >= 0 && animals.get(j).getId() > key.getId()) {
                animals.set(j + 1, animals.get(j));
                j--;
            }
            animals.set(j + 1, key);
        }
        System.out.println("✓ Tri par insertion par ID effectué.");
    }

    /**
     * Tri par insertion par âge
     * 
     * @param animals Liste des animaux à trier
     */
    public static void insertionSortByAge(ArrayList<Animal> animals) {
        int n = animals.size();
        for (int i = 1; i < n; i++) {
            Animal key = animals.get(i);
            int j = i - 1;
            while (j >= 0 && animals.get(j).getAge() > key.getAge()) {
                animals.set(j + 1, animals.get(j));
                j--;
            }
            animals.set(j + 1, key);
        }
        System.out.println("✓ Tri par insertion par âge effectué.");
    }

    /**
     * Tri par nom (ordre alphabétique) en utilisant le tri à bulles
     * 
     * @param animals Liste des animaux à trier
     */
    public static void sortByName(ArrayList<Animal> animals) {
        int n = animals.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (animals.get(j).getName().compareToIgnoreCase(animals.get(j + 1).getName()) > 0) {
                    // Échange
                    Animal temp = animals.get(j);
                    animals.set(j, animals.get(j + 1));
                    animals.set(j + 1, temp);
                }
            }
        }
        System.out.println("✓ Tri par nom effectué.");
    }

    /**
     * Tri par date d'arrivée (du plus ancien au plus récent)
     * 
     * @param animals Liste des animaux à trier
     */
    public static void sortByArrivalDate(ArrayList<Animal> animals) {
        int n = animals.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (animals.get(j).getArrivalDate().isAfter(animals.get(j + 1).getArrivalDate())) {
                    // Échange
                    Animal temp = animals.get(j);
                    animals.set(j, animals.get(j + 1));
                    animals.set(j + 1, temp);
                }
            }
        }
        System.out.println("✓ Tri par date d'arrivée effectué.");
    }

    /**
     * Affiche la liste triée des animaux
     * 
     * @param animals  Liste des animaux
     * @param sortType Type de tri effectué
     */
    public static void displaySortedList(ArrayList<Animal> animals, String sortType) {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    LISTE TRIÉE PAR: " + sortType);
        System.out.println("══════════════════════════════════════════");
        for (int i = 0; i < animals.size(); i++) {
            System.out.println((i + 1) + ". " + animals.get(i).toString());
        }
        System.out.println("══════════════════════════════════════════\n");
    }

    // ==================== TRI AVEC COMPARATOR (Collections.sort)
    // ====================
    /**
     * Tri par ID utilisant Collections.sort avec Comparator
     * Démontre l'utilisation des interfaces standards Java
     *
     * @param animals Liste des animaux à trier
     */
    public static void sortByIdWithComparator(ArrayList<Animal> animals) {
        Collections.sort(animals, Comparator.comparingInt(Animal::getId));
        System.out.println("✓ Tri par ID (Collections.sort + Comparator) effectué.");
    }

    /**
     * Tri par âge utilisant Collections.sort avec Comparator
     *
     * @param animals Liste des animaux à trier
     */
    public static void sortByAgeWithComparator(ArrayList<Animal> animals) {
        Collections.sort(animals, Comparator.comparingInt(Animal::getAge));
        System.out.println("✓ Tri par âge (Collections.sort + Comparator) effectué.");
    }

    /**
     * Tri par nom utilisant Collections.sort avec Comparator
     *
     * @param animals Liste des animaux à trier
     */
    public static void sortByNameWithComparator(ArrayList<Animal> animals) {
        Collections.sort(animals, Comparator.comparing(Animal::getName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("✓ Tri par nom (Collections.sort + Comparator) effectué.");
    }

    /**
     * Tri par date d'arrivée utilisant Collections.sort avec Comparator
     *
     * @param animals Liste des animaux à trier
     */
    public static void sortByArrivalDateWithComparator(ArrayList<Animal> animals) {
        Collections.sort(animals, Comparator.comparing(Animal::getArrivalDate));
        System.out.println("✓ Tri par date d'arrivée (Collections.sort + Comparator) effectué.");
    }

    // ==================== TRI DES BÉNÉVOLES ====================
    /**
     * Tri des bénévoles par heures travaillées (utilise Comparable implem. dans
     * Volunteer)
     *
     * @param volunteers Liste des bénévoles à trier
     */
    public static void sortVolunteersByHours(ArrayList<Volunteer> volunteers) {
        Collections.sort(volunteers);
        System.out.println("✓ Tri des bénévoles par heures travaillées effectué.");
    }

    /**
     * Tri des bénévoles par nom (utilise Comparator)
     *
     * @param volunteers Liste des bénévoles à trier
     */
    public static void sortVolunteersByName(ArrayList<Volunteer> volunteers) {
        Collections.sort(volunteers, Comparator.comparing(Volunteer::getName, String.CASE_INSENSITIVE_ORDER));
        System.out.println("✓ Tri des bénévoles par nom effectué.");
    }

    /**
     * Affiche la liste triée des bénévoles
     *
     * @param volunteers Liste des bénévoles
     * @param sortType   Type de tri effectué
     */
    public static void displaySortedVolunteerList(ArrayList<Volunteer> volunteers, String sortType) {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    BÉNÉVOLES TRIÉS PAR: " + sortType);
        System.out.println("══════════════════════════════════════════");
        for (int i = 0; i < volunteers.size(); i++) {
            volunteers.get(i).afficherResume();
        }
        System.out.println("══════════════════════════════════════════\n");
    }
}
