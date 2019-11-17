package edu.metrostate.ics425.p4.ejz360.model;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import edu.metrostate.ics425.foam.model.Athlete;

/**
 * P4 Roster Class provides connectivity to foam application to MySQL database
 * 
 * 
 * @author ezempel
 *
 */
public class Roster {
	private boolean testMode = false;
	private final String SELECT_ALL_QUERY = "SELECT NationalID, FirstName, LastName, DateOfBirth FROM foam.Athletes";

	private DataSource getDataSource() {
		DataSource dataSource = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/FoamDB");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataSource;
	}

	private Connection getConn() throws SQLException {
		// Used for JUnit testing
		String url = "jdbc:mysql://localhost/foam";
		String user = "foamapp";
		String password = "ics425";

		if (testMode) {
			return DriverManager.getConnection(url, user, password);
		} else {
			return getDataSource().getConnection();
		}

	}

	/**
	 * Set test mode to true to use with JUnit. False if using with connection pool.
	 * 
	 * @param mode true for JUnit, false for connection pool
	 */
	public void setTestMode(boolean mode) {
		this.testMode = mode;
	}

	/**
	 * Returns a list containing all athletes on the roster
	 * 
	 * @return List of athletes
	 */
	public List<Athlete> findAll() {
		List<Athlete> athletes;
		try (Connection con = getConn();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_ALL_QUERY);) {
			athletes = getAthletes(rs);
		} catch (SQLException e) {
			athletes = null;
		}
		return athletes;
	}

	/**
	 * Checks whether the specified athlete is on the roster
	 * 
	 * @param id NationalID of athlete
	 * @return true if the athlete is on the roster, false if not
	 */
	public boolean isOnRoster(String id) {
		boolean output = false;
		try (Connection con = getConn();
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

	/**
	 * Returns the athlete record for the athlete with the specified ID
	 * 
	 * @param id NationalID of the athlete
	 * @return record for athlete, or null if not found
	 */
	public Athlete find(String id) {
		AthleteBean athlete = null;

		String findQuery = "Select NationalID, FirstName, LastName, DateOfBirth "
				+ "FROM foam.Athletes WHERE NationalID = ?";
		if (id != null && isOnRoster(id)) {
			athlete = new AthleteBean();
			try (Connection con = getConn();

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

	/**
	 * Removes the record with the specified NationalID from the roster
	 * 
	 * @param id - NationalID of the athlete to be removed
	 * @return true if the athlete was successfully removed
	 */
	public boolean delete(String id) {
		int deleted = 0;
		String deleteQuery = "DELETE FROM foam.Athletes WHERE NationalID = ?";
		if (id != null && isOnRoster(id)) {
			try (Connection con = getConn();

					PreparedStatement ps = getFindPreparedStatement(con, deleteQuery, id);) {
				deleted = ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return deleted == 1 ? true : false;
	}

	/**
	 * Replaces existing record for the athlete with the record specified. The
	 * NationalID of the athlete cannot be changed.
	 * 
	 * @param anAthlete - updated athlete record
	 * @return true if the athlete was successfully updated
	 */
	public boolean update(Athlete anAthlete) {
		int updated = 0;
		String updateQuery = "UPDATE foam.Athletes SET " + "FirstName = ?, " + "LastName = ?, " + "DateOfBirth = ? "
				+ "WHERE NationalID = ?";
		if (anAthlete != null && isOnRoster(anAthlete.getNationalID())) {
			try (Connection con = getConn();

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

	/**
	 * Adds the specified athlete to the roster. If an athlete with the same
	 * NationalID already exists the roster is unchanged.
	 * 
	 * @param anAthlete athlete to add
	 * @return true if successfully added, false otherwise
	 */
	public boolean add(Athlete anAthlete) {
		int added = 0;
		String addQuery = "INSERT INTO foam.Athletes (NationalID, FirstName, LastName, DateOfBirth) "
				+ "VALUES (?,?,?,?)";
		if (anAthlete != null && !isOnRoster(anAthlete.getNationalID())) {
			try (Connection con = getConn();
					Statement stmt = con.createStatement();
					PreparedStatement ps = con.prepareStatement(addQuery);) {
				ps.setString(1, anAthlete.getNationalID());
				ps.setString(2, anAthlete.getFirstName());
				ps.setString(3, anAthlete.getLastName());

				LocalDate dateOfBirth = anAthlete.getDateOfBirth();
				ps.setDate(4, convertToDateViaSqlDate(dateOfBirth));

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
			Date date = rs.getDate("DateOfBirth");
			LocalDate dob = convertToLocalDateViaSqlDate(date);
			athlete.setNationalID(nationalID);
			athlete.setLastName(lastName);
			athlete.setFirstName(firstName);
			athlete.setDateOfBirth(dob);
			athletes.add(athlete);
		}

		return athletes;

	}

	private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
		return dateToConvert != null ? dateToConvert.toLocalDate() : null;
	}

	private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
		return dateToConvert != null ? Date.valueOf(dateToConvert) : null;
	}

	private PreparedStatement getFindPreparedStatement(Connection con, String query, String id) throws SQLException {

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, id);

		return ps;
	}
}
