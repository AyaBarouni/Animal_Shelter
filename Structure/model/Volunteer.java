package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe Volunteer (Bénévole)
 * 
 * Représente un bénévole du refuge avec ses horaires et tâches assignées.
 * Utilise la COMPOSITION avec ArrayList pour gérer les horaires et tâches.
 */
public class Volunteer implements Serializable, Comparable<Volunteer> {
    private static final long serialVersionUID = 1L;

    // Attributs
    private int volunteerId; // ID unique du bénévole
    private String name; // Nom complet du bénévole
    private String email; // Email de contact
    private String phone; // Numéro de téléphone
    private ArrayList<String> schedule; // Liste des créneaux horaires
    private ArrayList<String> assignedTasks; // Liste des tâches assignées
    private int hoursWorked; // Nombre d'heures travaillées
    private boolean isActive; // Statut actif/inactif

    /**
     * Constructeur
     * 
     * @param volunteerId ID unique du bénévole
     * @param name        Nom du bénévole
     * @param email       Email du bénévole
     * @param phone       Téléphone du bénévole
     */
    public Volunteer(int volunteerId, String name, String email, String phone) {
        this.volunteerId = volunteerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.schedule = new ArrayList<>(); // Initialiser la liste des horaires
        this.assignedTasks = new ArrayList<>(); // Initialiser la liste des tâches
        this.hoursWorked = 0; // Heures initiales à 0
        this.isActive = true; // Actif par défaut
    }

    // ==================== GETTERS ====================
    public int getVolunteerId() {
        return volunteerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public ArrayList<String> getSchedule() {
        return schedule;
    }

    public ArrayList<String> getAssignedTasks() {
        return assignedTasks;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public boolean isActive() {
        return isActive;
    }

    // ==================== SETTERS ====================
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    // ==================== GESTION DE L'HORAIRE ====================
    /**
     * Ajoute un créneau horaire à l'horaire du bénévole
     * Format suggéré: "Monday 9:00-12:00" ou "Lundi 9h-12h"
     * 
     * @param timeSlot Créneau horaire à ajouter
     */
    public void addSchedule(String timeSlot) {
        if (!schedule.contains(timeSlot)) {
            schedule.add(timeSlot);
            System.out.println("✓ Créneau '" + timeSlot + "' ajouté à l'horaire de " + name);
        } else {
            System.out.println("⚠ Ce créneau existe déjà pour " + name);
        }
    }

    /**
     * Retire un créneau horaire de l'horaire du bénévole
     * 
     * @param timeSlot Créneau horaire à retirer
     * @return True si retiré, false sinon
     */
    public boolean removeSchedule(String timeSlot) {
        if (schedule.remove(timeSlot)) {
            System.out.println("✓ Créneau '" + timeSlot + "' retiré de l'horaire de " + name);
            return true;
        } else {
            System.out.println("✗ Créneau non trouvé.");
            return false;
        }
    }

    /**
     * Affiche l'horaire complet du bénévole
     */
    public void afficherSchedule() {
        System.out.println("\n--- Horaire de " + name + " ---");
        if (schedule.isEmpty()) {
            System.out.println("Aucun créneau assigné.");
        } else {
            for (int i = 0; i < schedule.size(); i++) {
                System.out.println((i + 1) + ". " + schedule.get(i));
            }
        }
    }

    /**
     * Vérifie si le bénévole est disponible à un certain créneau
     * 
     * @param timeSlot Créneau à vérifier
     * @return True si disponible, false sinon
     */
    public boolean isAvailable(String timeSlot) {
        return schedule.contains(timeSlot);
    }

    // ==================== GESTION DES TÂCHES ====================
    /**
     * Assigne une tâche au bénévole
     * 
     * @param task Tâche à assigner (ex: "Feeding", "Cleaning", "Walking Dogs")
     */
    public void addTask(String task) {
        if (!assignedTasks.contains(task)) {
            assignedTasks.add(task);
            System.out.println("✓ Tâche '" + task + "' assignée à " + name);
        } else {
            System.out.println("⚠ Cette tâche est déjà assignée à " + name);
        }
    }

    /**
     * Retire une tâche du bénévole
     * 
     * @param task Tâche à retirer
     * @return True si retiré, false sinon
     */
    public boolean removeTask(String task) {
        if (assignedTasks.remove(task)) {
            System.out.println("✓ Tâche '" + task + "' retirée de " + name);
            return true;
        } else {
            System.out.println("✗ Tâche non trouvée.");
            return false;
        }
    }

    /**
     * Affiche toutes les tâches assignées au bénévole
     */
    public void afficherTasks() {
        System.out.println("\n--- Tâches de " + name + " ---");
        if (assignedTasks.isEmpty()) {
            System.out.println("Aucune tâche assignée.");
        } else {
            for (int i = 0; i < assignedTasks.size(); i++) {
                System.out.println((i + 1) + ". " + assignedTasks.get(i));
            }
        }
    }

    /**
     * Retourne le nombre de tâches assignées
     * 
     * @return Nombre de tâches
     */
    public int getTaskCount() {
        return assignedTasks.size();
    }

    // ==================== GESTION DES HEURES ====================
    /**
     * Ajoute des heures travaillées au total
     * 
     * @param hours Nombre d'heures à ajouter
     */
    public void addHoursWorked(int hours) {
        this.hoursWorked += hours;
        System.out.println("✓ " + hours + " heure(s) ajoutée(s) pour " + name +
                " (Total: " + hoursWorked + "h)");
    }

    /**
     * Réinitialise le compteur d'heures travaillées
     */
    public void resetHoursWorked() {
        this.hoursWorked = 0;
        System.out.println("✓ Heures réinitialisées pour " + name);
    }

    // ==================== AFFICHAGE ====================
    /**
     * Affiche tous les détails du bénévole
     */
    public void afficherDetails() {
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("       PROFIL BÉNÉVOLE #" + volunteerId);
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Nom: " + name);
        System.out.println("Email: " + email);
        System.out.println("Téléphone: " + phone);
        System.out.println("Statut: " + (isActive ? "ACTIF" : "INACTIF"));
        System.out.println("Heures travaillées: " + hoursWorked + "h");
        afficherSchedule();
        afficherTasks();
        System.out.println("═══════════════════════════════════════════\n");
    }

    /**
     * Affiche un résumé court des informations du bénévole
     */
    public void afficherResume() {
        System.out.println("ID: " + volunteerId + " | Nom: " + name +
                " | Tâches: " + assignedTasks.size() +
                " | Heures: " + hoursWorked + "h | " +
                (isActive ? "Actif" : "Inactif"));
    }

    /**
     * Génère un rapport de performance du bénévole
     * 
     * @return String contenant le rapport
     */
    public String generateReport() {
        return "─────────────────────────────────────────\n" +
                "RAPPORT DE BÉNÉVOLE\n" +
                "─────────────────────────────────────────\n" +
                "Nom: " + name + "\n" +
                "ID: " + volunteerId + "\n" +
                "Contact: " + email + " / " + phone + "\n" +
                "Heures travaillées: " + hoursWorked + "h\n" +
                "Créneau horaires: " + schedule.size() + "\n" +
                "Tâches assignées: " + assignedTasks.size() + "\n" +
                "Statut: " + (isActive ? "Actif" : "Inactif") + "\n" +
                "─────────────────────────────────────────\n";
    }

    // ==================== COMPARAISON ====================
    /**
     * Compare deux bénévoles par leur nombre d'heures travaillées
     * (pour pouvoir trier les bénévoles)
     * 
     * @param other Autre bénévole à comparer
     * @return -1 si moins d'heures, 0 si égal, 1 si plus d'heures
     */
    @Override
    public int compareTo(Volunteer other) {
        return Integer.compare(this.hoursWorked, other.hoursWorked);
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "volunteerId=" + volunteerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", hoursWorked=" + hoursWorked +
                ", isActive=" + isActive +
                '}';
    }
}
