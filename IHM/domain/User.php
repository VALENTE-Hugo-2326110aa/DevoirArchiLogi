<?php

namespace domain;

class User
{

    private $login;
    private $email;
    private $name;
    private $password;

    public function __construct($login, $email, $name, $password)
    {
        $this->login = $login;
        $this->email = $email;
        $this->name = $name;
        $this->password = $password;
    }

    /**
     * Getters de l'attribut login
     */
    public function getLogin()
    {
        return $this->login;
    }

}