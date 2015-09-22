package model.domain.message;


import java.io.Serializable;

/**
 * Created by william on 08-08-2015.
 */



public class Query extends Message implements Serializable {

    private String courseID;
    private String subject;
    private String categoryName;
    private Status status;
    private Privacy privacy;
    private boolean forwarded;

    public boolean isForwarded()
    {
        return forwarded;
    }

    public void setForwarded(boolean forwarded)
    {
        this.forwarded = forwarded;
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
        PENDING,
        REPLIED
    }

    public enum Privacy{
        PUBLIC,
        ADMINISTRATOR,
        CONVENER
    }
}
