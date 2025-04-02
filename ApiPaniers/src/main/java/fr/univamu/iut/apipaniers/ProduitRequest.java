package fr.univamu.iut.apipaniers;

public class ProduitRequest {

    private String produit; // Nom du produit à ajouter
    private int quantite;   // Quantité du produit

    // Constructeur par défaut
    public ProduitRequest() {}

    // Constructeur avec paramètres
    public ProduitRequest(String produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    // Getter et setter pour le nom du produit
    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    // Getter et setter pour la quantité
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
