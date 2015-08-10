package model.domain.message;

import model.domain.category.Category;
import model.domain.course.Course;

/**
 * Created by william on 08-08-2015.
 */



public class Query extends Message {

    private Course course;
    private String subject;
    private Category category;  // Category class exits somewhere
    public Status status;
    public Privacy privacy;



    public Query() {

    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {

        this.course = course;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    // set and get the status for the query a
    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    // set and get the subject of a query.
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject(){
        return subject;
    }

    public void setPrivacy(Privacy privacy) {
        this.privacy = privacy;
    }

    public Privacy getPrivacy() {
        return privacy;
    }

    public enum Status {
        OPENED,
        PENDING,
        VIEWED,
        REPLIED,
        ARCHIVED
    }

    public enum Privacy{
        GENERAL,
        PUBLIC,
        PRIVATE
    }
}
