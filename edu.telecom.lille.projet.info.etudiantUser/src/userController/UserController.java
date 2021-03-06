package userController;

import java.io.IOException;
import java.util.HashMap;

import userModel.*;

import java.io.File;


/**
 * Cette classe est le contrôleur d'utilisateurs que vous devez implémenter. 
 * Elle contient un attribut correspondant à la base de données utilisateurs que vous allez créer.
 * Elle contient toutes les fonctions de l'interface IUserController que vous devez implémenter.
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe à modifier

public class UserController implements IUserController
{
	
	/**
	 * Contient une instance de base de données d'utilisateurs
	 * 
	 */
	private UserDB userDB=null;
	
	
	/**
	 * Constructeur de controleur d'utilisateurs créant la base de données d'utilisateurs
	 * 
	 * @param userfile
	 * 		Fichier XML contenant la base de données d'utilisateurs
	 * @throws IOException 
	 */
	public UserController(String userfile) throws IOException{
		UserDB userDB=new UserDB(userfile);
		// Si le fichier existe, on charge la base de données correspondante.
		String arbre = System.getProperty("user.dir");
		File emplacement = new File(arbre+"/"+userfile);
		Boolean existence = emplacement.exists();
		if (existence) {
			userDB.loadfile();
		} else {
		}
		this.setUserDB(userDB);
	}

	@Override
	public String getUserName(String userLogin) {
		String name = "Not Found";
		for (Utilisateur user : userDB.loadDB().getUsers().values()) {
			//On parcourt les utilisateurs
			if (user.login().equals(userLogin)) {
				//On retrouve le login désiré
				name = user.prenom()+" "+user.nom();
			}
		}
		return name;
	}

	@Override
	public String getUserClass(String userLogin, String userPwd) {
		String userClass = "Not Found";
		for (Utilisateur user : userDB.loadDB().getUsers().values()) {
			//On parcourt les utilisateurss
			if (user.login().compareTo(userLogin) == 0 && user.mot_de_passe().compareTo(userPwd) == 0) {
				//On vérifie que le login est associé au mot de passe fourni.
				userClass = user.getClass().getName();
				System.out.println(userClass);
				if(userClass.compareTo("userModel.Etudiant")==0) {
					userClass="Student";
				} if (userClass.compareTo("userModel.Professeur")==0) {
					userClass="Teacher";
				} if (userClass.compareTo("userModel.Administrateur")==0) {
					userClass="Administrator";
				} else {
					userClass  = "";
				}
			}
		}
		return userClass;
	}

	@Override
	public int getStudentGroup(String studentLogin) {
		int studentGroup = 0;//Le groupe 0 équivaut à une absence de groupe
		for (Utilisateur user : userDB.loadDB().getUsers().values()) {
			//On parcourt les utilisateurs
			if (user.login().equals(studentLogin) & user.getClass().equals(Etudiant.class)) {
				//On vérifie que l'on a le login d'un étudiant
				studentGroup = user.ID_groupe();
			}
		}
		return studentGroup;
	}

	@Override
	public boolean addAdmin(String adminLogin, String newAdminlogin, int adminID, String firstname, String surname,
			String pwd) throws IOException {
		Boolean resultat;
		if (getUserName(newAdminlogin).equals(firstname+" "+surname)) {
			resultat = false;
		} else {
			Administrateur new_admin = new Administrateur(newAdminlogin, adminID, firstname, surname, pwd);
			Couple_DB db_temp = userDB.loadDB();//On accède à la base de données
			//On retire l'ensemble des utilisateurs
			HashMap<Integer, Utilisateur> DB_Utilisateurs_temp = db_temp.getUsers();
			//On ajoute l'admin à l'ensemble des utilisateurs
			DB_Utilisateurs_temp.put(adminID, new_admin);
			//On remet les utilisateurs dans la base de données.
			db_temp.setUsers(DB_Utilisateurs_temp);
			userDB.saveDB(db_temp);
			saveDB();
			resultat =  true;
		}
		return resultat;
	}

	@Override
	public boolean addTeacher(String adminLogin, String newteacherLogin, int teacherID, String firstname,
			String surname, String pwd) throws IOException {
		Boolean resultat;
		if (getUserName(newteacherLogin).equals(firstname+" "+surname)) {
			resultat = false;
		} else {
			Professeur new_prof = new Professeur(newteacherLogin, teacherID, firstname, surname, pwd);
			Couple_DB db_temp = userDB.loadDB();//On accède à la base de données
			//On retire l'ensemble des utilisateurs
			HashMap<Integer, Utilisateur> DB_Utilisateurs_temp = db_temp.getUsers();
			//On ajoute le professeur à l'ensemble des utilisateurs
			DB_Utilisateurs_temp.put(teacherID, new_prof);
			//On remet les utilisateurs dans la base de données.
			db_temp.setUsers(DB_Utilisateurs_temp);
			userDB.saveDB(db_temp);
			saveDB();
			resultat =  true;
		}
		return resultat;
	}

	@Override
	public boolean addStudent(String adminLogin, String newStudentLogin, int studentID, String firstname,
			String surname, String pwd) throws IOException {
		Boolean resultat;
		if (getUserName(newStudentLogin).equals(firstname+" "+surname)) {
			resultat = false;
		} else {
			Etudiant new_stud = new Etudiant(newStudentLogin, studentID, firstname, surname, pwd);
			Couple_DB db_temp = userDB.loadDB();//On accède à la base de données
			//On retire l'ensemble des utilisateurs
			HashMap<Integer, Utilisateur> DB_Utilisateurs_temp = db_temp.getUsers();
			//On ajoute l'étudiant à l'ensemble des étudiants
			DB_Utilisateurs_temp.put(studentID, new_stud);
			//On remet les utilisateurs dans la base de données.
			db_temp.setUsers(DB_Utilisateurs_temp);
			userDB.saveDB(db_temp);
			saveDB();
			resultat =  true;
		}
		return resultat;
	}

	@Override
	public boolean removeUser(String adminLogin, String userLogin) throws IOException {
		Boolean resultat;
		
		//Vérification d'existence
		if (getUserName(userLogin).equals("Not Found")) {
			resultat = false;
		} else {
			int key = 410;// On retire un utilisateur d'un HashMap par son identifiant
			Couple_DB db_temp = userDB.loadDB();//On accède à la base de données
			//On retire l'ensemble des utilisateurs
			HashMap<Integer, Utilisateur> DB_Utilisateurs_temp = db_temp.getUsers();
			//On identifie l'identifiant de l'utilisateur
			for (Utilisateur u : DB_Utilisateurs_temp.values()) {
				if (u.login().equals(userLogin)) {
					key = u.ID();
					if (u.getClass().getName().equals(Etudiant.class.getName()) && (u.ID_groupe() != 0)) {
						//Les Etudiants sont retirés de leur groupe si leur groupe existe
						//										i.e. ID_groupe différent de 0
						HashMap<Integer, Groupe> temp_group_db = db_temp.getGroups();
						temp_group_db.get(u.ID_groupe()).Supprimer(u);
						db_temp.setGroups(temp_group_db);
					}
				}
			}
			//On enlève l'utilisateur à l'ensemble des utilisateurs
			DB_Utilisateurs_temp.remove(key);
			//On remet les utilisateurs dans la base de données.
			db_temp.setUsers(DB_Utilisateurs_temp);
			userDB.saveDB(db_temp);
			saveDB();
			resultat =  true;
		}
		return resultat;
	}

	@Override
	public boolean addGroup(String adminLogin, int groupId) throws IOException {
		Boolean resultat;
		Boolean duplicata = false;
		for (String id : groupsIdToString()) {
			duplicata = duplicata || Integer.toString(groupId).equals(id);
			//Si le group est déjà présent, duplicata = true
		}
		if (duplicata) {
			resultat = false;
		} else {
			Groupe new_group = new Groupe(adminLogin, groupId);
			Couple_DB db_temp = userDB.loadDB();//On accède à la base de données
			//On retire l'ensemble des groupes
			HashMap<Integer, Groupe> DB_groupes_temp = db_temp.getGroups();
			//On ajoute le groupe à l'ensemble des groupes
			DB_groupes_temp.put(groupId, new_group);
			//On remet les groupes dans la base de données.
			db_temp.setGroups(DB_groupes_temp);
			userDB.saveDB(db_temp);
			saveDB();
			resultat =  true;
		}
		return resultat;
	}

	@Override
	public boolean removeGroup(String adminLogin, int groupId) throws IOException {
		Boolean resultat;
		Boolean resultat_temp = false;// Désigne l'existence ou non du groupe
		//Vérification d'existence
		for (String ID : groupsIdToString()) {
			if (ID.equals(Integer.toString(groupId))) {
				resultat_temp = true;
			}
		}
		if (!resultat_temp) {
			resultat = false;
		} else {
			// On retire un groupe d'un HashMap par son identifiant
			Couple_DB db_temp = userDB.loadDB();//On accède à la base de données
			//On retire l'ensemble des groupes
			HashMap<Integer, Groupe> DB_groupes_temp = db_temp.getGroups();
			// On enlève les utilisateurs du groupe
			HashMap<Integer, Utilisateur> DB_users_temp = db_temp.getUsers();
			for (Utilisateur u : DB_users_temp.values()) {
				if (u.ID_groupe() == groupId) {
					DB_groupes_temp.get(groupId).Supprimer(u);
				}
			}
			db_temp.setUsers(DB_users_temp);//On remet les utilisateurs dans la base de données.

			
			//On enlève le groupe à l'ensemble des groupes
			DB_groupes_temp.remove(groupId);

			//On remet les groupes dans la base de données.
			db_temp.setGroups(DB_groupes_temp);
			userDB.saveDB(db_temp);
			saveDB();
			resultat =  true;
		}
		return resultat;
	}

	@Override
	public boolean associateStudToGroup(String adminLogin, String studentLogin, int groupId) throws IOException {
		Boolean resultat;
		// On vérifie que l'étudiant existe et que le groupe existe
		if (!(getUserName(studentLogin).equals("Not Found")) && userDB.loadDB().getGroups().containsKey(groupId)) {
			// On ajoute l'étudiant au groupe
			Couple_DB db_temp = userDB.loadDB();
			HashMap<Integer, Groupe> DB_groupes_temp = db_temp.getGroups();
			Groupe grp;
			Utilisateur stud;
			for (Utilisateur u : db_temp.getUsers().values()) {
				if (u.login().equals(studentLogin)) {
					stud = u;
					grp = DB_groupes_temp.get(groupId);
					grp.Ajouter(stud);
					DB_groupes_temp.replace(groupId, grp);
				}
			}
			db_temp.setGroups(DB_groupes_temp);
			userDB.saveDB(db_temp);
			saveDB();
			resultat = true;
		} else {
			resultat = false;
		}
		return resultat;
	}

	@Override
	public String[] usersToString() {
		HashMap<Integer, Utilisateur> DB_Utilisateurs_temp = userDB.loadDB().getUsers();
		int taille = DB_Utilisateurs_temp.size();
		String[] tableau_utilisateurs = new String[taille];
		//tableau_utilisateurs[0] = "login ID firstname surname password groupID";
		int i = 0;//On initialise le compteur qui désigne la ligne du tableau
		for (Utilisateur u : DB_Utilisateurs_temp.values()) {
			tableau_utilisateurs[i] = u.login()+" "+Integer.toString(u.ID())+" "+u.prenom()+" "+u.nom()+" "+u.mot_de_passe()+" "+u.ID_groupe();
			i = i +1;
		}
		return tableau_utilisateurs;
	}

	@Override
	public String[] usersLoginToString() {
		HashMap<Integer, Utilisateur> DB_Utilisateurs_temp = userDB.loadDB().getUsers();
		int taille = DB_Utilisateurs_temp.size();
		String[] tableau_utilisateurs = new String[taille];
		int i = 0;//On initialise le compteur qui désigne la ligne du tableau
		for (Utilisateur u : DB_Utilisateurs_temp.values()) {
			tableau_utilisateurs[i] = u.login();
			i = i +1;
		}
		return tableau_utilisateurs;
	}

	@Override
	public String[] studentsLoginToString() {
		HashMap<Integer, Utilisateur> DB_Utilisateurs_temp = userDB.loadDB().getUsers();
		HashMap<Integer, Utilisateur> DB_etud_temp = new HashMap<Integer, Utilisateur>();
		for (Utilisateur u : DB_Utilisateurs_temp.values()) {
			if (u.getClass().getName().equals(Etudiant.class.getName())) {
				DB_etud_temp.put(u.ID(), u);
			}
		}
		int taille = DB_etud_temp.size();
		String[] tableau_etudiants_login = new String[taille];
		int i = 0;//On initialise le compteur qui désigne la ligne du tableau
		for (Utilisateur u : DB_etud_temp.values()) {
			tableau_etudiants_login[i] = u.login();
			i = i +1;
		}
		return tableau_etudiants_login;
	}

	@Override
	public String[] groupsIdToString() {
		HashMap<Integer, Groupe> DB_groupes_temp = userDB.loadDB().getGroups();
		int taille = DB_groupes_temp.size();
		String[] tableau_groupes = new String[taille];
		int i = 0;//On initialise le compteur qui désigne la ligne du tableau
		for (Groupe u : DB_groupes_temp.values()) {
			tableau_groupes[i] = Integer.toString(u.ID());
			i = i +1;
		}
		return tableau_groupes;
	}

	@Override
	public String[] groupsToString() {
		HashMap<Integer, Groupe> DB_groupes_temp = userDB.loadDB().getGroups();
		int taille = DB_groupes_temp.size();
		String[] tableau_groupes = new String[taille];
//		tableau_groupes[0] = "ID Admin taille membres";	
		int i = 0;//On initialise le compteur qui désigne la ligne du tableau
		for (Groupe u : DB_groupes_temp.values()) {
			tableau_groupes[i] = Integer.toString(u.ID())+" "+u.Admin()+" "+Integer.toString(u.taille())+" "+u.membres();
			i = i +1;
		}
		return tableau_groupes;
	}

	@Override
	public boolean loadDB() {
		// La base de données émane d'un fichier
		userDB.loadfile();
		return true;
	}

	@Override
	public boolean saveDB() throws IOException  {
		userDB.savefile();
		return false;
	}

	public UserDB getUserDB() {
		return userDB;
	}

	public void setUserDB(UserDB userDB) {
		this.userDB = userDB;
	}

	@Override
	public boolean addConstraint(String adminLogin, int constraintId, String prof, int debut, int fin, String com) throws IOException {
		Boolean resultat = true;
		//TODO Traitement des doublons
		Contrainte_horaire new_con = new Contrainte_horaire(constraintId, prof, debut, fin, com);
		Couple_DB db_temp = userDB.loadDB();//On accède à la base de données
		//On retire l'ensemble des utilisateurs
		HashMap<Integer, Contrainte_horaire> DB_con_temp = db_temp.getConstraints();
		//On ajoute l'étudiant à l'ensemble des étudiants
		DB_con_temp.put(constraintId, new_con);
		//On remet les utilisateurs dans la base de données.
		db_temp.setConstraints(DB_con_temp);
		userDB.saveDB(db_temp);
		saveDB();
		return resultat;
	}

	@Override
	public boolean removeConstraint(String adminLogin, int constraintId) throws IOException {
		Boolean resultat;
		//Vérification d'existence
		if (userDB.loadDB().getConstraints().containsKey(constraintId)) {
			// On retire une contrainte d'un HashMap par son identifiant
			Couple_DB db_temp = userDB.loadDB();//On accède à la base de données
			//On retire l'ensemble des contraintes
			HashMap<Integer, Contrainte_horaire> DB_cons_temp = db_temp.getConstraints();

			//On enlève la contrainte à l'ensemble des contraintes
			DB_cons_temp.remove(constraintId);

			//On remet les groupes dans la base de données.
			db_temp.setConstraints(DB_cons_temp);;
			userDB.saveDB(db_temp);
			saveDB();
			resultat =  true;
		} else {
			resultat = false;
		}
		return resultat;
	}
	
	

}

