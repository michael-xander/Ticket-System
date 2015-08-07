package model.domain.user;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * CourseUnitTest.java
 * A class to test the funct
 * Created by Michael on 2015/08/07.
 */
public class CourseUnitTest
{
    /**
     * A method to test the equals method for the Course class
     */
    @Test
    public void courseEqualsTest()
    {
        Course course1 = new Course();
        course1.setCourseCode("CSC3002S");
        course1.setCourseYear(2015);

        assertFalse(course1.equals(null));
        assertFalse(course1.equals(new Object()));

        Course course2 = new Course();
        course2.setCourseCode("CSC3002S");
        course2.setCourseYear(2014);

        assertFalse(course1.equals(course2));

        Course course3 = new Course();
        course3.setCourseCode("INF3002F");
        course3.setCourseYear(2015);

        assertFalse(course1.equals(course3));

        Course course4  = new Course();
        course4.setCourseCode("CSC3002S");
        course4.setCourseYear(2015);

        assertTrue(course1.equals(course4));
    }
}
