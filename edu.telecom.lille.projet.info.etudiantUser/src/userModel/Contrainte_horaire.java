package userModel;

public class Contrainte_horaire {
	private int identifiant;
	private String login_professeur;
	private int date_debut;
	private int date_fin;
	private String commentaire;
	
	public Contrainte_horaire(int ID, String prof, int debut, int fin, String com) {
		identifiant = ID;
		login_professeur = prof;
		date_debut = debut;
		date_fin = fin;
		commentaire = com;
	}
	
	public int ID() {
		return identifiant;
	}
	public String prof() {
		return login_professeur;
	}
	public int debut() {
		return date_debut;
	}
	public int fin() {
		return date_fin;
	}
	public String commentaire() {
		return commentaire;
	}
	
	/**
	 * Changer les horaires
	 * @param debut
	 * @param fin
	 */
	public void horaires(int debut, int fin) {
		date_debut = debut;
		date_fin = fin;
	}
	/**
	 * Changer le commentaire
	 * @param texte
	 */
	public void dire(String texte) {
		commentaire = texte;
	}
	
}
