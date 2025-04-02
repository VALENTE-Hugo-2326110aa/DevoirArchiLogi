package fr.univamu.iut.apicommandes.APIS;

import fr.univamu.iut.apicommandes.interfaces.PanierRepositoryInterface;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class PanierRepositoryAPI implements PanierRepositoryInterface {

    /**
     * URL de l'API des paniers
     */
    private String url;

    /**
     * Constructeur initialisant l'url de l'API
     * @param url chaîne de caractères avec l'url de l'API
     */
    public PanierRepositoryAPI(String url){
        this.url = url;
    }

    @Override
    public void close() {}

    @Override
    public Panier getPanier(String nom) {
        Panier panier = null;
        Client client = ClientBuilder.newClient();
        WebTarget panierResource = client.target(url).path("paniers/" + nom);
        Response response = panierResource.request(MediaType.APPLICATION_JSON).get();

        if(response.getStatus() == 200) {
            panier = response.readEntity(Panier.class);
        }
        client.close();
        return panier;
    }

    @Override
    public ArrayList<Panier> getAllPaniers() {
        ArrayList<Panier> paniers = new ArrayList<>();
        Client client = ClientBuilder.newClient();
        WebTarget panierResource = client.target(url).path("paniers");
        Response response = panierResource.request(MediaType.APPLICATION_JSON).get();

        if(response.getStatus() == 200) {
            Panier[] paniersArray = response.readEntity(Panier[].class);
            paniers.addAll(Arrays.asList(paniersArray));
        }
        client.close();
        return paniers;
    }

    @Override
    public boolean updatePanier(String nom, int quantite, float prix, Map<String, Integer> produits, String maj) {
        boolean result = false;
        Panier updatedPanier = new Panier(nom, quantite, prix, produits, maj);

        Client client = ClientBuilder.newClient();
        WebTarget panierResource = client.target(url).path("paniers/" + nom);
        Response response = panierResource.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updatedPanier, MediaType.APPLICATION_JSON));

        if(response.getStatus() == 200) {
            result = true;
        }
        client.close();
        return result;
    }

    @Override
    public boolean addPanier(Panier panier) {
        boolean result = false;
        Client client = ClientBuilder.newClient();
        WebTarget panierResource = client.target(url).path("paniers");
        Response response = panierResource.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(panier, MediaType.APPLICATION_JSON));

        if(response.getStatus() == 201) {
            result = true;
        }
        client.close();
        return result;
    }

    @Override
    public boolean deletePanier(String nom) {
        boolean result = false;
        Client client = ClientBuilder.newClient();
        WebTarget panierResource = client.target(url).path("paniers/" + nom);
        Response response = panierResource.request(MediaType.APPLICATION_JSON).delete();

        if(response.getStatus() == 200) {
            result = true;
        }
        client.close();
        return result;
    }
}
