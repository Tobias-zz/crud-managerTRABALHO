package controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import model.ModelException;
import model.Pet;
import model.User;
import model.dao.DAOFactory;
import model.dao.PetDAO;
import model.dao.UserDAO;

public class CommonsController {
	
	public static void listUsers(HttpServletRequest req) {
		UserDAO dao = DAOFactory.createDAO(UserDAO.class);
		
		List<User> listUsers = null;
		try {
			listUsers = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}
		
		if (listUsers != null)
			req.setAttribute("users", listUsers);		
	}

	public static void listPets(HttpServletRequest req) {
		PetDAO dao = DAOFactory.createDAO(PetDAO.class);
		
		List<Pet> listPets = null;
		try {
			listPets = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}
		
		if (listPets != null)
			req.setAttribute("pets", listPets);
	}
}
