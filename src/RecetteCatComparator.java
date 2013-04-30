import java.util.Comparator;

public class RecetteCatComparator implements Comparator<Recette>
{
    /**
     * Compare deux recettes:
     * - Valeur négative : r1.categorie < r2.categorie
     * - Zéro : r1.categorie == r2.categorie
     * - Valeur positive : r1.categorie > r2.categorie
     * 
     * @param r1
     *      Première recette à comparer.
     * @param r2
     *      Deuxième recette à comparer.
     * @return Ordre des recettes.
     */
    public int compare(Recette r1, Recette r2)
    {
        return r1.getCategorie().compareTo(r2.getCategorie());
    }
}
