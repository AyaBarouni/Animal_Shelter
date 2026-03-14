package model;

import java.io.Serializable;
import java.time.LocalDate;
import interfaces.Adoptable;
import exceptions.ShelterException;

/**
 * Classe abstraite Animal
 * 
 * Représente un animal générique dans le refuge avec des attributs communs.
 * Cette classe définit les propriétés et comportements de base que tous les
 * animaux
 * doivent avoir. Elle est abstraite car on ne peut pas instantier directement
 * un "animal"
 * générique - on doit créer des animaux spécifiques (chien, chat, oiseau).
 */
public abstract class Animal implements Serializable, Adoptable {
    private static final long serialVersionUID = 1L;

    // Attributs communs à tous les animaux
    private int id; // Identifiant unique
    private String name; // Nom de l'animal
    private int age; // Âge en années
    private LocalDate arrivalDate; // Date d'arrivée au refuge
    private boolean adopted; // Statut d'adoption (true = adopté, false = disponible)
    private MedicalRecord medicalRecord; // Composition : dossier médical

    /**
     * Constructeur avec paramètres
     * 
     * @param id          Identifiant unique de l'animal
     * @param name        Nom de l'animal
     * @param age         Âge de l'animal en années
     * @param arrivalDate Date d'arrivée au refuge
     */
    public Animal(int id, String name, int age, LocalDate arrivalDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.arrivalDate = arrivalDate;
        this.adopted = false; // Par défaut, l'animal n'est pas adopté
        this.medicalRecord = new MedicalRecord(id); // Créer un dossier médical vide
    }

    // ==================== GETTERS ====================
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public boolean isAdopted() {
        return adopted;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    // ==================== SETTERS ====================
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    // ==================== MÉTHODES ABSTRAITES ====================
    /**
     * Méthode abstraite que chaque sous-classe DOIT implémenter
     * Affiche les détails spécifiques de l'animal (race pour Dog, couleur pour Cat,
     * etc.)
     */
    public abstract void afficherDetails();

    // ==================== MÉTHODES COMMUNES ====================
    /**
     * Retourne une représentation textuelle générale de l'animal
     */
    @Override
    public String toString() {
        return "ID: " + id +
                " | Nom: " + name +
                " | Âge: " + age +
                " | Arrivée: " + arrivalDate +
                " | Adopté: " + (adopted ? "OUI" : "NON");
    }

    /**
     * Affiche l'historique médical de l'animal
     */
    public void afficherHistoriqueMedical() {
        System.out.println("\n=== Historique Médical de " + name + " ===");
        medicalRecord.afficherHistorique();
    }

    /**
     * Affiche le statut actuel de l'animal
     */
    public void afficherStatut() {
        System.out.println(name + " est actuellement " +
                (adopted ? "ADOPTÉ" : "DISPONIBLE À L'ADOPTION"));
    }

    // ==================== IMPLÉMENTATION DE L'INTERFACE ADOPTABLE
    // ====================
    /**
     * Adopte l'animal
     * 
     * @throws ShelterException Si l'animal est déjà adopté
     */
    @Override
    public void adopt() throws ShelterException {
        if (adopted) {
            throw new ShelterException("L'animal " + name + " (ID: " + id + ") est déjà adopté.");
        }
        adopted = true;
        System.out.println("✓ " + name + " a été adopté avec succès!");
    }

    /**
     * Retourne l'animal au refuge
     * 
     * @throws ShelterException Si l'animal n'est pas actuellement adopté
     */
    @Override
    public void returnToShelter() throws ShelterException {
        if (!adopted) {
            throw new ShelterException("L'animal " + name + " (ID: " + id + ") n'est pas actuellement adopté.");
        }
        adopted = false;
        System.out.println("✓ " + name + " est retourné au refuge.");
    }
}
