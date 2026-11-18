package controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Appointment;
import model.ModelException;
import model.Pet;
import model.dao.AppointmentDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/appointments", "/appointment/form", "/appointment/insert",
		"/appointment/update", "/appointment/delete"})
public class AppointmentsController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();

		switch (action) {
		case "/appointment/form": {
			CommonsController.listPets(req);
			req.setAttribute("action", "insert");
			ControllerUtil.forward(req, resp, "/form-appointment.jsp");
			break;
		}
		case "/appointment/update": {
			Appointment appointment = loadAppointment(req);
			if (appointment == null) {
				ControllerUtil.errorMessage(req, "Consulta não encontrada para alteração.");
				ControllerUtil.redirect(resp, req.getContextPath() + "/appointments");
				return;
			}
			CommonsController.listPets(req);
			req.setAttribute("appointment", appointment);
			req.setAttribute("action", "update");
			ControllerUtil.forward(req, resp, "/form-appointment.jsp");
			break;
		}
		default:
			listAppointments(req);
			ControllerUtil.transferSessionMessagesToRequest(req);
			ControllerUtil.forward(req, resp, "/appointments.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getServletPath();

		switch (action) {
		case "/appointment/insert":
			insertAppointment(req);
			break;
		case "/appointment/update":
			updateAppointment(req);
			break;
		case "/appointment/delete":
			deleteAppointment(req);
			break;
		default:
			System.out.println("URL inválida " + action);
		}

		ControllerUtil.redirect(resp, req.getContextPath() + "/appointments");
	}

	private Appointment loadAppointment(HttpServletRequest req) {
		try {
			int id = Integer.parseInt(req.getParameter("appointmentId"));
			AppointmentDAO dao = DAOFactory.createDAO(AppointmentDAO.class);
			return dao.findById(id);
		} catch (NumberFormatException e) {
			return null;
		} catch (ModelException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Appointment readForm(HttpServletRequest req, Appointment appointment) {
		String reason = req.getParameter("reason");
		String date = req.getParameter("appointmentDate");
		String price = req.getParameter("price");
		String pet = req.getParameter("pet");

		if (reason == null || reason.trim().isEmpty() || date == null || price == null || pet == null)
			throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");

		Date appointmentDate = ControllerUtil.formatDate(date);
		if (appointmentDate == null)
			throw new IllegalArgumentException("Data da consulta inválida.");

		double priceValue = Double.parseDouble(price);
		if (priceValue < 0)
			throw new IllegalArgumentException("O valor não pode ser negativo.");

		appointment.setReason(reason.trim());
		appointment.setAppointmentDate(appointmentDate);
		appointment.setPrice(priceValue);
		appointment.setReturnNeeded(req.getParameter("returnNeeded") != null);
		appointment.setPet(new Pet(Integer.parseInt(pet)));
		return appointment;
	}

	private void insertAppointment(HttpServletRequest req) {
		try {
			Appointment appointment = readForm(req, new Appointment());
			AppointmentDAO dao = DAOFactory.createDAO(AppointmentDAO.class);

			if (dao.save(appointment))
				ControllerUtil.sucessMessage(req, "Consulta '" + appointment.getReason() + "' salva com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Consulta '" + appointment.getReason() + "' não pode ser salva.");
		} catch (IllegalArgumentException e) {
			ControllerUtil.errorMessage(req, "Dados inválidos: " + e.getMessage());
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void updateAppointment(HttpServletRequest req) {
		try {
			Appointment appointment = readForm(req,
					new Appointment(Integer.parseInt(req.getParameter("appointmentId"))));
			AppointmentDAO dao = DAOFactory.createDAO(AppointmentDAO.class);

			if (dao.update(appointment))
				ControllerUtil.sucessMessage(req, "Consulta '" + appointment.getReason() + "' atualizada com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Consulta '" + appointment.getReason() + "' não pode ser atualizada.");
		} catch (IllegalArgumentException e) {
			ControllerUtil.errorMessage(req, "Dados inválidos: " + e.getMessage());
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void deleteAppointment(HttpServletRequest req) {
		try {
			int id = Integer.parseInt(req.getParameter("id"));
			AppointmentDAO dao = DAOFactory.createDAO(AppointmentDAO.class);
			Appointment appointment = dao.findById(id);

			if (appointment == null)
				throw new ModelException("Consulta não encontrada para exclusão.");

			if (dao.delete(appointment))
				ControllerUtil.sucessMessage(req, "Consulta '" + appointment.getReason() + "' excluída com sucesso.");
			else
				ControllerUtil.errorMessage(req, "Consulta '" + appointment.getReason() + "' não pode ser excluída.");
		} catch (NumberFormatException e) {
			ControllerUtil.errorMessage(req, "Consulta inválida para exclusão.");
		} catch (ModelException e) {
			e.printStackTrace();
			ControllerUtil.errorMessage(req, e.getMessage());
		}
	}

	private void listAppointments(HttpServletRequest req) {
		AppointmentDAO dao = DAOFactory.createDAO(AppointmentDAO.class);

		List<Appointment> appointments = null;
		try {
			appointments = dao.listAll();
		} catch (ModelException e) {
			e.printStackTrace();
		}

		if (appointments != null)
			ControllerUtil.paginate(req, appointments, "appointments", "/appointments");
	}
}
