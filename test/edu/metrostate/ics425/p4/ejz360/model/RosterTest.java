package edu.metrostate.ics425.p4.ejz360.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.*;

import edu.metrostate.ics425.foam.model.Athlete;

/**
 * JUnit Test class for P4 Roster This class worked before implementing the
 * connection pool using the driver directly
 * 
 * @author ezempel
 *
 */
public class RosterTest {
	private static final String ID_NOT_EXIST = "xxxx";
	private static Roster rosterDB;

	@BeforeClass
	public static void setupRoster() {
		rosterDB = new Roster();
		rosterDB.setTestMode(true);

	}

	@Test
	public void findAll() {
		List<Athlete> athletes = rosterDB.findAll();
		for (Athlete athlete : athletes) {
			System.out.println((AthleteBean) athlete);
		}
	}

	@Test
	public void isOnRoster() {
		assertTrue(rosterDB.isOnRoster("FD399"));
		assertFalse(rosterDB.isOnRoster(ID_NOT_EXIST));
		assertFalse(rosterDB.isOnRoster(""));
		assertFalse(rosterDB.isOnRoster(null));
	}

	@Test
	public void find() {
		// '1', 'FD399', 'Jarmo', 'Jokihaara', '1998-03-26'
		AthleteBean athlete = new AthleteBean();
		athlete = (AthleteBean) rosterDB.find("FD399");

		assertEquals("FD399", athlete.getNationalID());
		assertEquals("Jarmo", athlete.getFirstName());
		assertEquals("Jokihaara", athlete.getLastName());
		assertEquals("1998-03-26", athlete.getDateOfBirth().toString());

	}

	@Test
	public void add() {

		if (rosterDB.isOnRoster("123456")) {
			assertTrue(rosterDB.delete("123456"));
		}

		AthleteBean athlete = new AthleteBean();
		athlete.setNationalID("123456");
		athlete.setFirstName("Edward");
		athlete.setLastName("Zempel");
		athlete.setDateOfBirth(LocalDate.parse("2000-10-12"));

		assertTrue(rosterDB.add(athlete));
		// add the same athlete a second time should fail
		assertFalse(rosterDB.add(athlete));

		athlete = (AthleteBean) rosterDB.find("123456");
		assertEquals("123456", athlete.getNationalID());
		assertEquals("Edward", athlete.getFirstName());
		assertEquals("Zempel", athlete.getLastName());
		assertEquals("2000-10-12", athlete.getDateOfBirth().toString());

		assertTrue(rosterDB.isOnRoster("123456"));

		assertTrue(rosterDB.delete("123456"));
	}

	@Test
	public void update() {
		if (rosterDB.isOnRoster("123456")) {
			assertTrue(rosterDB.delete("123456"));
		}

		AthleteBean athlete = new AthleteBean();
		athlete.setNationalID("123456");
		athlete.setFirstName("Edward");
		athlete.setLastName("Zempel");
		athlete.setDateOfBirth(LocalDate.parse("2000-10-12"));

		assertTrue(rosterDB.add(athlete));

		athlete.setFirstName("George");
		assertTrue(rosterDB.update(athlete));

		athlete = (AthleteBean) rosterDB.find("123456");
		assertEquals("123456", athlete.getNationalID());
		assertEquals("George", athlete.getFirstName());
		assertEquals("Zempel", athlete.getLastName());
		assertEquals("2000-10-12", athlete.getDateOfBirth().toString());

		athlete.setNationalID(ID_NOT_EXIST);
		assertFalse(rosterDB.update(athlete));

		assertTrue(rosterDB.isOnRoster("123456"));

		assertTrue(rosterDB.delete("123456"));

	}

}
