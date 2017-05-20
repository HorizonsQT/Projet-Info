package userModel;

public class Etudiant extends Utilisateur {
	/**
	 * L'identifiant d'un utilisateur est unique.
	 */

	/**
	 * L'identifiant de groupe 0 correspond à une absence de groupe
	 */
	public int groupe() {
		return ID_group;
	}
	
	/**
	 * Etudiant
	 * @param Login
	 * @param ID
	 * @param prenom
	 * @param nom
	 * @param mot_de_passe
	 * 
	 * Lors de la création, on n'associe pas immédiatement de groupe
	 */
	public Etudiant(String Login, int ID, String prenom, String nom,  String mot_de_passe) {
		this.ID = ID;
		this.login = Login;
		this.prenom = prenom;
		this.nom_de_famille = nom;
		this.mot_de_passe = mot_de_passe;
	}
	
	/**
	 * Mettre l'étudiant dans une groupe
	 * @param int groupe_ID
	 */
	public void mettre(int groupe_ID) {
		ID_group = groupe_ID;
	}
	
	/**
	 * Enlever l'étudiant d'une groupe
	 */
	public void enlever() {
		ID_group = 0;
	}
	
	public int ID_groupe() {
		return ID_group;
	}
}
