import java.lang.Object;
import java.util.Arrays;
import java.util.Vector;

/**
 * @class Gestion
 * @brief Gestion des fichiers data
 */
public class Gestion
{
	/**
	 * Liste des recettes salées.
	 * 
	 * @see getSalees#Gestion
	 */
    Vector<Recette> salees;
    
    /**
	 * Liste des recettes sucrées.
	 * 
	 * @see getSucrees#Gestion
	 */
    Vector<Recette> sucrees;

	/**
	 * Constructeur des gestions.
	 * 
	 */
    public Gestion()
    {
        this.salees = Charger.lireRecettes("sale");
        this.sucrees = Charger.lireRecettes("sucrees");
    }

    /**
     * Liste les sous catégories.
     *
     * @return VallSousCat
     *       Toutes les sous-catégories.
     */
    public Vector<String> sousCat()
    {
        Vector<String> scat = new Vector<String>();
        int min = sucrees.size() < salees.size() ? sucrees.size() : salees.size();
        Vector<Recette> recMin = min == sucrees.size() ? sucrees : salees;
        Vector<Recette> recMax = min == sucrees.size() ? salees : sucrees;

        for (int i = 0; i < min; i++)
        {
            if (!scat.contains(recMin.elementAt(i).getSousCategorie()))
                scat.add(recMin.elementAt(i).getSousCategorie());
            if (!scat.contains(recMax.elementAt(i).getSousCategorie()))
                scat.add(recMax.elementAt(i).getSousCategorie());
        }

        for (int i = min; i < recMax.size(); i++)
            if (!scat.contains(recMax.elementAt(i).getSousCategorie()))
                scat.add(recMax.elementAt(i).getSousCategorie());

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
    public Vector<Recette> sousCatRecette(String souscat)
    {
        Vector<Recette> Vsouscat = new Vector<Recette>();
        int min = sucrees.size() < salees.size() ? sucrees.size() : salees.size();
        Vector<Recette> recMin = min == sucrees.size() ? sucrees : salees;
        Vector<Recette> recMax = min == sucrees.size() ? salees : sucrees;

        for (int i = 0; i < min; i++)
        {
            if (recMin.elementAt(i).getSousCategorie().equals(souscat))
                Vsouscat.add(recMin.elementAt(i));
            if (recMax.elementAt(i).getSousCategorie().equals(souscat))
                Vsouscat.add(recMax.elementAt(i));
        }

        for (int i = min; i < recMax.size(); i++)
            if (recMax.elementAt(i).getSousCategorie().equals(souscat))
                Vsouscat.add(recMax.elementAt(i));

        return Vsouscat;
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
	 * Liste toutes les recettes sucrées.
	 * 
	 * @return sucrees
	 * 		Vector des recettes sucrées.
	 */
    public Vector<Recette> getSucrees()
    {
        return sucrees;
    }

	/**
	 * Liste toutes les recettes salées.
	 * 
	 * @return sucrees
	 * 		Vector des recettes salées.
	 */
    public Vector<Recette> getSalees()
    {
        return salees;
    }

	/**
	 * Charge les recettes d'une sous catégorie
	 * 
	 * @param recettes
	 * 		Liste de recettes.
	 * @param sousCategorie
	 * 		La sous-catégorie concernée.
	 * @return recSousCat
	 * 		Un vector des recettes de la sous-catégorie.
	 */
    public static Vector<Recette> sousCategoriesVector(Vector<Recette> recettes, String sousCategorie)
    {
        Vector<Recette> recSousCat = new Vector<Recette>();

        for (int i = 0; i < recettes.size(); i++)
            if (recettes.elementAt(i).getSousCategorie().equals(sousCategorie))
                recSousCat.add(recettes.elementAt(i));

        return recSousCat;
    }

	/**
	 * Liste les sous-catégories de recettes.
	 * 
	 * @param recettes
	 * 		Liste de recettes.
	 * @return liste
	 * 		Liste des sous-catégories.
	 */
    public static Vector<String> listeSousCatVector(Vector<Recette> recettes)
    {
        Vector<String> liste = new Vector<String>();

        for (int i = 0; i < recettes.size(); i++)
            if (!liste.contains(recettes.elementAt(i).getSousCategorie()))
                liste.add(recettes.elementAt(i).getSousCategorie());

        return liste;
    }

	/**
	 * Ajout d'une recette dans salees ou sucrees.
	 * 
	 * @param r
	 * 		Recette à ajouter.
	 */
    public void ajouterRecette(Recette r)
    {
        if (r.getCategorie().equals("sale"))
            salees.add(r);
        else
            sucrees.add(r);
    }

	/**
	 * Supprime une recette dans une liste de recettes.
	 * 
	 * @param recettes
	 * 		Liste de recettes.
	 * @param r
	 * 		Recette à supprimer.
	 * @return recettes
	 * 		recettes-r.
	 */
    private static Vector<Recette> supprimerRecetteVector(Vector<Recette> recettes, Recette r)
    {
        recettes.remove(r);
        return recettes;
    }

	/**
	 * Supprime une recette sucrée.
	 * 
	 * @param r
	 * 		Recette à supprimer.
	 */
    public void supprimerRecetteSucree(Recette r)
    {
        sucrees = supprimerRecetteVector(sucrees, r);
    }

	/**
	 * Supprime une recette salée.
	 * 
	 * @param r
	 * 		Recette à supprimer.
	 */
    public void supprimerRecetteSalee(Recette r)
    {
        salees = supprimerRecetteVector(salees, r);
    }

	/**
	 * Supprime une recette dans une liste de recettes à l'aide du nom.
	 * 
	 * @param recettes
	 * 		Liste de recettes.
	 * @param s
	 * 		Nom de la recette à supprimer.
	 * @return recettes
	 * 		recettes-s.
	 */
    private static Vector<Recette> supprimerRecetteVector(Vector<Recette> recettes, String s)
    {
        return supprimerRecetteVector(recettes, rechercheVector(recettes, s));
    }

	/**
	 * Supprime une recette sucrée à l'aide du nom.
	 * 
	 * @param s
	 * 		Nom de la recette à supprimer.
	 */
    public void supprimerRecetteSucree(String s)
    {
        sucrees = supprimerRecetteVector(sucrees, s);
    }
	
	/**
	 * Supprime une recette salée à l'aide du nom.
	 * 
	 * @param s
	 * 		Nom de la recette à supprimer.
	 */
    public void supprimerRecetteSalee(String s)
    {
        salees = supprimerRecetteVector(salees, s);
    }

	/**
	 * Trie une liste de recettes.
	 * 
	 * @param recettes
	 * 		Liste de recettes.
	 * @return tab
	 * 		Liste de recettes triée.
	 */
    private static Vector<Recette> sortVectorRecettes(Vector<Recette> recettes)
    {
        Recette[] tab = recettes.toArray(new Recette[0]);
        Arrays.sort(tab);
        return new Vector<Recette>(Arrays.asList(tab));
    }

	/**
	 * Trie les recettes sucrées.
	 */
    private void sortRecettesSucrees()
    {
        sucrees = sortVectorRecettes(sucrees);
    }

	/**
	 * Trie les recettes salées.
	 */
    private void sortRecettesSalees()
    {
        salees = sortVectorRecettes(salees);
    }

	/**
	 * Trie toutes les recettes.
	 */
    private void sortRecettes()
    {
        sortRecettesSucrees();
        sortRecettesSalees();
    }

	/**
	 * Retourne les noms de la liste de recettes.
	 * 
	 * @param recettes
	 * 		Liste de recettes.
	 * @return liste
	 * 		Liste de nom de recettes.
	 */
    private static String[] listeRecetteToStringTab(Vector<Recette> recettes)
    {
        Vector<String> liste = new Vector<String>();
        for (int i = 0; i < recettes.size(); i++)
            liste.add(recettes.elementAt(i).getNom());

        return liste.toArray(new String[0]);
    }

	/**
	 * Existence d'une recette dans une liste de recettes.
	 * 
	 * @param recettes
	 * 		Liste de recettes.
	 * @param recherche
	 * 		Nom de la recette recherchée.
	 * @return true si elle existe, false sinon.
	 */
    private boolean existeRecetteVector(Vector<Recette> recettes, String recherche)
    {
        return Arrays.binarySearch(listeRecetteToStringTab(sortVectorRecettes(recettes)), recherche) >= 0;
    }

	/**
	 * Existence d'une recette dans la liste des recettes sucrées.
	 * 
	 * @param recette
	 * 		Nom de la recette recherchée.
	 * @return true si elle existe, false sinon.
	 */
    public boolean existeRecetteSucree(String recette)
    {
        return existeRecetteVector(sucrees, recette);
    }

	/**
	 * Existence d'une recette dans la liste des recettes salées.
	 * 
	 * @param recette
	 * 		Nom de la recette recherchée.
	 * @return true si elle existe, false sinon.
	 */
    public boolean existeRecetteSalee(String recette)
    {
        return existeRecetteVector(salees, recette);
    }
	
	/**
	 * Existence d'une recette dans la liste des recettes du gestionnaire.
	 * 
	 * @param recette
	 * 		Nom de la recette recherchée.
	 * @return true si elle existe, false sinon.
	 */
    public boolean existeRecette(String recette)
    {
        return existeRecetteSalee(recette) || existeRecetteSucree(recette);
    }

	/**
	 * Existence d'une recette dans une liste de recettes.
	 * 
	 * @param recettes
	 * 		Liste de recettes.
	 * @param recherche
	 * 		Recette recherchée.
	 * @return true si elle existe, false sinon.
	 */
    private boolean existeRecetteVector(Vector<Recette> recettes, Recette recherche)
    {
        return recettes.contains(recherche);
    }

	/**
	 * Existence d'une recette dans la liste des recettes sucrées.
	 * 
	 * @param recette
	 * 		Recette recherchée.
	 * @return true si elle existe, false sinon.
	 */
    public boolean existeRecetteSucree(Recette recette)
    {
        return existeRecetteVector(sucrees, recette);
    }

	/**
	 * Existence d'une recette dans la liste des recettes salées.
	 * 
	 * @param recette
	 * 		Recette recherchée.
	 * @return true si elle existe, false sinon.
	 */
    public boolean existeRecetteSalee(Recette recette)
    {
        return existeRecetteVector(salees, recette);
    }

	/**
	 * Existence d'une recette dans la liste des recettes du gestionnaire.
	 * 
	 * @param recette
	 * 		Recette recherchée.
	 * @return true si elle existe, false sinon.
	 */
    public boolean existeRecette(Recette recette)
    {
        return existeRecetteSalee(recette) || existeRecetteSucree(recette);
    }

	/**
	 * Retourne la recette si elle existe.
	 * 
	 * @param recettes
	 * 		Liste de recettes.
	 * @param recherche
	 * 		Nom de recette à rechercher dans la liste.
	 * @return La recette recherchée.
	 */
    private static Recette rechercheVector(Vector<Recette> recettes, String recherche)
    {
        for (int i = 0; i < recettes.size(); i++)
            if (recettes.elementAt(i).getNom().equals(recherche))
                return recettes.elementAt(i);

        return null;
    }

	/**
	 * Retourne la recette si elle existe dans la liste des recettes sucrées.
	 * 
	 * @param recherche
	 * 		Nom de recette à rechercher dans la liste.
	 * @return La recette recherchée.
	 */
    public Recette rechercheSucree(String recherche)
    {
        return rechercheVector(sucrees, recherche);
    }

	/**
	 * Retourne la recette si elle existe dans la liste des recettes salées.
	 * 
	 * @param recherche
	 * 		Nom de recette à rechercher dans la liste.
	 * @return La recette recherchée.
	 */
    public Recette rechercheSalee(String recherche)
    {
        return rechercheVector(salees, recherche);
    }

	/**
	 * Retourne la recette si elle existe dans la liste des recettes du gestionnaire.
	 * 
	 * @param recherche
	 * 		Nom de recette à rechercher dans la liste.
	 * @return La recette recherchée.
	 */
    public Recette recherche(String recherche)
    {
        Recette r = rechercheSalee(recherche);

        if (r != null)
            return r;
        else
            return rechercheSucree(recherche);
    }

    private static Vector<Recette> recherchePartielle(Vector<Recette> recettes, String recherche)
    {
        Vector<Recette> liste = new Vector<Recette>();

        for (int i = 0; i < recettes.size(); i++)
            if (recettes.elementAt(i).getNom().matches(recherche))
                liste.add(recettes.elementAt(i));

        return liste;
    }

    public Vector<Recette> recherchePartielleSucree(String recherche)
    {
        return recherchePartielle(sucrees, recherche);
    }

    public Vector<Recette> recherchePartielleSalee(String recherche)
    {
        return recherchePartielle(salees, recherche);
    }
}
