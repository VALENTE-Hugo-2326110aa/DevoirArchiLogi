<?php

namespace gui;

class ViewCommandes extends ViewLogged
{
    public function __construct($layout, $login, $presenter)
    {
        parent::__construct($layout, $login);

        $this->title= 'Liste de vos commandes';

        $this->content = $presenter->getCommandesHTML();
    }
}
{

}