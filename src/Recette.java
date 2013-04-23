import java.util.*;

public class Recette
{
    private String nom;
    private String[] categories;
    private String livre;
    private String[] sousCategories;
    private Date[] preparations;
    private String[] ingredients;
    private int[] pages;
    private int note;
    private URL lien;
    private String commentaire;
    private String texte;

    public Recette()
    {
    }

    public Recette(String nom, String[] categories, String[] sousCategories)
    {
        this.nom = new String(nom);
        this.categories = Array.copyOf(categories, categories.length);
        this.sousCategories = Array.copyOf(sousCategories, sousCategories.length);
    }

    public Recette(String nom, String[] categories, String[] sousCategories, String livre)
    {
        this(nom, categories, sousCategories)
        this.livre = new String(livre);
    }

    public Recette(String nom, String[] categories, String[] sousCategories)


    public Recette(String nom, String[] categories, String[] sousCategories, String livre, int[] pages)
    {
        this(nom, categories, sousCategories, livre);
        this.page = Array.copyOf(pages, pages.length);
    }

    public Recette(String nom, String[] categories, String[] sousCategories, String livre, int[] pages, String lien)
    {
        this(nom, categories, sousCategories, livre);
        this.lien = new URL(lien);
    }

    public Recette(String nom, String[] categories, String[] sousCategories, String lien)
    {
        this(nom, categories, sousCategories);
        this.lien = new URL(lien);
    }

    public String getNom()
    {
        return nom;
    }

    public String[] getCategories()
    {
        return categories;
    }

    public String[] getSousCategories()
    {
        return sousCategories;
    }

    public String getLivre()
    {
        return livre;
    }

    public URL getLien()
    {
        return lien;
    }

    public String getProvenance()
    {
        StringBuffer s = new StringBuffer();
        if (livre != null)
        {
            s.append(livre);
            if (pages != null)
                s.append(", pages " + pages.toString());
        }

        if (lien != null)
        {
            if (s.length() > 0)
                s.append(", ");
            s.append(lien.toString());
        }

        if (s.lenght() == 0)
            return "Nulle-part";
        else
            return s.toString();
    }

    public boolean estCategorie(String c)
    {
        return bynarySearch(categories, c, null) >= 0;
    }

    public boolean estSousCategorie(String s)
    {
        return bynarySearch(sousCategories, s, null) >= 0;
    }
}
