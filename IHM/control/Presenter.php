<?php

namespace control;

class Presenter
{

    private $checking;

    public function __construct($checking)
    {
        $this->checking = $checking;
    }

    public function getAllPaniersHTML()
    {
        $content = null;
        if ($this->checking->getPaniersTxt() != null) {
            $content = '<h1>List des Paniers Disponible</h1>  <ul>';
            foreach ($this->checking->getPaniersTxt() as $panier) {
                $content .= ' <li>';
                $content .= '<a href="/index.php/panier?id=' . $panier['title'] . '">' . $panier['title'] . '</a>';
                $content .= ' </li>';
            }
            $content .= '</ul>';
        }
        return $content;
    }

    public function getCurrentPanierHTML()
    {
        $content = null;
        if ($this->checking->getPaniersTxt() != null) {
            $panier = $this->checking->getPaniersTxt()[0];

            $content = '<h1>' . $panier['title'] . '</h1>';
            $content .= '<div class="date">Date mise à jour : ' . $panier['date'] . '</div>';

            $produits = json_decode($panier['body'], true); // Convertir le JSON en array

            $content .= '<div class="body">Produits:<ul>';
            foreach ($produits as $produit => $quantite) {
                if ($quantite == 0) {
                    continue; // Ignore les produits avec une quantité de 0
                }
                if ($quantite > 1) {
                    $produit = $produit . 's'; // Pluriel
                }
                $content .= "<li>$quantite " . " $produit  </li>";
            }
            $content .= '</ul></div>';

            $content .= '<div class="prix">Prix: ' . $panier['prix'] . '€</div>';
            $content .= '<div class="quantite">Quantité: ' . $panier['quantite'] . '</div>';
            $content .= '<br>';
            $content .= '<form method="post" action="/index.php/panier?id=' . $panier['title'] . '">';
            $content .= '<input type="hidden" value="' . $_SESSION['login'] . '" name="clientId">';
            $content .= '<input type="hidden" name="nomPanier" value="' . $panier['title'] . '">';
            $content .= '<input type="submit" value="Commander">';
            $content .= '</form>';
        }
        return $content;
    }

    public function getCommandesHTML()
    {
        $commandes = $this->checking->getCommandesTxt();
        $content = null;
        if ($commandes != null) {
            $content = '<h1>Liste des Commandes</h1>  <ul>';
            foreach ($commandes as $commande) {
                $content .= ' <li>';
                $content .= '<div>';
                $content .= '<a href="/index.php/panier?id=' . $commande['nomPanier'] . '">' . $commande['nomPanier'] . '</a>';
                $content .= '<div class="date">Date de la commande : ' . $commande['dateRetrait'] . '</div>';
                $content .= '<div class="prix">Prix total : ' . $commande['prix'] . '€</div>';
                $content .= '<div class="localisation">Localisation du relais : ' . $commande['localisationRelai'] . '</div>';
                $content .= '<form method="post" action="/index.php/commandes">
                <input type="hidden" value="' . $_SESSION['login'] . '" name="clientId">';
                $content .= '<input type="hidden" name="nomPanier" value="' . $commande['nomPanier'] . '">';
                $content .= '<input type="submit" value="Retirer">';
                $content .= '</form>';
                $content .= '</div>';
                $content .= '</li>';
            }
            $content .= '</ul>';
        } else {
            $content = '<h1>Pas de commande</h1>';
        }
        return $content;
    }

}