package userModelTest;

import static org.junit.Assert.*;

import org.junit.Test;

import userModel.*;

public class GroupDBTest {
	String fichier = "azerty";
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
		fail("Not yet implemented"); // TODO
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
