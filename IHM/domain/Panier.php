<?php

namespace domain;

class Panier
{
    private $maj;
    private $name;
    private $prix;
    private $produits;
    private $quantite;

    public function __construct($maj, $name, $prix, $quantite)
    {
        $this->maj = $maj;
        $this->name = $name;
        $this->prix = $prix;
        $this->produits = [];
        $this->quantite = $quantite;
    }

    public function addProduit($produit, $quantity)
    {
        if (array_key_exists($produit, $this->produits)) {
            $this->quantities[$produit] += $quantity;
        } else {
            $this->produits[$produit] = $produit;
            $this->quantities[$produit] = $quantity;
        }
    }

    public function removeProduit($produit)
    {
        if (array_key_exists($produit, $this->produits)) {
            unset($this->produits[$produit]);
            unset($this->quantities[$produit]);
        }
    }

    // Setters
    public function setQuantite($quandtite) {
        $this->quantite = $quandtite;
    }

    public function getQuantite() {
        return $this->quantite;
    }

    // Decrement la quantité d'un produit
    public function decrementQuantite()
    {
        if ($this->quantite > 0) {
            $this->quantite--;
        }
    }

    public function getMaj()
    {
        return $this->maj;
    }

    public function getName()
    {
        return $this->name;
    }

    public function getPrix()
    {
        return $this->prix;
    }

    public function getProduits()
    {
        return $this->produits;
    }

    public function getQuantities()
    {
        return $this->quantities;
    }

}