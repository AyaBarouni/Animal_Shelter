package service;

import model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Classe DataManager
 * 
 * Gère la persistance des données dans les fichiers texte.
 * Permet de charger et sauvegarder les animaux, bénévoles et formulaires
 * d'adoption.
 */
public class DataManager {
    private static final String ANIMALS_FILE = "data/animals_data.txt";
    private static final String VOLUNTEERS_FILE = "data/volunteers_data.txt";
    private static final String ADOPTIONS_FILE = "data/adoptions_data.txt";

    // ==================== SAUVEGARDE DES ANIMAUX ====================
    /**
     * Sauvegarde la liste des animaux dans le fichier texte
     * 
     * @param animals Liste des animaux à sauvegarder
     */
    public static void saveAnimals(ArrayList<Animal> animals) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ANIMALS_FILE))) {
            writer.println("# Données des animaux du refuge");
            writer.println(
                    "# Format: id|type|name|age|arrivalDate|adopted|field1|field2|vaccinations|treatments|lastCheckup|notes");

            for (Animal animal : animals) {
                StringBuilder sb = new StringBuilder();
                sb.append(animal.getId()).append("|");
                sb.append(animal.getClass().getSimpleName()).append("|");
                sb.append(animal.getName()).append("|");
                sb.append(animal.getAge()).append("|");
                sb.append(animal.getArrivalDate()).append("|");
                sb.append(animal.isAdopted()).append("|");

                // Champs spécifiques selon le type d'animal
                if (animal instanceof Dog) {
                    Dog dog = (Dog) animal;
                    sb.append(dog.getBreed()).append("|");
                    sb.append(dog.isTrained());
                } else if (animal instanceof Cat) {
                    Cat cat = (Cat) animal;
                    sb.append(cat.getColor()).append("|");
                    sb.append(cat.isIndoorOnly());
                } else if (animal instanceof Bird) {
                    Bird bird = (Bird) animal;
                    sb.append(bird.getWingSpan()).append("|");
                    sb.append(bird.getSpecies());
                }

                // Vaccinations (nom~date;nom~date)
                sb.append("|");
                MedicalRecord record = animal.getMedicalRecord();
                ArrayList<String> vaccinations = record.getVaccinations();
                ArrayList<LocalDate> vacDates = record.getVaccinationDates();
                for (int i = 0; i < vaccinations.size(); i++) {
                    if (i > 0)
                        sb.append(";");
                    sb.append(vaccinations.get(i)).append("~").append(vacDates.get(i));
                }

                // Traitements (nom~date;nom~date)
                sb.append("|");
                ArrayList<String> treatments = record.getTreatments();
                ArrayList<LocalDate> treatDates = record.getTreatmentDates();
                for (int i = 0; i < treatments.size(); i++) {
                    if (i > 0)
                        sb.append(";");
                    sb.append(treatments.get(i)).append("~").append(treatDates.get(i));
                }

                // Dernière visite médicale
                sb.append("|");
                sb.append(record.getLastCheckup());

                // Notes médicales (dernier champ pour éviter les conflits avec le séparateur)
                sb.append("|");
                sb.append(record.getNotes());

                writer.println(sb.toString());
            }
            System.out.println("✓ Données des animaux sauvegardées.");
        } catch (IOException e) {
            System.out.println("✗ Erreur de sauvegarde des animaux: " + e.getMessage());
        }
    }

    // ==================== CHARGEMENT DES ANIMAUX ====================
    /**
     * Charge la liste des animaux depuis le fichier texte
     *
     * @return Liste des animaux chargés
     */
    public static ArrayList<Animal> loadAnimals() {
        ArrayList<Animal> animals = new ArrayList<>();
        File file = new File(ANIMALS_FILE);
        if (!file.exists())
            return animals;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty())
                    continue;

                // Limite à 12 parties pour que les notes (dernier champ) puissent contenir le
                // séparateur
                String[] parts = line.split("\\|", 12);
                if (parts.length < 8)
                    continue;

                int id = Integer.parseInt(parts[0].trim());
                String type = parts[1].trim();
                String name = parts[2].trim();
                int age = Integer.parseInt(parts[3].trim());
                LocalDate arrivalDate = LocalDate.parse(parts[4].trim());
                boolean adopted = Boolean.parseBoolean(parts[5].trim());

                Animal animal = null;
                switch (type) {
                    case "Dog":
                        String breed = parts[6].trim();
                        boolean trained = Boolean.parseBoolean(parts[7].trim());
                        animal = new Dog(id, name, age, arrivalDate, breed, trained);
                        break;
                    case "Cat":
                        String color = parts[6].trim();
                        boolean indoorOnly = Boolean.parseBoolean(parts[7].trim());
                        animal = new Cat(id, name, age, arrivalDate, color, indoorOnly);
                        break;
                    case "Bird":
                        double wingSpan = Double.parseDouble(parts[6].trim());
                        String species = parts[7].trim();
                        animal = new Bird(id, name, age, arrivalDate, wingSpan, species);
                        break;
                }

                if (animal != null) {
                    animal.setAdopted(adopted);

                    // Charger les vaccinations (nom~date;nom~date)
                    if (parts.length > 8 && !parts[8].trim().isEmpty()) {
                        String[] vacEntries = parts[8].split(";");
                        for (String entry : vacEntries) {
                            String[] vacParts = entry.split("~");
                            if (vacParts.length == 2) {
                                animal.getMedicalRecord().addVaccinationWithDate(
                                        vacParts[0].trim(), LocalDate.parse(vacParts[1].trim()));
                            }
                        }
                    }

                    // Charger les traitements (nom~date;nom~date)
                    if (parts.length > 9 && !parts[9].trim().isEmpty()) {
                        String[] treatEntries = parts[9].split(";");
                        for (String entry : treatEntries) {
                            String[] treatParts = entry.split("~");
                            if (treatParts.length == 2) {
                                animal.getMedicalRecord().addTreatmentWithDate(
                                        treatParts[0].trim(), LocalDate.parse(treatParts[1].trim()));
                            }
                        }
                    }

                    // Charger la dernière visite
                    if (parts.length > 10 && !parts[10].trim().isEmpty()) {
                        animal.getMedicalRecord().setLastCheckup(LocalDate.parse(parts[10].trim()));
                    }

                    // Charger les notes (dernier champ)
                    if (parts.length > 11 && !parts[11].trim().isEmpty()) {
                        animal.getMedicalRecord().setNotes(parts[11]);
                    }

                    animals.add(animal);
                }
            }
            if (!animals.isEmpty()) {
                System.out.println("✓ " + animals.size() + " animaux chargés depuis le fichier.");
            }
        } catch (IOException e) {
            System.out.println("✗ Erreur de chargement des animaux: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Erreur de parsing des données animaux: " + e.getMessage());
        }
        return animals;
    }

    // ==================== SAUVEGARDE DES BÉNÉVOLES ====================
    /**
     * Sauvegarde la liste des bénévoles dans le fichier texte
     *
     * @param volunteers Liste des bénévoles à sauvegarder
     */
    public static void saveVolunteers(ArrayList<Volunteer> volunteers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(VOLUNTEERS_FILE))) {
            writer.println("# Données des bénévoles du refuge");
            writer.println("# Format: id|name|email|phone|hoursWorked|isActive|schedules|tasks");

            for (Volunteer vol : volunteers) {
                StringBuilder sb = new StringBuilder();
                sb.append(vol.getVolunteerId()).append("|");
                sb.append(vol.getName()).append("|");
                sb.append(vol.getEmail()).append("|");
                sb.append(vol.getPhone()).append("|");
                sb.append(vol.getHoursWorked()).append("|");
                sb.append(vol.isActive()).append("|");

                // Horaires (schedule1;schedule2)
                ArrayList<String> schedules = vol.getSchedule();
                for (int i = 0; i < schedules.size(); i++) {
                    if (i > 0)
                        sb.append(";");
                    sb.append(schedules.get(i));
                }

                sb.append("|");

                // Tâches (task1;task2)
                ArrayList<String> tasks = vol.getAssignedTasks();
                for (int i = 0; i < tasks.size(); i++) {
                    if (i > 0)
                        sb.append(";");
                    sb.append(tasks.get(i));
                }

                writer.println(sb.toString());
            }
            System.out.println("✓ Données des bénévoles sauvegardées.");
        } catch (IOException e) {
            System.out.println("✗ Erreur de sauvegarde des bénévoles: " + e.getMessage());
        }
    }

    // ==================== CHARGEMENT DES BÉNÉVOLES ====================
    /**
     * Charge la liste des bénévoles depuis le fichier texte
     *
     * @return Liste des bénévoles chargés
     */
    public static ArrayList<Volunteer> loadVolunteers() {
        ArrayList<Volunteer> volunteers = new ArrayList<>();
        File file = new File(VOLUNTEERS_FILE);
        if (!file.exists())
            return volunteers;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty())
                    continue;

                String[] parts = line.split("\\|", -1);
                if (parts.length < 6)
                    continue;

                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String email = parts[2].trim();
                String phone = parts[3].trim();
                int hoursWorked = Integer.parseInt(parts[4].trim());
                boolean isActive = Boolean.parseBoolean(parts[5].trim());

                Volunteer vol = new Volunteer(id, name, email, phone);
                vol.setHoursWorked(hoursWorked);
                vol.setActive(isActive);

                // Charger les horaires
                if (parts.length > 6 && !parts[6].trim().isEmpty()) {
                    String[] schedules = parts[6].split(";");
                    for (String schedule : schedules) {
                        vol.getSchedule().add(schedule.trim());
                    }
                }

                // Charger les tâches
                if (parts.length > 7 && !parts[7].trim().isEmpty()) {
                    String[] tasks = parts[7].split(";");
                    for (String task : tasks) {
                        vol.getAssignedTasks().add(task.trim());
                    }
                }

                volunteers.add(vol);
            }
            if (!volunteers.isEmpty()) {
                System.out.println("✓ " + volunteers.size() + " bénévoles chargés depuis le fichier.");
            }
        } catch (IOException e) {
            System.out.println("✗ Erreur de chargement des bénévoles: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Erreur de parsing des données bénévoles: " + e.getMessage());
        }
        return volunteers;
    }

    // ==================== SAUVEGARDE DES ADOPTIONS ====================
    /**
     * Sauvegarde la liste des formulaires d'adoption dans le fichier texte
     *
     * @param adoptions Liste des formulaires à sauvegarder
     */
    public static void saveAdoptions(ArrayList<AdoptionForm> adoptions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ADOPTIONS_FILE))) {
            writer.println("# Données des adoptions du refuge");
            writer.println(
                    "# Format: id|animalId|adopterName|adopterEmail|adopterPhone|adopterAddress|adoptionDate|approvalStatus|rejectionReason");

            for (AdoptionForm form : adoptions) {
                StringBuilder sb = new StringBuilder();
                sb.append(form.getFormId()).append("|");
                sb.append(form.getAnimalId()).append("|");
                sb.append(form.getAdopterName()).append("|");
                sb.append(form.getAdopterEmail()).append("|");
                sb.append(form.getAdopterPhone()).append("|");
                sb.append(form.getAdopterAddress()).append("|");
                sb.append(form.getAdoptionDate()).append("|");
                sb.append(form.getApprovalStatus()).append("|");
                sb.append(form.getRejectionReason() != null ? form.getRejectionReason() : "");

                writer.println(sb.toString());
            }
            System.out.println("✓ Données des adoptions sauvegardées.");
        } catch (IOException e) {
            System.out.println("✗ Erreur de sauvegarde des adoptions: " + e.getMessage());
        }
    }

    // ==================== CHARGEMENT DES ADOPTIONS ====================
    /**
     * Charge la liste des formulaires d'adoption depuis le fichier texte
     *
     * @return Liste des formulaires chargés
     */
    public static ArrayList<AdoptionForm> loadAdoptions() {
        ArrayList<AdoptionForm> adoptions = new ArrayList<>();
        File file = new File(ADOPTIONS_FILE);
        if (!file.exists())
            return adoptions;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty())
                    continue;

                String[] parts = line.split("\\|", -1);
                if (parts.length < 8)
                    continue;

                int formId = Integer.parseInt(parts[0].trim());
                int animalId = Integer.parseInt(parts[1].trim());
                String adopterName = parts[2].trim();
                String adopterEmail = parts[3].trim();
                String adopterPhone = parts[4].trim();
                String adopterAddress = parts[5].trim();
                LocalDate adoptionDate = LocalDate.parse(parts[6].trim());
                String approvalStatus = parts[7].trim();
                String rejectionReason = (parts.length > 8 && !parts[8].trim().isEmpty())
                        ? parts[8].trim()
                        : null;

                AdoptionForm form = new AdoptionForm(formId, animalId, adopterName,
                        adopterEmail, adopterPhone, adopterAddress);
                form.setAdoptionDate(adoptionDate);
                form.setApprovalStatus(approvalStatus);
                form.setRejectionReason(rejectionReason);

                adoptions.add(form);
            }
            if (!adoptions.isEmpty()) {
                System.out.println("✓ " + adoptions.size() + " adoptions chargées depuis le fichier.");
            }
        } catch (IOException e) {
            System.out.println("✗ Erreur de chargement des adoptions: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Erreur de parsing des données adoptions: " + e.getMessage());
        }
        return adoptions;
    }
}
