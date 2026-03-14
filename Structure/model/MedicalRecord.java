package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe MedicalRecord (Dossier Médical)
 * 
 * Représente l'historique médical d'un animal.
 * Utilise la COMPOSITION : chaque Animal contient un objet MedicalRecord.
 * Permet de suivre les vaccinations, traitements et notes médicales.
 */
public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributs
    private int recordId; // ID unique du dossier
    private ArrayList<String> vaccinations; // Liste des vaccins administrés
    private ArrayList<String> treatments; // Liste des traitements effectués
    private ArrayList<LocalDate> vaccinationDates; // Dates des vaccinations
    private ArrayList<LocalDate> treatmentDates; // Dates des traitements
    private String notes; // Notes médicales générales
    private LocalDate lastCheckup; // Date de la dernière visite médicale

    /**
     * Constructeur
     * 
     * @param recordId ID unique du dossier médical (généralement l'ID de l'animal)
     */
    public MedicalRecord(int recordId) {
        this.recordId = recordId;
        this.vaccinations = new ArrayList<>(); // Initialiser la liste des vaccins
        this.treatments = new ArrayList<>(); // Initialiser la liste des traitements
        this.vaccinationDates = new ArrayList<>();
        this.treatmentDates = new ArrayList<>();
        this.notes = "Aucune note"; // Notes par défaut
        this.lastCheckup = LocalDate.now(); // Première visite = maintenant
    }

    // ==================== GETTERS ====================
    public int getRecordId() {
        return recordId;
    }

    public ArrayList<String> getVaccinations() {
        return vaccinations;
    }

    public ArrayList<String> getTreatments() {
        return treatments;
    }

    public ArrayList<LocalDate> getVaccinationDates() {
        return vaccinationDates;
    }

    public ArrayList<LocalDate> getTreatmentDates() {
        return treatmentDates;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDate getLastCheckup() {
        return lastCheckup;
    }

    // ==================== SETTERS ====================
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setLastCheckup(LocalDate lastCheckup) {
        this.lastCheckup = lastCheckup;
    }

    // ==================== MÉTHODES DE GESTION DES VACCINATIONS
    // ====================
    /**
     * Ajoute un vaccin au dossier médical
     * 
     * @param vaccineName Nom du vaccin à ajouter
     */
    public void addVaccination(String vaccineName) {
        vaccinations.add(vaccineName);
        vaccinationDates.add(LocalDate.now());
        System.out.println("✓ Vaccin '" + vaccineName + "' ajouté au dossier médical.");
    }

    /**
     * Retire un vaccin du dossier
     * 
     * @param vaccineName Nom du vaccin à retirer
     * @return True si le vaccin a été retiré, false sinon
     */
    public boolean removeVaccination(String vaccineName) {
        int index = vaccinations.indexOf(vaccineName);
        if (index != -1) {
            vaccinations.remove(index);
            vaccinationDates.remove(index);
            System.out.println("✓ Vaccin '" + vaccineName + "' retiré du dossier.");
            return true;
        } else {
            System.out.println("✗ Vaccin '" + vaccineName + "' non trouvé.");
            return false;
        }
    }

    /**
     * Vérifie si un vaccin spécifique a été administré
     * 
     * @param vaccineName Nom du vaccin à vérifier
     * @return True si le vaccin est présent, false sinon
     */
    public boolean hasVaccination(String vaccineName) {
        return vaccinations.contains(vaccineName);
    }

    /**
     * Affiche tous les vaccins de l'animal
     */
    public void afficherVaccinations() {
        System.out.println("\n--- Vaccinations ---");
        if (vaccinations.isEmpty()) {
            System.out.println("Aucun vaccin enregistré.");
        } else {
            for (int i = 0; i < vaccinations.size(); i++) {
                System.out.println((i + 1) + ". " + vaccinations.get(i) +
                        " (Date: " + vaccinationDates.get(i) + ")");
            }
        }
    }

    // ==================== MÉTHODES DE GESTION DES TRAITEMENTS ====================
    /**
     * Ajoute un traitement au dossier médical
     * 
     * @param treatmentName Nom du traitement à ajouter
     */
    public void addTreatment(String treatmentName) {
        treatments.add(treatmentName);
        treatmentDates.add(LocalDate.now());
        System.out.println("✓ Traitement '" + treatmentName + "' ajouté au dossier médical.");
    }

    /**
     * Retire un traitement du dossier
     * 
     * @param treatmentName Nom du traitement à retirer
     * @return True si le traitement a été retiré, false sinon
     */
    public boolean removeTreatment(String treatmentName) {
        int index = treatments.indexOf(treatmentName);
        if (index != -1) {
            treatments.remove(index);
            treatmentDates.remove(index);
            System.out.println("✓ Traitement '" + treatmentName + "' retiré du dossier.");
            return true;
        } else {
            System.out.println("✗ Traitement '" + treatmentName + "' non trouvé.");
            return false;
        }
    }

    /**
     * Affiche tous les traitements de l'animal
     */
    public void afficherTreatments() {
        System.out.println("\n--- Traitements ---");
        if (treatments.isEmpty()) {
            System.out.println("Aucun traitement enregistré.");
        } else {
            for (int i = 0; i < treatments.size(); i++) {
                System.out.println((i + 1) + ". " + treatments.get(i) +
                        " (Date: " + treatmentDates.get(i) + ")");
            }
        }
    }

    // ==================== MÉTHODES DE GESTION DES NOTES ====================
    /**
     * Ajoute ou remplace les notes médicales
     * 
     * @param note Nouvelle note médicale
     */
    public void updateNotes(String note) {
        this.notes = note;
        System.out.println("✓ Notes médicales mises à jour.");
    }

    /**
     * Ajoute une note supplémentaire aux notes existantes
     * 
     * @param additionalNote Note à ajouter
     */
    public void appendNote(String additionalNote) {
        if (this.notes.equals("Aucune note")) {
            this.notes = additionalNote;
        } else {
            this.notes += " ; " + additionalNote;
        }
        System.out.println("✓ Note supplémentaire ajoutée.");
    }

    // ==================== MÉTHODES SILENCIEUSES (POUR CHARGEMENT)
    // ====================
    /**
     * Ajoute un vaccin avec une date spécifique sans affichage console
     * Utilisé pour le chargement de données depuis les fichiers
     *
     * @param vaccineName Nom du vaccin
     * @param date        Date de la vaccination
     */
    public void addVaccinationWithDate(String vaccineName, LocalDate date) {
        vaccinations.add(vaccineName);
        vaccinationDates.add(date);
    }

    /**
     * Ajoute un traitement avec une date spécifique sans affichage console
     * Utilisé pour le chargement de données depuis les fichiers
     *
     * @param treatmentName Nom du traitement
     * @param date          Date du traitement
     */
    public void addTreatmentWithDate(String treatmentName, LocalDate date) {
        treatments.add(treatmentName);
        treatmentDates.add(date);
    }

    // ==================== AFFICHAGE COMPLET ====================
    /**
     * Affiche l'historique médical complet de l'animal
     * (vaccinations, traitements, notes)
     */
    public void afficherHistorique() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("       HISTORIQUE MÉDICAL #" + recordId);
        System.out.println("═══════════════════════════════════════");
        System.out.println("Dernière visite: " + lastCheckup);
        afficherVaccinations();
        afficherTreatments();
        System.out.println("\n--- Notes Médicales ---");
        System.out.println(notes);
        System.out.println("═══════════════════════════════════════\n");
    }

    /**
     * Génère un résumé court du dossier médical
     * 
     * @return String contenant le résumé
     */
    public String getSummary() {
        return "Vaccins: " + vaccinations.size() + " | " +
                "Traitements: " + treatments.size() + " | " +
                "Dernière visite: " + lastCheckup;
    }

    /**
     * Retourne true si l'animal a besoin d'une visite médicale
     * (dernière visite > 6 mois)
     * 
     * @return True si visite recommandée, false sinon
     */
    public boolean needsCheckup() {
        return LocalDate.now().minusMonths(6).isAfter(lastCheckup);
    }

    // ==================== CALCUL RÉCURSIF DES COÛTS ====================
    /**
     * Calcule récursivement le coût total des soins médicaux
     * Utilise la récursion pour parcourir les listes de vaccins et traitements
     *
     * @param indexVaccin     Index actuel dans la liste des vaccins (pour
     *                        récursion)
     * @param indexTraitement Index actuel dans la liste des traitements (pour
     *                        récursion)
     * @return Coût total des soins médicaux
     */
    public double calculateTotalMedicalCostRecursive(int indexVaccin, int indexTraitement) {
        // Cas de base : si on a parcouru toutes les listes
        if (indexVaccin >= vaccinations.size() && indexTraitement >= treatments.size()) {
            return 0.0;
        }

        double cost = 0.0;

        // Traiter les vaccins récursivement
        if (indexVaccin < vaccinations.size()) {
            String vaccine = vaccinations.get(indexVaccin);
            cost += getVaccineCost(vaccine); // Coût du vaccin actuel
            // Appel récursif pour le prochain vaccin
            cost += calculateTotalMedicalCostRecursive(indexVaccin + 1, indexTraitement);
        }
        // Traiter les traitements récursivement
        else if (indexTraitement < treatments.size()) {
            String treatment = treatments.get(indexTraitement);
            cost += getTreatmentCost(treatment); // Coût du traitement actuel
            // Appel récursif pour le prochain traitement
            cost += calculateTotalMedicalCostRecursive(indexVaccin, indexTraitement + 1);
        }

        return cost;
    }

    /**
     * Méthode publique pour calculer le coût total (point d'entrée récursion)
     *
     * @return Coût total des soins médicaux
     */
    public double calculateTotalMedicalCost() {
        return calculateTotalMedicalCostRecursive(0, 0);
    }

    /**
     * Retourne le coût d'un vaccin (simulation de prix)
     *
     * @param vaccineName Nom du vaccin
     * @return Coût du vaccin
     */
    private double getVaccineCost(String vaccineName) {
        // Prix simulés pour les vaccins courants
        switch (vaccineName.toLowerCase()) {
            case "rage":
                return 25.0;
            case "parvovirus":
                return 20.0;
            case "hépatite":
                return 18.0;
            case "toux du chenil":
                return 15.0;
            case "leucose":
                return 30.0;
            case "typhus":
                return 22.0;
            default:
                return 15.0; // Prix par défaut
        }
    }

    /**
     * Retourne le coût d'un traitement (simulation de prix)
     *
     * @param treatmentName Nom du traitement
     * @return Coût du traitement
     */
    private double getTreatmentCost(String treatmentName) {
        // Prix simulés pour les traitements courants
        if (treatmentName.toLowerCase().contains("antibiotique"))
            return 45.0;
        if (treatmentName.toLowerCase().contains("antiparasitaire"))
            return 35.0;
        if (treatmentName.toLowerCase().contains("vaccin"))
            return 25.0;
        if (treatmentName.toLowerCase().contains("chirurgie"))
            return 150.0;
        if (treatmentName.toLowerCase().contains("radiographie"))
            return 80.0;
        return 25.0; // Prix par défaut
    }

    /**
     * Affiche le coût total avec détail récursif
     */
    public void displayMedicalCost() {
        double totalCost = calculateTotalMedicalCost();
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("    COÛT TOTAL DES SOINS MÉDICAUX");
        System.out.println("══════════════════════════════════════════");
        System.out.println("Vaccins: " + vaccinations.size() + " traitements");
        System.out.println("Traitements: " + treatments.size() + " procédures");
        System.out.printf("Coût total: %.2f €\n", totalCost);
        System.out.println("══════════════════════════════════════════\n");
    }
}
