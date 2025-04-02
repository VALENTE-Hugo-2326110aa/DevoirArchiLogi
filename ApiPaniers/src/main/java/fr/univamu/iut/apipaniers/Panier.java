package fr.univamu.iut.apipaniers;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Panier {

    private String nom;
    private int quantite;
    private float prix;
    private final Map<String, Integer> produits; // Clé : Nom du produit, Valeur : Quantité
    private String maj; // Date de la dernière mise à jour au format "yyyy-MM-dd"

    // Constructeur par défaut
    public Panier() {
        this.produits = new HashMap<>();
    }

    // Constructeur avec les paramètres pour la désérialisation du JSON
    @JsonbCreator
    public Panier(@JsonbProperty("nom") String nom,
                  @JsonbProperty("quantite") int quantite,
                  @JsonbProperty("prix") float prix,
                  @JsonbProperty("produits") Map<String, Integer> produits,
                  @JsonbProperty("maj") String maj) {
        this.nom = nom != null ? nom : ""; // Validation pour éviter un nom vide
        this.quantite = quantite;
        this.prix = prix;
        this.produits = produits != null ? produits : new HashMap<>();
        updateMaj();
    }

    public void ajouterQuantite(int quantite) {
        this.quantite += quantite;
        updateMaj(); // Mise à jour de la date maj
    }

    public void enleverQuantite(int quantite) {
        this.quantite -= quantite;
        updateMaj(); // Mise à jour de la date maj
    }

    public void setPrix(float prix) {
        this.prix = prix;
        updateMaj(); // Mise à jour de la date maj
    }

    public void ajouterProduits(String produit, int quantite) {
        this.produits.put(produit, this.produits.getOrDefault(produit, 0) + quantite);
        updateMaj(); // Mise à jour de la date maj
    }

    public void retirerProduits(String produit, int quantite) {
        if (this.produits.containsKey(produit)) {
            int nouvelleQuantite = this.produits.get(produit) - quantite;
            if (nouvelleQuantite > 0) {
                this.produits.put(produit, nouvelleQuantite);
            } else {
                this.produits.remove(produit);
            }
            updateMaj(); // Mise à jour de la date maj
        }
    }

    // Getter pour la date de mise à jour au format "yyyy-MM-dd"
    public String getMaj() {
        return maj;
    }

    // Setter pour la date de mise à jour (on reçoit une chaîne au format "yyyy-MM-dd")
    public void setMaj(String maj) {
        this.maj = maj;
    }

    // Mise à jour de la date de mise à jour (format "yyyy-MM-dd")
    private void updateMaj() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.maj = sdf.format(new Date()); // Formatage de la date actuelle
    }

    public String getNom() {
        return nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public float getPrix() {
        return prix;
    }

    public Map<String, Integer> getProduits() {
        return this.produits;
    }

    @Override
    public String toString() {
        return "Panier{" +
                    "nom='" + nom + '\'' +
                    ", quantite=" + quantite +
                    ", prix=" + prix +
                    ", produits=" + produits +
                    ", miseAJour='" + maj + '\'' +
                '}';
    }
}
