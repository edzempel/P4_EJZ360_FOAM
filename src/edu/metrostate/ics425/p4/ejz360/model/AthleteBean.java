package edu.metrostate.ics425.p4.ejz360.model;

import java.time.LocalDate;

import edu.metrostate.ics425.foam.model.Athlete;

/**
 * AthleteBean class from assignment P2
 * 
 * @author Ed Zempel
 *
 */
public class AthleteBean implements Athlete {

	private static final long serialVersionUID = 1L;
	private String nationalID;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	public AthleteBean() {
		super();
	}

	/**
	 * Set the national ID
	 * 
	 * @param newID String
	 */
	public void setNationalID(String newID) {
		this.nationalID = newID;
	}

	/**
	 * Set the first name
	 * 
	 * @param newFirst String
	 */
	public void setFirstName(String newFirst) {
		this.firstName = newFirst;
	}

	/**
	 * Set the last name
	 * 
	 * @param newLast String
	 */
	public void setLastName(String newLast) {
		this.lastName = newLast;
	}

	/**
	 * Set the date of birth of the athlete
	 * 
	 * @param newDob LocalDate athletete's date of birth
	 */
	public void setDateOfBirth(LocalDate newDob) {
		this.dateOfBirth = newDob;
	}

	@Override
	public int getAge() {
		int ageOnOlympicDate = UNSET_DATE;
		if (this.dateOfBirth != null) {
			ageOnOlympicDate = OLYMPIC_DATE.isBefore(dateOfBirth) ? INVALID_DATE
					: this.dateOfBirth.until(OLYMPIC_DATE).getYears();
		}

		return ageOnOlympicDate;
	}

	@Override
	public LocalDate getDateOfBirth() {
		return this.dateOfBirth;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return this.lastName;
	}

	@Override
	public String getNationalID() {
		return this.nationalID;
	}

	@Override
	public boolean isEligible() {
		return this.getAge() >= Athlete.ELIGIBLE_AGE;
	}

	@Override
	public String toString() {
		return "AthleteBean [nationalID=" + nationalID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", dateOfBirth=" + dateOfBirth + ", getAge()=" + getAge() + ", isEligible()=" + isEligible() + "]";
	}

}
