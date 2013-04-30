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

    private void sortRecettes()
    {
        Recette[] suc = sucrees.toArray(new Recette[0]);
        Recette[] sale = salees.toArray(new Recette[0]);

        Arrays.sort(suc);
        Arrays.sort(sale);
        sucrees = new Vector<Recette>(Arrays.asList(suc));
        salees = new Vector<Recette>(Arrays.asList(sale));
    }

    private String[] listeRecetteToStringTab(Vector<Recette> recettes)
    {
        Vector<String> liste = new Vector<String>();
        for (int i = 0; i < recettes.size(); i++)
            liste.add(recettes.elementAt(i).getNom());

        return liste.toArray(new String[0]);
    }

    public boolean existeRecette(String recette)
    {
        sortRecettes();

        return Arrays.binarySearch(listeRecetteToStringTab(sucrees), recette) >= 0
                || Arrays.binarySearch(listeRecetteToStringTab(salees), recette) >= 0;
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
