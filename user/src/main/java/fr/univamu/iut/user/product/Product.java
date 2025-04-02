package fr.univamu.iut.user.product;

public class Product {

    protected String name;
    protected String type;

    public Product(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getNom() {
        return name;
    }

    public void setNom(String nom) {
        this.name = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
