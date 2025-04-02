<?php

namespace service;

class PanierChecking
{

    private $paniersTxt;

    public function getPaniersTxt()
    {
        return $this->paniersTxt;
    }

    public function getAllPaniers(\PaniersData $data) {
        $paniers = $data->getAllPaniers();

        $this->paniersTxt = array();
        foreach ($paniers as $panier) {
            $this->paniersTxt[] = [
                'maj' => $panier['maj'],
                'title' => $panier['nom'],
                'body' => json_encode($panier['produits']),
                'date' => $panier['maj'],
                'prix' => $panier['prix'],
                'quantite' => $panier['quantite']
            ];
        }
    }

    public function getPanier($id, \PaniersData $data) {
        $panier = $data->getPanier($id);

        $this->paniersTxt[] = [
            'maj' => $panier['maj'],
            'title' => $panier['nom'],
            'body' => json_encode($panier['produits']),
            'date' => $panier['maj'],
            'prix' => $panier['prix'],
            'quantite' => $panier['quantite']
        ];

    }

}