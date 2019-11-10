package edu.metrostate.ics425.p4.ejz360.model;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.*;

import edu.metrostate.ics425.foam.model.Athlete;

public class RosterTest {
	private static Roster rosterDB;
	
	@BeforeClass
	public static void setupRoster() {
		rosterDB = new Roster();

	}
	
	@Test
	public void findAll() {
		List<Athlete> athletes = rosterDB.findAll();
		for (Athlete athlete : athletes) {
			System.out.println((AthleteBean)athlete);
		}
	}
	
	@Test
	public void isOnRoster() {
		assertTrue(rosterDB.isOnRoster("FD399"));
		assertFalse(rosterDB.isOnRoster("xxxx"));
		assertFalse(rosterDB.isOnRoster(""));
		assertFalse(rosterDB.isOnRoster(null));
	}

}
