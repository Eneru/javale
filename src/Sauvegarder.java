import java.util.Vector;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class Sauvegarder
{
    private final String sale = "data/sale.txt";
    private final String sucre = "data/sucre.txt";
    // private final String Index = "data/index_livres.txt";
    // private final String Com = "data/commentaire.txt";
    // private final String Url = "data/url.txt";
    // private final String Preparation = "data/preparation.txt"; // texte avec les préparations

    private FileWriter livresW;
    private FileWriter comW;
    private FileWriter urlW;
    private FileWriter dateW;
    private FileWriter texteW;
    private FileWriter recettes;

    private Vector<String> listeLivres;
    private Vector<String> listeComs;
    private Vector<String> listeURLs;
    private Vector<String> listeDates;
    private Vector<String> listeTextes;

    public Sauvegarder(String categorie)
    {
        this.listeLivres = new Vector<String>();
        this.listeComs = new Vector<String>();
        this.listeURLs = new Vector<String>();
        this.listeDates = new Vector<String>();

        try
        {
            if (categorie.equals("sale"))
                this.recettes = new FileWriter(this.sale);
            else if (categorie.equals("sucre"))
                this.recettes = new FileWriter(this.sucre);

            this.livresW = new FileWriter("data/index_livres.txt");
            this.comW = new FileWriter("data/commentaire.txt");
            this.urlW = new FileWriter("data/url.txt");
            this.dateW = new FileWriter("data/preparation.txt");
            this.texteW = new FileWriter("data/texte.txt");
        }
        catch(IOException ioe)
        {
            System.err.println(ioe.getMessage());
        }
    }

    private int indiceString(String chaine, Vector<String> liste, FileWriter fichier)
    {
        int index = liste.indexOf(chaine);

        if (index == -1)
        {
            liste.add(chaine);
            index = liste.indexOf(chaine);
            try
            {
                fichier.write(index + ". " + chaine + "\n");
            }
            catch (IOException ioe)
            {
                System.err.println(ioe.getMessage());
            }
        }

        return index;
    }

    public void sauvegarderRecettes(Recette[] listeRecettes)
    {
        Recette[] listeRecettesTriee = Arrays.copyOf(listeRecettes, listeRecettes.length);
        Arrays.sort(listeRecettesTriee, new Recette());
        String categorieCourante = "";
        for (int i = 0; i < listeRecettesTriee.length; i++)
        {
            try
            {
                if (! categorieCourante.equals(listeRecettesTriee[i].getCategorie()))
                {
                    categorieCourante = listeRecettesTriee[i].getCategorie();
                    this.recettes.write("\n* " + categorieCourante + ":\n");
                }

                this.recettes.write("- " + listeRecettesTriee[i].getNom());

                if (listeRecettesTriee[i].getLivre() != null)
                    this.recettes.write("("
                        + indiceString(listeRecettesTriee[i].getLivre(), listeLivres, livresW)
                        + ") (" + listeRecettesTriee[i].getPage() +") ");
                else
                    this.recettes.write("() () ");

                if (listeRecettesTriee[i].getLien() != null)
                    this.recettes.write("(" + indiceString(listeRecettesTriee[i].getLien().toString(), listeURLs, urlW) + ") ");
                else
                    this.recettes.write("() ");

                this.recettes.write("(" + listeRecettesTriee[i].getNote() + ") ");

                if (listeRecettesTriee[i].getDate() != null)
                    this.recettes.write("(" + indiceString(listeRecettesTriee[i].getDate().toString(), listeDates, dateW) + ") ");
                else
                    this.recettes.write("() ");

                if (listeRecettesTriee[i].getTexte() != null)
                    this.recettes.write("(" + indiceString(listeRecettesTriee[i].getTexte(), listeTextes, texteW) + ") ");
                else
                    this.recettes.write("() ");

                this.recettes.write("\n");
            }
            catch (IOException ioe)
            {
                System.err.println(ioe.getMessage());
            }
        }
    }
}
