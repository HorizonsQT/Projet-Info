package userModel;

public class Administrateur extends Utilisateur {
	private int identifiant_administrateur;
	
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
