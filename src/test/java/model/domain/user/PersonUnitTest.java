package model.domain.user;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * PersonUnitTest.java
 * A class that tests the functionality of the Person class
 * Created by Michael on 2015/08/07.
 */
public class PersonUnitTest
{
    @Test
    public void testPerson()
    {
        Person person = new Person("Michael", "Kyeyune", "michaelkyeyune@yahoo.com");

        assertEquals("Michael", person.getFirstName());
        assertEquals("Kyeyune", person.getLastName());
        assertEquals("michaelkyeyune@yahoo.com", person.getEmail());
    }

    @Test
    public void testPersonSetters()
    {
        Person person = new Person("Michael", "Kyeyune", "michaelkyeyune@yahoo.com");

        person.setFirstName("Marcelo");
        assertEquals("Marcelo", person.getFirstName());

        person.setLastName("Dauane");
        assertEquals("Dauane", person.getLastName());

        person.setEmail("marcelodauane@gmail.com");
        assertEquals("marcelodauane@gmail.com", person.getEmail());
    }
}
