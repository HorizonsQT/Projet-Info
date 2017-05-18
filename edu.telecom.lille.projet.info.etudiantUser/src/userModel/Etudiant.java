package userModel;

public class Etudiant extends Utilisateur {
	private int identifiant_de_groupe = 0;
	private int identifiant_etudiant;
	
	public int groupe() {
		return identifiant_de_groupe;
	}
	public int ID() {
		return identifiant_etudiant;
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
		this.identifiant_etudiant = ID;
		this.login = Login;
		this.prenom = prenom;
		this.nom_de_famille = nom;
		this.mot_de_passe = mot_de_passe;
	}
	
	/**
	 * L'identifiant de groupe 0 correspond à une absence de groupe
	 */
	
	/**
	 * Mettre l'étudiant dans une groupe
	 * @param int groupe_ID
	 */
	public void mettre(int groupe_ID) {
		identifiant_de_groupe = groupe_ID;
	}
	
	/**
	 * Enlever l'étudiant d'une groupe
	 */
	public void enlever() {
		identifiant_de_groupe = 0;
	}
}
