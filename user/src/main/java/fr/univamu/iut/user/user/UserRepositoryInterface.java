package fr.univamu.iut.user.user;

import java.util.*;

/**
 * Interface d'accès aux données des livres
 */
public interface UserRepositoryInterface {

    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les livres
     */
    public void close();

    /**
     * Méthode retournant le livre dont la référence est passée en paramètre
     * @param id identifiant du livre recherché
     * @return un objet Book représentant le livre recherché
     */
    public User getUser( String id );

    /**
     * Méthode retournant la liste des livres
     * @return une liste d'objets livres
     */
    public ArrayList<User> getAllUsers() ;


    public boolean updateUser( String id, String name, String mail, String pwd);



}

