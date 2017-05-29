package userControllerTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import userController.UserController;
import userModel.UserDB;
import userModel.*;

public class UserControllerTest {
	
	/**
	 * Génère une base de données, Couple_DB, dont on pourra se servir pour faire des tests
	 * @return
	 */
	public Couple_DB generate() {
		Administrateur admin1 = new Administrateur("sudo", 0, "su", "do", "admin");
		Groupe groupe1 = new Groupe(admin1.login(), 1);
		Groupe groupe2 = new Groupe(admin1.login(), 2);
		Etudiant etud1 = new Etudiant("login", 75, "prenom", "nom_de_famille", "mot_de_passe");
		Etudiant etud2 = new Etudiant("Tian", 456, "Tian", "Tian", "Tian");
		Professeur prof1 = new Professeur("Tian2", 42, "Tian", "Tian", "Tian");
		Contrainte_horaire cont1 = new Contrainte_horaire(123, "Tian3", 1995, 2005, "commentaire!");
		
		groupe1.Ajouter(etud1);
		groupe1.Ajouter(etud2);
				
		//On crée le HashMap qui sera enregistré dns un fichier
		HashMap<Integer, Groupe> DB_new_groupes = new HashMap<Integer, Groupe>();
		DB_new_groupes.put(1, groupe1);
		DB_new_groupes.put(2, groupe2);
		HashMap<Integer, Utilisateur> DB_new_users = new HashMap<Integer, Utilisateur>();
		DB_new_users.put(0, admin1);
		DB_new_users.put(75, etud1);
		DB_new_users.put(456, etud2);
		DB_new_users.put(42, prof1);
		HashMap<Integer, Contrainte_horaire> DB_new_constraints = new HashMap<Integer, Contrainte_horaire>();
		DB_new_constraints.put(123, cont1);
		
		Couple_DB DB_new = new Couple_DB(DB_new_users, DB_new_groupes, DB_new_constraints);
		return DB_new;
	}
	

	@Test
	public void testGetUserName() throws IOException {
		//Création de la base de données
		String fichier = "testGetUserName.xml";
		Couple_DB generated = generate();
		UserDB azerty = new UserDB(fichier);
		azerty.saveDB(generated);
		azerty.savefile();
		
		//On charge
		UserController uc = new UserController(fichier);
		uc.loadDB();
		
		//GetUserName
		String nom_fourni = uc.getUserName("Tian");
		
		//Vérification
		Boolean resultat = "Tian Tian".equals(nom_fourni);
		assertTrue(resultat);		
	}

	@Test
	public void testGetUserClass() throws IOException {
		//Création de la base de données
		String fichier = "testGetUserClass.xml";
		Couple_DB generated = generate();
		UserDB azerty = new UserDB(fichier);
		azerty.saveDB(generated);
		azerty.savefile();

		//On charge
		UserController uc = new UserController(fichier);
		uc.loadDB();

		//GetUserClass
		String classe_fournie = uc.getUserClass("Tian", "Tian");

		//Vérification
		Boolean resultat = Etudiant.class.getName().equals(classe_fournie);
		assertTrue(resultat);
	}

	@Test
	public void testGetStudentGroup() throws IOException {
		//Création de la base de données
		String fichier = "testGetStudentGroup.xml";
		Couple_DB generated = generate();
		UserDB azerty = new UserDB(fichier);
		azerty.saveDB(generated);
		azerty.savefile();

		//On charge
		UserController uc = new UserController(fichier);
		uc.loadDB();

		//GetStudentGroup
		int groupe_fourni = uc.getStudentGroup("Tian");

		//Vérification
		Boolean resultat = (groupe_fourni == 1);
		assertTrue(resultat);
	}

	@Test
	public void testAddAdmin() throws IOException {
		//Création de la base de données
		String fichier = "testAddAdmin.xml";//Ce fichier n'existe pas encore

		//On charge
		UserController uc = new UserController(fichier);
		
		//AddAdmin
		uc.addAdmin("oldadminlogin", "newlogin", 12, "newadmin_firstname", "new_admin_name", "password");
		uc.loadDB();//le fichier est bien créé dans la méthode précédente et on le lit.
		//Vérification
		Boolean resultat = uc.getUserClass("newlogin", "password").equals(Administrateur.class.getName());
		assertTrue(resultat);
	}

	@Test
	public void testAddTeacher() throws IOException {
		//Création de la base de données
		String fichier = "testAddTeacher.xml";//Ce fichier n'existe pas encore

		//On charge
		UserController uc = new UserController(fichier);

		//AddTeacher
		uc.addTeacher("oldadmin", "newteacherLogin", 42, "firstname", "surname", "pwd");
		uc.loadDB();//le fichier est bien créé dans la méthode précédente et on le lit.
		//Vérification
		Boolean resultat = uc.getUserClass("newteacherLogin", "pwd").equals(Professeur.class.getName());
		assertTrue(resultat);
	}

	@Test
	public void testAddStudent() throws IOException {
		//Création de la base de données
		String fichier = "testAddStudent.xml";//Ce fichier n'existe pas encore

		//On charge
		UserController uc = new UserController(fichier);

		//AddAdmin
		uc.addStudent("oldadmin", "newStudentLogin", 123, "firstname", "surname", "pwd");
		uc.loadDB();//le fichier est bien créé dans la méthode précédente et on le lit.
		//Vérification
		Boolean resultat = uc.getUserClass("newStudentLogin", "pwd").equals(Etudiant.class.getName());
		assertTrue(resultat);
	}

	@Test
	public void testRemoveUser() throws IOException {
		//Création de la base de données
		String fichier = "testRemoveUser.xml";//Ce fichier n'existe pas encore

		//On charge et on ajoute
		UserController uc = new UserController(fichier);
		uc.addStudent("oldadmin", "newstudentLogin", 42, "firstname", "surname", "pwd");
		// RemoveUser
		uc.removeUser("oldadmin", "newstudentLogin");
		Boolean resultat = uc.getUserDB().loadDB().getUsers().containsKey(42);
		//Vérification
		assertTrue(!resultat);
	}

	@Test
	public void testAddGroup() throws IOException {
		//Création de la base de données
		String fichier = "testAddGroup.xml";//Ce fichier n'existe pas encore

		//On charge
		UserController uc = new UserController(fichier);

		//AddGroup
		uc.addGroup("adminLogin", 42);
		uc.loadDB();//le fichier est bien créé dans la méthode précédente et on le lit.
		//Vérification de l'ajout du groupe
		Boolean resultat1 = uc.groupsIdToString()[0].equals("42");
		
		//Vérification de l'unicité d'un ajout
		Boolean resultat2 = uc.addGroup("adminLogin", 42);
		
		assertTrue(resultat1 && !resultat2);
	}

	@Test
	public void testRemoveGroup() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAssociateStudToGroup() throws IOException {
		//Création de la base de données
		String fichier = "testAssociatesStudToGroup.xml";//Ce fichier n'existe pas encore

		//On charge et on ajoute un groupe et un etudiant
		UserController uc = new UserController(fichier);
		uc.addGroup("adminLogin", 42);
		uc.addStudent("adminLogin", "newStudentLogin", 12, "firstname", "surname", "pwd");
		uc.loadDB();//le fichier est bien créé dans la méthode précédente et on le lit.

		//AssociateStudToGroupe
		System.out.println("1");
		uc.associateStudToGroup("adminLogin", "newStudentLogin", 42);
		System.out.println("2");

		//Vérification
		System.out.println(uc.getUserDB().loadDB().getGroups().get(42).membres());
		Boolean resultat = false;

		assertTrue(resultat);
		}

	@Test
	public void testUsersToString() throws IOException {
		//Création de la base de données
		String fichier = "testUsersToString.xml";
		Couple_DB generated = generate();
		UserDB azerty = new UserDB(fichier);
		azerty.saveDB(generated);
		azerty.savefile();

		//On charge
		UserController uc = new UserController(fichier);
		uc.loadDB();

		//UsersToString
		String[] tableau_utilisateurs = uc.usersToString();
//		for (String s : tableau_utilisateurs) {
//			System.out.println(s);
//		}
		//Vérification
		Boolean resultat = "Tian2 42 Tian Tian Tian 0".equals(tableau_utilisateurs[2]);
		assertTrue(resultat);
	}

	@Test
	public void testUsersLoginToString() throws IOException {
		//Création de la base de données
		String fichier = "testUsersLoginToString.xml";
		Couple_DB generated = generate();
		UserDB azerty = new UserDB(fichier);
		azerty.saveDB(generated);
		azerty.savefile();

		//On charge
		UserController uc = new UserController(fichier);
		uc.loadDB();

		//UsersToString
		String[] tableau_utilisateurs = uc.usersLoginToString();
//		for (String s : tableau_utilisateurs) {
//			System.out.println(s);
//		}
		//Vérification
		Boolean resultat = "Tian2".equals(tableau_utilisateurs[2]);
		assertTrue(resultat);
	}

	@Test
	public void testStudentsLoginToString() throws IOException {
		//Création de la base de données
		String fichier = "testStudentsLoginToString.xml";
		Couple_DB generated = generate();
		UserDB azerty = new UserDB(fichier);
		azerty.saveDB(generated);
		azerty.savefile();

		//On charge
		UserController uc = new UserController(fichier);
		uc.loadDB();

		//UsersToString
		String[] tableau_etudiants = uc.studentsLoginToString();
//		for (String s : tableau_etudiants) {
//			System.out.println(s);
//		}
		//Vérification
		Boolean resultat = "Tian".equals(tableau_etudiants[0]);
		assertTrue(resultat);
	}

	@Test
	public void testGroupsIdToString() throws IOException {
		//Création de la base de données
		String fichier = "testGroupsIdToString.xml";
		Couple_DB generated = generate();
		UserDB azerty = new UserDB(fichier);
		azerty.saveDB(generated);
		azerty.savefile();

		//On charge
		UserController uc = new UserController(fichier);
		uc.loadDB();

		//UsersToString
		String[] tableau_groupes = uc.groupsIdToString();
//		for (String s : tableau_groupes) {
//			System.out.println(s);
//		}
		//Vérification
		Boolean resultat = "1".equals(tableau_groupes[0]);
		assertTrue(resultat);
	}

	@Test
	public void testGroupsToString() throws IOException {
		//Création de la base de données
		String fichier = "testGroupsToString.xml";
		Couple_DB generated = generate();
		UserDB azerty = new UserDB(fichier);
		azerty.saveDB(generated);
		azerty.savefile();

		//On charge
		UserController uc = new UserController(fichier);
		uc.loadDB();

		//UsersToString
		String[] tableau_groupes = uc.groupsToString();
//		for (String s : tableau_groupes) {
//			System.out.println(s);
//		}
		//Vérification
		Boolean resultat = "1 sudo 2 Tian Tian; prenom nom_de_famille; ".equals(tableau_groupes[0]);
		assertTrue(resultat);
	}

	@Test
	public void testLoadDB() throws IOException {
		//On génère un fichier que l'on chargera
		String fichier = "testLoadDB.xml";
		Couple_DB generated = generate();
		UserDB azerty = new UserDB(fichier);
		azerty.saveDB(generated);
		azerty.savefile();
		
		//On charge
		UserController uc = new UserController(fichier);
		uc.loadDB();
		
		//On vérifie
		UserDB result_temp = uc.getUserDB();
		Utilisateur etud_temp = result_temp.loadDB().getUsers().get(75);
		Boolean result = etud_temp.login().equals(generated.getUsers().get(75).login());
		assertTrue(result);
	}

//	@Test
//	public void testSaveDB() {
//	Ça marche.
//	}

	@Test
	public void testSetUserDB() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddConstraint() throws IOException {
		//Création de la base de données
		String fichier = "testAddConstraint.xml";//Ce fichier n'existe pas encore

		//On charge
		UserController uc = new UserController(fichier);

		//AddGroup
		uc.addConstraint("adminLogin", 42, "prof", -753, 476, "com");
		uc.loadDB();//le fichier est bien créé dans la méthode précédente et on le lit.
		//Vérification de l'ajout du groupe
		//Vérification par lecture du ficihier xml
		Boolean resultat1 = true;
		//TODO
		assertTrue(resultat1);
	}

	@Test
	public void testRemoveConstraint() {
		fail("Not yet implemented"); // TODO
	}

}
