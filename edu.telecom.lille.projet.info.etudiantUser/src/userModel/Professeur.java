package userModel;

public class Professeur extends Utilisateur {
	/**
	 * L'identifiant d'un utilisateur est unique.
	 */
	
	public Professeur(String Login, int ID, String prenom, String nom,  String mot_de_passe) {
		this.ID = ID;
		this.login = Login;
		this.prenom = prenom;
		this.nom_de_famille = nom;
		this.mot_de_passe = mot_de_passe;
	}
	
}
