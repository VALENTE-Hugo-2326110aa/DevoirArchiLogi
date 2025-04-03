<?php

include_once 'gui/Layout.php';
include_once 'gui/ViewLogin.php';
include_once 'gui/ViewError.php';
include_once 'gui/ViewPaniers.php';
include_once 'gui/ViewPanier.php';
include_once 'gui/ViewCommandes.php';

include_once 'control/Controllers.php';
include "control/Presenter.php";

include_once 'service/UserChecking.php';
include_once 'service/PanierChecking.php';
include_once 'service/CommandeChecking.php';

include_once 'data/PaniersData.php';
include_once 'data/UsersData.php';
include_once 'data/CommandesData.php';


use control\Controllers;
use control\Presenter;
use data\CommandesData;
use data\UsersData;
use gui\Layout;
use gui\ViewCommandes;
use gui\ViewPanier;
use gui\ViewPaniers;
use gui\ViewError;
use gui\ViewLogin;
use service\CommandeChecking;
use service\PanierChecking;
use service\UserChecking;

if (session_status() == PHP_SESSION_NONE) {
    ini_set('session.gc_maxlifetime', 3600);
    session_set_cookie_params(3600);
    session_start();
}

$controller = new Controllers();

$paniersCheck = new PanierChecking();
$userCheck = new UserChecking();
$commandesCheck = new CommandeChecking();

$dataUsers = new UsersData();
$dataPaniers = new PaniersData();
$dataCommandes = new CommandesData();

// chemin de l'URL demandée au navigateur
$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);

if ('/' != $uri && '/index.php' != $uri && '/index.php/logout' != $uri) {

    $error = $controller->authenficateAction($userCheck, $dataUsers);

    if ($error != null) {
        $uri = '/index.php/error';
        if ($error === 'bad login or pwd' || $error === 'not connected') {
            $redirect = '/index.php';
        }
    }

}

if ('/' == $uri || '/index.php' == $uri || '/index.php/logout' == $uri) {
    session_destroy();
    $layout = new Layout("gui/layout.html");
    $viewLogin = new ViewLogin($layout);

    $viewLogin->display();
}
elseif('/index.php/paniers' == $uri) {

    $presenter = new Presenter($paniersCheck);

    $controller->paniersAction($dataPaniers, $paniersCheck);

    // Affichage de la page d'annonces
    $layout = new Layout("gui/layout.html");
    $viewAnnonces = new ViewPaniers($layout, $_SESSION['login'], $presenter);

    $viewAnnonces->display();
}
elseif ('/index.php/panier' == $uri && isset($_GET['id'])) {

    if (isset($_POST['clientId']) && isset($_POST['nomPanier'])) {
        $clientId = $_POST['clientId'];
        $nomPanier = $_POST['nomPanier'];

        if ($dataCommandes->registerCommand($clientId, $nomPanier)) {
            $dataPaniers->commanderPanier($nomPanier);
            // Commande enregistrée avec succès
            header("Location: /index.php/commandes");
            exit();
        } else {
            // Erreur lors de l'enregistrement de la commande
            $error = 'Erreur lors de l\'enregistrement de la commande.';
            $redirect = '/index.php/paniers';
        }
    }

    $presenter = new Presenter($paniersCheck);

    $controller->panierAction($_GET['id'], $dataPaniers, $paniersCheck);

    $layout = new Layout("gui/layout.html");
    $viewPanier = new ViewPanier($layout, $_SESSION['login'], $presenter, $_GET['id']);

    $viewPanier->display();
}
elseif ('/index.php/commandes' == $uri) {

    if (isset($_POST['clientId']) && isset($_POST['nomPanier'])) {
        $clientId = $_POST['clientId'];
        $nomPanier = $_POST['nomPanier'];

        if ($dataCommandes->removeCommand($clientId, $nomPanier)) {
            $dataPaniers->rechargerPanier($nomPanier);
            // Commande supprimé avec succès
            header("Location: /index.php/commandes");
            exit();
        } else {
            // Erreur lors de l'enregistrement de la commande
            $error = 'Erreur lors de l\'enregistrement de la commande.';
            $redirect = '/index.php/paniers';
        }
    }

    $presenter = new Presenter($commandesCheck);

    $controller->commandesAction($dataCommandes, $commandesCheck, $_SESSION['login']);

    // Affichage de la page d'annonces
    $layout = new Layout("gui/layout.html");
    $viewCommandes = new ViewCommandes($layout, $_SESSION['login'], $presenter);

    $viewCommandes->display();
}
elseif ('/index.php/error' == $uri) {
    // Affichage de la page d'erreur
    $layout = new Layout("gui/layout.html");
    $viewError = new ViewError($layout, $error, $redirect);

    $viewError->display();
}
else {
    header("Status: 404 Not Found");
    echo '<html><body><h1>404 Not Found</h1></body></html>';
}
