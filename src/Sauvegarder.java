import java.util.Vector;
import java.util.Arrays;

public class Sauvegarder
{
    private final String sale = "data/sale.txt";
    private final String sucre = "data/sucre.txt";
    // private final String Index = "data/index_livres.txt";
    // private final String Com = "data/commentaire.txt";
    // private final String Url = "data/url.txt";
    // private final String Preparation = "data/preparation.txt"; // texte avec les préparations

    private FileWriter livresW = new FileWriter("data/index_livres.txt");
    private FileWriter comW = new FileWriter("data/commentaire.txt");
    private FileWriter urlW = new FileWriter("data/url.txt");
    private FileWriter dateW = new FileWriter("data/preparation.txt");
    private FileWriter recettes;

    private Vector<String> listeLivres;
    private Vector<String> listeCom;
    private Vector<String> listeURL;
    private Vector<String> listeDate

    public Sauvegarder(String categorie)
    {
        if (categorie.equals("sale"))
            this.recettes = new FileWriter(this.sale);
        else if (categorie.equals("sucre"))
            this.recettes = new FileWriter(this.sucre);

        this.listeLivres = new Vector<String>();
        this.listeCom = new Vector<String>();
        this.listeURL = new Vector<String>();
        this.listeDate = new Vector<String>();
    }

    private indexLivre(String livre)
    {
        int index = listeLivres.indexOf(livre);

        if (index == -1)
        {
            listeLivres.add(livre);
            index = listeLivres.indexOf(livre);
            livresW.write(index + ". " + livre + "\n");
        }

        return index;
    }

    private indexCom(String commentaire)
    {
        int index = listeCom.indexOf(commentaire);

        if (index == -1)
        {
            listeCom.add(commentaire);
            index = listeCom.indexOf(commentaire);
            comW.write(index + ". " + commentaire + "\n");
        }

        return index;
    }

    private indexURL(String lien)
    {
        int index = listeURL.indexOf(lien);

        if (index == -1)
        {
            listeURL.add(lien);
            index = listeURL.indexOf(lien);
            urlW.write(index + ". " + lien + "\n");
        }

        return index;
    }

    private indexDate(String lien)
    {
        int index = listeURL.indexOf(lien);

        if (index == -1)
        {
            listeURL.add(lien);
            index = listeURL.indexOf(lien);
            urlW.write(index + ". " + lien + "\n");
        }

        return index;
    }

    public void sauvegarderRecettes(Recette[] listeRecettes)
    {
        for (int i = 0; i < listeRecettes.length; i++)
        {
            this.recettes.write("- " + listeRecettes[i].nom);

            if (listeRecettes[i].livre != null)
                this.recette.write("(" + indexLivre(listeRecettes[i].livre) + ") (" + listeRecettes[i].page +") " );
            else
                this.recette.write("() () ");

            if (listeRecettes[i].lien != null)
                this.recette.write("(" + indexURL(listeRecettes[i].lien.toString()) + ") ");
            else
                this.recette.write("() ");

            this.recettes.write("(" + listeRecettes[i].note + ") ");

            if (listeRecettes[i].preparation != null)
                this.recettes.write("(" + indexDate(listeRecettes[i].preparation.toString()) + ") ");
            else
                this.recette.write("() ");

            if (listeRecettes[i].texte != null)
                this.recettes.write("(" + indexTexte(listeRecettes[i].texte) + ") ");
            else
                this.recette.write("() ");

            this.recette.write("\n");
        }
    }
}
