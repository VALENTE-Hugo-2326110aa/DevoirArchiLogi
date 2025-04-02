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

    public function getClientId()
    {
        return $this->clientId;
    }

    public function getDateRetrait()
    {
        return $this->dateRetrait;
    }

    public function getLocalisation()
    {
        return $this->localisation;
    }

    public function getNomPanier()
    {
        return $this->nomPanier;
    }

    public function getPrix()
    {
        return $this->prix;
    }

}