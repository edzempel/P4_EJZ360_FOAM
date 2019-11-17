package edu.metrostate.ics425.p4.ejz360.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
//import java.util.Random;

import java.util.logging.Logger;
import org.junit.*;

import edu.metrostate.ics425.foam.model.Athlete;
import edu.metrostate.ics425.p4.ejz360.model.AthleteBean;

/**
 * JUnit test class for P4 Athlete Bean
 * 
 * This is a repeat of the test class for P2 and P3
 * @author ezempel
 *
 */
public class AthleteBeanTest {

	@SuppressWarnings("unused")
	private static final long SEED = 20190905001L;
//	private static final Random RAND = new Random(SEED);
//	private static final double TOLERANCE = 0.0001;
	private AthleteBean ab;

	@Before // running jUnit 4, jUnit5 uses @BeforeEach
	public void setup() {
		ab = new AthleteBean();
	}

	@Test
	public void testIsSerializable() {
		assertTrue(ab instanceof java.io.Serializable);
	}

	@Test
	public void testDefault() {
		assertNull(ab.getNationalID());
		assertNull(ab.getFirstName());
		assertNull(ab.getLastName());
		assertNull(ab.getDateOfBirth());
		assertFalse(ab.isEligible());
		assertEquals(Athlete.UNSET_DATE, ab.getAge());
	}

	@Test
	public void testSetStrings() {
		ab.setNationalID("123AD");
		assertEquals("123AD", ab.getNationalID());
		ab.setFirstName("carol");
		assertEquals("carol", ab.getFirstName());
		ab.setLastName("Newbaur");
		assertEquals("Newbaur", ab.getLastName());
		Logger.getAnonymousLogger().info(ab.toString());
	}

	@Test
	public void testBrithDate() {
		ab.setDateOfBirth(LocalDate.parse("2000-01-14"));
		LocalDate date = LocalDate.parse("2000-01-14");
		assertEquals(date, ab.getDateOfBirth());

	}

	@Test
	public void testAge() {
		assertTrue(Athlete.OLYMPIC_DATE.isEqual(LocalDate.parse("2020-07-24")));

		assertNull(ab.getDateOfBirth());
		assertTrue(ab.getAge() == Athlete.UNSET_DATE);

		// Born the day after the olympics
		ab.setDateOfBirth(LocalDate.parse("2020-07-25"));
		assertTrue(ab.getAge() == Athlete.INVALID_DATE);

		ab.setDateOfBirth(LocalDate.parse("2000-01-14"));
		assertEquals(20, ab.getAge());

		// born the day of, 16 years ago
		ab.setDateOfBirth(LocalDate.parse("2004-07-24"));
		assertEquals(16, ab.getAge());
		// born the day before, 16 years ago
		ab.setDateOfBirth(LocalDate.parse("2004-07-23"));
		assertEquals(16, ab.getAge());
		// born the day after, 16 years go
		ab.setDateOfBirth(LocalDate.parse("2004-07-25"));
		assertEquals(15, ab.getAge());

	}

	@Test
	public void testIsElligible() {
		assertEquals(Athlete.OLYMPIC_DATE, (LocalDate.parse("2020-07-24")));
		assertEquals(16, Athlete.ELIGIBLE_AGE);

		// born the day of, 16 years ago
		ab.setDateOfBirth(LocalDate.parse("2004-07-24"));
		assertTrue(ab.isEligible());
		// born the day before, 16 years ago
		ab.setDateOfBirth(LocalDate.parse("2004-07-23"));
		assertTrue(ab.isEligible());
		// born the day after, 16 years go
		ab.setDateOfBirth(LocalDate.parse("2004-07-25"));
		assertFalse(ab.isEligible());
	}
}
