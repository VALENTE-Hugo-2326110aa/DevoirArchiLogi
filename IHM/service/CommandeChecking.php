<?php

namespace service;

use data\CommandesData;

class CommandeChecking
{

    private $commandesTxt;

    public function getCommandesTxt()
    {
        return $this->commandesTxt;
    }

    public function getCommandesByClient(CommandesData $data, $clientId)
    {
        $commandes = $data->getCommandesByClient($clientId);

        $this->commandesTxt = array();
        foreach ($commandes as $commande) {
            $this->commandesTxt[] = [
                'clientId' => $commande['clientId'],
                'dateRetrait' => $commande['dateRetrait'],
                'localisationRelai' => $commande['localisationRelai'],
                'nomPanier' => $commande['nomPanier'],
                'prix' => $commande['prix']
            ];
        }
    }
}