package model.domain.message;


import java.io.Serializable;

/**
 * Created by william on 08-08-2015.
 */



public class Query extends Message implements Serializable {

    private String courseID;
    private String subject;
    private int categoryID;
    private String categoryName;
    public Status status;
    public Privacy privacy;



    public Query() {

    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {

        this.courseID = courseID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID){
        this.categoryID = categoryID;
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
