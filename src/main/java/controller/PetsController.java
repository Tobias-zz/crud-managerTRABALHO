package controller;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Pet;
import model.User;
import model.dao.DAOFactory;
import model.dao.PetDAO;

@WebServlet(urlPatterns = {"/pets", "/pet/form", "/pet/insert", "/pet/update", "/pet/delete"})
public class PetsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();

		switch (action) {
		case "/pet/form": {
			CommonsController.listUsers(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-pet.jsp");
			break;
		}
		case "/pet/update": {
			Pet pet = loadPet(req);
			if (pet == null) {
				ControllerUtil.errorMessage(req, "Pet não encontrado para alteração.");
				ControllerUtil.redirect(resp, req.getContextPath() + "/pets");
				return;
			}
			CommonsController.listUsers(req);
			req.setAttribute("pet", pet);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-pet.jsp");
			break;
		}
		default:
			listPets(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/pets.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();

		switch (action) {
		case "/pet/insert":
			insertPet(req);
			break;
		case "/pet/update":
			updatePet(req);
			break;
		case "/pet/delete":
			deletePet(req);
			break;
		default:
			System.out.println("URL inválida " + action);
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/pets");
	}

	private Pet loadPet(HttpServletRequest req) {
		try {
			int id = Integer.parseInt(req.getParameter("petId"));
			PetDAO dao = DAOFactory.createDAO(PetDAO.class);
			return dao.findById(id);
		} catch (NumberFormatException e) {
			return null;
		} catch (ModelException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Pet readForm(HttpServletRequest req, Pet pet) {
		String name = req.getParameter("name");
		String species = req.getParameter("species");
		String birth = req.getParameter("birthDate");
		String weight = req.getParameter("weight");
		String user = req.getParameter("user");

		if (name == null || name.trim().isEmpty() || species == null || birth == null
				|| weight == null || user == null)
			throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");

		if (!species.equals("CAO") && !species.equals("GATO") && !species.equals("OUTRO"))
			throw new IllegalArgumentException("Espécie inválida.");

		Date birthDate = ControllerUtil.formatDate(birth);
		if (birthDate == null)
			throw new IllegalArgumentException("Data de nascimento inválida.");

		double weightValue = Double.parseDouble(weight);
		if (weightValue < 0)
			throw new IllegalArgumentException("O peso não pode ser negativo.");

		pet.setName(name.trim());
		pet.setSpecies(species);
		pet.setBirthDate(birthDate);
		pet.setWeight(weightValue);
		pet.setNeutered(req.getParameter("neutered") != null);
		pet.setUser(new User(Integer.parseInt(user)));
		return pet;
	}

	private void insertPet(HttpServletRequest req) {
		try {
			Pet pet = readForm(req, new Pet());
			PetDAO dao = DAOFactory.createDAO(PetDAO.class);

			if (dao.save(pet))
				ControllerUtil.sucessMessage(req, "Pet '" + pet.getName() + "' salvo com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Pet '" + pet.getName() + "' não pode ser salvo.");
		} catch (IllegalArgumentException e) {
			ControllerUtil.errorMessage(req, "Dados inválidos: " + e.getMessage());
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updatePet(HttpServletRequest req) {
		try {
			Pet pet = readForm(req, new Pet(Integer.parseInt(req.getParameter("petId"))));
			PetDAO dao = DAOFactory.createDAO(PetDAO.class);

			if (dao.update(pet))
				ControllerUtil.sucessMessage(req, "Pet '" + pet.getName() + "' atualizado com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Pet '" + pet.getName() + "' não pode ser atualizado.");
		} catch (IllegalArgumentException e) {
			ControllerUtil.errorMessage(req, "Dados inválidos: " + e.getMessage());
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deletePet(HttpServletRequest req) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			PetDAO dao = DAOFactory.createDAO(PetDAO.class);
			Pet pet = dao.findById(id);

			if (pet == null)
				throw new ModelException("Pet não encontrado para exclusão.");

			if (dao.delete(pet))
				ControllerUtil.sucessMessage(req, "Pet '" + pet.getName() + "' excluído com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Pet '" + pet.getName() + "' não pode ser excluído.");
		} catch (NumberFormatException e) {
			ControllerUtil.errorMessage(req, "Pet inválido para exclusão.");
		} catch (ModelException e) {
			e.printStackTrace();
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException)
				ControllerUtil.errorMessage(req, "O pet não pode ser excluído. Há consultas relacionadas a ele.");
			else
				ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void listPets(HttpServletRequest req) {
		PetDAO dao = DAOFactory.createDAO(PetDAO.class);

		List<Pet> pets = null;
		try {
			pets = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}

		if (pets != null)
			ControllerUtil.paginate(req, pets, "pets", "/pets");
	}
}
