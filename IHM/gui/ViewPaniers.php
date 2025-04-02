<?php
namespace gui;

include_once "ViewLogged.php";

class ViewPaniers extends ViewLogged
{
    public function __construct($layout, $login, $presenter)
    {
        parent::__construct($layout, $login);

        $this->title= 'Liste des paniers disponibles';

        $this->content = $presenter->getAllPaniersHTML();
    }
}