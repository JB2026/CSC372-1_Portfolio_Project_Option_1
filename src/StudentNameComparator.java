//Imports
import java.util.Comparator;

/** 
* A class the implements comparator interface and allows the Student class to be sorted by name.
* 
* @author  Joshua Borck
*/
public class StudentNameComparator implements Comparator<Student> {
	@Override
	public int compare(Student lhs, Student rhs) {
		// Compare the students name
		return lhs.getName().compareTo(rhs.getName());
	}
}