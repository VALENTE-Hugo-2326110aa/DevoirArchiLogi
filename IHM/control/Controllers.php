<?php

namespace control;

use data\CommandesData;
use PaniersData;
use service\CommandeChecking;
use service\PanierChecking;
use service\UserChecking;

class Controllers
{
    /**
     * Authentification de l'utilisateur
     * @param UserChecking $userCheck
     * @param $dataUsers
     * @return string|null
     */
    public function authenficateAction(UserChecking $userCheck, $dataUsers) {
        // Regarde à déja une session ouverte
        if (!isset($_SESSION['login'])) {

            if (isset($_POST['login']) && isset($_POST['password'])) {
                if (!$userCheck->authenticate($_POST['login'], $_POST['password'], $dataUsers)) {
                    // retourne une erreur si le compte n'est pas enregistré
                    $error = 'bad login or pwd';
                    return $error;
                } else {
                    // Enregistrement des informations de session après une authentification réussie
                    $_SESSION['login'] = $_POST['login'];
                }

            } else {
                // retourne une erreur si la personne ne passe pas par le forumlaire de création ou de connexion
                $error = 'not connected';
                return $error;
            }

        }
    }

    /**
     * Affiche la page des paniers
     * @param PaniersData $dataPaniers
     * @param PanierChecking $paniersCheck
     * @return array|null
     */
    public function paniersAction(\PaniersData $dataPaniers, \service\PanierChecking $paniersCheck)
    {
        // Récupération de toutes les annonces
        $paniersCheck->getAllPaniers($dataPaniers);
    }

    /**
     * Affiche la page d'un panier
     * @param $panierId
     * @param PaniersData $dataPaniers
     * @param PanierChecking $paniersCheck
     */
    public function panierAction($panierId, \PaniersData $dataPaniers, \service\PanierChecking $paniersCheck)
    {
        // Récupération d'une annonce
        $paniersCheck->getPanier($panierId, $dataPaniers);
    }

    /**
     * Enregistre une commande
     * @param CommandesData $dataCommandes
     * @param CommandeChecking $commandesCheck
     * @param $clientId
     * @param $nomPanier
     */
    public function commandesAction(CommandesData $dataCommandes, CommandeChecking $commandesCheck, $clientId)
    {
        // Récupération de toutes les commandes
        $commandesCheck->getCommandesByClient($dataCommandes, $clientId);
    }

}