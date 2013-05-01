import java.util.Arrays;
import java.util.Vector;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @class Sauvegarder
 * @brief Gestion de la sauvegarde
 */
public class Sauvegarder extends ChargeSauv
{

    private Vector<String> listeLivres;
    private Vector<String> listeComs;
    private Vector<String> listeURLs;
    private Vector<String> listeDates;
    private Vector<String> listeTextes;

	/**
	 * Variable de la catégorie.
	 * 
	 * @see Sauvgarder#Sauvegarder()
	 */
    private String categorie;

	/**
	 * Constructeur de la sauvegarde
	 * 
	 * @param categorie
	 * 		Catégorie de la sauvegarde.
	 */
    public Sauvegarder(String categorie)
    {
        this.categorie = new String(categorie);
        this.listeLivres = Charger.indexLivres();
        this.listeComs = Charger.indexComs();
        this.listeURLs = Charger.indexURL();
        this.listeDates = Charger.indexDates();
        this.listeTextes = Charger.indexTextes();
    }

	/**
	 * Renvoie l'indice d'une chaine dans une liste
	 * 
	 * @param chaine
	 * 		Chaine de caractères à écrire.
	 * @param liste
	 * 		Liste d'index.
	 * @param fichier
	 * 		Fichier dans lequel écrire.
	 * @return index
	 * 		Indice de la chaine de caractères.
	 */
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

	/**
	 * Sauvegarde les recettes.
	 * 
	 * @param listeRecettes
	 * 		Liste des recettes.
	 */
    public void sauvegarderRecettes(Recette[] listeRecettes)
    {
        FileWriter livresW;
        FileWriter comW;
        FileWriter urlW;
        FileWriter dateW;
        FileWriter texteW;
        FileWriter recettes;

        try
        {

            if (this.categorie.equals("sale"))
                recettes = new FileWriter(Sauvegarder.sale);
            else
                recettes = new FileWriter(Sauvegarder.sucre);

            livresW = new FileWriter(Sauvegarder.livres, true);
            comW = new FileWriter(Sauvegarder.com, true);
            urlW = new FileWriter(Sauvegarder.url, true);
            dateW = new FileWriter(Sauvegarder.dates, true);
            texteW = new FileWriter(Sauvegarder.textes, true);

            Recette[] listeRecettesTriee = listeRecettes.clone();
            Arrays.sort(listeRecettesTriee, new RecetteSousCatComparator());
            // Arrays.sort(listeRecettesTriee, new Recette());
            String sousCategorieCourante = "";
            for (int i = 0; i < listeRecettesTriee.length; i++)
            {
                try
                {
                    if (! sousCategorieCourante.equals(listeRecettesTriee[i].getSousCategorie()))
                    {
                        sousCategorieCourante = listeRecettesTriee[i].getSousCategorie();
                        recettes.write("\n* " + sousCategorieCourante + ":\n");
                    }

                    recettes.write("- " + listeRecettesTriee[i].getNom() + " ");

                    if (listeRecettesTriee[i].getLivre() != null)
                        recettes.write("("
                            + (indiceString(listeRecettesTriee[i].getLivre(), listeLivres, livresW) + 1)
                            + ") (" + listeRecettesTriee[i].getPage() +") ");
                    else
                        recettes.write("() () ");

                    if (listeRecettesTriee[i].getLien() != null)
                        recettes.write("(" + indiceString(listeRecettesTriee[i].getLien().toString(), listeURLs, urlW) + ") ");
                    else
                        recettes.write("() ");

                    if (listeRecettesTriee[i].getDate() != null)
                        recettes.write("(" + indiceString("" + listeRecettesTriee[i].getDate().getTime(), listeDates, dateW) + ") ");
                    else
                        recettes.write("() ");

                    recettes.write("(" + listeRecettesTriee[i].getNote() + ") ");

                    if (listeRecettesTriee[i].getCommentaire() != null)
                        recettes.write("(" + indiceString(listeRecettesTriee[i].getCommentaire(), listeComs, comW) + ") ");
                    else
                        recettes.write("() ");

                    if (listeRecettesTriee[i].getTexte() != null)
                        recettes.write("(" + indiceString(listeRecettesTriee[i].getTexte(), listeTextes, texteW) + ") ");
                    else
                        recettes.write("() ");

                    recettes.write("\n");
                }
                catch (IOException ioe)
                {
                    System.err.println(ioe.getMessage());
                }
            }

            recettes.flush();

            recettes.close();
            livresW.close();
            comW.close();
            urlW.close();
            dateW.close();
            texteW.close();
        }
        catch(IOException ioe)
        {
            System.err.println(ioe.getMessage());
        }
    }
}
