package userModel;

import java.util.HashMap;


public class Couple_DB {
	private HashMap<Integer, Utilisateur> DB_Utilisateurs;
	private HashMap<Integer, Groupe> DB_Groupe;
	private HashMap<Integer, Contrainte_horaire> DB_Contraintes;
	/**
	 * Permet de manipuler deux base de données HashMap
	 * @param utilisateurs
	 * @param groupes
	 */
	public Couple_DB(HashMap<Integer, Utilisateur> utilisateurs, HashMap<Integer, Groupe> groupes, HashMap<Integer, Contrainte_horaire> contraintes) {
		DB_Utilisateurs = utilisateurs;
		DB_Groupe = groupes;
		DB_Contraintes =  contraintes;
	}
	
	public HashMap<Integer, Utilisateur> getUsers() {
		return DB_Utilisateurs;
	}
	
	public HashMap<Integer, Groupe> getGroups() {
		return DB_Groupe;
	}
	
	public HashMap<Integer, Contrainte_horaire> getConstraints() {
		return DB_Contraintes;
	}
	
	public void setUsers(HashMap<Integer, Utilisateur> new_users) {
		DB_Utilisateurs = new_users;
	}
	
	public void setGroups(HashMap<Integer, Groupe> new_groups) {
		DB_Groupe = new_groups;
	}
	
	public void setConstraints(HashMap<Integer, Contrainte_horaire> new_constraints) {
		DB_Contraintes = new_constraints;
	}

}
