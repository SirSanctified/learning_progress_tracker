package tracker;


import java.util.*;

public class Main {
	private final LinkedHashMap<Integer, Student> students = new LinkedHashMap<>();
	private final Set<String> set = new HashSet<>();
	private Status state = Status.STARTUP;
	Scanner scanner = new Scanner(System.in);
	int[] points = new int[5];
	
	public static void main(String[] args) {
		Main tracker = new Main();
		tracker.run();
	}
	
	public void run() {
		System.out.println("Learning Progress Tracker");
		do {
			switch (this.state) {
				case STARTUP: 
				this.state = Status.MAIN_MENU;
				break;
				
				case MAIN_MENU: 
				this.state = mainMenu();
				break;
				
				case ADD_STUDENTS: 
				this.state = addStudents();
				break;
				
				case LIST: 
				this.state = list();
				break;
				
				case ADD_POINTS: 
				this.state = addPoints();
				break;
				
				case FIND: 
				this.state = find();
				break;
				
				case SHUTDOWN: 
				System.out.println("Bye!");
				break;
				
				default: 
				System.out.printf("Error: Program state %s not implemented!\n", this.state);
				this.state = Status.MAIN_MENU;
				break;
				
			}
		} while (this.state != Status.SHUTDOWN);
	}
	
	private Status mainMenu() {
		String action = scanner.nextLine().toLowerCase().trim();
		switch (action) {
			case "add students": 
			return Status.ADD_STUDENTS;
			
			case "exit": 
			System.out.println("Bye!");
			return Status.SHUTDOWN;
			
			case "back": 
			System.out.println("Enter 'exit' to exit the program");
			return Status.MAIN_MENU;
			
			case "list": 
			return Status.LIST;
			
			case "find": 
			return Status.FIND;
			
			case "add points": 
			System.out.println("Enter an id and points or 'back' to return:");
			return Status.ADD_POINTS;
			
			case "": 
			System.out.println("No input");
			return Status.MAIN_MENU;
			
			default: 
			System.out.println("Unknown command!");
			return Status.MAIN_MENU;
			
		}
	}
	
	private Status addStudents() {
		System.out.println("Enter student credentials or 'back' to return:");
		Scanner scanner = new Scanner(System.in);
		int id = 10000;
		String input = scanner.nextLine();
		while (!"back".equalsIgnoreCase(input)) {
			String[] parts = input.split("\\s");
			if (parts.length < 3) {
				System.out.println("Incorrect credentials");
			} else {
				try {
					String firstName = parts[0];
					String lastName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));
					String email = parts[parts.length - 1];
					Student student = Student.createStudent(firstName, lastName, email);
					if (!set.contains(email)) {
						this.students.putIfAbsent(id, student);
						System.out.println("The student has been added.");
						set.add(email);
						id++;
					} else {
						System.out.println("This email is already taken.");
					}
				} catch (IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
				}
			}
			input = scanner.nextLine();
		}
		System.out.printf("Total %d students have been added.%n", this.students.size());
		return Status.MAIN_MENU;
	}
	
	private Status list() {
		if (students.isEmpty()) {
			System.out.println("No students found");
		} else {
			System.out.println("Students:");
			for (Integer i : students.keySet()) {
				System.out.println(i);
			}
		}
		return Status.MAIN_MENU;
	}
	
	private Status addPoints() {
		String input = scanner.nextLine();
		while (!"back".equalsIgnoreCase(input)) {
			String[] tokens = input.split(" ");
			try {
				for (int i = 0; i < tokens.length; i++) {
					points[i] = Integer.parseInt(tokens[i]);
					if (points[i] < 0 || tokens.length < 4) {
						throw new NumberFormatException();
					}
				}
				if (students.containsKey(points[0])) {
					Student student = students.get(points[0]);
					for (int i = 0; i < student.courses.length; i++) {
						student.points.put(student.courses[i], points[i + 1]);
					}
					students.put(points[0], student);
					System.out.println("Points updated.");
				} else {
					System.out.printf("No student is found for id=%s.%n", points[0]);
				}
			} catch (Exception e) {
				System.out.println("Incorrect points format.");
			}
			input = scanner.nextLine();
		}
		return Status.MAIN_MENU;
	}
	
	private Status find() {
		int id;
		System.out.println("Enter an id or 'back' to return.");
		String input = scanner.nextLine();
		while (!"back".equalsIgnoreCase(input)) {
			id = Integer.parseInt(input);
			if (students.containsKey(id)) {
				Student student = students.get(id);
				System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d%n", id, student.points.get("Java"), student.points.get("DSA"), student.points.get("Databases"), student.points.get("Spring"));
			} else {
				System.out.printf("No student is found for id=%d.%n", id);
			}
			input = scanner.nextLine();
		}
		return Status.MAIN_MENU;
	}
}