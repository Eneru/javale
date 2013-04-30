import java.util.Comparator;

public class RecetteSousCatComparator implements Comparator<Recette>
{
    /**
     * Compare deux recettes:
     * - Valeur négative : r1.sousCategorie < r2.sousCategorie
     * - Zéro : r1.sousCategorie == r2.sousCategorie
     * - Valeur positive : r1.sousCategorie > r2.sousCategorie
     * 
     * @param r1
     *      Première recette à comparer.
     * @param r2
     *      Deuxième recette à comparer.
     * @return Ordre des recettes.
     */
    public int compare(Recette r1, Recette r2)
    {
        return r1.getSousCategorie().compareTo(r2.getSousCategorie());
    }
}
