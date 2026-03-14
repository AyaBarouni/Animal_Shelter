package model;

import java.time.LocalDate;

/**
 * Classe Dog (Chien)
 * 
 * Hérite de la classe abstraite Animal.
 * Représente un chien avec des attributs spécifiques : race et statut de
 * dressage.
 * Implémente la méthode abstraite afficherDetails() de la classe parent.
 */
public class Dog extends Animal {
    private static final long serialVersionUID = 1L;

    // Attributs spécifiques au chien
    private String breed; // Race du chien (Golden Retriever, Labrador, etc.)
    private boolean isTrained; // Si le chien est dressé ou pas

    /**
     * Constructeur avec paramètres (données du chien)
     * 
     * @param id          Identifiant unique
     * @param name        Nom du chien
     * @param age         Âge du chien
     * @param arrivalDate Date d'arrivée au refuge
     * @param breed       Race du chien
     * @param isTrained   Statut de dressage (true = dressé, false = non dressé)
     */
    public Dog(int id, String name, int age, LocalDate arrivalDate,
            String breed, boolean isTrained) {
        super(id, name, age, arrivalDate); // Appel du constructeur de la classe parent
        this.breed = breed;
        this.isTrained = isTrained;
    }

    // ==================== GETTERS ====================
    public String getBreed() {
        return breed;
    }

    public boolean isTrained() {
        return isTrained;
    }

    // ==================== SETTERS ====================
    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setTrained(boolean trained) {
        isTrained = trained;
    }

    // ==================== IMPLÉMENTATION DE LA MÉTHODE ABSTRAITE
    // ====================
    /**
     * Affiche les détails spécifiques du chien
     * Impression des attributs propres au chien (race, dressage)
     */
    @Override
    public void afficherDetails() {
        System.out.println("\n========== DÉTAILS DU CHIEN ==========");
        System.out.println("ID: " + getId());
        System.out.println("Nom: " + getName());
        System.out.println("Âge: " + getAge() + " ans");
        System.out.println("Date d'arrivée: " + getArrivalDate());
        System.out.println("Race: " + breed);
        System.out.println("Dressé: " + (isTrained ? "OUI" : "NON"));
        System.out.println("Adopté: " + (isAdopted() ? "OUI" : "NON"));
        System.out.println("=======================================\n");
    }

    // ==================== MÉTHODES SPÉCIFIQUES AU CHIEN ====================
    /**
     * Affiche un aboiement du chien
     * Méthode ludique spécifique aux chiens
     */
    public void bark() {
        System.out.println(getName() + " aboie : Woof Woof!");
    }

    /**
     * Met à jour le statut de dressage du chien
     * 
     * @param trained True si le chien a été dressé
     */
    public void trainDog(boolean trained) {
        this.isTrained = trained;
        System.out.println(getName() + " a été " + (trained ? "dressé" : "n'a pas pu être dressé"));
    }

    /**
     * Information sur l'aptitude à l'adoption
     * 
     * @return True si le chien peut être adopté (ni trop âgé ni malade gravément)
     */
    public boolean isAdoptionEligible() {
        // Un chien est adoptable s'il a moins de 15 ans
        return getAge() < 15 && !isAdopted();
    }
}
