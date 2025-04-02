package fr.univamu.iut.user.user;

public class User {


    protected String id;

    /**
     * titre du livre
     */
    protected String name;

    /**
     * Auteurs du livre
     */
    protected String mail;

    /**
     * Statut du livre
     * ('r' pour réservé, 'e' pour emprunté, et 'd' pour disponible)
     */
    protected String pwd;

    /**
     * Constructeur par défaut
     */
    public User(){
    }


    public User(String id, String name, String mail, String pwd) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.pwd = pwd;
    }

    /**
     * Méthode permettant d'accéder à la réference du livre
     * @return un chaîne de caractères avec la référence du livre
     */
    public String getId() {
        return id;
    }

    /**
     * Méthode permettant d'accéder au titre du livre
     * @return un chaîne de caractères avec le titre du livre
     */
    public String getName() {
        return name;
    }

    /**
     * Méthode permettant d'accéder aux auteurs du livre
     * @return un chaîne de caractères avec la liste des auteurs
     */
    public String getMail() {
        return mail;
    }

    /**
     * Méthode permettant d'accéder au statut du livre
     * @return un caractère indiquant lestatu du livre ('r' pour réservé, 'e' pour emprunté, et 'd' pour disponible)
     */
    public String getPwd() {
        return pwd;
    }


    public void setId(String id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setMail(String mail) {
        this.mail = mail;
    }


    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", nom='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", pwd=" + pwd +
                '}';
    }
}
