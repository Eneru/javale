import java.lang.Exception;
import java.sql.Date;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Vector;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * @class Charger
 * @brief Gestion des charges des informations de toutes les recettes
 */
public class Charger extends ChargeSauv
{
    /**
     * Choix de la catégorie.
     *
     * (salé ou sucré)
     */
    private final String choixduPeuple;

    private final Vector<Recette> recettes;
    /**
     * Constructeur de la class Charger.
     *
     * @param s
     *      Le choix du fichier de recettes.
     */
    public Charger(String s){
        this.choixduPeuple = s.equals("sale") ? Charger.sale : Charger.sucre;
        this.recettes = lireRecettes();
    }

    public Vector<Recette> getRecettes()
    {
        return this.recettes;
    }

    public static Vector<String> lireIndexes(String fichier)
    {
        Vector <String> indexes = new Vector<String>();
        String ligne = null;
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(fichier));

            try
            {
                while ((ligne = input.readLine()) != null)
                {
                    String[] mots = ligne.split("\\.\\s*");
                    if (mots.length >= 1)
                    {
                        int indice = Integer.parseInt(mots[0]);
                        if (indexes.size() < indice)
                            indexes.setSize(indice);
                        indexes.setElementAt(mots[1], indice - 1);
                    }
                }
            }
            catch (IOException ioe)
            {
                System.err.println(ioe.getMessage());
            }
            finally
            {
                try
                {
                    input.close();
                }
                catch (IOException ioe)
                {
                    System.err.println(ioe.getMessage());
                }
            }
        }
        catch (FileNotFoundException fnfe)
        {
            System.err.println(fnfe.getMessage());
        }

        return indexes;
    }

    /**
     * Charge les indexs dans un tableau.
     *
     * @return Un tableau avec les livres utilisés dans l'index.
     */
    public static Vector<String> indexLivres()
    {
        return Charger.lireIndexes(Charger.livres);
    }

    /**
     * Charge les commentaires dans un tableau.
     *
     * @return Un tableau avec les commentaires dans l'ordre des indices.
     */
    public static Vector<String> indexComs() // je prends comme convention qu'il n'y a qu'un espace entre "chiffre." et le commentaire (à la différence de l'index)
    {
        return Charger.lireIndexes(Charger.com);
    }

    /**
     * Charge les préparations dans un tableau.
     *
     * @return Un tableau avec les préparations dans l'ordre des indices.
     */
    public static Vector<String> indexDates() // de même que pour les commentaires
    {
        return Charger.lireIndexes(Charger.dates);
    }

    public static Vector<String> indexTextes()
    {
        return Charger.lireIndexes(Charger.textes);
    }
    /**
     * Charge les URLs dans un tableau.
     *
     * @return Un tableau avec les URLs dans l'ordre des indices.
     */
    public static Vector<String> indexURL() // de même que pour les commentaires
    {
        return Charger.lireIndexes(Charger.url);
    }

    private String motParentheses(String chaine)
    {
        String[] mots = chaine.split("\\(|\\)");
        if (mots.length > 0)
            return mots[0];
        else
            return "";
    }
    /**
     * Charge les recettes (salé ou sucré) dans un tableau
     *
     * @return Un tableau avec toutes les recettes.
     */
    public Vector<Recette> lireRecettes()
    {
        Vector<Recette> listRec = new Vector<Recette>();
        Vector<String> livres = Charger.indexLivres();
        Vector<String> coms = Charger.indexComs();
        Vector<String> urls = Charger.indexURL();
        Vector<String> preps = Charger.indexDates();
        Vector<String> textes = Charger.indexTextes();

        try
        {
            String line = null ;

            // Lecture
            BufferedReader input = new BufferedReader(new FileReader(choixduPeuple));
            try
            {
                String scat = null;
                // Pour toute ligne lue
                while ((line = input.readLine()) != null)
                {
                    if (Charger.est_une_sous_cat(line)==true)
                    {
                        String[] mots = line.split("^\\*\\s*|\\:");
                        scat = new String(mots[1]);
                    }
                    else if(Charger.est_une_recette(line)==true)
                    {
                        Recette r;
                        String[] mots = line.split("^\\-\\s*|\\s*\\(");
                        r = new Recette(mots[1], choixduPeuple, scat);
                        if (mots.length >= 3 && mots[2] != null)
                        {
                            String s = motParentheses(mots[2]);
                            if (!s.equals(""))
                                r.setLivre(livres.elementAt(Integer.parseInt(s) - 1));
                        }
                        if (mots.length >= 4 && mots[3] != null)
                        {
                            String s = motParentheses(mots[3]);
                            r.setPage(Integer.parseInt(s.equals("") ? "-1" : s));
                        }
                        if (mots.length >= 5 && mots[4] != null)
                        {
                            String s = motParentheses(mots[4]);
                            if (!s.equals(""))
                                r.setLien(urls.elementAt(Integer.parseInt(s) - 1));
                        }
                        if (mots.length >= 6 && mots[5] != null)
                        {
                            String s = motParentheses(mots[5]);
                            if (!s.equals(""))
                                r.setDate(new Date(Long.parseLong(preps.elementAt(Integer.parseInt(s) - 1))));
                        }
                        if (mots.length >= 7 && mots[6] != null)
                            try
                            {
                                String s = motParentheses(mots[6]);
                                if (!s.equals(""))
                                    r.setNote(Integer.parseInt(s));
                            }
                            catch (Exception e)
                            {
                                System.err.println(e.getMessage());
                            }
                        if (mots.length >= 8 && mots[7] != null)
                        {
                            String s = motParentheses(mots[7]);
                            if (!s.equals(""))
                                r.setCommentaire(coms.elementAt(Integer.parseInt(s) - 1));
                        }
                        if (mots.length >= 9 && mots[8] != null)
                        {
                            String s = motParentheses(mots[8]);
                            if (!s.equals(""))
                                r.setTexte(textes.elementAt(Integer.parseInt(s) - 1));
                        }

                        listRec.add(r);
                    }
                }
            }
            catch (IOException ioe)
            {
                System.err.println(ioe.getMessage());
            }
            finally // si jamais il y a un problème, on passe tout de même ici
            {
                input.close() ;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace() ;
        }

        return listRec;
    }

    /**
     * Liste les sous catégories.
     *
     * @return VallSousCat
     *       Toutes les sous-catégories.
     */
    public Vector<String> sousCat ()
    {
        Vector<String> scat = new Vector<String>();
        Recette[] rec = recettes.toArray(new Recette[0]);
        for (int i = 0; i < rec.length; i++)
            if (!scat.contains(rec[i].getSousCategorie()))
                scat.add(rec[i].getSousCategorie());

        return scat;
    }

    /**
     * Charge les recettes pour une seule sous-catégorie uniquement.
     *
     * @param souscat
     *       Sous-catégorie de recette.
     * @return Vsouscat
     *       Vector avec les recettes d'une sous-catégorie.
     */
    public Vector<Recette> sousCatRecette (String souscat)
    {
        Vector<Recette> Vsouscat = new Vector<Recette>();
        Recette[] rec = recettes.toArray(new Recette[0]);

        for (int i = 0; i < rec.length; i++)
        {
            if (rec[i].getSousCategorie().equals(souscat))
                Vsouscat.add(rec[i]);
        }

        return Vsouscat;
    }

    /**
     * Nombre d'index de livre.
     *
     * @return Le nombre de lignes comptées dans l'index.
     */
    public int countNbIndex()
    {
        return indexLivres().size();
    }

    /**
     * Nombre total de commentaires.
     *
     * @return Le nombre de lignes comptées dans le fichier de commentaires.
     */
    public int countNbCom()
    {
        return indexComs().size();
    }

    /**
     * Nombre total d'URLs.
     *
     * @return Le nombre de lignes comptées dans le fichier d'URLs.
     */
    public int countNbUrl()
    {
        return indexURL().size();
    }

    /**
     * Nombre total de préparations.
     *
     * @return Le nombre de lignes comptées dans le fichier de préparations.
     */
    public int countNbPrep()
    {
        return indexDates().size();
    }

    /**
     * Nombre de recettes dans une sous-catégorie donnée.
     *
     * @param souscat
     *      La sous-catégorie dans laquelle in compte.
     * @return nbRecSousCat
     */
    public int countNbRecettePourSousCat(String souscat)
    {
        return sousCatRecette(souscat).size();
    }

    /**
     * @fn public static boolean est_une_sous_cat(String s)
     * @brief Teste si le mot est une sous catégorie
     * @return true si c'est une sous-catégorie, false sinon
     */
    public static boolean est_une_sous_cat(String s)
    {
        if (s.length() == 0)
            return false;
        else if (s.charAt(0)=='*')
            return true;
        else
            return false;
    }

    /**
     * @fn public static boolean est_une_recette(String s)
     * @brief Teste si le mot est un début d'une recette
     * @return true si c'est une recette, false sinon
     */
    public static boolean est_une_recette(String s)
    {
        if (s.length() == 0)
            return false;
        else if (s.charAt(0)=='-')
            return true;
        else
            return false;
    }

}
