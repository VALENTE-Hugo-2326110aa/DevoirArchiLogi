<?php

namespace service;

class UserChecking
{

    // Regarde si le login et le mot de passe correspondent à un utilisateur enregistré
    public function authenticate($login, $password, $data) {
        return ($data->getUser($login, $password) != null);
    }

}