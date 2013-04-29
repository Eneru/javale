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
    
    /**
     * Choix de la catégorie.
     * 
     * (salé ou sucré)
     */
    private final String choixduPeuple;
        
    /**
     * Constructeur de la class Charger.
     * 
     * @param s
     *      Le choix du fichier de recettes.
     */
    public Charger(String s){
        this.choixduPeuple = "data/"+s+".txt";
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
    
    /**
     * Charge les URLs dans un tableau.
     * 
     * @return Un tableau avec les URLs dans l'ordre des indices.
     */
    public Vector<String> indexURL() // de même que pour les commentaires
    {
        return Charger.lireIndexes(Url);
    }
    
    /**
     * Charge les recettes (salé ou sucré) dans un tableau
     * 
     * @return Un tableau avec toutes les recettes.
     */
    public Vector<Recette> recette ()
    {
        Vector<Recette> listRec = new Vector<Recette>();
        
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
                        StringTokenizer st = new StringTokenizer(line,"- ");
                        String sanstiret = st.nextToken();
                        st = new StringTokenizer(sanstiret," (");
                        int passage=0;
                        while (st.hasMoreTokens())
                        {
                            if (passage==0)
                            {
                                String rec = st.nextToken().toString();
                                r = new Recette(rec,choixduPeuple,scat.toString());
                                passage++;
                            }
                            else
                            {
                                String option = st.nextToken().toString();
                                StringBuffer sb = new StringBuffer(option);
                                sb.deleteCharAt(sb.length()-1);
                                switch (passage){
                                    case 1 : if(sb.length()>0)
                                            {
                                                int indexLivre = Integer.parseInt(sb.toString());
                                                String livre = index().elementAt(indexLivre-1); // élément indice -1 du vector de la liste d'index
                                                r.setLivre(livre);
                                            }
                                            passage++;
                                            break;
                                    
                                    case 2 : if(sb.length()>0)
                                            { 
                                                int page = Integer.parseInt(sb.toString());
                                                r.setPage(page);
                                            }
                                            passage++;
                                            break;
                                            
                                    case 3 : if(sb.length()>0)
                                            {
                                                int indexLien = Integer.parseInt(sb.toString());
                                                String link = url().elementAt(indexLien-1); // élément indice -1 du vector de la liste d'Url
                                                r.setLien(link);
                                            }
                                            passage++;
                                            break;
                                            
                                    case 4 : if(sb.length()>0)
                                            {
                                                int note = Integer.parseInt(sb.toString());
                                                r.setNote(note);
                                            }
                                            passage++;
                                            break;
                                            
                                    case 5 : if(sb.length()>0)
                                            {
                                                int indexCom = Integer.parseInt(sb.toString());
                                                String commentaire = com().elementAt(indexCom-1); // élément indice -1 du vector de la liste d'index;
                                                r.setCommentaire(commentaire);
                                            }
                                            passage++;
                                            break;
                                            
                                    case 6 : if(sb.length()>0)
                                            {
                                                Date date = new Date(Long.parseLong(sb.toString()));
                                                r.setDate(date);
                                            }
                                            passage++;
                                            break;
                                            
                                    case 7 : if(sb.length()>0)
                                            {
                                                int indexTexte = Integer.parseInt(sb.toString());
                                                String text = prep().elementAt(indexTexte-1); // élément indice -1 du vector de la liste de préparation;
                                                r.setTexte(text);
                                            }
                                            passage++;
                                            break;
                                            
                                    default : break;
                                }
                            }
                        }
                        
                        listRec.add(r);
                    }
                                
                }
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
    
    // /**
    //  * Liste les sous catégories.
    //  * 
    //  * @return VallSousCat
    //  *       Toutes les sous-catégories.
    //  */
    //  public Vector<String> sousCat ()
    //  {
    //   Vector<String> VallSousCat = new Vector<String>();
    //  try
        // {
            // String line = null ;

    //    // Lecture
            // BufferedReader input = new BufferedReader(new FileReader(choixduPeuple));
            // try
            // {
    //  // Pour toute ligne lue
                // while ((line = input.readLine()) != null)
                // { 
                    // if (Charger.est_une_sous_cat(line)==true)
                    // {
    //                  StringBuffer sb = new StringBuffer(line);
    //                  StringBuffer scat = new StringBuffer();
                        
    //                  for (int j = 2 ; sb.charAt(j)!=':' ;j++)
    //                  {
    //                      scat.append(sb.charAt(j));
    //                  }
    //                  VallSousCat.add(scat.toString());
    //              }
    //          }
    //      }
            // finally // si jamais il y a un problème, on passe tout de même ici
            // {
                // input.close() ;
            // }
        // }
        // catch (IOException e)
        // {
            // e.printStackTrace() ;
        // }
        
        
    // }
    
    // /**
    //  * Charge les recettes pour une seule sous-catégorie uniquement.
    //  * 
    //  * @param souscat
    //  *       Sous-catégorie de recette.
    //  * @return Vsouscat
    //  *       Vector avec les recettes d'une sous-catégorie.
    //  */
    // public Vector<Recette> sousCatRecette (String souscat)
    // {
    //  Vector<Recette> rec = recette();
    //  Vector<Recette> Vsouscat = new Vector<Recette>();
        
    //  for (int i = 0; i<rec.size(); i++)
    //  {
    //      if (rec.elementAt(i).getSousCategorie() == souscat)
    //      {
    //          Vsouscat.add(rec.elementAt(i));
    //      }
    //  }
        
    //  return Vsouscat;
    // }
    
    
    
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
// public int countNbRecettePourSousCat(String souscat)
    // /*{
    //  try
        // {
            // String line = null ;

    //    // Lecture
            // BufferedReader input = new BufferedReader(new FileReader(choixduPeuple));
            // try
            // {
                // int nbRecSousCat = 0;
                // boolean test=false;
        // // Pour toute ligne lue
                // while ((line = input.readLine()) != null)
                // {
    //              if (Charger.est_une_sous_cat(line)==true && test==false)
    //              { // si l'on a pas encore trouvé la sous-catégorie
    //                  StringBuffer sb = new StringBuffer(line);
    //                  StringBuffer scat = new StringBuffer();
                        
    //                  for (int j = 2 ; sb.charAt(j)!=':' ;j++)
    //                  {
    //                      scat.append(sb.charAt(j));
    //                  }
    //                  if (scat.toString().equals(souscat)==true)
    //                  { // on teste si c'est celle qu'on cherche
    //                      test=true; // on met le test comme "trouvé"
    //                  }
    //              }
                    
    //              else if (Charger.est_une_recette(line)==true && test==true)
    //              { // si on est dans la bonne sous catégorie et que c'est une recette
    //                  nbRecSousCat++;
    //              }
    //              else if (line.equals("\n")==true)
    //              {
    //                  test=false; // on remet le test à faux lorsqu'on arrive à la ligne vide entre sous-catégorie
    //              }
    //          }
            // }
    //      finally // si jamais il y a un problème, on passe tout de même ici
    //      {
            // input.close() ;
    //      }
    //  }
    //  catch (IOException e)
    //  {
    //      e.printStackTrace() ;
    //  }

    // return nbRecSousCat;
    // }*/
    // {
    //  return sousCatRecette(souscat).size();
    // }
   
    /**
     * @fn public static boolean est_une_sous_cat(String s)
     * @brief Teste si le mot est une sous catégorie
     * @return true si c'est une sous-catégorie, false sinon
     */    
    public static boolean est_une_sous_cat(String s)
    {
        StringBuffer sb = new StringBuffer(s);
        if (sb.charAt(0)=='*')
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
        StringBuffer sb = new StringBuffer(s);
        if (sb.charAt(0)=='-')
            return true;
        return false;
    }
    
}
