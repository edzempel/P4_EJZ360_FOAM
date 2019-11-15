package edu.metrostate.ics425.p4.ejz360.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import edu.metrostate.ics425.foam.model.Athlete;

public class Roster {
	private String url = "jdbc:mysql://localhost/foam";
	private String user = "root";
	private String password = "";
	private final String SELECT_ALL_QUERY = "SELECT NationalID, FirstName, LastName, DateOfBirth FROM foam.Athletes";

//	 1. Connect to the database
//	 2. create query statement
//	 3. execute query
//	 4. display the results
//	 5. close the results and query and the connection
	
	public List<Athlete> findAll() {
		List<Athlete> athletes;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);) {
			athletes = getAthletes(rs);
		} catch (SQLException e) {
			athletes = null;
		}
		return athletes;
	}

	public boolean isOnRoster(String id) {
		boolean output = false;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);) {
			while (rs.next()) {
				if (rs.getString("NationalID").equalsIgnoreCase(id)) {
					output = true;
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}

	public Athlete find(String id) {
		AthleteBean athlete = new AthleteBean();

		String findQuery = "Select NationalID, FirstName, LastName, DateOfBirth "
				+ "FROM foam.Athletes WHERE NationalID = ?";
		if (id != null && isOnRoster(id)) {
			try (Connection con = DriverManager.getConnection(url, user, password);
					PreparedStatement ps = getFindPreparedStatement(con, findQuery, id);
					ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					String NationalID = rs.getString("NationalID");
					String FirstName = rs.getString("FirstName");
					String LastName = rs.getString("LastName");
					LocalDate dob = convertToLocalDateViaSqlDate(rs.getDate("DateOfBirth"));
					athlete.setNationalID(NationalID);
					athlete.setFirstName(FirstName);
					athlete.setLastName(LastName);
					athlete.setDateOfBirth(dob);

				}

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		return athlete;
	}

	private PreparedStatement getFindPreparedStatement(Connection con, String query, String id) throws SQLException {

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);

		return ps;
	}

	public boolean delete(String id) {
		int deleted = 0;
		String deleteQuery = "DELETE FROM foam.Athletes WHERE NationalID = ?";
		if (id != null && isOnRoster(id)) {
			try (Connection con = DriverManager.getConnection(url, user, password);
					PreparedStatement ps = getFindPreparedStatement(con, deleteQuery, id);) {
				deleted = ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return deleted == 1 ? true : false;
	}

	public boolean update(Athlete anAthlete) {
		int updated = 0;
		String updateQuery = "UPDATE foam.Athletes SET " + "FirstName = ?, " + "LastName = ?, " + "DateOfBirth = ? "
				+ "WHERE NationalID = ?";
		if (anAthlete != null && isOnRoster(anAthlete.getNationalID())) {
			try (Connection con = DriverManager.getConnection(url, user, password);
					Statement stmt = con.createStatement();
					PreparedStatement ps = con.prepareStatement(updateQuery);) {
				ps.setString(1, anAthlete.getFirstName());
				ps.setString(2, anAthlete.getLastName());
				ps.setDate(3, convertToDateViaSqlDate(anAthlete.getDateOfBirth()));
				ps.setString(4, anAthlete.getNationalID());

				updated = ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		return updated == 1 ? true : false;

	}

	public boolean add(Athlete anAthlete) {
		int added = 0;
		String addQuery = "INSERT INTO foam.Athletes (NationalID, FirstName, LastName, DateOfBirth) "
				+ "VALUES (?,?,?,?)";
		if (anAthlete != null && !isOnRoster(anAthlete.getNationalID())) {
			try (Connection con = DriverManager.getConnection(url, user, password);
					Statement stmt = con.createStatement();
					PreparedStatement ps = con.prepareStatement(addQuery);) {
				ps.setString(1, anAthlete.getNationalID());
				ps.setString(2, anAthlete.getFirstName());
				ps.setString(3, anAthlete.getLastName());
				ps.setDate(4, convertToDateViaSqlDate(anAthlete.getDateOfBirth()));

				added = ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		return added == 1 ? true : false;
	}

	

	private List<Athlete> getAthletes(ResultSet rs) throws SQLException {
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();

		while (rs.next()) {
			AthleteBean athlete = new AthleteBean();
			String nationalID = rs.getString("NationalID");
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
//			LocalDate dob = LocalDate.parse(rs.getDate("DateOfBirth").toString());
			LocalDate dob = convertToLocalDateViaSqlDate(rs.getDate("DateOfBirth"));
			athlete.setNationalID(nationalID);
			athlete.setLastName(lastName);
			athlete.setFirstName(firstName);
			athlete.setDateOfBirth(dob);
			athletes.add(athlete);
		}

		return athletes;

	}

	private LocalDate convertToLocalDateViaSqlDate(java.sql.Date dateToConvert) {
		return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}

	private java.sql.Date convertToDateViaSqlDate(LocalDate dateToConvert) {
		return java.sql.Date.valueOf(dateToConvert);
	}
}
