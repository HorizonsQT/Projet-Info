package userModelTest;

import static org.junit.Assert.*;

import org.junit.Test;

import userModel.*;

/**
 * 
 * @author harry
 *
 * On souahite vérifier le foncitonnement des méthodes
 */
public class GroupeTest {
	String admin;
	int ID;
	Etudiant etudiant = new Etudiant("Login", 1, "prenom", "nom",  "mot_de_passe");
	Etudiant etudiant2 = new Etudiant("Login2", 2, "prenom2", "nom2",  "mot_de_passe2");

	
	Groupe groupe1 = new Groupe(admin, ID);

	@Test
	public void testAdmin() {
		String result = groupe1.Admin();
		assertTrue(result == admin);
	}

	@Test
	public void testID() {
		int result = groupe1.ID();
		assertTrue(result == ID);
	}

	@Test
	public void testTaille() {
		int result = groupe1.taille();
		assertTrue(result == 0);
	}

	@Test
	public void testAjouter() {
		groupe1.Ajouter(etudiant);
		int result = groupe1.taille();
		assertTrue(result == 1);
	}
	
	/**
	 * On vérifie l'unicité des étudiants dans le groupe
	 */
	@Test
	public void testAjouterDouble() {
		groupe1.Ajouter(etudiant);
		groupe1.Ajouter(etudiant);
		boolean result = groupe1.membres().equals("prenom nom; ");
		assertTrue(result);
	}

	@Test
	public void testSupprimer() {
		groupe1.Ajouter(etudiant);
		groupe1.Supprimer(etudiant);
		int result = groupe1.taille();
		assertTrue(result == 0);
	}

	@Test
	public void testMembres() {
		groupe1.Ajouter(etudiant);
		groupe1.Ajouter(etudiant2);
//		System.out.println(groupe1.membres());
		boolean result = groupe1.membres().equals("prenom nom; prenom2 nom2; ");
		/**
		 *  On ne peut vérifier une égalité entre chaînes de caractères dans assertTrue
		 */
		assertTrue(result);
	}

}
