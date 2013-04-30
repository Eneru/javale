import java.lang.Exception;
import java.sql.Date;
import java.net.URL;
import java.net.MalformedURLException;
import java.lang.*;
import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

/**
 * @class Charger
 * @brief Gestion des charges des informations de toutes les recettes
 */
public class Charger{
    
    /**
     * Chemin vers l'index.
     */
    private final String Index = "data/index_livres.txt";
    
    /**
     * Chemin vers les commentaires.
     */
    private final String Com = "data/commentaire.txt";
    
    /**
     * Chemin vers les url.
     */
    private final String Url = "data/url.txt";
    
    /**
     * Chemin vers les préparations.
     */
    private final String Preparation = "data/preparation.txt"; // texte avec les préparations
    
    private final String Texte = "data/texte.txt";
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
        this.choixduPeuple = "data/"+s+".txt";
        this.recettes = lireRecettes();
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
                    String[] mots = ligne.split(".\\s*");
                    int indice = Integer.parseInt(mots[0]);
                    if (indexes.size() < indice)
                        indexes.setSize(indice);
                    indexes.setElementAt(mots[1], indice - 1);
                }
            }
            catch (IOException ioe)
            {
                System.err.println(ioe.getMessage());
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
    public Vector<String> indexLivres()
    {
        return Charger.lireIndexes(Index);
    }
    
    /**
     * Charge les commentaires dans un tableau.
     * 
     * @return Un tableau avec les commentaires dans l'ordre des indices.
     */
    public Vector<String> indexComs() // je prends comme convention qu'il n'y a qu'un espace entre "chiffre." et le commentaire (à la différence de l'index)
    {
        return Charger.lireIndexes(Com);
    }
    
    /**
     * Charge les préparations dans un tableau.
     * 
     * @return Un tableau avec les préparations dans l'ordre des indices.
     */
    public Vector<String> indexPreps() // de même que pour les commentaires
    {
        return Charger.lireIndexes(Preparation);
    }
    
    public Vector<String> indexTextes()
    {
        return Charger.lireIndexes(Texte);
    }
    /**
     * Charge les URLs dans un tableau.
     * 
     * @return Un tableau avec les URLs dans l'ordre des indices.
     */
    public Vector<String> indexURL() // de même que pour les commentaires
    {
        return Charger.lireIndexes(Url);
    }
    
    private String motParentheses(String chaine)
    {
        String[] mots = chaine.split("\\(|\\)");
        return mots[0];
    }
    /**
     * Charge les recettes (salé ou sucré) dans un tableau
     * 
     * @return Un tableau avec toutes les recettes.
     */
    public Vector<Recette> lireRecettes()
    {
        Vector<Recette> listRec = new Vector<Recette>();
        Vector<String> livres = indexLivres();
        Vector<String> coms = indexComs();
        Vector<String> urls = indexURL();
        Vector<String> preps = indexPreps();
        Vector<String> textes = indexTextes();
        
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
                        String[] mots = line.split("\\*\\s*|\\:");
                        scat = new String(mots[0]);
                    }
                    else if(Charger.est_une_recette(line)==true)
                    {
                        Recette r;
                        String[] mots = line.split("\\-\\s*|\\s*");
                        r = new Recette(mots[0]);
                        if (mots.length >= 2)
                            r.setLivre(livres.elementAt(Integer.parseInt(motParentheses(mots[1]))));
                        if (mots.length >= 3)
                            r.setPage(Integer.parseInt(motParentheses(mots[2])));
                        if (mots.length >= 4)
                            r.setLien(urls.elementAt(Integer.parseInt(motParentheses(mots[3]))));
                        if (mots.length >= 5)
                            r.setDate(new Date(Long.parseLong(preps.elementAt(Integer.parseInt(motParentheses(mots[4]))))));
                        if (mots.length >= 6)
                            try
                            {
                                r.setNote(Integer.parseInt(motParentheses(mots[5])));
                            }
                            catch (Exception e)
                            {
                                System.err.println(e.getMessage());
                            }
                        if (mots.length >= 7)
                            r.setCommentaire(coms.elementAt(Integer.parseInt(motParentheses(mots[6]))));
                        if (mots.length >= 8)
                            r.setTexte(textes.elementAt(Integer.parseInt(motParentheses(mots[7]))));

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
        return indexPreps().size();
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
        if (s.charAt(0)=='*')
            return true;
        return false;
    }

    /**
     * @fn public static boolean est_une_recette(String s)
     * @brief Teste si le mot est un début d'une recette
     * @return true si c'est une recette, false sinon
     */
    public static boolean est_une_recette(String s)
    {
        if (s.charAt(0)=='-')
            return true;
        return false;
    }

}
