package model;

import java.time.LocalDate;

/**
 * Classe Bird (Oiseau)
 * 
 * Hérite de la classe abstraite Animal.
 * Représente un oiseau avec des attributs spécifiques : envergure des ailes et
 * espèce.
 * Implémente la méthode abstraite afficherDetails() de la classe parent.
 */
public class Bird extends Animal {
    private static final long serialVersionUID = 1L;

    // Attributs spécifiques à l'oiseau
    private double wingSpan; // Envergure des ailes en centimètres
    private String species; // Espèce d'oiseau (Perroquet, Colombe, Canari, etc.)

    /**
     * Constructeur avec paramètres (données de l'oiseau)
     * 
     * @param id          Identifiant unique
     * @param name        Nom de l'oiseau
     * @param age         Âge de l'oiseau
     * @param arrivalDate Date d'arrivée au refuge
     * @param wingSpan    Envergure des ailes en centimètres
     * @param species     Espèce d'oiseau
     */
    public Bird(int id, String name, int age, LocalDate arrivalDate,
            double wingSpan, String species) {
        super(id, name, age, arrivalDate); // Appel du constructeur de la classe parent
        this.wingSpan = wingSpan;
        this.species = species;
    }

    // ==================== GETTERS ====================
    public double getWingSpan() {
        return wingSpan;
    }

    public String getSpecies() {
        return species;
    }

    // ==================== SETTERS ====================
    public void setWingSpan(double wingSpan) {
        this.wingSpan = wingSpan;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    // ==================== IMPLÉMENTATION DE LA MÉTHODE ABSTRAITE
    // ====================
    /**
     * Affiche les détails spécifiques de l'oiseau
     * Impression des attributs propres à l'oiseau (espèce, envergure)
     */
    @Override
    public void afficherDetails() {
        System.out.println("\n========== DÉTAILS DE L'OISEAU ==========");
        System.out.println("ID: " + getId());
        System.out.println("Nom: " + getName());
        System.out.println("Âge: " + getAge() + " ans");
        System.out.println("Date d'arrivée: " + getArrivalDate());
        System.out.println("Espèce: " + species);
        System.out.println("Envergure des ailes: " + wingSpan + " cm");
        System.out.println("Adopté: " + (isAdopted() ? "OUI" : "NON"));
        System.out.println("========================================\n");
    }

    // ==================== MÉTHODES SPÉCIFIQUES À L'OISEAU ====================
    /**
     * Affiche un chant ou un cri de l'oiseau
     * Méthode ludique spécifique aux oiseaux
     */
    public void sing() {
        System.out.println(getName() + " chante : Tweet Tweet!");
    }

    /**
     * Affiche une animation de vol de l'oiseau
     */
    public void fly() {
        System.out.println(getName() + " s'envole gracieusement avec une envergure de " + wingSpan + " cm!");
    }

    /**
     * Classifie la taille de l'oiseau selon l'envergure
     * 
     * @return Catégorie de taille (Petit, Moyen, Grand)
     */
    public String getSize() {
        if (wingSpan < 20) {
            return "Petit";
        } else if (wingSpan < 50) {
            return "Moyen";
        } else {
            return "Grand";
        }
    }

    /**
     * Information sur l'aptitude à l'adoption
     * 
     * @return True si l'oiseau peut être adopté (pas trop âgé, pas adopté)
     */
    public boolean isAdoptionEligible() {
        // Un oiseau est adoptable s'il a moins de 20 ans
        return getAge() < 20 && !isAdopted();
    }

    /**
     * Donne des conseils spécifiques pour l'adoption d'un oiseau
     * 
     * @return Messages de conseil selon l'espèce
     */
    public String getAdoptionAdvice() {
        return "Les oiseaux de l'espèce " + species + " ont besoin d'une bonne cage, " +
                "d'une alimentation variée et d'une stimulation mentale quotidienne. " +
                "Avec une envergure de " + wingSpan + " cm, cet oiseau a besoin d'espace.";
    }
}
