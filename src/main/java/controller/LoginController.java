package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.ModelException;
import model.dao.AccountDAO;
import model.dao.DAOFactory;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getServletPath().equals("/logout")) {
			HttpSession session = req.getSession(false);
			if (session != null)
				session.invalidate();
			ControllerUtil.redirect(resp, req.getContextPath() + "/login");
			return;
		}

		HttpSession session = req.getSession(false);
		if (session != null && session.getAttribute("loggedAccount") != null) {
			ControllerUtil.redirect(resp, req.getContextPath() + "/posts");
			return;
		}

		ControllerUtil.forward(req, resp, "/login.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if (username != null && password != null) {
			AccountDAO dao = DAOFactory.createDAO(AccountDAO.class);

			try {
				Account account = dao.findByCredentials(username.trim(), password);

				if (account != null) {
					HttpSession old = req.getSession(false);
					if (old != null)
						old.invalidate();
					req.getSession(true).setAttribute("loggedAccount", account);
					ControllerUtil.redirect(resp, req.getContextPath() + "/posts");
					return;
				}
			} catch (ModelException e) {
				e.printStackTrace();
				req.setAttribute("error", "Não foi possível acessar o banco de dados.");
				ControllerUtil.forward(req, resp, "/login.jsp");
				return;
			}
		}

		req.setAttribute("error", "Usuário ou senha inválidos.");
		ControllerUtil.forward(req, resp, "/login.jsp");
	}
}
