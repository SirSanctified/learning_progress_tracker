package tracker;

import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class Student {
	private final String firstName;
	private final String lastName;
	private final String email;
	private int id;
	Map<String, Integer> points = new HashMap<>();
	String[] courses = {"Java", "DSA", "Databases", "Spring"};

	private Student(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public static Student createStudent(String firstName, String lastName, String email) {
		if (!validateName(firstName)) throw new IllegalArgumentException("Incorrect first name");
		if (!validateName(lastName)) throw new IllegalArgumentException("Incorrect last name");
		if (!validateEmail(email)) throw new IllegalArgumentException("Incorrect email");
		return new Student(firstName, lastName, email);
	}

	private static boolean validateName(String name) {
		if (name == null) return false;
		Pattern regex = Pattern.compile("^[a-z]((?<=[-'])[a-z ]|(?<![-'])[-'a-z ])*[a-z]$", Pattern.CASE_INSENSITIVE);
		return regex.matcher(name).matches();
	}

	private static boolean validateEmail(String email) {
		if (email == null) return false;
		Pattern regex = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");
		return regex.matcher(email).matches();
	}
}
