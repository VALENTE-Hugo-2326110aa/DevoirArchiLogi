<?php

namespace service;

class PanierChecking
{

    private $paniersTxt;

    /**
     * Retourne les paniers d'un client
     * @return array|null
     */
    public function getPaniersTxt()
    {
        return $this->paniersTxt;
    }

    /**
     * Donne les paniers d'un client
     * @param \PaniersData $data
     * @return array|null
     */
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

    /**
     * Donne les paniers d'un client
     * @param $id
     * @param \PaniersData $data
     * @return array|null
     */
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