import java.lang.*;
import java.util.*;
import java.net.*;

public class Recette
{
    private String nom;
    private String livre;
    private int page;
    private URL lien;
    private String texte;
    private Vector<String> ingredients;
    private Vector<String> categories;
    private Vector<String> sousCategories;
    private Vector<Date> preparations;
    private int note;
    private String commentaire;

    public Recette(String nom, String[] categories, String[] sousCategories)
    {
        this.nom = new String(nom);
        this.categories = new Vector<String>(Arrays.asList(categories));
        this.sousCategories = new Vector<String>(Arrays.asList(sousCategories));
    }

    public Recette(String nom, String[] categories, String[] sousCategories, String lien)
    {
        this(nom, categories, sousCategories);
        try
        {
            this.lien = new URL(lien);
        }
        catch (MalformedURLException mue)
        {
            System.err.println(mue.getMessage());
        }
    }

    public Recette(String nom, String[] categories, String[] sousCategories, String livre, int page)
    {
        this(nom, categories, sousCategories, livre);
        this.livre = new String(livre);
        this.page = page;
    }

    public Recette(String nom, String[] categories, String[] sousCategories, String livre, int page, String lien)
    {
        this(nom, categories, sousCategories, lien);
        this.livre = new String(livre);
        this.page = page;
    }


    public String getNom()
    {
        return nom;
    }

    public String[] getCategories()
    {
        return (String[]) categories.toArray();
    }

    public String[] getSousCategories()
    {
        return (String[]) sousCategories.toArray();
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
            if (page != 0)
                s.append(", page " + page);
        }

        if (lien != null)
        {
            if (s.length() > 0)
                s.append(", ");
            s.append(lien.toString());
        }

        if (s.length() == 0)
            return "Nulle-part";
        else
            return s.toString();
    }

    public String getTexte()
    {
        return this.texte;
    }

    public boolean estCategorie(String c)
    {
        return Arrays.binarySearch(this.getCategories(), c, null) >= 0;
    }

    public boolean estSousCategorie(String s)
    {
        return Arrays.binarySearch(this.getSousCategories(), s, null) >= 0;
    }

    public void setNote(int note) throws Exception
    {
        if (note < 0 || note > 10)
            throw new Exception("Note non valide (0 <= n <= 10).");
        this.note = note;
    }

    public void ajouterCategorie(String categorie)
    {
        this.categories.add(categorie);
    }

    public void retirerCategorie(String categorie)
    {
        this.categories.remove(categorie);
    }

    public void ajouterSousCategorie(String sousCategorie)
    {
        this.sousCategories.add(sousCategorie);
    }

    public void retirerSousCategorie(String sousCategorie)
    {
        this.sousCategories.remove(sousCategorie);
    }

    public void ajouterDatePreparation(Date date)
    {
        this.preparations.add(date);
    }

    public void retirerDatePreparation(Date date)
    {
        this.preparations.remove(date);
    }

    public void setCommentaire(String commentaire)
    {
        this.commentaire = new String(commentaire);
    }

    public void setTexte(String texte)
    {
        this.texte = new String(texte);
    }

    public void setIngredients(String[] ingredients)
    {
        this.ingredients = new Vector<String>(Arrays.asList(ingredients));
    }

    public void ajouterIngredient(String ingredient)
    {
        if (this.ingredients == null)
        {
            this.ingredients = new Vector<String>();
            this.ingredients.add(ingredient);
        }
        else
        {
            this.ingredients.add(ingredient);
        }
    }

    public void retirerIngredient(String ingredient)
    {
        this.ingredients.remove(ingredient);
    }

    public void main(String[] args)
    {
        return;
    }
}
