package userModelTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.jdom2.JDOMException;
import org.junit.Test;

import userModel.*;

public class GroupDBTest {
	String fichier = "groupDB.xml";
	GroupDB database = new GroupDB(fichier);

	@Test
	public void testGetFile() {
		String temp = database.getFile();
		Boolean result = temp.equals("groupDB.xml");
		assertTrue(result);
	}

	@Test
	public void testSetFile() {
		String fichier_temp = "groupDB_2.xml";
		database.setFile(fichier_temp);
		String resultat_temp = database.getFile();
		Boolean result = resultat_temp.equals(fichier_temp);
		assertTrue(result);
	}

	@Test
	public void testLoadDB() {
		HashMap<Integer, Groupe> set_temp = database.loadDB();
		Groupe groupe_temp = set_temp.get(1);
		int resultat = groupe_temp.ID();
		assertTrue(resultat == 1);
	}

	@Test
	public void testSaveDB() throws IOException, JDOMException {
		String fichier_temp = "testSaveGroupDB.xml";
		database.setFile(fichier_temp);
		//On crée les groupes qui seront mis dans le nouveau HashMap
		Administrateur admin1 = database.root_admin();
		Groupe groupe1 = new Groupe(admin1.login(), 1);
		Groupe groupe2 = new Groupe(admin1.login(), 2);
		Groupe groupe3 = new Groupe(admin1.login(), 3);
		//On crée le HashMap qui sera enregistré dns un fichier
		HashMap<Integer, Groupe> DB_new = new HashMap<Integer, Groupe>();
		DB_new.put(1, groupe1);
		DB_new.put(2, groupe2);
		DB_new.put(3, groupe3);
		
		database.saveDB(DB_new);
		//vérification
		HashMap<Integer, Groupe> DB_resultat = database.loadDB();
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
