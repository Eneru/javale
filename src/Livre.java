import java.lang.*;
import java.util.*;

/**
 * Classe pour les livres
 *
 * @author Harenome Razanajato
 */
public class Livre implements Comparator<Livre>
{
    /**
     * Titre du livre.
     *
     * @see #getEditeur()
     */
    private String titre;
    /**
     * Éditeur du livre.
     *
     * @see #getEditeur()
     */
    private String editeur;
    /**
     * Liste des auteurs du livre.
     *
     * @see #getAuteurs()
     */
    private Vector<String> auteurs;
    /**
     * Date d'édition du livre.
     *
     * @see #getDate()
     */
    private Date date;

    /**
     * Constructeur d'un livre.
     *
     * @param titre Titre du livre.
     * @param editeur Éditeur du livre.
     */
    private Livre(String titre, String editeur)
    {
        this.titre = new String(titre);
        this.editeur = new String(editeur);

    }

    /**
     * Constructeur d'un livre.
     *
     * @param titre Titre du livre.
     * @param editeur Éditeur du livre.
     * @param auteurs Liste des auteurs du livre.
     */
    private Livre(String titre, String editeur, String[] auteurs)
    {
        this(titre, editeur);
        this.auteurs = new Vector<String>(Arrays.asList(auteurs));
    }

    /**
     * Constructeur d'un livre.
     *
     * @param titre Titre du livre.
     * @param editeur Éditeur du livre.
     * @param auteur Auteur.
     */
    private Livre(String titre, String editeur, String auteur)
    {
        this(titre, editeur);
        this.auteurs = new Vector<String>();
        this.auteurs.add(auteur);
    }

    /**
     * Constructeur d'un livre.
     *
     * @param titre Titre du livre.
     * @param editeur Éditeur du livre.
     * @param auteur Auteur d'un livre.
     * @param date Date d'édition du livre.
     */
    public Livre(String titre, String editeur, String auteur, Date date)
    {
        this(titre, editeur, auteur);
        this.date = new Date(date.getTime());
    }

    /**
     * Constructeur d'un livre.
     *
     * @param titre Titre du livre.
     * @param editeur Éditeur du livre.
     * @param auteur Liste des auteurs d'un livre.
     * @param date Date d'édition du livre.
     */
    public Livre(String titre, String editeur, String[] auteurs, Date date)
    {
        this(titre, editeur, auteurs);
        this.date = new Date(date.getTime());
    }

    /**
     * Sélecteur du titre
     *
     * @return Titre du livre
     */
    public String getTitre()
    {
        return this.titre;
    }

    /**
     * Sélecteur de l'éditeur
     *
     * @return Éditeur du livre
     */
    public String getEditeur()
    {
        return this.editeur;
    }

    /**
     * Sélecteur de la liste des auteurs
     *
     * @return Liste des auteurs
     */
    public String[] getAuteurs()
    {
        return (String[]) this.auteurs.toArray();
    }

    /**
     * Sélecteurs de la date
     *
     * @return Date d'édition
     */
    public Date getDate()
    {
        return this.date;
    }

    /**
     * Ajout d'un auteur
     *
     * @param auteur Auteur du livre
     */
    public void ajouterAuteur(String auteur)
    {
        this.auteurs.add(auteur);
    }

    /**
     * Retrain d'un auteur
     *
     * @param auteur Ancien auteur du livre
     */
    public void retirerAuteur(String auteur)
    {
        this.auteurs.remove(auteur);
    }

    /**
     * toString
     *
     * @return titre, liste des auteurs, éditeur et date
     */
    public String toString()
    {
        return this.titre + ", " + this.auteurs.toString() + ", " + this.editeur + ", " + this.date;
    }

    /**
     * Comparaison des livres par titre :
     * - Valeur négative : l1.titre < l2.titre
     * - Zéro : l1.titre == l2.titre
     * - Valeur positive : l1.titre > l2.titre
     *
     * @return ordre des livres
     */
    public int compare(Livre l1, Livre l2)
    {
        return l1.titre.compareTo(l2.titre);
    }

    /**
     * Test d'égalité de livres
     *
     * @return true si les livres sont égaux
     */
    public boolean equals(Object o)
    {
        if (o instanceof Livre)
        {
            Livre l = (Livre) o;
            if (this.titre.equals(l.titre)
                && this.editeur.equals(l.editeur)
                && this.date.equals(l.date)
                && Arrays.equals(this.auteurs.toArray(), l.auteurs.toArray())
                )
                return true;
            else
                return false;

        }
        else
            return false;
    }
}
