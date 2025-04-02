package fr.univamu.iut.apipaniers;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe permettant d'accèder aux paniers stockés dans une base de données Mariadb
 */
public class PanierRepositoryMariadb   implements PanierRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection ;

    /**
     * Constructeur de la classe
     * @param infoConnection chaîne de caractères avec les informations de connexion
     *                       (p.ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db
     * @param user chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd chaîne de caractères contenant le mot de passe à utiliser
     */
    public PanierRepositoryMariadb(String infoConnection, String user, String pwd ) throws java.sql.SQLException, java.lang.ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection( infoConnection, user, pwd ) ;
    }

    @Override
    public void close() {
        try{
            dbConnection.close();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public Panier getPanier(String nom) {

        Panier selectedPanier = null;

        String query = "SELECT * FROM Panier WHERE nom=?";

        String queryProduits = "SELECT * FROM Composant_Panier WHERE nom_panier=?";

        // construction et exécution d'une requête préparée
        try ( PreparedStatement ps = dbConnection.prepareStatement(query) ){
            ps.setString(1, nom);

            // exécution de la requête
            ResultSet result = ps.executeQuery();

            try (PreparedStatement psProduits = dbConnection.prepareStatement(queryProduits)) {
                psProduits.setString(1, nom);

                // exécution de la requête produits
                ResultSet rsProduits = psProduits.executeQuery();

                // récupération du premier (et seul) tuple résultat
                // (si le nom du panier est valide)
                if (result.next()) {
                    int quantite = result.getInt("quantite");
                    float prix = result.getFloat("prix");
                    String maj = result.getString("maj"); // La date au format "yyyy-MM-dd"

                    Map<String, Integer> produits = new HashMap<>();
                    // Remplissage de la map avec les produits et quantités
                    while (rsProduits.next()) {
                        String nomProduit = rsProduits.getString("nom_produit");
                        int quantiteProduit = rsProduits.getInt("quantite_produit");
                        produits.put(nomProduit, quantiteProduit);
                    }

                    // création et initialisation de l'objet Panier
                    selectedPanier = new Panier(nom, quantite, prix, produits, maj);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return selectedPanier;
    }

    @Override
    public ArrayList<Panier> getAllPaniers() {
        ArrayList<Panier> listPaniers = new ArrayList<>();

        String query = "SELECT * FROM Panier";
        String queryProduits = "SELECT * FROM Composant_Panier WHERE nom_panier=?";

        // Construction et exécution de la requête principale
        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ResultSet result = ps.executeQuery();

            // Parcours des paniers
            while (result.next()) {
                String nom = result.getString("nom");
                int quantite = result.getInt("quantite");
                float prix = result.getFloat("prix");
                String maj = result.getString("maj"); // La date au format "yyyy-MM-dd"

                // Initialisation de la map des produits
                Map<String, Integer> produits = new HashMap<>();

                // Requête pour récupérer les produits associés au panier courant
                try (PreparedStatement psProduits = dbConnection.prepareStatement(queryProduits)) {
                    psProduits.setString(1, nom);
                    ResultSet rsProduits = psProduits.executeQuery();

                    // Ajout des produits à la map
                    while (rsProduits.next()) {
                        String nomProduit = rsProduits.getString("nom_produit");
                        int quantiteProduit = rsProduits.getInt("quantite_produit");
                        produits.put(nomProduit, quantiteProduit);
                    }
                }

                // Création et ajout du panier avec ses produits
                Panier currentPanier = new Panier(nom, quantite, prix, produits, maj);
                listPaniers.add(currentPanier);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listPaniers;
    }


    @Override
    public boolean updatePanier(String nom, int quantite, float prix, Map<String, Integer> produits, String date) {
        String queryPanier = "UPDATE Panier SET quantite=?, prix=?, maj=? WHERE nom=?";
        String deleteProduits = "DELETE FROM Composant_Panier WHERE nom_panier=?";
        String insertProduits = "INSERT INTO Composant_Panier (nom_panier, nom_produit, quantite_produit) VALUES (?, ?, ?)";

        int nbRowModified = 0;

        try {
            // Désactivation de l'auto-commit pour effectuer une transaction
            dbConnection.setAutoCommit(false);

            // Mise à jour des informations du panier
            try (PreparedStatement ps = dbConnection.prepareStatement(queryPanier)) {
                ps.setInt(1, quantite);
                ps.setFloat(2, prix);
                ps.setString(3, date);
                ps.setString(4, nom);
                nbRowModified = ps.executeUpdate();
            }

            // Suppression des anciens produits du panier
            try (PreparedStatement psDelete = dbConnection.prepareStatement(deleteProduits)) {
                psDelete.setString(1, nom);
                psDelete.executeUpdate();
            }

            // Réinsertion des nouveaux produits
            try (PreparedStatement psInsert = dbConnection.prepareStatement(insertProduits)) {
                for (Map.Entry<String, Integer> entry : produits.entrySet()) {
                    psInsert.setString(1, nom);
                    psInsert.setString(2, entry.getKey());
                    psInsert.setInt(3, entry.getValue());
                    psInsert.executeUpdate();
                }
            }

            // Validation de la transaction
            dbConnection.commit();
            dbConnection.setAutoCommit(true);

        } catch (SQLException e) {
            try {
                dbConnection.rollback(); // Annulation en cas d'erreur
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            throw new RuntimeException(e);
        }

        return (nbRowModified != 0);
    }

    @Override
    public boolean addPanier(Panier panier) {
        String query = "INSERT INTO Panier (nom, quantite, prix) VALUES (?, ?, ?)";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, panier.getNom());
            ps.setInt(2, panier.getQuantite());
            ps.setFloat(3, panier.getPrix());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePanier(String nom) {
        String query = "DELETE FROM Panier WHERE nom = ?";

        try (PreparedStatement ps = dbConnection.prepareStatement(query)) {
            ps.setString(1, nom);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}