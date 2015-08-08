package data.access;

import java.time.LocalDate;

/**
 * Created by william on 08-08-2015.
 */

public class Message {

    //variables
    private int messageID;
    private String sender;
    private String text;
    private LocalDate date;

    // empty constructor
    public Message() {

    }

    // method to set the time.. this will be done when message is sent or submitted
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }



    public void setText(String text) {
        this.text = text;
    }

    // all the get methods
    public int getMessageID(){
        return messageID;
    }

    public String getSender(){
        return sender;
    }

    public String getText(){
        return text;
    }

    public LocalDate getDate(){
        return date;
    }
}
