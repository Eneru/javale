import java.util.Vector;
import java.util.Arrays;
import java.io.FileWriter;

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
    private FileWriter texteW = new FileWriter("data/texte.txt");
    private FileWriter recettes;

    private Vector<String> listeLivres;
    private Vector<String> listeComs;
    private Vector<String> listeURLs;
    private Vector<String> listeDates;
    private Vector<String> listeTextes;

    public Sauvegarder(String categorie)
    {
        if (categorie.equals("sale"))
            this.recettes = new FileWriter(this.sale);
        else if (categorie.equals("sucre"))
            this.recettes = new FileWriter(this.sucre);

        this.listeLivres = new Vector<String>();
        this.listeComs = new Vector<String>();
        this.listeURLs = new Vector<String>();
        this.listeDates = new Vector<String>();
    }

    private int indexString(String chaine, Vector<String> liste, FileWriter fichier)
    {
        int index = liste.indexOf(chaine);

        if (index == -1)
        {
            liste.add(chaine);
            index = liste.indexOf(chaine);
            fichier.write(index + ". " + chaine + "\n");
        }

        return index;
    }

    public void sauvegarderRecettes(Recette[] listeRecettes)
    {
        String categorieCourante = "";
        for (int i = 0; i < listeRecettes.length; i++)
        {
            if (! categorieCourante.equals(listeRecettes[i].getCategorie()))
            {
                categorieCourante = listeRecettes[i].getCategorie();
                this.recettes.write("\n* " + categorieCourante + ":\n");
            }

            this.recettes.write("- " + listeRecettes[i].getNom());

            if (listeRecettes[i].getLivre() != null)
                this.recettes.write("("
                    + indexString(listeRecettes[i].getLivre(), listeLivres, livresW)
                    + ") (" + listeRecettes[i].getPage() +") ");
            else
                this.recettes.write("() () ");

            if (listeRecettes[i].getLien() != null)
                this.recettes.write("(" + indexURL(listeRecettes[i].getLien().toString(), listeURLs, urlW) + ") ");
            else
                this.recettes.write("() ");

            this.recettes.write("(" + listeRecettes[i].getNote() + ") ");

            if (listeRecettes[i].getDate() != null)
                this.recettes.write("(" + indexDate(listeRecettes[i].getDate().toString(), listeDates, dateW) + ") ");
            else
                this.recettes.write("() ");

            if (listeRecettes[i].getTexte() != null)
                this.recettes.write("(" + indexTexte(listeRecettes[i].getTexte(), listeTextes, texteW) + ") ");
            else
                this.recettes.write("() ");

            this.recettes.write("\n");
        }
    }
}
