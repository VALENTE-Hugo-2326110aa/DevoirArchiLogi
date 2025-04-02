package fr.univamu.iut.apicommandes.interfaces;

import fr.univamu.iut.apicommandes.APIS.Panier;

import java.util.*;

/**
 * Interface d'accès aux données des paniers
 */
public interface PanierRepositoryInterface {

    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les paniers
     */
    public void close();

    /**
     * Méthode retournant le panier dont le nom est passé en paramètre
     * @param nom nom du panier recherché
     * @return un objet Panier représentant le panier recherché
     */
    public Panier getPanier(String nom );

    /**
     * Méthode retournant la liste des paniers
     * @return une liste d'objets paniers
     */
    public ArrayList<Panier> getAllPaniers() ;

    /**
     * Méthode permettant de mettre à jours un panier enregistré
     *
     * @param quantite nouvelle quantite restante du panier
     * @param prix     nouveau prix du panier
     * @param produits nouveaux produits contenus dans le panier
     * @param maj
     * @return true si le panier existe et la mise à jour a été faite, false sinon
     */
    public boolean updatePanier(String nom, int quantite, float prix, Map<String, Integer> produits, String maj);

    boolean addPanier(Panier panier);

    boolean deletePanier(String nom);

}