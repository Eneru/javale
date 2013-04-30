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

    public static final String sale    = "test_sale";
    // private static final String sale    = "test_sale2";
    public static final String sucre   = "test_sucre";
    public static final String livres  = "test_livres";
    public static final String com     = "test_com";
    public static final String url     = "test_url";
    public static final String dates   = "test_dates";
    public static final String textes  = "test_textes";

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

    private String categorie;

    public Sauvegarder(String categorie)
    {
        this.categorie = new String(categorie);
        this.listeLivres = Charger.indexLivres();
        this.listeComs = Charger.indexComs();
        this.listeURLs = Charger.indexURL();
        this.listeDates = Charger.indexDates();
        this.listeTextes = Charger.indexTextes();

        if (!this.livres.equals(Charger.livres))
            sauvegarderIndexes(listeLivres, livresW);
        if (!this.com.equals(Charger.com))
            sauvegarderIndexes(listeComs, comW);
        if (!this.url.equals(Charger.url))
            sauvegarderIndexes(listeURLs, urlW);
        if (!this.dates.equals(Charger.dates))
            sauvegarderIndexes(listeDates, dateW);
        if (!this.textes.equals(Charger.textes))
            sauvegarderIndexes(listeTextes, texteW);
    }

    private void sauvegarderIndexes(Vector<String> listeIndexes, FileWriter fichier)
    {
        String[] indexes = listeIndexes.toArray(new String[0]);

        for (int i = 0; i < indexes.length; i++)
        {
            try
            {
                fichier.write(i + ". " + indexes[i] + "\n");
            }
            catch (IOException ioe)
            {
                System.err.println(ioe.getMessage());
            }
        }

        try
        {
            fichier.flush();
        }
        catch (IOException ioe)
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
                fichier.flush();
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
        try
        {
            if (this.categorie.equals("sale"))
                this.recettes = new FileWriter(Sauvegarder.sale);
            else
                this.recettes = new FileWriter(Sauvegarder.sucre);

            this.livresW = new FileWriter(Sauvegarder.livres);
            this.comW = new FileWriter(Sauvegarder.com);
            this.urlW = new FileWriter(Sauvegarder.url);
            this.dateW = new FileWriter(Sauvegarder.dates);
            this.texteW = new FileWriter(Sauvegarder.textes);

            Recette[] listeRecettesTriee = listeRecettes.clone();
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
                            + (indiceString(listeRecettesTriee[i].getLivre(), listeLivres, livresW) + 1)
                            + ") (" + listeRecettesTriee[i].getPage() +") ");
                    else
                        this.recettes.write("() () ");

                    if (listeRecettesTriee[i].getLien() != null)
                        this.recettes.write("(" + indiceString(listeRecettesTriee[i].getLien().toString(), listeURLs, urlW) + ") ");
                    else
                        this.recettes.write("() ");

                    if (listeRecettesTriee[i].getDate() != null)
                        this.recettes.write("(" + indiceString(listeRecettesTriee[i].getDate().toString(), listeDates, dateW) + ") ");
                    else
                        this.recettes.write("() ");

                    this.recettes.write("(" + listeRecettesTriee[i].getNote() + ") ");

                    if (listeRecettesTriee[i].getCommentaire() != null)
                        this.recettes.write("(" + indiceString(listeRecettesTriee[i].getCommentaire(), listeComs, comW) + ") ");
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

            recettes.flush();
        }
        catch(IOException ioe)
        {
            System.err.println(ioe.getMessage());
        }
        finally
        {
            try
            {
                this.recettes.close();
                this.livresW.close();
                this.comW.close();
                this.urlW.close();
                this.dateW.close();
                this.texteW.close();
            }
            catch (IOException ioe)
            {
                System.err.println(ioe.getMessage());
            }
        }
    }
}
