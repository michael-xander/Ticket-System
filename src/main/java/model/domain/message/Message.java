package model.domain.message;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by william on 08-08-2015.
 */

public class Message implements Serializable,Comparable {

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

    @Override
    public int compareTo(Object object) {
        if(object == null || !(object instanceof Message))
            return 0;
        else
        {
            Message message = (Message) object;

            if(this.messageID < message.messageID)
                return -1;
            else if(this.messageID > message.messageID)
                return 1;
            else
                return 0;
        }
    }
}
