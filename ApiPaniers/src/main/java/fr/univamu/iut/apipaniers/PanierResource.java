package fr.univamu.iut.apipaniers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

/**
 * Ressource associée aux paniers
 * (point d'accès de l'API REST)
 */
@Path("/paniers")
@ApplicationScoped
public class PanierResource {

    /**
     * Service utilisé pour accéder aux données des paniers et récupérer/modifier leurs informations
     */
    private PanierService service;

    /**
     * Constructeur par défaut
     */
    public PanierResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec une interface d'accès aux données
     * @param panierRepo objet implémentant l'interface d'accès aux données
     */
    public @Inject PanierResource( PanierRepositoryInterface panierRepo , ProductRepositoryInterface productRepo){
        this.service = new PanierService(panierRepo, productRepo) ;
    }

    /**
     * Constructeur permettant d'initialiser le service d'accès aux paniers
     */
    public PanierResource( PanierService service ){
        this.service = service;
    }

    /**
     * Enpoint permettant de publier de tous les paniers enregistrés
     * @return la liste des paniers (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllPaniers() {
        return service.getAllPaniersJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'un panier dont le nom est passée paramètre dans le chemin
     * @param nom nom du panier recherché
     * @return les informations du panier recherché au format JSON
     */
    @GET
    @Path("{nom}")
    @Produces("application/json")
    public String getPanier( @PathParam("nom") String nom){

        String result = service.getPanierJSON(nom);

        // si le panier n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }

    /**
     * Endpoint pour supprimer un panier par son nom
     * @param nom Nom du panier à supprimer
     * @return Réponse HTTP avec statut de confirmation
     */
    @DELETE
    @Path("{nom}")
    public Response deletePanier(@PathParam("nom") String nom) {
        boolean success = service.deletePanier(nom);

        if (success) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Panier non trouvé").build();
        }
    }

    /**
     * Endpoint pour commander des paniers
     * @param nomPanier Nom du panier à commander
     * @param request Commande de la quantité de paniers souhaitée
     * @return Réponse HTTP avec statut de confirmation
     */
    @POST
    @Path("/commanderPanier/{nom}")
    @Consumes("application/json")
    public Response commanderPanier(@PathParam("nom") String nomPanier, CommandeRequest request) {
        boolean success = service.commanderPanier(nomPanier, request.quantite);

        if (success) {
            return Response.status(Response.Status.OK).entity("Commande traitée avec succès").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Quantité insuffisante ou panier non disponible").build();
        }
    }

    /**
     * Endpoint pour ajouter un panier
     * @param panier Le panier à ajouter (reçu en JSON)
     * @return Réponse HTTP avec statut de confirmation
     */
    @POST
    @Path("/ajouter")
    @Consumes("application/json")
    public Response addPanier(Panier panier) {

        if (panier.getNom().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Le panier doit avoir un nom et des produits").build();
        }

        boolean success = service.addPanier(panier);

        if (success) {
            return Response.status(Response.Status.CREATED).entity("Panier ajouté avec succès").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Échec de l'ajout du panier").build();
        }
    }

    /**
     *
     * @param nomPanier nom du panier a modifier
     * @param produitRequest correspond au produit a ajouter et a sa quantité
     * @return
     */
    @POST
    @Path("/ajouter/{nom}")
    @Consumes("application/json")
    public Response addProduct(@PathParam("nom") String nomPanier, ProduitRequest produitRequest) {
        // ProduitRequest contient le nom du produit et la quantité à ajouter
        String produitNom = produitRequest.getProduit();
        int quantite = produitRequest.getQuantite();

        boolean success = service.ajouterProduitAuPanier(nomPanier, produitNom, quantite);

        if (success) {
            return Response.status(Response.Status.OK)
                    .entity("Produit ajouté avec succès au panier")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Le produit ou le panier n'existe pas")
                    .build();
        }
    }

    @POST
    @Path("/retirer/{nom}")
    @Consumes("application/json")
    public Response retirerProduit(@PathParam("nom") String nomPanier, ProduitRequest produitRequest) {
        // ProduitRequest contient le nom du produit et la quantité à retirer
        String produitNom = produitRequest.getProduit();
        int quantite = produitRequest.getQuantite();

        boolean success = service.retirerProduitDuPanier(nomPanier, produitNom, quantite);

        if (success) {
            return Response.status(Response.Status.OK)
                    .entity("Produit retiré avec succès du panier")
                    .build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erreur lors de la suppression du produit du panier")
                    .build();
        }
    }

}