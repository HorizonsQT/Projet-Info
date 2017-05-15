package userModel;

import java.util.HashMap;

public class Groupe {
	public String AdminLogin;
	public int Group_ID;
	public int Nombre_etudiants;
	public HashMap<Etudiant, Integer> Ensemble_etudiant = new HashMap<Etudiant, Integer>();
}
