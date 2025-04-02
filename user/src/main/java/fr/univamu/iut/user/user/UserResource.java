package fr.univamu.iut.user.user;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


/**
 * Ressource associée aux livres
 * (point d'accès de l'API REST)
 */
@Path("/users")
public class UserResource {

    /**
     * Service utilisé pour accéder aux données des livres et récupérer/modifier leurs informations
     */
    private UserService service;

    /**
     * Constructeur par défaut
     */
     public UserResource(){}


    public @Inject UserResource( UserRepositoryInterface userRepo ){
        this.service = new UserService( userRepo) ;
    }
    /**
     * Constructeur permettant d'initialiser le service d'accès aux livres
     */
    public UserResource( UserService service ){
        this.service = service;
    }

    /**
     * Enpoint permettant de publier de tous les livres enregistrés
     * @return la liste des livres (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllUsers() {
        return service.getAllUsersJSON();
    }


    @GET
    @Path("{id}")
    @Produces("application/json")
    public String getUser( @PathParam("id") String id){

        String result = service.getUserJSON(id);

        // si le livre n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }


    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response updateUser(@PathParam("id") String id, User user ){

        // si le livre n'a pas été trouvé
        if( ! service.updateUser(id, user) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }
}
