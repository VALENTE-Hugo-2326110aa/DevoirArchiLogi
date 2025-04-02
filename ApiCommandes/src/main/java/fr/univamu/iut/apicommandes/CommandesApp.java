package fr.univamu.iut.apicommandes;

import fr.univamu.iut.apicommandes.APIS.PanierRepositoryAPI;
import fr.univamu.iut.apicommandes.APIS.UserRepositoryAPI;
import fr.univamu.iut.apicommandes.data.CommandeMariadb;
import fr.univamu.iut.apicommandes.interfaces.CommandeInterface;
import fr.univamu.iut.apicommandes.interfaces.PanierRepositoryInterface;
import fr.univamu.iut.apicommandes.interfaces.UserRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Application de l'API REST pour les commandes
 */
@ApplicationPath("/api")
@ApplicationScoped
public class CommandesApp extends Application {

    /**
     * Méthode appelée par l'API CDI pour injecter la connexion à la base de données au moment de la création
     * de la ressource.
     * @return un objet implémentant l'interface CommandeInterface utilisée pour accéder aux données des commandes.
     */
    @Produces
    private CommandeInterface openDbConnection(){
        CommandeMariadb db = null;

        try {
            // Paramètres de connexion à la base de données
            db = new CommandeMariadb("jdbc:mariadb://mysql-quentinfournieriut.alwaysdata.net/quentinfournieriut_agricole",
                    "395478_agricole", "lesPetitsPaniersFruites");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée.
     * @param commandeRepo la connexion à la base de données instanciée dans la méthode @openDbConnection.
     */
    private void closeDbConnection(@Disposes CommandeInterface commandeRepo) {
        commandeRepo.close();
    }

    /**
     * Méthode appelée par l'API CDI pour injecter l'API Panier au moment de la création de la ressource.
     * @return une instance de l'API avec l'URL à utiliser.
     */
    @Produces
    private PanierRepositoryInterface connectPanierApi(){
        return new PanierRepositoryAPI("http://localhost:8161/ApiPaniers-1.0-SNAPSHOT/api/");
    }

    /**
     * Méthode appelée par l'API CDI pour injecter l'API User au moment de la création de la ressource.
     * @return une instance de l'API avec l'URL à utiliser.
     */
    @Produces
    private UserRepositoryInterface connectUserApi(){
        return new UserRepositoryAPI("http://localhost:8080/user_product-1.0-SNAPSHOT/api/");
    }
}
