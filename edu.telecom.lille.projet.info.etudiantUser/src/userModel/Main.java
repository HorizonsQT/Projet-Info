package userModel;

import java.io.IOException;

import userController.UserController;
/**
 * Cette classe permet de tester les fonctions du contrôleur d'utilisateurs.
 * Elle crée une base de données de 6 utilisateurs et les sauvegarde dans le fichier "usersDB.xml". 
 * 
 * @author Jose Mennesson (Mettre à jour)
 * @version 04/2016 (Mettre à jour)
 * 
 */

//TODO Classe pouvant être modifiée suivant vos besoins

public class Main {
	/**
	 * Fonction principale 
	 * 
	 * @param args
	 * 			Les arguments donnés en entrée du programme.
	 * @throws IOException 
	 * 
	 */
	public static void main(String[] args) throws IOException {
		final String file="usersDB.xml";
		UserController UC=new UserController(file);
		UC.addAdmin("su","KF",0001,"Kevin", "Flynn",  "@tron");
		UC.addAdmin("su","KR",0002,"Keanu", "Reeves",  "redpill");
		UC.addTeacher("su","GS",1001,"Grand", "Schtroumpf",  "salsepareille");
		UC.addTeacher("su","MF",1002,"Morgan", "Freeman",  "iknowall");
		UC.addStudent("su","BS",2001,"Buffy", "Summers",  "stake");
		UC.addStudent("su","NL",2002,"Nicolas", "Lepetit",  "prout");
		UC.addConstraint("su", 3001, "MF", 410, 475, "com");
		UC.saveDB();
	}
}
