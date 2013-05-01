import java.util.Scanner;
import java.lang.System;
import java.util.Date;



public class Affichage
{	
	static Gestion g = new Gestion();
	static private boolean quit = true;
	
	public Affichage()
	{}
	
	public static void quitter()
	{
		quit=false;
	}
	
	public static void aidePrincipale()
	{
		System.out.println("- Ajout : Ajouter une recette");
		System.out.println("- Suppr : Supprimer une recette");
		System.out.println("- Rech : Rechercher une recette");
		System.out.println("- Sous-Catégorie : Lister toutes les sous-catégories");
		System.out.println("- Recette : Lister les recettes d'une sous-catégorie");
		System.out.println("- Quitter : Quitter le gestionnaire de recette\n");
	}
	
	public static void menuPrincipal()
	{
		String rep;
		Scanner sc = new Scanner(System.in);
		Affichage.affichePrompt();
		rep = sc.nextLine();
		switch(rep)
		{
			case "Ajout" : Affichage.ajout(); break;
			
			case "Suppr" : Affichage.suppr(); break;
			
			case "Rech" : Affichage.rech(); break;
			
			case "Sous-Catégorie" : Affichage.souscat(); break;
			
			case "Recette" : Affichage.rec(); break;
			
			case "Aide" : Affichage.aidePrincipale(); break;
			
			case "Quitter" : Affichage.quitter(); break;
			
			default : System.out.println("Commande invalide, écrire 'Aide' pour avoir les différentes commandes\n");
		}
	}
	
	public static void ajout()
	{
		Recette[] rArray;
		String cat;
		String sscat;
		String nom;
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez la catégorie : ");
		cat = sc.nextLine();
		while ("sucre".equals(cat) != true || "sale".equals(cat) != true || "Quitter".equals(cat) == true)
		{
			System.out.print("Catégorie inexistante, entrez à nouveau la catégorie (sucre ou sale) : ");
			cat = sc.nextLine();
		}
		if ("Quitter".equals(cat) != true)
		{
			System.out.print("\nEntrez une sous-catégorie : ");
			sscat = sc.nextLine();
			System.out.print("\nEntrez un nom de recette : ");
			nom = sc.nextLine();
		
			Recette r = new Recette(nom,cat,sscat);
		
			r = Affichage.menuAjout(r);
		
			g.ajouterRecette(r);
		
			if ("sucre".equals(r.getCategorie())==true)
			{
				rArray = g.getSucrees().toArray(new Recette[0]);
			}
			else
			{
				rArray = g.getSalees().toArray(new Recette[0]);
			}
			Sauvegarder s = new Sauvegarder(cat);
			s.sauvegarderRecettes(rArray);
		}
	}
	
	public static Recette menuAjout(Recette r)
	{
		Recette nr = r;
		System.out.println("1- Ajouter un livre");
		System.out.println("2- Ajouter une note");
		System.out.println("3- Ajouter un commentaire");
		System.out.println("4- Ajouter une URL");
		System.out.println("5- Ajouter une date");
		System.out.println("6- Ajouter un texte");
		System.out.println("0- Ajouter la recette");
		Scanner sc = new Scanner(System.in);
		Affichage.affichePrompt();
		int rep = sc.nextInt();
		String repChaine;
		while (rep != 0)
		{
			switch(rep)
			{
				case 1 : System.out.print("\nLivre : ");
						 repChaine = sc.nextLine();
						 nr.setLivre(repChaine);
						 System.out.print("\nPage (0 s'il n'y en a pas): ");
						 rep = sc.nextInt();
						 nr.setPage(rep);
						 break;
				
				case 2 : System.out.print("\nNote (sur 10): ");
						 rep = sc.nextInt();
						 try
						 {
							nr.setNote(rep);
						 }
						 catch (Exception e)
						 {
							 System.err.println(e);
						 }
						 break;
				
				case 3 : System.out.print("\nCommentaire : ");
						 repChaine = sc.nextLine();
						 nr.setCommentaire(repChaine);
						 break;
				
				case 4 : System.out.print("\nLien : ");
						 repChaine = sc.nextLine();
						 nr.setLien(repChaine);
						 break;
				
				case 5 : System.out.print("\nDate : ");
						 long repD = sc.nextLong();
						 Date d = new Date(repD);
						 nr.setDate(d);
						 break;
				
				case 6 : System.out.print("\nTexte (sur une ligne) : ");
						 repChaine = sc.nextLine();
						 nr.setTexte(repChaine);
						 break;
				
				case 0 : break;
				
				default : System.out.println("\nCommande inconnue"); break;
				
			}
			Affichage.affichePrompt();
			rep = sc.nextInt();
		}
		return nr;
	}
	
	public static void suppr()
	{
		System.out.print("Entrez un nom de recette à supprimer : ");
		Scanner sc = new Scanner(System.in);
		String rep = sc.nextLine();
		if (g.existeRecetteSalee(rep)==true)
			g.supprimerRecetteSalee(rep);
		else if (g.existeRecetteSucree(rep)==true)
			g.supprimerRecetteSucree(rep);
		else
			System.out.println("\nLa recette que vous avez entrée n'existe pas\n");
	}
			
	
	public static void rech()
	{
		String rep;
		Scanner sc = new Scanner(System.in);
		System.out.print("\nVeuillez entrer le nom de la recette à rechercher : ");
		rep = sc.nextLine();
		if (g.existeRecette(rep) == true)
		{
			Recette r = g.recherche(rep);
			System.out.println("\nCatégorie : "+r.getCategorie()+"\nSous-catégorie : "+r.getSousCategorie());
			if (r.getLivre() != null)
			{
				System.out.print("\nLivre : "+r.getLivre());
				if (r.getPage() != -1)
					System.out.println("(page "+r.getPage()+")");
				System.out.println(" ");
			}
			if (r.getCommentaire() != null)
				System.out.println("\nCommentaire : "+r.getCommentaire());
			if (r.getLien() != null)
				System.out.println("\nLien : "+r.getLien());
			if (r.getDate() != null)
				System.out.println("\nDate : "+r.getDate());
			if (r.getTexte() != null)
				System.out.println("\nPréparation : "+r.getTexte());
			
		}
	}
	
	public static void souscat()
	{
		for (int i = 0 ; i < g.sousCat().size() ; i++)
		{
			System.out.println(g.sousCat().elementAt(i));
		}
		System.out.println(" ");
	}
	
	public static void rec ()
	{
		System.out.println("\nEntrez une sous-catégorie");
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		for (int i = 0 ; i < g.sousCatRecette(str).size() ; i++)
		{
			System.out.println(g.sousCatRecette(str).elementAt(i).toString());
		}
	}
		
	public static void affichePrompt()
	{
		System.out.print("\n> ");
	}
		
	public static void main(String[] args)
	{
		System.out.println("Bienvenu sur le gestionnaire de recettes\n");
		Affichage.aidePrincipale();
		System.out.println("- Aide : Réafficher ce menu\n");
		while(quit)
		{
			Affichage.menuPrincipal();
		}
	}
}
