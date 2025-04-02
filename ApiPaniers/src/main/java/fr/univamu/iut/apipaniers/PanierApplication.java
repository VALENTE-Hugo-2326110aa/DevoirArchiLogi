package fr.univamu.iut.apipaniers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api")
@ApplicationScoped
public class PanierApplication extends Application {

    @Produces
    private PanierRepositoryInterface openDbConnection(){
        PanierRepositoryMariadb db = null;

        try{
            db = new PanierRepositoryMariadb("jdbc:mariadb://mysql-quentinfournieriut.alwaysdata.net/quentinfournieriut_agricole", "395478_agricole", "lesPetitsPaniersFruites");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    private void closeDbConnection(@Disposes PanierRepositoryInterface panierRepo ) {
        panierRepo.close();
    }

    /**
     * Méthode appelée par l'API CDI pour injecter l'API Produit au moment de la création de la ressource.
     * @return une instance de l'API avec l'URL à utiliser.
     */
    @Produces
    private ProductRepositoryInterface connectProductApi(){
        return new ProductRepositoryAPI("http://localhost:8080/user_product-1.0-SNAPSHOT/api/");
    }
}