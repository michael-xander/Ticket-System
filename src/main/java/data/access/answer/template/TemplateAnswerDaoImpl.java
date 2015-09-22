package data.access.answer.template;

import data.access.Dao;
import model.domain.answer.template.TemplateAnswer;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that implements the TemplateAnswerDAO interface
 * Created by Michael on 2015/09/20.
 */
public class TemplateAnswerDaoImpl extends Dao implements TemplateAnswerDAO, Serializable{

    private transient Logger logger = Logger.getLogger(TemplateAnswerDaoImpl.class.getName());

    public TemplateAnswerDaoImpl(String dbUrl, String dbUser, String dbPassword) {
        super(dbUrl, dbUser, dbPassword);
    }

    /*
     * Returns a template answer read from the resultset
     */
    private TemplateAnswer readTemplateAnswer(Connection connection, ResultSet resultSet) throws SQLException
    {
        TemplateAnswer templateAnswer = new TemplateAnswer();
        templateAnswer.setID(resultSet.getInt(1));
        templateAnswer.setUser(resultSet.getString(2));
        templateAnswer.setQuestion(resultSet.getString(3));
        templateAnswer.setAnswer(resultSet.getString(4));
        return templateAnswer;
    }

    /**
     * Read a template answer with the given ID from the database
     * @param ID - of the template answer to search for
     * @return - the template answer or null if not found
     */
    @Override
    public TemplateAnswer getTemplateAnswer(int ID) {
        TemplateAnswer templateAnswer = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection  = super.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Template_Answers WHERE ID = ?");
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
                templateAnswer = readTemplateAnswer(connection, resultSet);
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
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return templateAnswer;
    }

    /**
     * Add the template answer to the database
     * @param templateAnswer - template answer to add to the database
     */
    @Override
    public void addTemplateAnswer(TemplateAnswer templateAnswer)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Template_Answers (UserID, Question, Answer) VALUES (?,?,?)");
            preparedStatement.setString(1, templateAnswer.getUser());
            preparedStatement.setString(2, templateAnswer.getQuestion());
            preparedStatement.setString(3, templateAnswer.getAnswer());
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
     * Update the provided template answer in the database
     * @param templateAnswer - template answer to be updated
     */
    @Override
    public void updateTemplateAnswer(TemplateAnswer templateAnswer)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Template_Answers SET Question = ?, Answer = ? WHERE ID = ?");
            preparedStatement.setString(1, templateAnswer.getQuestion());
            preparedStatement.setString(2, templateAnswer.getAnswer());
            preparedStatement.setInt(3, templateAnswer.getID());

            preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
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
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }

    /**
     * Gets the list of template answers created by the user with given ID
     * @param userID - ID of the user
     * @return the list of template answers by the user with given ID
     */
    @Override
    public List<TemplateAnswer> getTemplateAnswersForUser(String userID) {
        ArrayList<TemplateAnswer> templates = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Template_Answers WHERE UserID = ?");
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                TemplateAnswer templateAnswer = readTemplateAnswer(connection, resultSet);
                templates.add(templateAnswer);
            }

        }
        catch (SQLException ex)
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
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return templates;
    }

    /**
     * Deletes the given template answer from the database
     * @param templateAnswer - template answer to be deleted
     */
    @Override
    public void deleteTemplateAnswer(TemplateAnswer templateAnswer)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Template_Answers WHERE ID = ?");
            preparedStatement.setInt(1, templateAnswer.getID());
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex)
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
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}
