// Imports
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** 
* A class that gets input from the user and create a student roster.
* 
* @author  Joshua Borck
*/
public class StudentRoster {
	
	/** 
	* The main method that runs upon program execution.
	* 
	* @param args Extra arguments.
	* 
	*/
	public static void main(String[] args) {
		// Initialize properties
		Scanner scnr = new Scanner(System.in);
		LinkedList<Student> studentRoster = new LinkedList<Student>();
		boolean hasCompletedRoster = false;
		
		// Until the user is done filling the roster, allow them to keep creating students.
		while (!hasCompletedRoster) {
			// Create a student and get all needed properties
			Student student = new Student();
			student.addName(scnr);
			student.addAddress(scnr);
			student.addGPA(scnr);
			studentRoster.add(student);
			
			// Check if the user is done entering values
			hasCompletedRoster = checkIfCompleted(scnr);
		}
		
		// Attempt to save the student roster and output the result
		System.out.println(addRosterToFile(studentRoster, scnr));
		
		// Close the scanner
		scnr.close();
	}
	
	/** 
	* A static private method that checks if the user is done entering students.
	* 
	* @param scnr A Scanner to get value from user.
	* 
	* @return A boolean to indicate if the user is done entering students.
	* 
	*/
	private static boolean checkIfCompleted(Scanner scnr) {
		boolean hasValidInput = false;
		boolean rosterComplete = false;
		
		System.out.println("\nWould you like to enter another student?");
		System.out.println("Enter \"y\" for yes or \"n\" for no. If no, current student roster will be saved and the program will exit.");
		
		// While the response is not valid, try to get a valid response
		while (!hasValidInput) {
			try {
				// Get the user's input
				String input = scnr.nextLine().trim();
				
				if (input.equals("y")) {
					// User wants to enter another student and so return false for not being done
					rosterComplete = false;
					hasValidInput = true;
				} else if (input.equals("n")) {
					// User does not want to enter another student and so return true for being done
					rosterComplete = true;
					hasValidInput = true;
				} else {
					// Invalid response so throw an error
					throw new InvalidResponseValue();
				}
			} catch (NoSuchElementException | IllegalStateException | InvalidResponseValue e) {
				// Inform the user of an invalid response and have them try again
				System.out.println();
				System.out.println("Invalid yes or no response. Please try again:");
			}
		}
		
		System.out.println();
		
		return rosterComplete;
	}
	
	/** 
	* A static private method that builds the roster's output string.
	* 
	* @param studentRoster The current student roster.
	* @param scnr A Scanner to get value from user.
	* 
	* @return A string value of the entire roster list.
	* 
	*/
	private static String buildFullRosterString(LinkedList<Student> studentRoster, Scanner scnr) {
		String output = "Student Roster\n";
		output = output +"------------------------------------------------------------\n\n";
		
		// If the list is empty, return the empty roster value
		if (studentRoster.size() == 0) {
			return output + "No students in the roster.";
		}
		
		try {
			// Try to sort the student roster by name
			Collections.sort(studentRoster, new StudentNameComparator());
		} catch (ClassCastException | UnsupportedOperationException | IllegalArgumentException e) {
			// List sorting failed. Inform the user and exit the program.
			System.out.println("Failed to sort the student roster. Exiting program.");
			scnr.close();
			System.exit(0);
		}
		
		// For each student, add their info to the output
		for(Student student: studentRoster) {
			output = output + student.info() + "\n";
		}
		
		// Return the output
		return output;
	}
	
	/** 
	* A private method to add the full inventory to a text file.
	* 
	* @param studentRoster The current student roster.
	* @param scnr A Scanner to get value from user.
	* 
	* @return A success or failure message.
	* 
	*/
	private static String addRosterToFile(LinkedList<Student> studentRoster, Scanner scnr) {
		String output = "";
		FileOutputStream outputStream = null;
		PrintWriter fileWriter = null;
		
		System.out.println();
		System.out.println("Saving Student Roster to studentRoster.txt");
		System.out.println();
		
		try {
			// Try to open/create the file and input the student roster to it.
			outputStream = new FileOutputStream("studentRoster.txt");
			fileWriter = new PrintWriter(outputStream);
			String rosterOutput = buildFullRosterString(studentRoster, scnr);
			fileWriter.println(rosterOutput);
			output = "Successfully saved student roster.";
		
		// Handle all the errors and update output.
		} catch (FileNotFoundException e) {
			output = "Failed find or create file.";
		} catch (SecurityException e) {
			output = "No write permissions.";
		} finally {
			// Close the file.
			fileWriter.close();
			try {
				// Attempt to close the stream
				outputStream.close();
			} catch (IOException e) {
				output = "Failed to close the output stream";
			}
		}
		
		// Return output.
		return output;
	}
}
