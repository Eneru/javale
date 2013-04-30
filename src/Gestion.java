import java.lang.Object;
import java.util.Arrays;
import java.util.Vector;

public class Gestion
{
    Vector<Recette> salees;
    Vector<Recette> sucrees;

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

    public Vector<Recette> getSucrees()
    {
        return sucrees;
    }

    public Vector<Recette> getSalees()
    {
        return salees;
    }

    public static Vector<Recette> sousCategoriesVector(Vector<Recette> recettes, String sousCategorie)
    {
        Vector<Recette> recSousCat = new Vector<Recette>();

        for (int i = 0; i < recettes.size(); i++)
            if (recettes.elementAt(i).getSousCategorie().equals(sousCategorie))
                recSousCat.add(recettes.elementAt(i));

        return recSousCat;
    }

    public static Vector<String> listeSousCatVector(Vector<Recette> recettes)
    {
        Vector<String> liste = new Vector<String>();

        for (int i = 0; i < recettes.size(); i++)
            if (!liste.contains(recettes.elementAt(i).getSousCategorie()))
                liste.add(recettes.elementAt(i).getSousCategorie());

        return liste;
    }

    public void ajouterRecette(Recette r)
    {
        if (r.getCategorie().equals("sale"))
            salees.add(r);
        else
            sucrees.add(r);
    }

    private static Vector<Recette> supprimerRecetteVector(Vector<Recette> recettes, Recette r)
    {
        recettes.remove(r);
        return recettes;
    }

    public void supprimerRecetteSucree(Recette r)
    {
        sucrees = supprimerRecetteVector(sucrees, r);
    }

    public void supprimerRecetteSalee(Recette r)
    {
        salees = supprimerRecetteVector(salees, r);
    }

    private static Vector<Recette> supprimerRecetteVector(Vector<Recette> recettes, String s)
    {
        return supprimerRecetteVector(recettes, rechercheVector(recettes, s));
    }

    public void supprimerRecetteSucree(String s)
    {
        sucrees = supprimerRecetteVector(sucrees, s);
    }

    public void supprimerRecetteSalee(String s)
    {
        salees = supprimerRecetteVector(salees, s);
    }

    private static Vector<Recette> sortVectorRecettes(Vector<Recette> recettes)
    {
        Recette[] tab = recettes.toArray(new Recette[0]);
        Arrays.sort(tab);
        return new Vector<Recette>(Arrays.asList(tab));
    }

    private void sortRecettesSucrees()
    {
        sucrees = sortVectorRecettes(sucrees);
    }

    private void sortRecettesSalees()
    {
        salees = sortVectorRecettes(salees);
    }

    private void sortRecettes()
    {
        sortRecettesSucrees();
        sortRecettesSalees();
    }

    private static String[] listeRecetteToStringTab(Vector<Recette> recettes)
    {
        Vector<String> liste = new Vector<String>();
        for (int i = 0; i < recettes.size(); i++)
            liste.add(recettes.elementAt(i).getNom());

        return liste.toArray(new String[0]);
    }

    private boolean existeRecetteVector(Vector<Recette> recettes, String recherche)
    {
        return Arrays.binarySearch(listeRecetteToStringTab(sortVectorRecettes(recettes)), recherche) >= 0;
    }

    public boolean existeRecetteSucree(String recette)
    {
        return existeRecetteVector(sucrees, recette);
    }

    public boolean existeRecetteSalee(String recette)
    {
        return existeRecetteVector(salees, recette);
    }

    public boolean existeRecette(String recette)
    {
        return existeRecetteSalee(recette) || existeRecetteSucree(recette);
    }

    private boolean existeRecetteVector(Vector<Recette> recettes, Recette recherche)
    {
        return recettes.contains(recherche);
    }

    public boolean existeRecetteSucree(Recette recette)
    {
        return existeRecetteVector(sucrees, recette);
    }

    public boolean existeRecetteSalee(Recette recette)
    {
        return existeRecetteVector(salees, recette);
    }

    public boolean existeRecette(Recette recette)
    {
        return existeRecetteSalee(recette) || existeRecetteSucree(recette);
    }

    private static Recette rechercheVector(Vector<Recette> recettes, String recherche)
    {
        for (int i = 0; i < recettes.size(); i++)
            if (recettes.elementAt(i).getNom().equals(recherche))
                return recettes.elementAt(i);

        return null;
    }

    public Recette rechercheSucree(String recherche)
    {
        return rechercheVector(sucrees, recherche);
    }

    public Recette rechercheSalee(String recherche)
    {
        return rechercheVector(salees, recherche);
    }

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

    public static void main(String[] args)
    {
        Vector<Recette> rr = Charger.lireRecettes("sale");
        rr.add(new Recette("lolilol", "sale", "azerty", "ah", 3));
        Recette[] r = rr.toArray(new Recette[0]);

        Sauvegarder s = new Sauvegarder("sale");
        s.sauvegarderRecettes(r);
    }
}
