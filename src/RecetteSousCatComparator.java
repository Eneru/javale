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

    // /**
    //  * Teste d'égalité de deux recettes
    //  *
    //  * @param o
    //  *      Recette à comparer.
    //  * @return true si elles ont égales, false sinon.
    //  */
    // public boolean equals(Object o)
    // {
    //     if (o instanceof Recette)
    //     {
    //         Recette r = (Recette) o;
    //         if (this.nom.equals(r.nom)
    //             && this.livre.equals(r.livre)
    //             && this.page == r.page
    //             && this.texte.equals(r.texte)
    //             && this.note == r.note
    //             && this.commentaire.equals(r.commentaire)
    //             && this.categorie.equals(r.categorie)
    //             && this.sousCategorie.equals(r.sousCategorie)
    //             && this.date.equals(r.date)
    //             && this.lien.equals(r.lien)
    //             )
    //             return true;
    //         else
    //             return false;
    //     }
    //     else
    //         return false;
    // }
}
