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

    /**
     * Ajoute un produit au panier
     * @param $produit
     * @param $quantity
     */
    public function addProduit($produit, $quantity)
    {
        if (array_key_exists($produit, $this->produits)) {
            $this->quantities[$produit] += $quantity;
        } else {
            $this->produits[$produit] = $produit;
            $this->quantities[$produit] = $quantity;
        }
    }

    /**
     * Supprime un produit du panier
     * @param $produit
     * @return void
     */
    public function removeProduit($produit)
    {
        if (array_key_exists($produit, $this->produits)) {
            unset($this->produits[$produit]);
            unset($this->quantities[$produit]);
        }
    }

    /**
     * Modifie la quantité de panier disponible
     * @param $produit
     * @param $quantity
     */
    public function setQuantite($quandtite) {
        $this->quantite = $quandtite;
    }

    /**
     * Retourne la quantité de panier disponible
     * @return int
     */
    public function getQuantite() {
        return $this->quantite;
    }

    /**
     * Incrémente la quantité de produit dans le panier
     * @return void
     */
    public function decrementQuantite()
    {
        if ($this->quantite > 0) {
            $this->quantite--;
        }
    }

    /**
     * Retourne la date de mise à jour du panier
     * @return string
     */
    public function getMaj()
    {
        return $this->maj;
    }

    /**
     * Retourne le nom du panier
     * @return string
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * Retourne le prix du panier
     * @return float
     */
    public function getPrix()
    {
        return $this->prix;
    }

    /**
     * Retourne les produits du panier
     * @return array
     */
    public function getProduits()
    {
        return $this->produits;
    }

    /**
     * Retourne les quantités de produits dans le panier
     * @return array
     */
    public function getQuantities()
    {
        return $this->quantities;
    }

}