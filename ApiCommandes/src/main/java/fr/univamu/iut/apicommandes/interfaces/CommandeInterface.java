package fr.univamu.iut.apicommandes.interfaces;

import fr.univamu.iut.apicommandes.domain.Commande;

import java.sql.Date;
import java.util.*;

/**
 * Interface d'accès aux données des commandes
 */
public interface CommandeInterface {

    /**
     * Méthode fermant le dépôt où sont stockées les informations sur les commandes
     */
    public void close();

    /**
     * Méthode retournant la commande dont l'identifiant est passé en paramètre
     * @param clientId identifiant de la commande recherchée
     * @param nomPanier identifiant de la commande recherchée
     * @return un objet Commande représentant la commande recherchée
     */
    public Commande getCommande(String clientId, String nomPanier);

    /**
     * Méthode retournant la commande dont l'identifiant est passé en paramètre
     * @param clientId identifiant de la commande recherchée
     * @return un objet Commande représentant la commande recherchée
     */
    public ArrayList<Commande> getCommandesClient(String clientId);

    /**
     * Méthode retournant la liste des commandes
     * @return une liste d'objets Commande
     */
    public ArrayList<Commande> getAllCommandes();

    /**
     * Méthode permettant de mettre à jour une commande enregistrée
     * @param clientId identifiant de la commande à mettre à jour
     * @param nomPanier identifiant du panier à mettre à jour
     * @param prix nouveau prix de la commande
     * @param localisationRelai nouvelle localisation du relai
     * @param dateRetrait nouvelle date de retrait
     * @return true si la commande existe et la mise à jour a été faite, false sinon
     */
    public boolean updateCommande(String clientId, String nomPanier, double prix, String localisationRelai, Date dateRetrait);

    public boolean registerCommande(String userId, String panierNom, float prix, String relaiParDéfaut, Date date);

    boolean deleteCommande(String clientId, String nomPanier);
}
