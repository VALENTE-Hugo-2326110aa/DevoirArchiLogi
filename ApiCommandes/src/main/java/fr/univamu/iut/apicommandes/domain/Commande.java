package fr.univamu.iut.apicommandes.domain;

import java.time.LocalDate;

/**
 * Classe représentant une commande
 */
public class Commande {

    /**
     * Nom du panier associé à la commande
     */
    protected String nomPanier;

    /**
     * Identifiant du client ayant passé la commande
     */
    protected String clientId;

    /**
     * Prix total de la commande
     */
    protected double prix;

    /**
     * Localisation du relai pour le retrait du panier
     */
    protected String localisationRelai;

    /**
     * Date de retrait du panier
     */
    protected LocalDate dateRetrait;

    /**
     * Constructeur par défaut
     */
    public Commande() {
    }

    /**
     * Constructeur de commande
     * @param nomPanier Nom du panier
     * @param clientId Identifiant du client
     * @param prix Prix total
     * @param localisationRelai Localisation du relai pour le retrait
     * @param dateRetrait Date de retrait
     */
    public Commande(String nomPanier, String clientId, double prix, String localisationRelai, LocalDate dateRetrait) {
        this.nomPanier = nomPanier;
        this.clientId = clientId;
        this.prix = prix;
        this.localisationRelai = localisationRelai;
        this.dateRetrait = dateRetrait;
    }

    public String getNomPanier() {
        return nomPanier;
    }

    public void setNomPanier(String nomPanier) {
        this.nomPanier = nomPanier;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getLocalisationRelai() {
        return localisationRelai;
    }

    public void setLocalisationRelai(String localisationRelai) {
        this.localisationRelai = localisationRelai;
    }

    public LocalDate getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(LocalDate dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "nomPanier='" + nomPanier + '\'' +
                ", clientId='" + clientId + '\'' +
                ", prix=" + prix +
                ", localisationRelai='" + localisationRelai + '\'' +
                ", dateRetrait=" + dateRetrait +
                '}';
    }
}
