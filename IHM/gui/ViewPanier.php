<?php

namespace gui;

class ViewPanier extends ViewLogged
{
    public function __construct($layout, $login, $presenter, $id)
    {
        parent::__construct($layout, $login);

        $this->title= 'Panier';

        $this->content = $presenter->getCurrentPanierHTML();

        $this->content .= '</br>';

        $this->content .= '<a class="retour" href="/index.php/paniers">Retour</a>';

    }
}