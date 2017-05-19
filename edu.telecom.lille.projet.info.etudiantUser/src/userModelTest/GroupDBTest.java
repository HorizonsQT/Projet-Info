package userModelTest;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import userModel.*;

public class GroupDBTest {
	String fichier = "userDB.xml";
	GroupDB database = new GroupDB(fichier);

	@Test
	public void testGetFile() {
		String temp = database.getFile();
		Boolean result = temp.equals("azerty");
		assertTrue(result);
	}

	@Test
	public void testSetFile() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testLoadDB() {
		HashMap<Integer, Groupe> set_temp = database.loadDB();
		Groupe groupe_temp = set_temp.get(2);
		int result = groupe_temp.ID();
		assertTrue(result == 2);
	}

	@Test
	public void testSaveDB() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testRoot_admin() {
		Administrateur admin = database.root_admin();
		Administrateur temp = new Administrateur("sudo", 0, "su", "do", "admin");
		Boolean result = admin.equals(temp);
		assertTrue(result);
	}

}
