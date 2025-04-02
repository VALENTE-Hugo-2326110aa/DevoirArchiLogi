package fr.univamu.iut.apicommandes;

import fr.univamu.iut.apicommandes.domain.Commande;
import fr.univamu.iut.apicommandes.interfaces.CommandeInterface;
import fr.univamu.iut.apicommandes.interfaces.PanierRepositoryInterface;
import fr.univamu.iut.apicommandes.interfaces.UserRepositoryInterface;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * Ressource associée aux commandes
 * (point d'accès de l'API REST)
 */
@Path("/commandes")
public class CommandeResource {

    /**
     * Service utilisé pour accéder aux données des commandes et récupérer/modifier leurs informations
     */
    private CommandeService service;

    /**
     * Constructeur par défaut
     */
    public CommandeResource() {}

    /**
     * Constructeur permettant d'initialiser le service avec les interfaces d'accès aux données
     * @param commandeRepo objet implémentant l'interface d'accès aux données des commandes
     */
    public @Inject CommandeResource(CommandeInterface commandeRepo, PanierRepositoryInterface panierRepo, UserRepositoryInterface userRepo) {
        this.service = new CommandeService(commandeRepo, panierRepo, userRepo);

    }

    /**
     * Endpoint permettant de récupérer toutes les commandes enregistrées
     * @return la liste des commandes (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllCommandes() {
        return service.getAllCommandesJSON();
    }

    /**
     * Endpoint permettant de récupérer les informations d'une commande spécifique
     * @param clientId l'identifiant du client associé à la commande
     * @param nomPanier le nom du panier associé à la commande
     * @return les informations de la commande au format JSON
     */
    @GET
    @Path("{clientId}/{nomPanier}")
    @Produces("application/json")
    public String getCommande(@PathParam("clientId") String clientId, @PathParam("nomPanier") String nomPanier) {
        String result = service.getCommandeJSON(clientId, nomPanier);
        return (result == null) ? "{}" : result;
    }

    /**
     * Endpoint permettant de récupérer les informations d'une commande spécifique
     * @param clientId l'identifiant du client associé à la commande
     * @return les informations de la commande au format JSON
     */
    @GET
    @Path("{clientId}")
    @Produces("application/json")
    public String getCommandesClient(@PathParam("clientId") String clientId) {
        String result = service.getCommandesClientJSON(clientId);
        return (result == null) ? "{}" : result;
    }

    /**
     * Endpoint permettant de mettre à jour les informations d'une commande spécifique
     * @param clientId l'identifiant du client associé à la commande
     * @param nomPanier le nom du panier associé à la commande
     * @param commande l'objet Commande contenant les nouvelles informations
     * @return une réponse indiquant le succès ou l'échec de la mise à jour
     */
    @PUT
    @Path("{clientId}/{nomPanier}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateCommande(@PathParam("clientId") String clientId, @PathParam("nomPanier") String nomPanier, Commande commande) {
        boolean updated = service.updateCommande(clientId, nomPanier, commande);
        if (updated) {
            return Response.status(Response.Status.OK).entity("{\"status\":\"success\"}").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"status\":\"failed\"}").build();
        }
    }

    /**
     * Endpoint permettant de soumettre une nouvelle commande d'un panier par un utilisateur
     * @param id identifiant de l'utilisateur
     * @param nomPanier nom du panier à commander
     * @return un objet Response indiquant "registered" si la commande a été enregistrée ou une erreur "conflict" sinon
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response registerCommande(@FormParam("id") String id, @FormParam("nomPanier") String nomPanier) {
        if (service.registerCommande(id, nomPanier)) {
            return Response.ok("registered").build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    /**
     * Endpoint permettant de supprimer une commande
     * @param clientId identifiant du client associé à la commande
     * @param nomPanier nom du panier associé à la commande
     * @return un objet Response indiquant "deleted" si la commande a été supprimée, ou une erreur "not found" sinon
     */
    @DELETE
    @Path("{clientId}/{nomPanier}")
    public Response removeCommande(@PathParam("clientId") String clientId, @PathParam("nomPanier") String nomPanier) {
        if (service.removeCommande(clientId, nomPanier)) {
            return Response.ok("deleted").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
