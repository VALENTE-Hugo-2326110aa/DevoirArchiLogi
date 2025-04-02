package fr.univamu.iut.apicommandes.APIS;

import fr.univamu.iut.apicommandes.interfaces.UserRepositoryInterface;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;

public class UserRepositoryAPI implements UserRepositoryInterface {

    /**
     * URL de l'API des utilisateurs
     */
    private String url;

    /**
     * Constructeur initialisant l'url de l'API
     * @param url chaîne de caractères avec l'url de l'API
     */
    public UserRepositoryAPI(String url){
        this.url = url;
    }

    @Override
    public void close() {}

    @Override
    public User getUser(String id) {
        User user = null;
        Client client = ClientBuilder.newClient();
        WebTarget userResource = client.target(url).path("users/" + id);
        Response response = userResource.request(MediaType.APPLICATION_JSON).get();

        if(response.getStatus() == 200) {
            user = response.readEntity(User.class);
        }
        client.close();
        return user;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        Client client = ClientBuilder.newClient();
        WebTarget userResource = client.target(url).path("users");
        Response response = userResource.request(MediaType.APPLICATION_JSON).get();

        if(response.getStatus() == 200) {
            User[] usersArray = response.readEntity(User[].class);
            users.addAll(Arrays.asList(usersArray));
        }
        client.close();
        return users;
    }

    @Override
    public boolean updateUser(String id, String name, String mail, String pwd) {
        boolean result = false;
        User updatedUser = new User(id, name, mail, pwd);

        Client client = ClientBuilder.newClient();
        WebTarget userResource = client.target(url).path("users/" + id);
        Response response = userResource.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(updatedUser, MediaType.APPLICATION_JSON));

        if(response.getStatus() == 200) {
            result = true;
        }
        client.close();
        return result;
    }
}
