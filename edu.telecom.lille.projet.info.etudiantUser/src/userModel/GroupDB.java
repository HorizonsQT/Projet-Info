package userModel;

import java.util.HashMap;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;


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
	private String fichier;
	/**
	 * Le HashMap contenant la base de données
	 */
	private HashMap<Integer, Groupe> DB_Groupe = new HashMap<Integer, Groupe>();
	
	/**
	 * 
	 * Constructeur de GroupDB. 
	 * 
	 * !!!!!!!!!!!! PENSEZ À AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÉES AU DEMARRAGE DE L'APPLICATION !!!!!!
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public GroupDB(String fichier){
		//TODO Fonction à modifier
		super();
		Administrateur su = root_admin();
		this.setFile(fichier);
	}
	
	/**
	 * Getter de file
	 * 
	 * @return 
	 * 		Le nom du fichier qui contient la base de données.
	 */
	
	public String getFile() {
		return fichier;
	}
	
	/**
	 * Setter de file
	 * 
	 * @param file
	 * 		Le nom du fichier qui contient la base de données.
	 */
	
	public void setFile(String fichier) {
		this.fichier = fichier;
	}
	/**
	 * loadDB
	 * 
	 * On accède au HashMap
	 */
	public HashMap<Integer, Groupe> loadDB() {
		SAXBuilder builder = new SAXBuilder();
		File fichier_xml = new File(fichier);
		
		/**
		 * L'utilisation d'un fichier extérieur nécessite la construction try/catch
		 */
		try {
			Document document = (Document) builder.build(fichier_xml);
			Element racine = document.getRootElement();
			List<Element> list_groupe = racine.getChildren("Groups");
			for (int i = 0; i < list_groupe.size(); i++) {
			   Element etud = (Element) list_groupe.get(i);
			   int ID = Integer.parseInt(etud.getChildText("studentid"));
			   String su = root_admin().login;
			   Groupe groupe_temp = new Groupe(su, ID);
			   DB_Groupe.put(i, groupe_temp);
			}		
			
			
			} catch(IOException e) {
			    // Lorsque des erreurs se présentent.
				System.out.println("Erreur 1!");
			} catch (Throwable e) {
				// Pour les erreurs.
				System.out.println("Erreur 2!");
				e.printStackTrace();
			} finally {
			    // Encore les erreurs
				System.out.println("Erreur 3!");
		}
		return DB_Groupe;
	}
	/**
	 * saveDB
	 * 
	 * On remplace le HashMap
	 */
	public void saveDB(HashMap<Integer, Groupe> DB_new) {
		DB_Groupe = DB_new;
	}
	
	public Administrateur root_admin() {
		Administrateur su = new Administrateur("sudo", 0, "su", "do", "admin");
		return su;
	}
}
