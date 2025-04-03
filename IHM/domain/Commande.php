<?php

namespace domain;

class Commande
{
    private $clientId;
    private $dateRetrait;
    private $localisation;
    private $nomPanier;
    private $prix;

    public function __construct($clientId, $dateRetrait, $localisation, $nomPanier, $prix)
    {
        $this->clientId = $clientId;
        $this->dateRetrait = $dateRetrait;
        $this->localisation = $localisation;
        $this->nomPanier = $nomPanier;
        $this->prix = $prix;
    }

    /**
     * Getters de l'attribut clientId
     */
    public function getClientId()
    {
        return $this->clientId;
    }

    /**
     * Getters de l'attribut dateRetrait
     */
    public function getDateRetrait()
    {
        return $this->dateRetrait;
    }

    /**
     * Getters de l'attribut localisation
     */
    public function getLocalisation()
    {
        return $this->localisation;
    }

    /**
     * Getters de l'attribut nomPanier
     */
    public function getNomPanier()
    {
        return $this->nomPanier;
    }

    /**
     * Getters de l'attribut prix
     */
    public function getPrix()
    {
        return $this->prix;
    }

}