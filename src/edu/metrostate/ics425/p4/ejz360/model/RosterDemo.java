package edu.metrostate.ics425.p4.ejz360.model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import edu.metrostate.ics425.foam.model.Athlete;

public class RosterDemo {
	private String url = "jdbc:mysql://localhost/foam";
	private String user = "root";
	private String password = "";
	private String query = "SELECT NationalID, FirstName, LastName, DateOfBirth FROM foam.Athletes";

	public List<Athlete> findAll() {
		List<Athlete> athletes;
		try (Connection con = DriverManager.getConnection(url, user, password);
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {
			athletes = getAthletes(rs);
		} catch (SQLException e) {
			athletes = null;
		}
		return athletes;
	}

	public static void main(String[] args) {
		// 1. Connect to the database
		// 2. create query statement
		// 3. execute query
		// 4. display the results
		// 5. close the results and query and the connection

//		String url = "jdbc:mysql://localhost/foam";
//		String user = "root";
//		String password = "";
//		String query = "SELECT NationalID, FirstName, LastName, DateOfBirth FROM foam.Athletes";
//
//		try (Connection con = DriverManager.getConnection(url, user, password);
//				Statement stmt = con.createStatement();
//				ResultSet rs = stmt.executeQuery(query);) { // user for Read, other CUD actions use executeUpdate()
//															// AutoClosable resources can be declared in try with
//															// resources
//
//			// turn the results into Athletes, results are kind of like an iterator
//			// results start before the first record, returns true if there is a 'next'
//			// record
//			while (rs.next()) { // true while records are available to be processed
//				String nationalID = rs.getString("NationalID");
//				String firstName = rs.getString("FirstName");
//				String lastName = rs.getString("LastName");
//				LocalDate dob = LocalDate.parse(rs.getDate("DateOfBirth").toString()); // figure out how to convert a
//				// Date/SQL date to LocalDate
//				System.out.printf("%s\t%s\t%s\t%s\n", nationalID, firstName, lastName, dob);
//			}
//
//		} catch (
//
//		SQLException e) {
//
//			e.printStackTrace();
//		}
		RosterDemo rd = new RosterDemo();
		List<Athlete> athletes = rd.findAll();
		for (Athlete athlete : athletes) {
			System.out.println((AthleteBean)athlete);
		}
		
	}

	private List<Athlete> getAthletes(ResultSet rs) throws SQLException {
		ArrayList<Athlete> athletes = new ArrayList<Athlete>();
		AthleteBean athlete = new AthleteBean();

		while (rs.next()) {
			String nationalID = rs.getString("NationalID");
			String firstName = rs.getString("FirstName");
			String lastName = rs.getString("LastName");
			LocalDate dob = LocalDate.parse(rs.getDate("DateOfBirth").toString());
			athlete.setNationalID(nationalID);
			athlete.setLastName(lastName);
			athlete.setFirstName(firstName);
			athlete.setDateOfBirth(dob);
			athletes.add(athlete);
		}

		return athletes;

	}
}
