package data.access;

import model.domain.user.Course;

import java.util.List;

/**
 * CourseDao.java
 * An interface for the Course
 * Created by Michael on 2015/08/08.
 */
public interface CourseDao {

    Course getCourse(String courseID);

    List<Course> getAllCourses();

    void updateCourse(Course course);

    void addCourse(Course course);

    void deleteCourse(Course course);
}
