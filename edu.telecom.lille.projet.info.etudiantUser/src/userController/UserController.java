package userController;

import java.util.HashMap;

import userModel.*;

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
	 */
	public UserController(String userfile){
		UserDB userDB=new UserDB(userfile);
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
			if (user.login().equals(userLogin) & user.mot_de_passe().equals(userPwd)) {
				//On vérifie que le login est associé au mot de passe fourni.
				userClass = user.getClass().getName();
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
			String pwd) {
		Administrateur new_admin = new Administrateur(newAdminlogin, adminID, firstname, surname, pwd);
		Couple_DB db_temp = userDB.loadDB();//On accède à la base de données
		//On retire l'ensemble des utilisateurs
		HashMap<Integer, Utilisateur> DB_Utilisateurs_temp = db_temp.getUsers();
		//On ajoute l'admin à l'ensemble des utilisateurs
		DB_Utilisateurs_temp.put(adminID, new_admin);
		//On remet les utilisateurs dans la base de données.
		db_temp.setUsers(DB_Utilisateurs_temp);
		userDB.saveDB(db_temp);
		//Vérification
		String username = getUserName(newAdminlogin);
		String admin_class = getUserClass(newAdminlogin, pwd);
		Boolean result = false;
		if (username.equals(firstname+" "+surname) & admin_class.equals(Administrateur.class.getName())) {
			result = true;
		}	
		return result;
	}

	@Override
	public boolean addTeacher(String adminLogin, String newteacherLogin, int teacherID, String firstname,
			String surname, String pwd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addStudent(String adminLogin, String newStudentLogin, int studentID, String firstname,
			String surname, String pwd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(String adminLogin, String userLogin) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addGroup(String adminLogin, int groupId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeGroup(String adminLogin, int groupId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean associateStudToGroup(String adminLogin, String studentLogin, int groupId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] usersToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] usersLoginToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] studentsLoginToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] groupsIdToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] groupsToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean loadDB() {
		userDB.loadfile();
		userDB.loadDB();
		return true;
	}

	@Override
	public boolean saveDB() {
		// TODO Auto-generated method stub
		return false;
	}

	public UserDB getUserDB() {
		return userDB;
	}

	public void setUserDB(UserDB userDB) {
		this.userDB = userDB;
	}

	@Override
	public boolean addConstraint(String adminLogin, int constraintId, String prof, int debut, int fin, String com) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeConstraint(String adminLogin, int constraintId) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}

