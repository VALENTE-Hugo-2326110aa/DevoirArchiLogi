<?php

namespace control;

use data\CommandesData;
use service\CommandeChecking;
use service\UserChecking;

class Controllers
{

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

    public function paniersAction(\PaniersData $dataPaniers, \service\PanierChecking $paniersCheck)
    {
        // Récupération de toutes les annonces
        $paniersCheck->getAllPaniers($dataPaniers);
    }

    public function panierAction($panierId, \PaniersData $dataPaniers, \service\PanierChecking $paniersCheck)
    {
        // Récupération d'une annonce
        $paniersCheck->getPanier($panierId, $dataPaniers);
    }

    public function commandesAction(CommandesData $dataCommandes, CommandeChecking $commandesCheck, $clientId)
    {
        // Récupération de toutes les commandes
        $commandesCheck->getCommandesByClient($dataCommandes, $clientId);
    }

}