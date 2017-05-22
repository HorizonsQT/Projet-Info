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
		Professeur prof1 = new Professeur("Tian", 42, "Tian", "Tian", "Tian");
		Contrainte_horaire cont1 = new Contrainte_horaire(123, "Tian", 1995, 2005, "commentaire!");
		
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
	public void testGetUserName() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetUserClass() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGetStudentGroup() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddAdmin() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddTeacher() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddStudent() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testRemoveUser() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddGroup() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testRemoveGroup() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAssociateStudToGroup() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUsersToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUsersLoginToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testStudentsLoginToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGroupsIdToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testGroupsToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testLoadDB() throws IOException {
		//On génère un fichier que l'on chargera
		String fichier = "test_controller.xml";
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

	@Test
	public void testSaveDB() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testSetUserDB() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddConstraint() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testRemoveConstraint() {
		fail("Not yet implemented"); // TODO
	}

}
