<?php
namespace gui;

include_once "View.php";

class ViewLogin extends View
{
    public function __construct($layout)
    {
        parent::__construct($layout);

        $this->title = 'Exemple Annonces Basic PHP: Connexion';

        $this->content = '
        <div class="container">
            ';

        $this->content .= '
            <form class="login" method="post" action="/index.php/paniers">
                <label for="login"> Votre identifiant </label>
                <input type="text" name="login" id="login" placeholder="Identifiant" maxlength="12" required />
                <br />
                <label for="password"> Votre mot de passe </label>
                <input type="password" name="password" id="password" placeholder="Mot de passe" minlength="12" required />
        
                <input type="submit" value="Envoyer">
            </form>
            ';

        $this->content .= '
        </div>
        ';

    }
}