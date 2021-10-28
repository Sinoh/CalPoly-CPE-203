import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


class ExampleMap
{
    public static List<String> highEnrollmentStudents(
	Map<String, List<Course>> courseListsByStudentName, int unitThreshold)
    {
	List<String> overEnrolledStudents = new LinkedList<>();

	/*
	    Build a list of the names of students currently enrolled
	    in a number of units strictly greater than the unitThreshold.
	*/

	Set<String> studenNames = courseListsByStudentName.keySet();
	for (String name: studenNames ) {
        List<Course> courseList = courseListsByStudentName.get(name);
        int units = 0;
        for (Course course : courseList) {
            units = units + course.getNumUnits();
        }
        if (units > unitThreshold) {
            overEnrolledStudents.add(name);
        }

    }


	return overEnrolledStudents;	     
    }
}
