<?php

namespace gui;

include_once "View.php";

abstract class ViewLogged extends View
{
    protected $connexion = '';

    public function __construct($layout, $login)
    {
        parent::__construct($layout);

        $this->connexion = "<header>
            <a href='/index.php/paniers'>Accueil</a>
            <a href='/index.php/commandes'>Vos commandes</a>
            <div class='info-utilisateur>'
            <p> Hello $login </p>".
            '<a href="/index.php/logout">Déconnexion</a>
            </div>
            </header>';
    }

    public function display()
    {
        $this->layout->display( $this->title, $this->connexion, $this->content );
    }

}