package model.domain.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Course.java
 * A class that represents a course in the Ticket System class
 * Created by Michael on 2015/08/07.
 */
public class Course
{

    private String courseCode;
    private int courseYear;
    private ArrayList<User> teachingAssistants;
    private TreeSet<LocalDate> testDates;
    private TreeSet<LocalDate> assignmentDueDates;
    private User courseConvener;
    private String description;

    public TreeSet<LocalDate> getTestDates()
    { return testDates;}

    public void setTestDates(TreeSet<LocalDate> testDates)
    {this.testDates = testDates;}

    public void addTestDate(LocalDate testDate)
    {testDates.add(testDate);}

    public TreeSet<LocalDate> getAssignmentDueDates()
    {return assignmentDueDates;}

    public void setAssignmentDueDates(TreeSet<LocalDate> assignmentDueDates)
    {this.assignmentDueDates = assignmentDueDates;}

    public void addAssignmentDueDate(LocalDate assignmentDueDate)
    {assignmentDueDates.add(assignmentDueDate);}

    public String getDescription()
    { return description;}

    public void setDescription(String description)
    { this.description = description;}

    public User getCourseConvener() {
        return courseConvener;
    }

    public void setCourseConvener(User courseConvener) {
        this.courseConvener = courseConvener;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(int courseYear) {
        this.courseYear = courseYear;
    }

    public ArrayList<User> getTeachingAssistants() {
        return teachingAssistants;
    }

    public void setTeachingAssistants(ArrayList<User> teachingAssistants) {
        this.teachingAssistants = teachingAssistants;
    }

    public void addTeachingAssistant(User teachingAssistant)
    { teachingAssistants.add(teachingAssistant);}

    public int getNumberOfTeachingAssistants()
    { return teachingAssistants.size();}

    public String getCourseID()
    { return courseCode + " " + courseYear;}

    @Override
    public boolean equals(Object object)
    {
        if((object == null) || !(object.getClass().equals(this.getClass())))
            return false;
        else
        {
            Course course = (Course) object;

            if((course.courseCode == null) || (this.courseCode == null) ||
                    !course.getCourseID().equals(this.getCourseID()))
                return false;
            else
                return true;
        }
    }

}
