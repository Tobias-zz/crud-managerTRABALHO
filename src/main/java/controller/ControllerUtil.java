package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ControllerUtil {

	private static String MESSAGE_KEY = "message";
	private static int ERROR = 0;
	private static int SUCESS = 1;

	static void forward(HttpServletRequest req, HttpServletResponse resp, String target)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher(target);

		dispatcher.forward(req, resp);
	}

	static void redirect(HttpServletResponse resp, String target) throws IOException {
		resp.sendRedirect(target);
	}

	static void sucessMessage(HttpServletRequest req, String message) {
		req.getSession().setAttribute(MESSAGE_KEY, message);
		req.getSession().setAttribute("alertType", SUCESS);
	}

	static void errorMessage(HttpServletRequest req, String message) {
		req.getSession().setAttribute(MESSAGE_KEY, message);
		req.getSession().setAttribute("alertType", ERROR);
	}

	static void transferSessionMessagesToRequest(HttpServletRequest req) {
		if (req.getSession().getAttribute(MESSAGE_KEY) != null) {
			String message = (String) req.getSession().getAttribute(MESSAGE_KEY);
			req.setAttribute(MESSAGE_KEY, message);
			req.getSession().removeAttribute(MESSAGE_KEY);
		}

		if (req.getSession().getAttribute("alertType") != null) {
			Integer message = (Integer) req.getSession().getAttribute("alertType");
			req.setAttribute("alertType", message);
			req.getSession().removeAttribute("alertType");
		}
	}

	public static final int PAGE_SIZE = 5;

	static <T> void paginate(HttpServletRequest req, List<T> items, String attribute, String url) {
		int total = items == null ? 0 : items.size();
		int totalPages = Math.max(1, (int) Math.ceil(total / (double) PAGE_SIZE));

		int page = 1;
		try {
			page = Integer.parseInt(req.getParameter("page"));
		} catch (NumberFormatException e) {
			page = 1;
		}
		if (page < 1)
			page = 1;
		if (page > totalPages)
			page = totalPages;

		List<T> pageItems = new ArrayList<T>();
		if (total > 0) {
			int from = (page - 1) * PAGE_SIZE;
			int to = Math.min(from + PAGE_SIZE, total);
			pageItems = items.subList(from, to);
		}

		req.setAttribute(attribute, pageItems);
		req.setAttribute("currentPage", page);
		req.setAttribute("totalPages", totalPages);
		req.setAttribute("pageUrl", url);
	}

	public static Date formatDate(String stringDate) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			return dateFormat.parse(stringDate);
		} catch (ParseException e) {
			return null;
		}
	}
}
