package model.domain.faq;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Faq.java
 * A class that represents a frequently asked question in the Ticket System.
 *
 * @author Marcelo Dauane
 */
public class Faq implements Serializable{

    private int faqID;
    private String courseID;
    private String question;
    private String answer;
    private LocalDate date;

    public void setFaqID(int faqID) {
        this.faqID = faqID;
    }

    public int getFaqID(){
        return faqID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseID(){
        return courseID;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion(){
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer(){
        return answer;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }
}
