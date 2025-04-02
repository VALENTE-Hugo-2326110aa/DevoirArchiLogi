<?php

namespace gui;

class ViewError extends View
{

    public function __construct($layout, $error, $redirect)
    {
        parent::__construct($layout);

        header("refresh:5;url=$redirect");
        echo $error;
    }

}