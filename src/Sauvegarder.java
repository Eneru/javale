import java.util.Vector;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class Sauvegarder
{
    // private static final String sale    = ChargeSauv.sale;
    // private static final String sucre   = ChargeSauv.sucre;
    // private static final String livres  = ChargeSauv.livres;
    // private static final String com     = ChargeSauv.com;
    // private static final String url     = ChargeSauv.url;
    // private static final String dates   = ChargeSauv.dates;
    // private static final String textes  = ChargeSauv.textes;

    private static final String sale    = "test_sale";
    private static final String sucre   = "test_sucre";
    private static final String livres  = "test_livres";
    private static final String com     = "test_com";
    private static final String url     = "test_url";
    private static final String dates   = "test_dates";
    private static final String textes  = "test_textes";

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
        this.listeLivres = Charger.indexLivres();
        this.listeComs = Charger.indexComs();
        this.listeURLs = Charger.indexURL();
        this.listeDates = Charger.indexDates();
        this.listeTextes = Charger.indexTextes();

        try
        {
            if (categorie.equals("sale"))
                this.recettes = new FileWriter(Sauvegarder.sale);
            else
                this.recettes = new FileWriter(Sauvegarder.sucre);

            this.livresW = new FileWriter(Sauvegarder.livres);
            this.comW = new FileWriter(Sauvegarder.com);
            this.urlW = new FileWriter(Sauvegarder.url);
            this.dateW = new FileWriter(Sauvegarder.dates);
            this.texteW = new FileWriter(Sauvegarder.textes);
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
        String sousCategorieCourante = "";
        for (int i = 0; i < listeRecettesTriee.length; i++)
        {
            try
            {
                if (! sousCategorieCourante.equals(listeRecettesTriee[i].getSousCategorie()))
                {
                    sousCategorieCourante = listeRecettesTriee[i].getSousCategorie();
                    this.recettes.write("\n* " + sousCategorieCourante + ":\n");
                }

                this.recettes.write("- " + listeRecettesTriee[i].getNom() + " ");

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
