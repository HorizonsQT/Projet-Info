package userModel;

import java.util.HashMap;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
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
	 * Le fichier xml qui contient les groupes a pour elements:
	 * <UsersDB>
	 * 		<Groups>
	 * 			<GROUPE>
	 * 				<groupId>
	 * 					"ID" 
	 */
	private String fichier = "fichier_initial";//Le nom du fichier contenant la base de données.
	private String arbre = System.getProperty("user.dir");// Le chemin pour y parvenir
	/**
	 * Le HashMap contenant la base de données; la valeur à laquelle est associée chaque groupe sera son identifiant, que l'on suppose unique
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
		//super();
		//Administrateur su = root_admin();
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
	
	public void setFile(String fichier_set) {
		this.fichier = fichier_set;
	}
	/**
	 * loadDB
	 * 
	 * Cette focntion renvoie le HashMap associé au fichier xml défini par setFile().
	 */
	public HashMap<Integer, Groupe> loadDB() {
		SAXBuilder builder = new SAXBuilder();			// Constructeur à partir du fichier
		File fichier_xml = new File(arbre, fichier);	// Le fichier que l'on ouvre
		
		String su = root_admin().login;					//On crée l'administrateur du groupe
		
		/**
		 * L'utilisation d'un fichier extérieur nécessite la construction try/catch
		 */
		try {
			Document document = (Document) builder.build(fichier_xml);
			Element racine = document.getRootElement();	//La racine représente <UsersDB>
			Element groups = racine.getChild("Groups");	// <Groups>
			List<Element> list_groupe = groups.getChildren();// L'ensemble des fils de <Groups>
			for (Element groupe : list_groupe) {
			   String id_temp = groupe.getChildText("groupId");// Chaque groupe se caractérise par son identifiant de groupe
			   int id_int = Integer.parseInt(id_temp);	// On convertit la chaîne de caractères en nombre
			   int ID = id_int;
			   Groupe groupe_temp = new Groupe(su, ID);
			   DB_Groupe.put(ID, groupe_temp);
			}			
			
			} catch(IOException e) {
			    										// Lorsque des erreurs se présentent.
				e.printStackTrace();
			} catch (Throwable e) {
														// Pour les erreurs.
				e.printStackTrace();
			} finally {
		}
		return DB_Groupe;
	}
	/**
	 * saveDB
	 * 
	 * On remplace le HashMap
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public void saveDB(HashMap<Integer, Groupe> DB_new) throws IOException, JDOMException {
		DB_Groupe = DB_new;
		
		File fichier_xml = new File(arbre, fichier);
		fichier_xml.createNewFile();
		
		Document document = new Document();			//On crée un document
		Element UsersDB = new Element("UsersDB");	//Elément qui deviendra la racine
		document.setRootElement(UsersDB);			//<UsersDB> devient la racine
		
		Element Groups = new Element("Groups");
		
		for (Groupe g : DB_Groupe.values()) {		//On parcourt les identifiants de groupe dans le HashMap
			String ID = Integer.toString(g.ID());
			Element Group = new Element("Group");	//On crée un nouveau sous-élement de groupes, qui repréente un groupe
			Group.addContent(new Element("groupId").setText(ID));// Chaque groupe a un sous-élément groupId qui est associé à une chaîne de carctères, i.e. son identifiant
	
			Groups.addContent(Group);				// L'élément <Groupe> créé est ajouté à l'arborescence de <Groupes>
			
		}
		
		document.getRootElement().addContent(Groups);// L'élément <Groupes>, contenant tous les groupes de la base de données, est associé à la racine
													// Nouvelle sortie arbre +"\\" + fichier
		XMLOutputter fichier_xml_sortie = new XMLOutputter();

													// On écrit à l'emplacement
		fichier_xml_sortie.setFormat(Format.getPrettyFormat());
		fichier_xml_sortie.output(document, new FileWriter(fichier_xml));
	}
	
	public Administrateur root_admin() {
		Administrateur su = new Administrateur("sudo", 0, "su", "do", "admin");
		return su;
	}
}
