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
	 * Le fichier xml qui contient les groupes a pour elements:
	 * <UsersDB>
	 * 		<Students>
	 * 			<Student>
	 * 				<login>String</login>
     * 				etc
     * 			</Student>
     * etc
     * 
     * 
     * On distingue la base de donnée locale, de type Couple_DB, et le fichier xml.
	 */
	private String fichier = "fichier_initial";//Nom du fichier
	private String arbre = System.getProperty("user.dir");
	
	// On a un HashMap contenant la base de données des utilisateurs
	//et un HashMap contenant celle des groupes
	//et un HashMap contenant celle des contraintes horaires
	private HashMap<Integer, Utilisateur> DB_Utilisateurs = new HashMap<Integer, Utilisateur>();	//Utilisateurs
	private HashMap<Integer, Groupe> DB_Groupe = new HashMap<Integer, Groupe>();//Groupes
	private HashMap<Integer, Contrainte_horaire> DB_Contraintes = new HashMap<Integer, Contrainte_horaire>();
		
	//On a une instance de la base de données
	private Couple_DB DB_tout = new Couple_DB(DB_Utilisateurs, DB_Groupe, DB_Contraintes);

	
	/**
	 * 
	 * Constructeur de UserDB. 
	 * 
	 * !!!!!!!!!!!! PENSEZ À AJOUTER UN ADMINISTRATEUR (su par exemple) QUI VOUS PERMETTRA DE CHARGER LA BASE DE DONNÉES AU DEMARRAGE DE L'APPLICATION !!!!!!
	 * 
	 * @param fichier
	 * 		Le nom du fichier qui contient la base de données.
	 * @throws IOException 
	 */
	public UserDB(String fichier) {
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
	 * @param fichier
	 * 		Le nom du fichier qui contient la base de données.
	 */
	
	public void setFile(String fichier_set) {
		this.fichier = fichier_set;
	}
	/**
	 * loadDB
	 *
	 * On créé à partir du fichier XML une instance Couple_DB de la base de données
	 */
	public void loadfile() {
		SAXBuilder builder = new SAXBuilder();				// Constructeur à partir du fichier
		File fichier_xml = new File(arbre, fichier);		// Le fichier que l'on ouvre
		String su = root_admin().login;						// On crée l'administrateur du groupe
		/**
		 * L'utilisation d'un fichier extérieur nécessite la construction try/catch
		 */
		try {
			Document document = (Document) builder.build(fichier_xml);
			Element racine = document.getRootElement();		//La racine représente <UsersDB>
			// Les groupes
			Element groups = racine.getChild("Groups");	// <Groups>
			List<Element> list_groupe = groups.getChildren();// L'ensemble des fils de <Groups>
			for (Element groupe : list_groupe) {
				String id_temp = groupe.getChildText("groupId");// Chaque groupe se caractérise par son identifiant de groupe
				int id_int = Integer.parseInt(id_temp);	// On convertit la chaîne de caractères en nombre
				int ID = id_int;
				Groupe groupe_temp = new Groupe(su, ID);
				DB_Groupe.put(ID, groupe_temp);
			}

			// Les étudiants
			Element etudiants = racine.getChild("Students");// <Students>
			List<Element> liste_etud = etudiants.getChildren();// L'ensemble des fils de <Students>			
			for (Element etud : liste_etud) {
			   String login = etud.getChildText("login");
			   int ID = Integer.parseInt(etud.getChildText("studentId"));
			   String prenom = etud.getChildText("firstname");
			   String nom = etud.getChildText("surname");
			   String mot_de_passe = etud.getChildText("pwd");
			   int group_ID = Integer.parseInt(etud.getChildText("groupId"));
			   
			   Etudiant etudiant_temp = new Etudiant(login, ID, prenom, nom, mot_de_passe);
			   //Ajoutons l'étudiant à son groupe

			   if (group_ID != 0) {//On considère que 0 signifie une absence de groupe
				   Groupe temp_group;
				   if (!(DB_Groupe.containsValue(group_ID))) {
					   // L'identifiant de groupe correspond à un groupe qui n'existe pas
					   // On crée ce groupe.
					   temp_group = new Groupe(root_admin().login, group_ID);
				   } else {
					   temp_group = DB_Groupe.get(group_ID);
				   }
				   temp_group.Ajouter(etudiant_temp);
				   DB_Groupe.remove(group_ID);
				   DB_Groupe.put(group_ID, temp_group);
			   }
			   DB_Utilisateurs.put(ID, etudiant_temp);
			}

			// Les professeurs
			Element professeurs = racine.getChild("Teachers");// <Teachers>
			List<Element> liste_prof = professeurs.getChildren();// L'ensemble des fils de <Teachers>			
			for (Element prof : liste_prof) {
			   String login = prof.getChildText("login");
			   int ID = Integer.parseInt(prof.getChildText("teacherId"));
			   String prenom = prof.getChildText("firstname");
			   String nom = prof.getChildText("surname");
			   String mot_de_passe = prof.getChildText("pwd");
			   
			   
			   Professeur prof_temp = new Professeur(login, ID, prenom, nom, mot_de_passe);
			   DB_Utilisateurs.put(ID, prof_temp);
			}

			// Les administrateurs
			Element administrateurs = racine.getChild("Administrators");// <Administrators>
			List<Element> liste_admin = administrateurs.getChildren();// L'ensemble des fils de <Administrators>			
			for (Element admin : liste_admin) {
			   String login = admin.getChildText("login");
			   int ID = Integer.parseInt(admin.getChildText("adminId"));
			   String prenom = admin.getChildText("firstname");
			   String nom = admin.getChildText("surname");
			   String mot_de_passe = admin.getChildText("pwd");
			   
			   Administrateur admin_temp = new Administrateur(login, ID, prenom, nom, mot_de_passe);
			   DB_Utilisateurs.put(ID, admin_temp);
			
			}
			
	
			// Les contraintes
			Element contraintes = racine.getChild("Constraints");// <Constraints>
			List<Element> liste_contraintes = contraintes.getChildren();// L'ensemble des fils de <Constraints>			
			for (Element c : liste_contraintes) {
				String login = c.getChildText("login");
				int ID = Integer.parseInt(c.getChildText("constraintId"));
				int begin = Integer.parseInt(c.getChildText("begin"));
				int end = Integer.parseInt(c.getChildText("end"));
				String comment = c.getChildText("comment");

				Contrainte_horaire contrainte_temp = new Contrainte_horaire(ID, login, begin, end, comment);
				DB_Contraintes.put(ID, contrainte_temp);
			}

			
			} catch(IOException e) {
			    												// Lorsque des erreurs se présentent.
				e.printStackTrace();
			} catch (Throwable e) {
																// Pour les erreurs.
				e.printStackTrace();
			} finally {
		}
		
		DB_tout.setUsers(DB_Utilisateurs);
		DB_tout.setGroups(DB_Groupe);
		DB_tout.setConstraints(DB_Contraintes);
	}
	/**
	 * On renvoie l'instance locale de la base de données
	 * @return
	 */
	public Couple_DB loadDB() {
		return DB_tout;
	}
	/**
	 * saveDB
	 * 
	 * On enregistre la base de données locale dans un fichier xml
	 * @throws IOException 
	 */
	public void savefile() throws IOException {
		DB_Groupe = DB_tout.getGroups();
		DB_Utilisateurs = DB_tout.getUsers();
		DB_Contraintes = DB_tout.getConstraints();
		
		File fichier_xml = new File(arbre, fichier);
		fichier_xml.createNewFile();
		
		Document document = new Document();			//On crée un document
		
		Comment comment = new Comment("Base de données contenant les utilisateurs, les groupes, et les contraintes temporelles.");
		document.addContent(comment);

		Element UsersDB = new Element("UsersDB");	//Elément qui deviendra la racine
		document.setRootElement(UsersDB);			//<UsersDB> devient la racine
		
		
		//Création de groupes
		Element Groups = new Element("Groups");
		for (Groupe g : DB_Groupe.values()) {		//On parcourt les groupes dans le HashMap
			String ID = Integer.toString(g.ID());
			Element Group = new Element("Group");	//On crée un nouveau sous-élement de groupes, qui repréente un groupe
			Group.addContent(new Element("groupId").setText(ID));// Chaque groupe a un sous-élément groupId qui est associé à une chaîne de carctères, i.e. son identifiant
	
			Groups.addContent(Group);				// L'élément <Groupe> créé est ajouté à l'arborescence de <Groupes>
			
		}
		//Création des utilisateurs
		Element Students = new Element("Students");
		Element Teachers = new Element("Teachers");
		Element Administrators = new Element("Administrators");
		for (Utilisateur s : DB_Utilisateurs.values()) {// s est un utilisateur
		//	Déterminons le type de l'utilisateur s
			if (s.getClass().equals(Etudiant.class)) {// s est un étudiant
				Element Student = new Element("Student");
				Student.addContent(new Element("login").setText(s.login));
				Student.addContent(new Element("firstname").setText(s.prenom));
				Student.addContent(new Element("surname").setText(s.nom_de_famille));
				Student.addContent(new Element("pwd").setText(s.mot_de_passe));
				Student.addContent(new Element("studentId").setText(Integer.toString(s.ID)));
				Student.addContent(new Element("groupId").setText(Integer.toString(s.ID_group)));
				
				Students.addContent(Student);
			}
			
			else if (s.getClass().equals(Professeur.class)) {// s est un professeur
				Element Teacher = new Element("Teacher");
				Teacher.addContent(new Element("login").setText(s.login));
				Teacher.addContent(new Element("firstname").setText(s.prenom));
				Teacher.addContent(new Element("surname").setText(s.nom_de_famille));
				Teacher.addContent(new Element("pwd").setText(s.mot_de_passe));
				Teacher.addContent(new Element("teacherId").setText(Integer.toString(s.ID)));
				
				Teachers.addContent(Teacher);
			}
			
			else {// s est un administrateur
				Element Administrator = new Element("Administrator");
				Administrator.addContent(new Element("login").setText(s.login));
				Administrator.addContent(new Element("firstname").setText(s.prenom));
				Administrator.addContent(new Element("surname").setText(s.nom_de_famille));
				Administrator.addContent(new Element("pwd").setText(s.mot_de_passe));
				Administrator.addContent(new Element("adminId").setText(Integer.toString(s.ID)));
				
				Administrators.addContent(Administrator);
			}
		}
		
		// Création des contraintes horaires
		Element Constraints = new Element("Constraints");
		for (Contrainte_horaire c : DB_Contraintes.values()) {		//On parcourt les contraintes dans le HashMap
			Element Constraint = new Element("Constraint");
			Constraint.addContent(new Element("login").setText(c.login()));
			Constraint.addContent(new Element("constraintId").setText(Integer.toString(c.ID())));
			Constraint.addContent(new Element("begin").setText(Integer.toString(c.debut())));
			Constraint.addContent(new Element("end").setText(Integer.toString(c.fin())));
			Constraint.addContent(new Element("comment").setText(c.commentaire()));
			
			Constraints.addContent(Constraint);				// L'élément <Constraint> créé est ajouté à l'arborescence de <Constraints>
			
		}
		
		document.getRootElement().addContent(Groups);// L'élément <Groupes>, contenant tous les groupes de la base de données, est associé à la racine
		document.getRootElement().addContent(Students);// <Students>
		document.getRootElement().addContent(Teachers);// <Teachers>
		document.getRootElement().addContent(Administrators);// <Administrators>
		document.getRootElement().addContent(Constraints);// <Constraints>
		// Nouvelle sortie arbre +"\\" + fichier
		XMLOutputter fichier_xml_sortie = new XMLOutputter();

		// On écrit à l'emplacement prévu
		fichier_xml_sortie.setFormat(Format.getPrettyFormat());
		fichier_xml_sortie.output(document, new FileWriter(fichier_xml));
	}
	/**
	 * On remplace la base de données locale par une nouvelle version
	 * @param DB_new
	 */
	public void saveDB(Couple_DB DB_new) {
		DB_tout = DB_new;
	}
	
	public Administrateur root_admin() {
		Administrateur su = new Administrateur("sudo", 0, "su", "do", "admin");
		return su;
	}
}
