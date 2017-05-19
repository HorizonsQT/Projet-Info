package userModel;

public class Administrateur extends Utilisateur {
	/**
	 * L'identifiant d'un utilisateur est unique.
	 */
	private int identifiant_administrateur;
	
	/**
	 * 
	 * @param Login
	 * @param ID
	 * @param prenom
	 * @param nom
	 * @param mot_de_passe
	 */
	public Administrateur(String Login, int ID, String prenom, String nom,  String mot_de_passe) {
		this.identifiant_administrateur = ID;
		this.login = Login;
		this.prenom = prenom;
		this.nom_de_famille = nom;
		this.mot_de_passe = mot_de_passe;
	}
	
	public int ID() {
		return identifiant_administrateur;
	}
}
