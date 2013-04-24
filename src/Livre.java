import java.lang.*;
import java.util.*;

public class Livre
{
    private String titre;
    private String editeur;
    private Vector<String> auteurs;
    private Date date;

    private Livre(String titre, String editeur)
    {
        this.titre = new String(titre);
        this.editeur = new String(editeur);

    }

    private Livre(String titre, String editeur, String[] auteurs)
    {
        this(titre, editeur);
        this.auteurs = new Vector<String>(Arrays.asList(auteurs));
    }

    private Livre(String titre, String editeur, String auteur)
    {
        this(titre, editeur);
        this.auteurs = new Vector<String>();
        this.auteurs.add(auteur);
    }

    public Livre(String titre, String editeur, String auteur, Date date)
    {
        this(titre, editeur, auteur);
        this.date = new Date(date.getTime());
    }

    public Livre(String titre, String editeur, String[] auteurs, Date date)
    {
        this(titre, editeur, auteurs);
        this.date = new Date(date.getTime());
    }

    public String getTitre()
    {
        return this.titre;
    }

    public String getEditeur()
    {
        return this.editeur;
    }

    public String[] getAuteurs()
    {
        return (String[]) this.auteurs.toArray();
    }

    public Date getDate()
    {
        return this.date;
    }

    public void ajouterAuteurs(String auteur)
    {
        this.auteurs.add(auteur);
    }

    public String toString()
    {
        return this.titre + ", " + this.auteurs.toString() + ", " + this.editeur + ", " + this.date;
    }
}
