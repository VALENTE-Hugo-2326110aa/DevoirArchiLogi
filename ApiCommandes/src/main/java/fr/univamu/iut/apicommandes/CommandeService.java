package fr.univamu.iut.apicommandes;

import fr.univamu.iut.apicommandes.APIS.Panier;
import fr.univamu.iut.apicommandes.APIS.User;
import fr.univamu.iut.apicommandes.domain.Commande;
import fr.univamu.iut.apicommandes.interfaces.CommandeInterface;
import fr.univamu.iut.apicommandes.interfaces.PanierRepositoryInterface;
import fr.univamu.iut.apicommandes.interfaces.UserRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'accès aux données)
 */
public class CommandeService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les commandes
     */
    protected CommandeInterface commandeRepo;

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les paniers
     */
    protected PanierRepositoryInterface panierRepo;

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les utilisateurs
     */
    protected UserRepositoryInterface userRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param commandeRepo objet implémentant l'interface d'accès aux données des commandes
     * @param panierRepo objet implémentant l'interface d'accès aux données des paniers
     * @param userRepo objet implémentant l'interface d'accès aux données des utilisateurs
     */
    public CommandeService(CommandeInterface commandeRepo, PanierRepositoryInterface panierRepo, UserRepositoryInterface userRepo) {
        this.commandeRepo = commandeRepo;
        this.panierRepo = panierRepo;
        this.userRepo = userRepo;
    }
    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param commandeRepo objet implémentant l'interface d'accès aux données
     */
    public CommandeService(CommandeInterface commandeRepo) {
        this.commandeRepo = commandeRepo;
    }

    /**
     * Méthode retournant les informations sur toutes les commandes au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllCommandesJSON() {
        ArrayList<Commande> allCommandes = commandeRepo.getAllCommandes();
        String result = null;
        try (Jsonb jsonb = JsonbBuilder.create()) {
            result = jsonb.toJson(allCommandes);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur une commande recherchée
     * @param clientId identifiant du client associé à la commande
     * @param nomPanier le nom du panier associé à la commande
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getCommandeJSON(String clientId, String nomPanier) {
        String result = null;
        Commande myCommande = commandeRepo.getCommande(clientId, nomPanier);

        if (myCommande != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myCommande);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur une commande recherchée
     * @param clientId identifiant du client associé à la commande
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getCommandesClientJSON(String clientId) {
        String result = null;
        ArrayList<Commande> allCommande = commandeRepo.getCommandesClient(clientId);

        if (allCommande != null) {
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(allCommande);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    /**
     * Méthode permettant de mettre à jour les informations d'une commande
     * @param clientId identifiant du client associé à la commande
     * @param nomPanier le nom du panier associé à la commande
     * @param commande les nouvelles informations à utiliser
     * @return true si la commande a pu être mise à jour
     */
    public boolean updateCommande(String clientId, String nomPanier, Commande commande) {
        return commandeRepo.updateCommande(clientId, nomPanier, commande.getPrix(), commande.getLocalisationRelai(), java.sql.Date.valueOf(commande.getDateRetrait()));
    }


    /**
     * Méthode permettant d'enregistrer une commande (la date est définie automatiquement)
     * @param userId identifiant de l'utilisateur
     * @param panierNom nom du panier à commander
     * @return true si la commande a pu être enregistrée, false sinon
     */
    public boolean registerCommande(String userId, String panierNom) {
        boolean result = false;

        // récupération des informations du panier
        Panier myPanier = panierRepo.getPanier(panierNom);

        // si le panier n'est pas trouvé
        if (myPanier == null)
            throw new NotFoundException("Panier non existant");

        // recherche de l'utilisateur
        User myUser = userRepo.getUser(userId);

        // si l'utilisateur n'existe pas
        if (myUser == null)
            throw new NotFoundException("Utilisateur non existant");

        // enregistrer la commande
        result = commandeRepo.registerCommande(userId, panierNom, myPanier.getPrix(), "Relai par défaut", new java.sql.Date(new Date().getTime()));

        return result;
    }

    /**
     * Méthode supprimant une commande
     * @param clientId identifiant du client associé à la commande
     * @param nomPanier nom du panier associé à la commande
     * @return true si la commande a été supprimée, false sinon
     */
    public boolean removeCommande(String clientId, String nomPanier) {
        boolean result = false;

        // Récupération des informations de la commande
        Commande myCommande = commandeRepo.getCommande(clientId, nomPanier);

        // Si la commande n'existe pas, lever une exception
        if (myCommande == null) {
            throw new NotFoundException("Commande non existante");
        }

        // Supprimer la commande
        result = commandeRepo.deleteCommande(clientId, nomPanier);

        return result;
    }

}
