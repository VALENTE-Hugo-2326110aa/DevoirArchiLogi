<?php

class PaniersData
{

    private $URL = "http://localhost:8080/ApiPaniers-1.0-SNAPSHOT/api/paniers/";

    /**
     * Récupère tous les paniers
     * @return array|null
     */
    public function getAllPaniers() {

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $this->URL);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPGET, true);

        $response = curl_exec($ch);
        if (curl_errno($ch)) {
            return null;
        }
        curl_close($ch);
        $paniers = json_decode($response, true);
        if ($paniers == null) {
            return null;
        }
        return $paniers;
    }

    /**
     * Récupère un panier par son ID
     * @param $id
     * @return array|null
     */
    public function getPanier($id)
    {
        $url = $this->URL . urldecode($id);

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPGET, true);

        $response = curl_exec($ch);
        if (curl_errno($ch)) {
            return null;
        }
        curl_close($ch);
        $panier = json_decode($response, true);
        if ($panier == null) {
            return null;
        }
        return $panier;
    }

    /**
     * Enregistre une commande
     * @param $clientId
     * @param $nomPanier
     * @return bool
     */
    public function commanderPanier($nomPanier) {
        $url = $this->URL . 'commanderPanier/' . $nomPanier; // Ajouter le nom du panier à l'URL

        // Créer le corps de la requête en JSON
        $requestData = json_encode([
            'quantite' => 1, // Quantité par défaut
        ]);

        $ch = curl_init();

        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $requestData);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, [
            'Content-Type: application/json'
        ]);

        // Exécuter la requête cURL
        $response = curl_exec($ch);

        // Vérifier le code de statut HTTP
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

        curl_close($ch);

        // Retourner vrai si la requête a réussi, sinon faux
        if ($httpCode == 200) {
            return true;
        } else {
            // Afficher l'erreur si la commande échoue
            echo "Erreur : " . $response;
            return false;
        }
    }

    public function rechargerPanier($nomPanier) {
        $url = $this->URL . 'rechargerPanier/' . $nomPanier; // Ajouter le nom du panier à l'URL

        // Créer le corps de la requête en JSON
        $requestData = json_encode([
            'quantite' => 1, // Quantité par défaut
        ]);

        $ch = curl_init();

        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $requestData);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, [
            'Content-Type: application/json'
        ]);

        // Exécuter la requête cURL
        $response = curl_exec($ch);

        // Vérifier le code de statut HTTP
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);

        curl_close($ch);

        // Retourner vrai si la requête a réussi, sinon faux
        if ($httpCode == 200) {
            return true;
        } else {
            // Afficher l'erreur si la commande échoue
            echo "Erreur : " . $response;
            return false;
        }
    }

}