package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Classe AdoptionForm (Formulaire d'Adoption)
 * 
 * Représente un formulaire d'adoption complété par un adoptant potentiel.
 * Contient les informations de l'adoptant et le statut d'approbation.
 * Utilise la COMPOSITION : chaque adoption est liée à un animal et à un
 * formulaire.
 */
public class AdoptionForm implements Serializable {
    private static final long serialVersionUID = 1L;

    // Attributs
    private int formId; // ID unique du formulaire
    private int animalId; // ID de l'animal à adopter
    private String adopterName; // Nom complet de l'adoptant
    private String adopterEmail; // Email de l'adoptant
    private String adopterPhone; // Numéro de téléphone
    private String adopterAddress; // Adresse de l'adoptant
    private LocalDate adoptionDate; // Date d'adoption
    private String approvalStatus; // Statut : "pending", "approved", "rejected"
    private String rejectionReason; // Raison du refus (si rejeté)

    /**
     * Constructeur
     * 
     * @param formId         ID unique du formulaire
     * @param animalId       ID de l'animal à adopter
     * @param adopterName    Nom de l'adoptant
     * @param adopterEmail   Email de l'adoptant
     * @param adopterPhone   Téléphone de l'adoptant
     * @param adopterAddress Adresse de l'adoptant
     */
    public AdoptionForm(int formId, int animalId, String adopterName,
            String adopterEmail, String adopterPhone, String adopterAddress) {
        this.formId = formId;
        this.animalId = animalId;
        this.adopterName = adopterName;
        this.adopterEmail = adopterEmail;
        this.adopterPhone = adopterPhone;
        this.adopterAddress = adopterAddress;
        this.adoptionDate = LocalDate.now(); // Date actuelle par défaut
        this.approvalStatus = "pending"; // Statut initial : en attente
        this.rejectionReason = null; // Pas de raison si pas rejeté
    }

    // ==================== GETTERS ====================
    public int getFormId() {
        return formId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public String getAdopterName() {
        return adopterName;
    }

    public String getAdopterEmail() {
        return adopterEmail;
    }

    public String getAdopterPhone() {
        return adopterPhone;
    }

    public String getAdopterAddress() {
        return adopterAddress;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    // ==================== SETTERS ====================
    public void setAdopterName(String adopterName) {
        this.adopterName = adopterName;
    }

    public void setAdopterEmail(String adopterEmail) {
        this.adopterEmail = adopterEmail;
    }

    public void setAdopterPhone(String adopterPhone) {
        this.adopterPhone = adopterPhone;
    }

    public void setAdopterAddress(String adopterAddress) {
        this.adopterAddress = adopterAddress;
    }

    public void setAdoptionDate(LocalDate adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    // ==================== MÉTHODES DE GESTION DU STATUT ====================
    /**
     * Approuve la demande d'adoption
     * Change le statut à "approved"
     */
    public void approveAdoption() {
        this.approvalStatus = "approved";
        this.rejectionReason = null;
        System.out.println("✓ Adoption approuvée pour " + adopterName);
    }

    /**
     * Rejette la demande d'adoption avec une raison
     * 
     * @param reason Raison du refus
     */
    public void rejectAdoption(String reason) {
        this.approvalStatus = "rejected";
        this.rejectionReason = reason;
        System.out.println("✗ Adoption rejetée pour " + adopterName +
                " - Raison: " + reason);
    }

    /**
     * Marque le formulaire comme en attente (état initial)
     */
    public void setPending() {
        this.approvalStatus = "pending";
        this.rejectionReason = null;
        System.out.println("⏳ Formulaire mis en attente pour " + adopterName);
    }

    /**
     * Vérifie si le formulaire est approuvé
     * 
     * @return True si approuvé, false sinon
     */
    public boolean isApproved() {
        return "approved".equalsIgnoreCase(approvalStatus);
    }

    /**
     * Vérifie si le formulaire est rejeté
     * 
     * @return True si rejeté, false sinon
     */
    public boolean isRejected() {
        return "rejected".equalsIgnoreCase(approvalStatus);
    }

    /**
     * Vérifie si le formulaire est en attente
     * 
     * @return True si en attente, false sinon
     */
    public boolean isPending() {
        return "pending".equalsIgnoreCase(approvalStatus);
    }

    // ==================== VALIDATION ====================
    /**
     * Valide les informations du formulaire
     * Vérifie que tous les champs sont remplis correctement
     * 
     * @return True si toutes les informations sont valides, false sinon
     */
    public boolean isValid() {
        boolean isEmailValid = adopterEmail != null && adopterEmail.contains("@");
        boolean isPhoneValid = adopterPhone != null && adopterPhone.length() >= 10;
        boolean isNameValid = adopterName != null && !adopterName.trim().isEmpty();
        boolean isAddressValid = adopterAddress != null && !adopterAddress.trim().isEmpty();

        return isNameValid && isEmailValid && isPhoneValid && isAddressValid;
    }

    /**
     * Retourne un message d'erreur si le formulaire n'est pas valide
     * 
     * @return Message d'erreur ou "Valide" si OK
     */
    public String getValidationError() {
        if (adopterName == null || adopterName.trim().isEmpty()) {
            return "Le nom de l'adoptant est requis.";
        }
        if (adopterEmail == null || !adopterEmail.contains("@")) {
            return "Adresse email invalide.";
        }
        if (adopterPhone == null || adopterPhone.length() < 10) {
            return "Numéro de téléphone invalide (minimum 10 chiffres).";
        }
        if (adopterAddress == null || adopterAddress.trim().isEmpty()) {
            return "L'adresse est requise.";
        }
        return "Valide";
    }

    // ==================== AFFICHAGE ====================
    /**
     * Affiche tous les détails du formulaire d'adoption
     */
    public void afficherDetails() {
        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("   FORMULAIRE D'ADOPTION #" + formId);
        System.out.println("═══════════════════════════════════════════");
        System.out.println("Animal ID: " + animalId);
        System.out.println("Nom de l'adoptant: " + adopterName);
        System.out.println("Email: " + adopterEmail);
        System.out.println("Téléphone: " + adopterPhone);
        System.out.println("Adresse: " + adopterAddress);
        System.out.println("Date d'adoption: " + adoptionDate);
        System.out.println("Statut: " + approvalStatus.toUpperCase());
        if (rejectionReason != null) {
            System.out.println("Raison du refus: " + rejectionReason);
        }
        System.out.println("═══════════════════════════════════════════\n");
    }

    /**
     * Affiche un résumé court du formulaire
     */
    public void afficherResume() {
        System.out.println("Form #" + formId + " | Adoptant: " + adopterName +
                " | Statut: " + approvalStatus + " | Date: " + adoptionDate);
    }

    /**
     * Génère un rapport d'adoption
     * 
     * @return String contenant le rapport
     */
    public String generateReport() {
        return "─────────────────────────────────────────\n" +
                "RAPPORT D'ADOPTION\n" +
                "─────────────────────────────────────────\n" +
                "Formulaire: " + formId + "\n" +
                "Animal: " + animalId + "\n" +
                "Adoptant: " + adopterName + "\n" +
                "Contact: " + adopterEmail + " / " + adopterPhone + "\n" +
                "Adresse: " + adopterAddress + "\n" +
                "Date: " + adoptionDate + "\n" +
                "Statut: " + approvalStatus + "\n" +
                "─────────────────────────────────────────\n";
    }

    @Override
    public String toString() {
        return "AdoptionForm{" +
                "formId=" + formId +
                ", animalId=" + animalId +
                ", adopterName='" + adopterName + '\'' +
                ", approvalStatus='" + approvalStatus + '\'' +
                '}';
    }
}
