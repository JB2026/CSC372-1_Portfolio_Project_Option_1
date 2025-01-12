// Imports
import java.util.IllegalFormatException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** 
* A class that represents a Student object.
* 
* @author  Joshua Borck
*/
public class Student {
	// Class properties
	private String name;
	private String address;
	private double GPA;
	
	/** 
	* A default constructor that sets a default value for all class properties.
	* 
	* @return An instance of an Student with all default properties.
	* 
	*/
	public Student() {
		this.name = "";
		this.address = "";
		this.GPA = -1;
	}
	
	/** 
	* A parameterized constructor that sets all class properties to the parameters passed in.
	* 
	* @param name The name of the student.
	* @param address The current address of the student.
	* @param gpa The current GPA of the student.
	* 
	* @return An instance of an Student with all properties set to what was passed in.
	* 
	*/
	public Student(String name, String address, double gpa) {
		this.name = name;
		this.address = address;
		this.GPA = gpa;
	}
	
	/** 
	* A public method to get the student's name.
	* 
	* @return The student's name.
	* 
	*/
	public String getName() {
		return this.name;
	}
	
	/** 
	* A public method to get the student's address.
	* 
	* @return The student's address.
	* 
	*/
	public String getAddress() {
		return this.address;
	}
	
	/** 
	* A public method to get the student's GPA.
	* 
	* @return The student's GPA.
	* 
	*/
	public double getGPA() {
		return this.GPA;
	}
	
	/** 
	* A public method to set the student's name.
	*/
	public void setName(String name) {
		this.name = name;
	}
	
	/**a
	* A public method to set the student's address.
	*/
	public void setAddress(String address) {
		this.address = address;
	}
	
	/** 
	* A public method to set the student's GPA.
	*/
	public void setGPA(double GPA) {
		this.GPA = GPA;
	}
	
	/** 
	* A public method to set the student's name by getting input from the user.
	* 
	* @param scnr A Scanner to get value from user.
	* 
	*/
	public void addName(Scanner scnr) {
		System.out.println("Please enter the students full name:");
		this.name = getValidStringValue(scnr, "Invalid name entered. Please try again:\n");
		System.out.println();
	}
	
	/** 
	* A public method to set the student's address by getting input from the user.
	* 
	* @param scnr A Scanner to get value from user.
	* 
	*/
	public void addAddress(Scanner scnr) {
		System.out.println("Please enter the students address:");
		this.address = getValidStringValue(scnr, "Invalid address entered. Please try again:\n");
		System.out.println();
	}
	
	/** 
	* A public method to add the student's GPA by requesting the valid information from the user.
	* 
	* @param scnr A Scanner to get value from user.
	* 
	*/
	public void addGPA(Scanner scnr) {
		boolean hasValidGPA = false;
		
		// Ask the user for the GPA
		System.out.println("Please enter the students GPA. This can be a decimal or whole number:");
		
		// Keep getting input for the GPA until valid
		while (!hasValidGPA) {
			try {
				String input = scnr.nextLine().trim();
				
				// If GPA is empty, throw error
				if (input.equals("")) {
					throw new InvalidGPAValue();
				}
				
				// Attempt to convert input into a double
				double gpa = Double.parseDouble(input);
				
				// Validate the GPA value
				if (gpa > 0 && gpa <= 4) {
					this.GPA = gpa;
					hasValidGPA = true;
				} else if (gpa == 0 && correctValue("\nGPA entered is zero, do you want to keep this value?", scnr)) {
					this.GPA = gpa;
					hasValidGPA = true;
				} else if (gpa > 4 && correctValue("\nGPA entered is greater than 4.0 which is often the highest GPA unless on an adjusted scale, do you want to keep this value?", scnr)) {
					this.GPA = gpa;
					hasValidGPA = true;
				} else {
					// GPA is invalid and throw an error
					throw new InvalidGPAValue();
				}
			} catch (NoSuchElementException | IllegalStateException | NullPointerException | NumberFormatException | InvalidGPAValue e) {
				System.out.println("GPA value is not valid. Please try again:");
			}
		}
	}
	
	/** 
	* A public method to create an output string of the student's info.
	* 
	* @return Student's info as a string if a valid student or "Invalid Student" if the student is not set up or an error occurs.
	* 
	*/
	public String info() {
		String output = "";
		String invalidStudentOutput = "Invalid Student";
		
		if (isDefaultStudent()) {
			// The student's information was never updated and so set the invalid student as the output
			output = invalidStudentOutput;
		} else {
			try {
				output = output + String.format("Student Name: %s\n", this.name);
				output = output + String.format("Student Address: %s\n", this.address);
				output = output + String.format("Student Name: %.1f\n", this.GPA);
			} catch (IllegalFormatException e) {
				// Invalid formatting and so set output to the invalid student output
				output = invalidStudentOutput;
			}
		}
		
		// Return the output
		return output;
	}
	
	/** 
	* A private method that checks if the student has been set up.
	* 
	* @return A boolean indicating if the student object has been set up.
	* 
	*/
	private boolean isDefaultStudent() {
		return this.name.equals("") && this.address.equals("") && this.GPA == -1;
	}
	
	/** 
	* A private method to add the student's GPA by requesting the valid information from the user.
	* 
	* @param scnr A Scanner to get value from user.
	* 
	* @return A boolean indicating if the value entered is what the user wants to keep.
	* 
	*/
	private boolean correctValue(String output, Scanner scnr) {
		boolean hasValidInput = false;
		boolean keepValue = false;
		
		System.out.println(output);
		System.out.println("Enter \"y\" for yes or \"n\" for no.");
		
		// Check if the user wants to keep the value that may have been entered incorrectly.
		while (!hasValidInput) {
			try {
				String input = scnr.nextLine().trim();
				
				if (input.equals("y")) {
					// User wants to keep the value
					keepValue = true;
					hasValidInput = true;
				} else if (input.equals("n")) {
					// User entered invalid value
					keepValue = false;
					hasValidInput = true;
				} else {
					// Invalid response so throw an error
					throw new InvalidResponseValue();
				}
			} catch (NoSuchElementException | IllegalStateException | InvalidResponseValue e) {
				// Inform the user that they entered an incorrect response and should try again
				System.out.println();
				System.out.println("Invalid yes or no response. Please try again:");
			}
		}
		
		System.out.println();
		
		// Return if the value should be kept
		return keepValue;
	}
	
	/** 
	* A private method to get a valid string from the user.
	* 
	* @param scnr A Scanner to get value from user.
	* @param retryOutput The output when the string value is invalid.
	* 
	* @return A valid string value.
	* 
	*/
	private String getValidStringValue(Scanner scnr, String retryOutput) {
		boolean hasValidString = false;
		String output = "";
		
		// Keep trying to get a valid string value
		while (!hasValidString) {
			try {
				String input = scnr.nextLine().trim();
				
				// If the string is empty, throw an error
				if (input.equals("")) {
					throw new InvalidStringValue();
				}
				
				output = input;
				hasValidString = true;
			} catch (NoSuchElementException | IllegalStateException | InvalidStringValue e) {
				// Failed to get string value and use the retry output to get another from the user.
				System.out.print(retryOutput);
			}
		}
		
		// Return the output
		return output;
	}
}