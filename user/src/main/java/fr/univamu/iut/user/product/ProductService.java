package fr.univamu.iut.user.product;

import fr.univamu.iut.user.product.Product;
import fr.univamu.iut.user.product.ProductRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class ProductService {

    /**
     * Objet permettant d'accéder au dépôt où sont stockées les informations sur les produits
     */
    protected ProductRepositoryInterface productRepo ;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param productRepo objet implémentant l'interface d'accès aux données
     */
    public  ProductService( ProductRepositoryInterface productRepo) {
        this.productRepo = productRepo;
    }




    public boolean updateProduct(String name, Product product) {
        return productRepo.updateProduct(name, product.type);
    }

    /**
     * Méthode retournant les informations (sans mail et mot de passe) sur les utilisateurs au format JSON
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getAllProductsJSON(){

        ArrayList<Product> allProducts = productRepo.getAllProducts();



        // création du json et conversion de la liste de produits
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allProducts);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }

        return result;
    }

    /**
     * Méthode retournant au format JSON les informations sur un utilisateur recherché
     * @param id l'identifiant de l'utilisateur recherché
     * @return une chaîne de caractère contenant les informations au format JSON
     */
    public String getProductJSON( String id ){
        String result = null;
       Product myProduct = productRepo.getProduct(id);

        // si le produit a été trouvé
        if( myProduct != null ) {

            // création du json et conversion du produit
            try (Jsonb jsonb = JsonbBuilder.create()) {
                result = jsonb.toJson(myProduct);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
}
