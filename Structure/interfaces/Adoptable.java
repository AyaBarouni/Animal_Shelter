package interfaces;

import exceptions.ShelterException;

/**
 * Interface Adoptable
 * 
 * Cette interface définit le contrat que tous les animaux adoptables doivent
 * suivre.
 * Elle garantit que chaque animal adoptable implémente les méthodes d'adoption
 * et de retour.
 */
public interface Adoptable {
    /**
     * Adopte l'animal
     * 
     * @throws ShelterException Si l'animal est déjà adopté ou n'est pas éligible
     */
    void adopt() throws ShelterException;

    /**
     * Retourne l'animal au refuge
     * 
     * @throws ShelterException Si l'animal n'est pas actuellement adopté
     */
    void returnToShelter() throws ShelterException;
}
