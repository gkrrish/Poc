package com.poc.entity;

import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USER_DETAILS")
public class UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userid;

	@Column(name = "mobilenumber", nullable = false, length = 13)
	private String mobileNumber;

	@Column(name = "username", nullable = false, length = 50)
	private String username;

	@Column(name = "age")
	private Integer age;

	@Column(name = "gender", nullable = false, length = 10)
	private String gender;

	@Column(name = "location", length = 100)
	private String location;

	@Column(name = "registrationdate")
	private Timestamp registrationDate;

	@Column(name = "active", nullable = false, length = 1)
	private Character active;

	public UserDetails() {
	}

	public UserDetails(Long userid, String mobileNumber, String username, Integer age, String gender, String location,
			Timestamp registrationDate, Character active) {
		this.userid = userid;
		this.mobileNumber = mobileNumber;
		this.username = username;
		this.age = age;
		this.gender = gender;
		this.location = location;
		this.registrationDate = registrationDate;
		this.active = active;
	}

	public Long getUserid() {
		return userid;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getUsername() {
		return username;
	}

	public Integer getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public String getLocation() {
		return location;
	}

	public Timestamp getRegistrationDate() {
		return registrationDate;
	}

	public Character getActive() {
		return active;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRegistrationDate(Timestamp registrationDate) {
		this.registrationDate = registrationDate;
	}

	public void setActive(Character active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, age, gender, location, mobileNumber, registrationDate, userid, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetails other = (UserDetails) obj;
		return Objects.equals(active, other.active) && Objects.equals(age, other.age)
				&& Objects.equals(gender, other.gender) && Objects.equals(location, other.location)
				&& Objects.equals(mobileNumber, other.mobileNumber)
				&& Objects.equals(registrationDate, other.registrationDate) && Objects.equals(userid, other.userid)
				&& Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UserDetails [userid=" + userid + ", mobileNumber=" + mobileNumber + ", username=" + username + ", age="
				+ age + ", gender=" + gender + ", location=" + location + ", registrationDate=" + registrationDate
				+ ", active=" + active + "]";
	}

}
