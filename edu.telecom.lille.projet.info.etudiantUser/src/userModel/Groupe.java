package userModel;

import java.util.HashMap;
import java.util.Set;

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
		this.Ensemble_etudiant.clear();
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
		Ensemble_etudiant.putIfAbsent(etud.ID(), etud);
		Nombre_etudiants = Ensemble_etudiant.size();
		etud.mettre(Group_ID);
	}
	/**
	 * Supprimer un étudiant
	 */
	public void Supprimer(Etudiant etud) {
		Ensemble_etudiant.remove(etud.ID());
		Nombre_etudiants = Ensemble_etudiant.size();
		etud.enlever();
	}
	/**
	 * Les membres du groupe
	 * 
	 */
	public String membres() {
		String membres = new String();
		Set<Integer> keyset_temp = Ensemble_etudiant.keySet();
		for (Integer i : keyset_temp) {
			String nom_temp = Ensemble_etudiant.get(i).nom();
			String prenom_temp = Ensemble_etudiant.get(i).prenom();
			membres = membres + prenom_temp + " " + nom_temp + "; ";
		}
		return membres;
	}
}
