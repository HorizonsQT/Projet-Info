package userModel;

public class Utilisateur {
	protected String login;
	protected String prenom;
	protected String nom_de_famille;
	protected String mot_de_passe;
	
	public String login() {
		return login;
	}
	public String prenom() {
		return prenom;
	}
	public String nom() {
		return nom_de_famille;
	}
	public String mot_de_passe() {
		return mot_de_passe;
	}
}
