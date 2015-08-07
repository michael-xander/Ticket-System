package model.domain.user;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * UserUnitTest.java
 * A class that contains unit tests for the User class
 * Created by Michael on 2015/08/07.
 */
public class UserUnitTest
{
    /**
     * A method to test the equals method for the User class
     */
    @Test
    public void userEqualsTest()
    {
        User user1 = new User();
        user1.setUserID("KYYMIC001");

        assertFalse(user1.equals(null));
        assertFalse(user1.equals(new Object()));

        User user2 = new User();
        user2.setUserID("LMLWIL001");

        assertFalse(user1.equals(user2));

        User user3 = new User();
        user3.setUserID("KYYMIC001");

        assertTrue(user1.equals(user3));
    }
}
