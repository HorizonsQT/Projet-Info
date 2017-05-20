package userModel;

public class Utilisateur {
	protected String login;
	protected String prenom;
	protected String nom_de_famille;
	protected String mot_de_passe;
	protected int ID;
	protected int ID_group = 0;
	
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
	public int ID() {
		return ID;
	}
	public int ID_groupe() {// Ne sera utilisé que par les étudiants
		return 0;// Pour les utilisateurs non-étudiants, la méthode n'a pas de sens et ne devrait pas être utilisée.
	}
	
}
