package userModel;

import java.util.HashMap;

/**
 * 
 * Cette classe gére la base de données d'utilisateurs. Elle doit permettre de sauvegarder et charger les utilisateurs et les groupes à partir d'un fichier XML. 
 * La structure du fichier XML devra être la même que celle du fichier userDB.xml.
 * @see <a href="../../userDB.xml">userDB.xml</a> 
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe à modifier

public class UserDB {
	
	/**
	 * 
	 * Le fichier contenant la base de données.
	 * 
	 */
	private String file;
	/**
	 * Le HashMap contenant la base de données des utilisateurs
	 */
	private HashMap<Utilisateur, Integer> DB_Utilisateurs = new HashMap<Utilisateur, Integer>();
	
	/**
	 * 
	 * Constructeur de UserDB. 
	 * 
	 * !!!!!!!!!!!! PENSEZ À AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÉES AU DEMARRAGE DE L'APPLICATION !!!!!!
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public UserDB(String file){
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
	public HashMap<Utilisateur, Integer> loadDB() {
		return DB_Utilisateurs;
	}
	/**
	 * saveDB
	 * 
	 * On remplace le HashMap
	 */
	public void saveDB(HashMap<Utilisateur, Integer> DB_new) {
		DB_Utilisateurs = DB_new;
	}
}
