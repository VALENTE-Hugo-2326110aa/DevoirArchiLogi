package fr.univamu.iut.apipaniers;

import java.util.*;

public interface ProductRepositoryInterface {
   /**
    *  Méthode fermant le dépôt où sont stockées les informations sur les produits
    */
    public void close();

    /**
     * Méthode retournant le produit dont la référence est passée en paramètre
     * @param name nom du produit recherché
     * @return un objet Product représentant le produit recherché
     */
    public Product getProduct(String name );

    /**
     * Méthode retournant la liste des produits
     * @return une liste d'objets produits
     */
    public ArrayList<Product> getAllProducts() ;


    public boolean updateProduct(  String name,  String type);



}
