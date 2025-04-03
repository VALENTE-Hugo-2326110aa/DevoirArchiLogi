<?php

namespace data;

use domain\User;

include_once "domain/User.php";

class UsersData
{

    private $URL = "http://localhost:8080/user_product-1.0-SNAPSHOT/api/users/";

    /**
     * Récupère un utilisateur par son login
     * @param $login
     * @param $password
     * @return User|null
     */
    public function getUser($login, $password)
    {

        // Modification possible à l'avenir :D -Hugo
        $url = $this->URL . urldecode($login);

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPGET, true);

        $response = curl_exec($ch);

        if (curl_errno($ch)) {
            return null;
        }
        curl_close($ch);
        $data = json_decode($response, true);
        if (isset($data['pwd']) && $data['pwd'] === $password) {
            return new User($data['id'], $data['mail'], $data['name'], $data['pwd']);
        }
        return null;
    }
}