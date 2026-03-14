package model;

import java.time.LocalDate;

/**
 * Classe Cat (Chat)
 * 
 * Hérite de la classe abstraite Animal.
 * Représente un chat avec des attributs spécifiques : couleur et restrictions
 * d'habitat.
 * Implémente la méthode abstraite afficherDetails() de la classe parent.
 */
public class Cat extends Animal {
    private static final long serialVersionUID = 1L;

    // Attributs spécifiques au chat
    private String color; // Couleur du chat (noir, blanc, gris, roux, etc.)
    private boolean indoorOnly; // Si le chat ne vit que en intérieur (true) ou peut aussi sortir (false)

    /**
     * Constructeur avec paramètres (données du chat)
     * 
     * @param id          Identifiant unique
     * @param name        Nom du chat
     * @param age         Âge du chat
     * @param arrivalDate Date d'arrivée au refuge
     * @param color       Couleur du chat
     * @param indoorOnly  Restriction d'habitat (true = intérieur seulement, false =
     *                    intérieur/extérieur possible)
     */
    public Cat(int id, String name, int age, LocalDate arrivalDate,
            String color, boolean indoorOnly) {
        super(id, name, age, arrivalDate); // Appel du constructeur de la classe parent
        this.color = color;
        this.indoorOnly = indoorOnly;
    }

    // ==================== GETTERS ====================
    public String getColor() {
        return color;
    }

    public boolean isIndoorOnly() {
        return indoorOnly;
    }

    // ==================== SETTERS ====================
    public void setColor(String color) {
        this.color = color;
    }

    public void setIndoorOnly(boolean indoorOnly) {
        this.indoorOnly = indoorOnly;
    }

    // ==================== IMPLÉMENTATION DE LA MÉTHODE ABSTRAITE
    // ====================
    /**
     * Affiche les détails spécifiques du chat
     * Impression des attributs propres au chat (couleur, restrictions)
     */
    @Override
    public void afficherDetails() {
        System.out.println("\n========== DÉTAILS DU CHAT ==========");
        System.out.println("ID: " + getId());
        System.out.println("Nom: " + getName());
        System.out.println("Âge: " + getAge() + " ans");
        System.out.println("Date d'arrivée: " + getArrivalDate());
        System.out.println("Couleur: " + color);
        System.out.println("Intérieur seulement: " + (indoorOnly ? "OUI" : "NON"));
        System.out.println("Adopté: " + (isAdopted() ? "OUI" : "NON"));
        System.out.println("====================================\n");
    }

    // ==================== MÉTHODES SPÉCIFIQUES AU CHAT ====================
    /**
     * Affiche un miaulement du chat
     * Méthode ludique spécifique aux chats
     */
    public void meow() {
        System.out.println(getName() + " miaule : Meow Meow!");
    }

    /**
     * Change les restrictions d'habitat du chat
     * 
     * @param indoorOnly True si le chat doit rester à l'intérieur
     */
    public void setHabitatRestriction(boolean indoorOnly) {
        this.indoorOnly = indoorOnly;
        System.out.println(getName() + " peut désormais " +
                (indoorOnly ? "seulement vivre en intérieur" : "vivre à l'intérieur ou l'extérieur"));
    }

    /**
     * Information sur l'aptitude à l'adoption
     * 
     * @return True si le chat peut être adopté (pas trop âgé, pas encore adopté)
     */
    public boolean isAdoptionEligible() {
        // Un chat est adoptable s'il a moins de 18 ans
        return getAge() < 18 && !isAdopted();
    }

    /**
     * Donne des conseils pour l'adoption d'un chat intérieur
     * 
     * @return Message de conseil selon le type de chat
     */
    public String getAdoptionAdvice() {
        if (indoorOnly) {
            return "Ce chat doit absolument rester en intérieur. " +
                    "Assurez-vous d'avoir un appartement ou une maison adaptée.";
        } else {
            return "Ce chat peut vivre à l'intérieur et à l'extérieur. " +
                    "Il a besoin d'espace et de liberté de mouvement.";
        }
    }
}
