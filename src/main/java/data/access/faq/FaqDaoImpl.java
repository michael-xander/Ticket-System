package data.access.faq;

import data.access.Dao;
import model.domain.faq.Faq;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FaqDaoImpl.java
 * A DAO for the Faq class.
 * @author Marcelo Dauane
 */
public class FaqDaoImpl extends Dao implements FaqDao, Serializable {

    private transient Logger logger = Logger.getLogger(FaqDaoImpl.class.getName());

    public FaqDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        super(dbUrl, dbUser, dbPassword);
    }

    /**
     * A method that returns the Faq object from the database with given Faq ID
     * @param faqID - ID of the Faq to check for
     * @return Faq object if exists in database of else null
     */
    @Override
    public Faq getFaq(int faqID)
    {
        Faq faq = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Faqs WHERE FaqID = ?");
            preparedStatement.setInt(1, faqID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                faq = readFaq(connection, resultSet);
            }
        }
        catch (SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch(SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return faq;
    }

    /**
     * A method that returns the Faq object read from the result set provided
     * @param connection - the connection between the solution and the data source
     * @param resultSet - an array with all the attributes of the faq
     * @return Faq object if exists in database of else null
     */
    private Faq readFaq(Connection connection, ResultSet resultSet) throws SQLException
    {
        Faq faq = new Faq();
        faq.setFaqID(resultSet.getInt(1));
        faq.setCourseID(resultSet.getString(2));
        faq.setQuestion(resultSet.getString(3));
        faq.setAnswer(resultSet.getString(4));
        faq.setDate(LocalDate.parse(resultSet.getString(5)));
        return faq;
    }

    /**
     * A method that returns all Faqs in the database.
     * @return - List of all Queries
     */
    @Override
    public List<Faq> getAllFaqs()
    {
        ArrayList<Faq> faqs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Faqs");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Faq faq = readFaq(connection, resultSet);
                faqs.add(faq);
            }

        }
        catch(SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch(SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        return faqs;
    }

    /**
     * Get all faqs from the database for the given course ID
     * @param courseID the course whose faqs are to be attained
     * @return list of faqs for the course
     */
    @Override
    public List<Faq> getAllFaqsForCourse(String courseID)
    {
        ArrayList<Faq> faqs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Faqs WHERE CourseID = ?");
            preparedStatement.setString(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Faq faq = readFaq(connection, resultSet);
                faqs.add(faq);
            }
        }
        catch(SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch(SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        return faqs;
    }

    /**
     * A method that updates the values of the given Faq in the database
     * @param faq - Faq with values to be updated
     */
    @Override
    public void updateFaq(Faq faq) {
        Connection connection  = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Faqs SET Question = ?, Answer = ?, FaqDate = ?  WHERE FaqID = ?");
            preparedStatement.setString(1, faq.getQuestion());
            preparedStatement.setString(2, faq.getAnswer());
            preparedStatement.setString(3, faq.getDate().toString());
            preparedStatement.setInt(4, faq.getFaqID());
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch(SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

    }

    /**
     * A method that adds provided Faq to the database
     * @param faq - Faq to be added
     */
    @Override
    public void addFaq(Faq faq){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Faqs (CourseID, Question, Answer, FaqDate) VALUES (?,?,?,?)");
            preparedStatement.setString(1, faq.getCourseID());
            preparedStatement.setString(2, faq.getQuestion());
            preparedStatement.setString(3, faq.getAnswer());
            preparedStatement.setString(4, faq.getDate().toString());
            preparedStatement.executeUpdate();

        }
        catch (SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    /**
     * A method to delete a Faq from the database
     * @param faqID - ID of the faq to delete
     */
    @Override
    public void deleteFaq(int faqID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Faqs WHERE FaqID = ?");
            preparedStatement.setInt(1, faqID);
            preparedStatement.executeUpdate();
        }
        catch(SQLException ex)
        {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        finally
        {
            try
            {
                if(preparedStatement != null)
                    preparedStatement.close();

                if(connection != null)
                    connection.close();
            }
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}
