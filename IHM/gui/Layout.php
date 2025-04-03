<?php
namespace gui;
class Layout
{
    protected $templateFile;

    public function __construct( $templateFile )
    {
        $this->templateFile = $templateFile;
    }

    /**
     * Affiche la page
     * @param $title
     * @param $connexion
     * @param $content
     */
    public function display( $title, $connexion, $content )
    {
        $style = '<link rel="stylesheet" href="/css/style.css">';
        $page = file_get_contents( $this->templateFile );
        $page = str_replace( ['%title%','%connexion%','%content%', '%css%'], [$title, $connexion, $content, $style], $page);
        echo $page;
    }

}