package model.domain.message;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A class that contains unit tests for the Message class
 * Created by Michael on 2015/08/08.
 */
public class MessageUnitTest
{
    /**
     * A method to test the equals method for the Message class
     */
    @Test
    public void messageEqualsTest()
    {
        Message message1 = new Message();
        message1.setMessageID(1);

        assertFalse(message1.equals(null));
        assertFalse(message1.equals(new Object()));

        Message message2 = new Message();
        message2.setMessageID(2);

        assertFalse(message1.equals(message2));

        Message message3 = new Message();

        assertFalse(message1.equals(message3));

        Message message4 = new Message();
        message4.setMessageID(1);
        assertTrue(message1.equals(message4));
    }

    /**
     * A method to test the compareTo method for the Message class
     */
    @Test
    public void messageCompareToTest()
    {
        Message message1 = new Message();
        message1.setMessageID(1);

        Message message2 = new Message();
        message2.setMessageID(1);
        assertTrue(message1.compareTo(message2) == 0);

        Message message3 = new Message();
        message3.setMessageID(2);
        assertTrue(message1.compareTo(message3) < 0);
        assertTrue(message3.compareTo(message1) > 0);
    }
}
