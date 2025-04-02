package fr.univamu.iut.user.product;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;



@Path("/products")
public class ProductResource  {
    /**
     * Service utilisé pour accéder aux données des produits et récupérer/modifier leurs informations
     */
    private ProductService service;

    /**
     * Constructeur par défaut
     */
    public ProductResource(){}


    public @Inject ProductResource(ProductRepositoryInterface productRepo ){
        this.service = new ProductService( productRepo) ;
    }
    /**
     * Constructeur permettant d'initialiser le service d'accès aux produits
     */
    public ProductResource( ProductService service ){
        this.service = service;
    }

    /**
     * Enpoint permettant de publier de tous les produits enregistrés
     * @return la liste des produits (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllProducts() {
        return service.getAllProductsJSON();
    }


    @GET
    @Path("{name}")
    @Produces("application/json")
    public String getUser( @PathParam("name") String id){

        String result = service.getProductJSON(id);

        // si le produit n'a pas été trouvé
        if( result == null )
            throw new NotFoundException();

        return result;
    }


    @PUT
    @Path("{name}")
    @Consumes("application/json")
    public Response updateProduct(@PathParam("name") String name, Product product ){

        // si le produit n'a pas été trouvé
        if( ! service.updateProduct(name, product) )
            throw new NotFoundException();
        else
            return Response.ok("updated").build();
    }


}
