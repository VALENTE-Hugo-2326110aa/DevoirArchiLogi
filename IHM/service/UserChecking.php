<?php

namespace service;

class UserChecking
{

    /**
     * Vérifie si le login et le mot de passe sont valides
     * @param string $login
     * @param string $password
     * @param \data\UsersData $data
     * @return bool
     */
    public function authenticate($login, $password, $data) {
        return ($data->getUser($login, $password) != null);
    }

}