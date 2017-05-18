package userModel;

import java.util.HashMap;


/**
 * 
 * Cette classe gére la base de données de groupes. Elle doit permettre de sauvegarder et charger les groupes à partir d'un fichier XML. 
 * La structure du fichier XML devra être la même que celle du fichier userDB.xml.
 * @see <a href="../../userDB.xml">userDB.xml</a> 
 * 
 * @version 05/2016 (Mettre à jour)
 * 
 */

public class GroupDB {
	
	/**
	 * 
	 * Le fichier contenant la base de données.
	 * 
	 */
	private String file;
	/**
	 * Le HashMap contenant la base de données
	 */
	private HashMap<Groupe, Integer> DB_Groupe = new HashMap<Groupe, Integer>();
	
	/**
	 * 
	 * Constructeur de GroupDB. 
	 * 
	 * !!!!!!!!!!!! PENSEZ À AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÉES AU DEMARRAGE DE L'APPLICATION !!!!!!
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public GroupDB(String file){
		//TODO Fonction à modifier
		super();
		this.setFile(file);
	}
	
	/**
	 * Getter de file
	 * 
	 * @return 
	 * 		Le nom du fichier qui contient la base de données.
	 */
	
	public String getFile() {
		return file;
	}
	
	/**
	 * Setter de file
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	
	public void setFile(String file) {
		this.file = file;
	}
	/**
	 * loadDB
	 * 
	 * On accède au HashMap
	 */
	public HashMap<Groupe, Integer> loadDB() {
		return DB_Groupe;
	}
	/**
	 * saveDB
	 * 
	 * On remplace le HashMap
	 */
	public void saveDB(HashMap<Groupe, Integer> DB_new) {
		DB_Groupe = DB_new;
	}
}
