package userModel;

import java.util.HashMap;

public class Groupe {
	private String AdminLogin;
	private int Group_ID;
	private int Nombre_etudiants = 0;
	private HashMap<Etudiant, Integer> Ensemble_etudiant = new HashMap<Etudiant, Integer>();
	
	public String Admin() {
		return AdminLogin;
	}
	
	public int ID() {
		return Group_ID;
	}
	public int taille() {
		return Nombre_etudiants;
	}
	/**
	 * Groupe
	 * @param Admin
	 * @param ID
	 */
	public Groupe(String Admin, int ID) {
		this.AdminLogin = Admin;
		this.Group_ID = ID;
	}
	/**
	 * Ajouter un étudiant
	 * 
	 * On ajoute un etudiant
	 * Si l'étudiant est déjà dans le groupe, on ne l'ajoute pas
	 */
	public void Ajouter(Etudiant etud) {
		Ensemble_etudiant.putIfAbsent(etud, Nombre_etudiants+1);
		Nombre_etudiants = Ensemble_etudiant.size();
		etud.mettre(Group_ID);
	}
	/**
	 * Supprimer un étudiant
	 */
	public void Supprimer(Etudiant etud) {
		Ensemble_etudiant.remove(etud);
		Nombre_etudiants = Ensemble_etudiant.size();
		etud.enlever();
	}
	/**
	 * Les membres du groupe
	 * 
	 */
	//TODO
	public String membres() {
		return Ensemble_etudiant.keySet().toString();
	}
}
