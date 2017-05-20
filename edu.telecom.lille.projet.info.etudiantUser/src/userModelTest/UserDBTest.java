package userModelTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import userModel.*;

public class UserDBTest {
	String fichier = "userDB.xml";
	UserDB database = new UserDB(fichier);
	
	@Test
	public void testGetFile() {
		String temp = database.getFile();
		Boolean result = temp.equals(fichier);
		assertTrue(result);
	}

	@Test
	public void testSetFile() {
		String fichier_temp = "UserDB_2.xml";
		database.setFile(fichier_temp);
		String resultat_temp = database.getFile();
		Boolean result = resultat_temp.equals(fichier_temp);
		assertTrue(result);
	}

	@Test
	public void testLoadDB() {
		Couple_DB set_temp = database.loadDB();
		HashMap<Integer, Utilisateur> utilisateurs_temp = set_temp.getUsers();
		HashMap<Integer, Groupe> groupes_temp = set_temp.getGroups();
		Groupe groupe_1 = groupes_temp.get(3);
		int id_groupe = groupe_1.ID();
		Utilisateur kr = utilisateurs_temp.get(2);
		String kr_login = kr.login();
		Boolean resultat1 = kr_login.equals("KR");
		
		assertTrue(resultat1 & (id_groupe == 3));
	}

	@Test
	public void testSaveDB() throws IOException {
		String fichier_temp = "testSaveUserDB.xml";
		database.setFile(fichier_temp);
		//On crée les groupes et les utilisateurs qui seront mis dans le nouveau HashMap
		Administrateur admin1 = database.root_admin();
		Groupe groupe1 = new Groupe(admin1.login(), 1);
		Groupe groupe2 = new Groupe(admin1.login(), 2);
		Etudiant etud1 = new Etudiant("login", 75, "prenom", "nom_de_famille", "mot_de_passe");
		Etudiant etud2 = new Etudiant("Tian", 456, "Tian", "Tian", "Tian");
		Professeur prof1 = new Professeur("Tian", 42, "Tian", "Tian", "Tian");
		
		//On crée le HashMap qui sera enregistré dns un fichier
		HashMap<Integer, Groupe> DB_new_groupes = new HashMap<Integer, Groupe>();
		DB_new_groupes.put(1, groupe1);
		DB_new_groupes.put(2, groupe2);
		HashMap<Integer, Utilisateur> DB_new_users = new HashMap<Integer, Utilisateur>();
		DB_new_users.put(0, admin1);
		DB_new_users.put(75, etud1);
		DB_new_users.put(456, etud2);
		DB_new_users.put(42, prof1);
		
		Couple_DB DB_new = new Couple_DB(DB_new_users, DB_new_groupes); 
		database.saveDB(DB_new);
		//vérification
		Couple_DB DB_resultat = database.loadDB();
//		System.out.println(DB_resultat);
//		System.out.println(DB_new);
		Boolean resultat = DB_resultat.equals(DB_new);
		assertTrue(resultat);
	}

	@Test
	public void testRoot_admin() {
		Administrateur admin = database.root_admin();
		Administrateur temp = new Administrateur("sudo", 0, "su", "do", "admin");
		Boolean result1 = admin.login().equals(temp.login());
		Boolean result2 = admin.mot_de_passe().equals(temp.mot_de_passe());
		Boolean result3 = admin.nom().equals(temp.nom());
		assertTrue(result1 & result2 & result3);
	}

}
