<?php

namespace data;

class CommandesData
{

    private $URL = 'http://localhost:8163/ApiCommandes-1.0-SNAPSHOT/api/commandes/';

    /**
     * Récupère les commandes d'un client
     * @param $clientId
     * @return mixed|null
     */
    public function getCommandesByClient($clientId)
    {
        $url = $this->URL . urlencode($clientId);

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPGET, true);

        $response = curl_exec($ch);
        if (curl_errno($ch)) {
            return null;
        }
        curl_close($ch);
        $commandes = json_decode($response, true);
        if ($commandes == null) {
            return null;
        }
        return $commandes;
    }

    /**
     * Enregistre une commande
     * @param $clientId
     * @param $nomPanier
     * @return bool
     */
    public function registerCommand($clientId, $nomPanier) {

        $postData = http_build_query([
            'id' => $clientId,
            'nomPanier' => $nomPanier
        ]);

        $ch = curl_init();

        curl_setopt($ch, CURLOPT_URL, $this->URL);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $postData);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, [
            'Content-Type: application/x-www-form-urlencoded'
        ]);

        curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

        curl_close($ch);

        if ($httpCode == 200) {
            return true; // Commande enregistrée avec succès
        } else {
            return false; // Erreur lors de l'enregistrement de la commande
        }

    }

    /**
     * Supprime une commande
     * @param $clientId
     * @param $nomPanier
     * @return bool
     */
    public function removeCommand($clientId, $nomPanier)
    {
        $url = $this->URL . urlencode($clientId) . '/' . urlencode($nomPanier);

        $ch = curl_init();

        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, "DELETE"); // Utiliser DELETE
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

        curl_close($ch);

        if ($httpCode == 200) {
            return true; // Commande supprimée avec succès
        } else {
            return false; // Erreur lors de la suppression de la commande
        }
    }

}