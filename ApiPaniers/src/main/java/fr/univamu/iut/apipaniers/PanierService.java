package fr.univamu.iut.apipaniers;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.util.ArrayList;

/**
 * Classe utilisée pour récupérer les informations nécessaires à la ressource
 * (permet de dissocier ressource et mode d'éccès aux données)
 */
public class PanierService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les paniers
     */
    protected PanierRepositoryInterface panierRepo ;
    protected ProductRepositoryInterface productRepo ;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param panierRepo objet implémentant l'interface d'accès aux données
     * @param productRepo objet implémentant l'interface produit
     */
    public  PanierService(PanierRepositoryInterface panierRepo, ProductRepositoryInterface productRepo) {
        this.panierRepo = panierRepo;
        this.productRepo = productRepo;
    }

    /**
     * Méthode retournant les informations sur les paniers au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllPaniersJSON(){

        ArrayList<Panier> allPaniers = panierRepo.getAllPaniers();

        // création du json et conversion de la liste de paniers
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allPaniers);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un panier recherché
     * @param nom le nom du panier recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getPanierJSON( String nom ){
        String result = null;
        Panier myPanier = panierRepo.getPanier(nom);

        // si le livre a été trouvé
        if( myPanier != null ) {

            // création du json et conversion du livre
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myPanier);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }

    public boolean addPanier(Panier panier) {
        return panierRepo.addPanier(panier);
    }

    public boolean deletePanier(String nom) {
        return panierRepo.deletePanier(nom);
    }

    public boolean commanderPanier(String nomPanier, int quantiteCommandee) {
        // Vérifier si le panier existe
        Panier panier = panierRepo.getPanier(nomPanier);  // Requête pour récupérer le panier par son nom

        if (panier == null) {
            // Panier non trouvé
            return false;
        }

        // Vérification de la quantité disponible
        if (panier.getQuantite() < quantiteCommandee) {
            // Quantité demandée supérieure à la quantité disponible
            return false;
        }

        // Mettre à jour la quantité du panier
        panier.enleverQuantite(quantiteCommandee);

        // Si la quantité a changé, mettre à jour la base de données avec la nouvelle quantité
        return panierRepo.updatePanier(panier.getNom(), panier.getQuantite(), panier.getPrix(), panier.getProduits(), panier.getMaj());
    }

    // Dans la classe PanierService
    public Panier getPanier(String nomPanier) {
        return panierRepo.getPanier(nomPanier);
    }

    public boolean ajouterProduitAuPanier(String nomPanier, String produitNom, int quantite) {
        // Vérifier si le produit existe
        Product produit = productRepo.getProduct(produitNom);
        if (produit == null) {
            return false; // Le produit n'existe pas
        }

        // Récupérer le panier
        Panier panier = panierRepo.getPanier(nomPanier);
        if (panier == null) {
            return false; // Le panier n'existe pas
        }

        // Ajouter le produit au panier
        panier.ajouterProduits(produitNom, quantite);

        // Sauvegarder le panier mis à jour
        return panierRepo.updatePanier(panier.getNom(), panier.getQuantite(), panier.getPrix(), panier.getProduits(), panier.getMaj());
    }

    public boolean retirerProduitDuPanier(String nomPanier, String produitNom, int quantite) {
        // Vérifier si le produit existe dans le panier
        Panier panier = panierRepo.getPanier(nomPanier); // Récupérer le panier par son nom
        if (panier == null) {
            return false; // Le panier n'existe pas
        }

        if (!panier.getProduits().containsKey(produitNom) || panier.getProduits().get(produitNom) < quantite) {
            return false; // Le produit n'existe pas ou la quantité demandée est supérieure à la quantité disponible
        }

        // Retirer le produit du panier
        panier.retirerProduits(produitNom, quantite);

        // Sauvegarder le panier mis à jour
        return panierRepo.updatePanier(panier.getNom(), panier.getQuantite(), panier.getPrix(), panier.getProduits(), panier.getMaj());
    }


}