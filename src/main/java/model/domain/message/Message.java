package model.domain.message;

import java.time.LocalDate;

/**
 * Created by william on 08-08-2015.
 */

public class Message {

    //variables
    private int messageID;
    private String senderID;
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

    public void setSender(String senderID) {
        this.senderID = senderID;
    }



    public void setText(String text) {
        this.text = text;
    }

    // all the get methods
    public int getMessageID(){
        return messageID;
    }

    public String getSenderID(){
        return senderID;
    }

    public String getText(){
        return text;
    }

    public LocalDate getDate(){
        return date;
    }

    @Override
    public boolean equals(Object object)
    {
        if(object == null || !object.getClass().equals(this.getClass()))
            return false;
        else
        {
            Message message = (Message) object;

            return (this.messageID == message.messageID);
        }
    }
}
