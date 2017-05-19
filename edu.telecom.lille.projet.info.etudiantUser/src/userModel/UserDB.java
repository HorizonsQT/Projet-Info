package userModel;

import java.util.HashMap;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;

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
	private String fichier;
	/**
	 * Le HashMap contenant la base de données des utilisateurs
	 */
	private HashMap<Integer, Utilisateur> DB_Utilisateurs = new HashMap<Integer, Utilisateur>();
	
	/**
	 * 
	 * Constructeur de UserDB. 
	 * 
	 * !!!!!!!!!!!! PENSEZ À AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÉES AU DEMARRAGE DE L'APPLICATION !!!!!!
	 * 
	 * @param fichier
	 * 		Le nom du fichier qui contient la base de données.
	 */
	public UserDB(String fichier){
		//TODO Fonction à modifier
		super();
		@SuppressWarnings("unused")
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
	 * @param fichier
	 * 		Le nom du fichier qui contient la base de données.
	 */
	
	public void setFile(String fichier) {
		this.fichier = fichier;
	}
	/**
	 * loadDB
	 *
	 * On accède au HashMap après l'avoir créé à partir du fichier XML
	 */
	public HashMap<Integer, Utilisateur> loadDB() {
		SAXBuilder builder = new SAXBuilder();
		File fichier_xml = new File(fichier);
		
		/**
		 * L'utilisation d'un fichier extérieur nécessite la construction try/catch
		 */
		try {
			Document document = (Document) builder.build(fichier_xml);
			Element racine = document.getRootElement();
			
			List<Element> liste_etud = racine.getChildren("Students");
			for (Element etud : liste_etud) {
			   String login = etud.getChildText("login");
			   int ID = Integer.parseInt(etud.getChildText("studentid"));
			   String prenom = etud.getChildText("firstname");
			   String nom = etud.getChildText("surname");
			   String mot_de_passe = etud.getChildText("pwd");
			   int group_ID = Integer.parseInt(etud.getChildText("groupid"));
			   
			   Etudiant etudiant_temp = new Etudiant(login, ID, prenom, nom, mot_de_passe);
			   etudiant_temp.mettre(group_ID);
			   DB_Utilisateurs.put(ID, etudiant_temp);
			}
			
			List<Element> liste_prof = racine.getChildren("Teachers");
			for (Element prof : liste_prof) {
			   String login = prof.getChildText("login");
			   int ID = Integer.parseInt(prof.getChildText("teacherId"));
			   String prenom = prof.getChildText("firstname");
			   String nom = prof.getChildText("surname");
			   String mot_de_passe = prof.getChildText("pwd");
			   
			   
			   Professeur prof_temp = new Professeur(login, ID, prenom, nom, mot_de_passe);
			   DB_Utilisateurs.put(ID, prof_temp);
			}
						
			List<Element> liste_admin = racine.getChildren("Administrators");
			for (Element admin : liste_admin) {
			   String login = admin.getChildText("login");
			   int ID = Integer.parseInt(admin.getChildText("adminId"));
			   String prenom = admin.getChildText("firstname");
			   String nom = admin.getChildText("surname");
			   String mot_de_passe = admin.getChildText("pwd");
			   
			   Administrateur admin_temp = new Administrateur(login, ID, prenom, nom, mot_de_passe);
			   DB_Utilisateurs.put(ID, admin_temp);
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
		return DB_Utilisateurs;
	}
	/**
	 * saveDB
	 * 
	 * On remplace le HashMap
	 */
	public void saveDB(HashMap<Integer, Utilisateur> DB_new) {
		DB_Utilisateurs = DB_new;
	}
	
	public Administrateur root_admin() {
		Administrateur su = new Administrateur("sudo", 0, "su", "do", "admin");
		return su;
	}
}
