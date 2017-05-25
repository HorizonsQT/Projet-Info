package userModelTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import userModel.*;

public class UserDBTest {

	@Test
	public void testGetFile() throws IOException {
		String fichier = "userDB.xml";
		UserDB database = new UserDB(fichier);
		String temp = database.getFile();
		Boolean result = temp.equals(fichier);
		assertTrue(result);
	}

	@Test
	public void testSetFile() throws IOException {
		String fichier = "userDB.xml";
		UserDB database = new UserDB(fichier);
		String fichier_temp = "UserDB_2.xml";
		database.setFile(fichier_temp);
		String resultat_temp = database.getFile();
		Boolean result = resultat_temp.equals(fichier_temp);
		assertTrue(result);
	}

	@Test
	public void testLoadDB() throws IOException {
		String fichier = "userDB.xml";
		UserDB database = new UserDB(fichier);
		database.loadfile();
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
		String fichier = "userDB.xml";
		UserDB database = new UserDB(fichier);
		String fichier_temp = "testSaveUserDB.xml";
		database.setFile(fichier_temp);
		//On crée les groupes, les utilisateurs, et les cotraintes qui seront mis dans le nouveau HashMap
		Administrateur admin1 = database.root_admin();
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
		database.saveDB(DB_new);
		database.savefile();
		//vérification
		Couple_DB DB_resultat = database.loadDB();
		
		Boolean resultat = DB_resultat.equals(DB_new);
		assertTrue(resultat);
	}

	@Test
	public void testRoot_admin() throws IOException {
		String fichier = "userDB.xml";
		UserDB database = new UserDB(fichier);
		Administrateur admin = database.root_admin();
		Administrateur temp = new Administrateur("sudo", 0, "su", "do", "admin");
		Boolean result1 = admin.login().equals(temp.login());
		Boolean result2 = admin.mot_de_passe().equals(temp.mot_de_passe());
		Boolean result3 = admin.nom().equals(temp.nom());
		assertTrue(result1 & result2 & result3);
	}
	
	@Test
	public void testMain() throws IOException {
		String fichier = "userDB.xml";
		UserDB database = new UserDB(fichier);
		String fichier_temp_1 = "db_save_1.xml";
		String fichier_temp_2 = "db_save_2.xml";
		
		database.setFile(fichier_temp_1);
		UserDB database_2 = new UserDB(fichier_temp_2);
		UserDB database_3 = new UserDB("");
		
		//On crée les groupes et les utilisateurs qui seront mis dans le nouveau HashMap
		Administrateur admin1 = database.root_admin();
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
		database.saveDB(DB_new);
		database.savefile();
		//vérification
		database.loadfile();
		database_2.saveDB(database.loadDB());
		database_2.savefile();
		database_3.setFile(fichier_temp_2);
		database.loadfile();
		database.loadDB();
		database_2.loadfile();
		database_2.loadDB();
		database_3.loadfile();
		database_3.loadDB();
		System.out.println("database:  ");
		

		for (Utilisateur i : database.loadDB().getUsers().values()) {
			System.out.print(i.login());
		}
		for (Groupe i : database.loadDB().getGroups().values()) {
			System.out.print(i.ID());
		}
		System.out.println("\nAnd database_3:  ");
		for (Utilisateur i : database_3.loadDB().getUsers().values()) {
			System.out.print(i.login());
		}
		for (Groupe i : database_3.loadDB().getGroups().values()) {
			System.out.print(i.ID());
		}
		Boolean resultat = database_3.loadDB().getUsers().get(456).nom().equals(etud2.nom());
		//Boolean resultat = database.loadDB().equals(database_3.loadDB()); False car les bases de données ne sont pas identiques, bien que les Utilisateurs et les groupes le sont.
		assertTrue(resultat);
	}

}
