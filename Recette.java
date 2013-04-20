import java.lang.String;
import java.io.*;
import java.util.System;

public class Recette{
	/**
	 * @see Recette#getT
	 * @see Recette#setT
	 */
	private String titre;
	
	/**
	 * @see Recette#getC
	 * @see Recette#setC
	 */
	private String categorie;
	
	/**
	 * @see Recette#getSC
	 * @see Recette#setSC
	 */
	private String sous-categorie;
	
	/**
	 * @see Recette#getP
	 * @see Recette#setP
	 */
	private String provenance;
	
	/**
	 * Constructeur de Recette.
	 * 
	 * @param t
	 * 			Titre de la recette.
	 * @param c
	 * 			Categorie de la recette.
	 * @param sc
	 * 			Sous-categorie de la recette.
	 * @param p
	 * 			Provenance de la recette.
	 */
	public Recette (String t, String c, String sc, String p){
		titre = t;
		categorie = c;
		sous-categorie = sc;
		provenance = p;
	}
	
	/**
	 * Retourne le titre de la recette.
	 * 
	 * @return Le titre.
	 */
	public String getT(){
		return titre;
	}	
	
	/**
	 * Retourne la categorie de la recette.
	 * 
	 * @return La categorie.
	 */
	public String getC(){
		return categorie;
	}	
	
	/**
	 * Retourne la sous-categorie de la recette.
	 * 
	 * @return La sous-categorie.
	 */	
	public String getSC(){
		return sous-categorie;
	}
	
	/**
	 * Retourne la provenance de la recette.
	 * 
	 * @return La provenance.
	 */	
	public String getP(){
		return provenance;
	}
	
	/**
	 * Permet de modifier le titre de la recette.
	 * 
	 * @param t
	 * 			Nouveau titre de la recette.
	 */
	public void setT(String t){
		titre = t;
	}	
	
	/**
	 * Permet de modifier la categorie de la recette.
	 * 
	 * @param c
	 * 			Nouvelle categorie de la recette.
	 */
	public void setC(String c){
		categorie = c;
	}	
	
	/**
	 * Permet de modifier la sous-categorie de la recette.
	 * 
	 * @param sc
	 * 			Nouvelle sous-categorie de la recette.
	 */	
	public void setSC(String sc){
		sous-categorie = sc;
	}
	
	/**
	 * Permet de modifier la provenance de la recette.
	 * 
	 * @param p
	 * 			Nouvelle provenance de la recette.
	 */	
	public void setP(String p){
		provenance = p;
	}
}
	
