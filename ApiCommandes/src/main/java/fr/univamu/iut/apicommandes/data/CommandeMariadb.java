package fr.univamu.iut.apicommandes.data;

import fr.univamu.iut.apicommandes.domain.Commande;
import fr.univamu.iut.apicommandes.interfaces.CommandeInterface;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant d'accéder aux commandes stockées dans une base de données MariaDB
 */
public class CommandeMariadb implements CommandeInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe à utiliser
     */
    public CommandeMariadb(String infoConnection, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
    public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Commande getCommande(String clientId, String nomPanier) {
        Commande selectedCommande = null;
        String query = "SELECT * FROM Commande WHERE clientId=? AND nomPanier=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, clientId);
            ps.setString(2, nomPanier);
            ResultSet result = ps.executeQuery();

            if (result.next()) {
                double prix = result.getDouble("prix");
                String localisationRelai = result.getString("localisationRelai");
                Date dateRetrait = result.getDate("dateRetrait");

                selectedCommande = new Commande(nomPanier, clientId, prix, localisationRelai, dateRetrait.toLocalDate());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedCommande;
    }

    @Override
    public ArrayList<Commande> getCommandesClient(String clientId) {
        ArrayList<Commande> listCommandes = new ArrayList<>();
        String query = "SELECT * FROM Commande WHERE clientId=?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, clientId);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String nomPanier = result.getString("nomPanier");
                double prix = result.getDouble("prix");
                String localisationRelai = result.getString("localisationRelai");
                Date dateRetrait = result.getDate("dateRetrait");

                Commande currentCommande = new Commande(nomPanier, clientId, prix, localisationRelai, dateRetrait.toLocalDate());
                listCommandes.add(currentCommande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCommandes;
    }

    @Override
    public ArrayList<Commande> getAllCommandes() {
        ArrayList<Commande> listCommandes = new ArrayList<>();
        String query = "SELECT * FROM Commande";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String nomPanier = result.getString("nomPanier");
                String clientId = result.getString("clientId");
                double prix = result.getDouble("prix");
                String localisationRelai = result.getString("localisationRelai");
                Date dateRetrait = result.getDate("dateRetrait");

                Commande currentCommande = new Commande(nomPanier, clientId, prix, localisationRelai, dateRetrait.toLocalDate());
                listCommandes.add(currentCommande);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listCommandes;
    }

    @Override
    public boolean updateCommande(String clientId, String nomPanier, double prix, String localisationRelai, Date dateRetrait) {
        String query = "UPDATE Commande SET prix=?, localisationRelai=?, dateRetrait=? WHERE clientId=? AND nomPanier=?";
        int nbRowModified = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setDouble(1, prix);
            ps.setString(2, localisationRelai);
            ps.setDate(3, dateRetrait);
            ps.setString(4, clientId);
            ps.setString(5, nomPanier);

            nbRowModified = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowModified != 0);
    }

    @Override
    public boolean registerCommande(String userId, String panierNom, float prix, String relaiParDéfaut, Date date) {
        String query = "INSERT INTO Commande (clientId, nomPanier, prix, localisationRelai, dateRetrait) VALUES (?, ?, ?, ?, ?)";
        int nbRowInserted = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, userId);
            ps.setString(2, panierNom);
            ps.setFloat(3, prix);
            ps.setString(4, relaiParDéfaut);
            ps.setDate(5, date);

            nbRowInserted = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowInserted != 0);
    }

    @Override
    public boolean deleteCommande(String clientId, String nomPanier) {
        String query = "DELETE FROM Commande WHERE clientId=? AND nomPanier=?";
        int nbRowDeleted = 0;

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, clientId);
            ps.setString(2, nomPanier);

            nbRowDeleted = ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return (nbRowDeleted != 0);
    }

}
