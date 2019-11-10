package edu.metrostate.ics425.p4.ejz360.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.metrostate.ics425.foam.data.Roster;
import edu.metrostate.ics425.foam.data.RosterException;
import edu.metrostate.ics425.p4.ejz360.model.AthleteBean;

/**
 * Servlet implementation class FoamServlet
 * 
 * @author ezempel
 */
@WebServlet({ "/FoamServlet", "/view", "/add", "/edit", "/delete" })
public class FoamServlet extends HttpServlet {
	private static final long serialVersionUID = 20191002L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FoamServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if ("edit-form".equals(action)) {
			editForm(request, response);

		} else if ("confirmDelete".equals(action)) {
			confirmDelete(request, response);

		} else if ("delete".equals(action)) {
			deleteAthlete(request, response);

		} else if (request.getQueryString() != null) {

			request.getRequestDispatcher("/error-404.jsp").forward(request, response);
		} else {
			processRequest(request, response);
		}

	}

	private void editForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String url = "/index.jsp";
		HashMap<String, String> errList = new HashMap<String, String>();
		var sc = getServletContext();
		Roster rosterDB = (Roster) sc.getAttribute("rosterDB");

		try {
			if (rosterDB.isOnRoster(id)) {
				AthleteBean athlete = (AthleteBean) rosterDB.find(id);
				request.setAttribute("athlete", athlete);
				url = "/edit.jsp";
			} else {
				errList.put("Unkown ID", String.format("Athlete with id: %s is no longer on the roster.", id));
				request.setAttribute("roster", rosterDB.findAll());
				url = "/index.jsp";
			}
		} catch (RosterException e) {
			errList.put("Roster error", "Unable to access roster.");
		}
		if (!errList.isEmpty()) {
			request.setAttribute("errMsg", errList);
		}

		request.getRequestDispatcher(url).forward(request, response);
	}

	private void confirmDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		String url = "/index.jsp";
		HashMap<String, String> errList = new HashMap<String, String>();
		var sc = getServletContext();
		Roster rosterDB = (Roster) sc.getAttribute("rosterDB");

		try {
			if (rosterDB.isOnRoster(id)) {
				AthleteBean athlete = (AthleteBean) rosterDB.find(id);
				request.setAttribute("athlete", athlete);
				url = "/delete.jsp";
			} else {
				errList.put("Unkown ID", String.format("Athlete with id: %s is no longer on the roster.", id));
				request.setAttribute("roster", rosterDB.findAll());
				url = "/index.jsp";
			}
		} catch (RosterException e) {
			errList.put("Roster error", "Unable to access roster.");
		}

		if (!errList.isEmpty()) {
			request.setAttribute("errMsg", errList);
		}

		request.getRequestDispatcher(url).forward(request, response);
	}

	private void deleteAthlete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		var sc = getServletContext();
		Roster rosterDB = (Roster) sc.getAttribute("rosterDB");
		HashMap<String, String> errList = new HashMap<String, String>();
		String url = "/index.jsp";
		try {
			if (rosterDB.isOnRoster(id)) {
				rosterDB.delete(id);
				url = "/index.jsp";
			} else {
				errList.put("Unkown ID", String.format("Athlete with id: %s is no longer on the roster.", id));
			}
		} catch (RosterException e) {
			errList.put("Delete error", "Unable to delete athlete " + id);
		}

		try {
			request.setAttribute("roster", rosterDB.findAll());
		} catch (RosterException e) {
			errList.put("Roster error", "Unable to read roster.");
		}

		if (!errList.isEmpty()) {
			request.setAttribute("errMsg", errList);
			url = "/index.jsp";
		}

		request.getRequestDispatcher(url).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);

	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		var sc = getServletContext();
		String url = "/index.jsp";
		HashMap<String, String> errList = new HashMap<String, String>();
		Roster rosterDB = (Roster) sc.getAttribute("rosterDB");
		String welcome = "Welcome to the Freedonia Olympic" + "Athlete Management System (FOAMS).";
		request.setAttribute("welcome", welcome);

		// get current action and mode
		String action = request.getParameter("action");
		action = action == null || action.isBlank() ? "view" : action;
		String mode = request.getParameter("mode");

		if ("view".equals(action)) {
			// requests with no action are allowed to be processed
		} else if ("create-new".equals(action) && mode != null) { // Create new Athlete
			boolean readyToAdd = false;

			try {
				// validate national id
				String newId = request.getParameter("newId");
				newId = newId != null ? newId.trim() : null;
				String errId = null;
				String feedbackIdMessage = null;
				if (newId.isBlank()) {
					errId = "true";
					feedbackIdMessage = String.format("Required field");
					errList.put("National Id missing", "required field");
				}
				// duplicate id is rejected in add mode and required in edit mode
				else if (rosterDB.isOnRoster(newId)) {
					if ("add".equals(mode)) {
						errId = "true";
						feedbackIdMessage = String.format("'%s' is already in roster", newId);
						errList.put("DupId", String.format("%s is a duplicate id.", newId));
					} else if ("edit".equals(mode)) {
						errId = "false";
					}
				} else {
					errId = "false";
				}
				
				//validate last name
				String newLast = request.getParameter("newLast");
				newLast = newLast != null ? newLast.trim() : null;
				String errLast = null;
				String feedbackLastMessage = null;
				if (newLast.isBlank()) {
					errLast = "true";
					feedbackLastMessage = String.format("Required field");
					errList.put("Last name missing", "required field");
				} else {
					errLast = "false";
				}
				
				// validate first name
				String newFirst = request.getParameter("newFirst").trim();
				newFirst = newFirst != null ? newFirst.trim() : null;
				String errFirst = null;
				String feedbackFirstMessage = null;
				if (newFirst.isBlank()) {
					errFirst = "true";
					feedbackFirstMessage = String.format("Required field");
					errList.put("First name missing", "required field");
				} else {
					errFirst = "false";
				}
				
				// validate date of birth
				String newDobString = request.getParameter("newDob");
				newDobString = newDobString != null ? newDobString.trim() : null;
				String errDob = null;
				String feedbackDobMessage = null;

				LocalDate newDob = null;
				if (newDobString.isBlank()) {
					newDob = null;
				} else {
					try {
						newDob = LocalDate.parse(newDobString);
						if (newDob != null && newDob.isBefore(LocalDate.parse("1900-01-01"))) {
							errDob = "true";
							feedbackDobMessage = "The date of birth must be after 1900-01-01.";
							errList.put("Date of birth out of range", String.format(
									"'%s' is out of range. The date of birth must be after 1900-01-01.", newDobString));
						} else if (newDob == null) {
							errDob = null;
						} else {
							errDob = "false";
							feedbackDobMessage = "Looks good!";
						}
					} catch (DateTimeParseException dtpex) {
						errDob = "true";
						feedbackDobMessage = String.format("'%s' is an invalid date.", newDobString);
						errList.put("Date format error",
								String.format("'%s' must be in yyyy-MM-dd format.", request.getParameter("newDob")));
					}
				}

				// if validation passed then add or update the athlete depending on the mode
				readyToAdd = errList.isEmpty();
				if (readyToAdd) {
					// create new athlete
					AthleteBean newAthlete = createAthlete(newId, newLast, newFirst, newDob);

					// add athlete to roster and one last check for duplicate id in case it was
					// added while waiting for user
					if ("add".equals(mode) && !rosterDB.add(newAthlete)) {

						errId = "true";
						feedbackIdMessage = String.format("%s is already in roster", newId);
						errList.put("DupId",
								String.format("%s is a duplicate id.\n Cannot add: %s.", newId, newAthlete));
					} else if ("edit".equals(mode) && !rosterDB.update(newAthlete)) {
						errId = "true";
						feedbackIdMessage = String.format("%s is no longer in the roster.", newId);
						errList.put("Update error", String.format(
								"Athlete with id '%s' is no longer on the roster. Click cancel or add athlete to create a new athlete.", newId));
						request.setAttribute("updateDisabled", true);
						request.setAttribute("addEnabled", true);
					}

				}

				// send all other messages to the view
				request.setAttribute("errId", errId);
				request.setAttribute("feedbackIdMessage", feedbackIdMessage);
				request.setAttribute("errFirst", errFirst);
				request.setAttribute("feedbackFirstMessage", feedbackFirstMessage);
				request.setAttribute("errLast", errLast);
				request.setAttribute("feedbackLastMessage", feedbackLastMessage);
				request.setAttribute("errDob", errDob);
				request.setAttribute("feedbackDobMessage", feedbackDobMessage);

			} catch (RosterException e) {
				e.printStackTrace();
				errList.put("errRoster",
						String.format("Unable to add athlete: %s. ", request.getParameter("newId")) + e.getMessage());
			} catch (Exception ex) {
				errList.put("errMsg", String.format("%s.", ex.getMessage()));
			} finally {
				if (!errList.isEmpty()) {
					url = "add".equals(mode) ? "/add.jsp" : "edit.jsp";
				} else {
					url = "/index.jsp";
				}

			}
		} else {
			errList.put("Application error", "Invalid action");
			request.setAttribute("errMsg", errList);
			url = "/index.jsp";
		}

		// Send lists of all athletes to the view
		try {
			request.setAttribute("roster", rosterDB.findAll());
		} catch (RosterException e) {
			errList.put("Roster error", "Unable to send roster to browser. " + e.getMessage());
		}
		request.setAttribute("errMsg", errList);
		// Forward control to the view
		request.getRequestDispatcher(url).forward(request, response);
	}

	@Override
	public void init() throws ServletException {
		super.init();
		var sc = getServletContext();
		String virtualPath = sc.getInitParameter("filePath");
		String realPath = sc.getRealPath(virtualPath);

		if (realPath != null) {
			Roster.initialize(realPath);

			try {
				Roster rosterDB = Roster.getInstance();
				sc.setAttribute("rosterDB", rosterDB);
				sc.setAttribute("roster", rosterDB.findAll());
			} catch (RosterException e) {
				System.out.println("Unable to create roster. " + e.getMessage());
				e.printStackTrace();
			}
		}

	}

	private AthleteBean createAthlete(String newID, String newLast, String newFirst, LocalDate newDob) {
		AthleteBean ab = new AthleteBean();
		ab.setNationalID(newID);
		ab.setLastName(newLast);
		ab.setFirstName(newFirst);
		ab.setDateOfBirth(newDob);
		return ab;
	}

}
