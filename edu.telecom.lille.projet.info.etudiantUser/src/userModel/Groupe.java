package userModel;

import java.util.HashMap;

public class Groupe {
	private String AdminLogin;
	private int Group_ID;
	private int Nombre_etudiants = 0;
	private HashMap<Integer, Etudiant> Ensemble_etudiant = new HashMap<Integer, Etudiant>();
	
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
		Ensemble_etudiant.putIfAbsent(Nombre_etudiants+1, etud);
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
	public String membres() {
		String membres = Ensemble_etudiant.get(0).prenom().concat(" ").concat(Ensemble_etudiant.get(0).nom());
		for (Integer i : Ensemble_etudiant.keySet()) {
			membres.concat(", ").concat(Ensemble_etudiant.get(i).prenom()).concat(" ").concat(Ensemble_etudiant.get(i).nom());
		}
		return membres;
	}
}
