package service;

import model.Animal;
import java.util.ArrayList;

/**
 * Classe SearchUtils
 * 
 * Utilitaires de recherche pour les animaux.
 * Contient les méthodes de recherche linéaire et binaire.
 */
public class SearchUtils {

    /**
     * Recherche linéaire d'un animal par nom
     * 
     * @param animals Liste des animaux
     * @param name    Nom à rechercher
     * @return Animal trouvé ou null
     */
    public static Animal linearSearchByName(ArrayList<Animal> animals, String name) {
        for (Animal animal : animals) {
            if (animal.getName().equalsIgnoreCase(name)) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Recherche linéaire d'un animal par ID
     * 
     * @param animals  Liste des animaux
     * @param animalId ID à rechercher
     * @return Animal trouvé ou null
     */
    public static Animal linearSearchById(ArrayList<Animal> animals, int animalId) {
        for (Animal animal : animals) {
            if (animal.getId() == animalId) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Recherche tous les animaux d'un type spécifique
     * 
     * @param animals Liste des animaux
     * @param type    Type d'animal ("Dog", "Cat", "Bird")
     * @return Liste des animaux du type spécifié
     */
    public static ArrayList<Animal> searchByType(ArrayList<Animal> animals, String type) {
        ArrayList<Animal> result = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equalsIgnoreCase(type)) {
                result.add(animal);
            }
        }
        return result;
    }

    /**
     * Recherche binaire d'un animal par ID (nécessite une liste triée)
     * 
     * @param animals Liste triée des animaux par ID
     * @param id      ID à rechercher
     * @return Animal trouvé ou null
     */
    public static Animal binarySearchById(ArrayList<Animal> animals, int id) {
        int left = 0;
        int right = animals.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Animal midAnimal = animals.get(mid);

            if (midAnimal.getId() == id) {
                return midAnimal;
            } else if (midAnimal.getId() < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    /**
     * Recherche tous les animaux disponibles à l'adoption
     * 
     * @param animals Liste des animaux
     * @return Liste des animaux disponibles
     */
    public static ArrayList<Animal> searchAvailableAnimals(ArrayList<Animal> animals) {
        ArrayList<Animal> available = new ArrayList<>();
        for (Animal animal : animals) {
            if (!animal.isAdopted()) {
                available.add(animal);
            }
        }
        return available;
    }

    /**
     * Recherche tous les animaux adoptés
     * 
     * @param animals Liste des animaux
     * @return Liste des animaux adoptés
     */
    public static ArrayList<Animal> searchAdoptedAnimals(ArrayList<Animal> animals) {
        ArrayList<Animal> adopted = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.isAdopted()) {
                adopted.add(animal);
            }
        }
        return adopted;
    }

    /**
     * Recherche des animaux par tranche d'âge
     * 
     * @param animals Liste des animaux
     * @param minAge  Âge minimum
     * @param maxAge  Âge maximum
     * @return Liste des animaux dans la tranche d'âge
     */
    public static ArrayList<Animal> searchByAgeRange(ArrayList<Animal> animals, int minAge, int maxAge) {
        ArrayList<Animal> result = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.getAge() >= minAge && animal.getAge() <= maxAge) {
                result.add(animal);
            }
        }
        return result;
    }

    /**
     * Affiche les résultats de recherche
     * 
     * @param results Liste des animaux trouvés
     * @param query   Critère de recherche
     */
    public static void displaySearchResults(ArrayList<Animal> results, String query) {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    RÉSULTATS DE RECHERCHE: " + query);
        System.out.println("══════════════════════════════════════════");
        if (results.isEmpty()) {
            System.out.println("Aucun résultat trouvé.");
        } else {
            System.out.println("Nombre de résultats: " + results.size());
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).toString());
            }
        }
        System.out.println("══════════════════════════════════════════\n");
    }
}
