package data.access.message;


import data.access.Dao;
import model.domain.message.Query;

import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * QueryDaoImpl.java
 * A DAO for the Query class.
 * Created by Marcelo on 2015/08/08.
 */

public class QueryDaoImpl extends Dao implements QueryDao, Serializable {

    private transient Logger logger = Logger.getLogger(QueryDaoImpl.class.getName());

    public QueryDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        super(dbUrl, dbUser, dbPassword);
    }

    /**
     * A method that returns the Query object from the database with given Query ID
     * @param queryID - ID of the Query to check for
     * @return Query object if exists in database of else null
     */
    @Override
    public Query getQuery(int queryID)
    {
        Query query = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Queries WHERE QueryID = ?");
            preparedStatement.setInt(1, queryID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                query = readQuery(connection, resultSet);
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
        return query;
    }

    /**
     * A method that returns the Query object read from the result set provided
     * @param connection - the connection between the solution and the data source
     * @param resultSet - an array with all the attributes of the query
     * @return Query object if exists in database of else null
     */
    private Query readQuery(Connection connection, ResultSet resultSet) throws SQLException
    {
        Query query = new Query();
        query.setMessageID(resultSet.getInt(1));
        query.setSender(resultSet.getString(2));
        query.setSubject(resultSet.getString(3));
        query.setText(resultSet.getString(4));
        //query.setDate(LocalDate.parse(resultSet.getString(5))); Commented out due to testing for prototype presentation
        query.setDate(LocalDate.now());
        query.setCourseID(resultSet.getString(6));
        query.setCategoryID(resultSet.getInt(7));
        query.setStatus(Query.Status.valueOf(resultSet.getString(8)));
        query.setPrivacy(Query.Privacy.valueOf(resultSet.getString(9)));
        return query;
    }

    /**
     * A method that returns all Queries in the database.
     * @return - List of all Queries
     */
    @Override
    public List<Query> getAllQueries()
    {
        ArrayList<Query> queries = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Queries");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Query query = readQuery(connection, resultSet);
                queries.add(query);
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

        return queries;
    }

    /**
     * Get all pending queries from the database for the given course ID
     * @param courseID the course whose queries are to be attained
     * @return list of pending queries
     */
    @Override
    public List<Query> getAllQueriesForCourse(String courseID, Query.Status status)
    {
        ArrayList<Query> queries = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Queries WHERE CourseID = ? AND QueryStatus = ?");
            preparedStatement.setString(1, courseID);
            preparedStatement.setString(2, status.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Query query = readQuery(connection, resultSet);
                queries.add(query);
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

        return queries;
    }

    /**
     * A method that returns all Queries from a certain user in the database.
     * @param userID - ID of the User to get the queries from
     * @param courseID - course ID for which queries should fall
     * @return - List of all Queries of that user
     */
    @Override
    public Set<Query> getAllQueriesForUser(String userID, String courseID)
    {
        LinkedHashSet<Query> queries = new LinkedHashSet<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            if(courseID == null) {
                preparedStatement = connection.prepareStatement("SELECT * FROM Queries WHERE SenderID = ? OR PrivacySetting = ?");
                preparedStatement.setString(1, userID);
                preparedStatement.setString(2, Query.Privacy.GENERAL.toString());
            }
            else
            {
                preparedStatement = connection.prepareStatement("SELECT * FROM Queries WHERE (SenderID = ? OR PrivacySetting = ?) AND CourseID = ?");
                preparedStatement.setString(1, userID);
                preparedStatement.setString(2, Query.Privacy.GENERAL.toString());
                preparedStatement.setString(3, courseID);
            }

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Query query = readQuery(connection, resultSet);
                queries.add(query);
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

        return queries;
    }

    /**
     * A method that updates the status of the given Query in that database
     * @param query - Query with new status
     */
    @Override
    public void updateQueryRole(Query query)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Queries SET QueryStatus = ? WHERE QueryID = ?");
            preparedStatement.setString(1, query.getStatus().toString());
            preparedStatement.setInt(2, query.getMessageID());
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
     * A method that adds provided Query to the database
     * @param query- Query to be added
     */
    @Override
    public void addQuery(Query query){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Queries (SenderID, Subject, Content, QueryDate, CourseID, CategoryID, QueryStatus, PrivacySetting) VALUES (?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, query.getSenderID());
            preparedStatement.setString(2, query.getSubject());
            preparedStatement.setString(3, query.getText());
            preparedStatement.setString(4, query.getDate().toString());
            preparedStatement.setString(5, query.getCourseID());
            preparedStatement.setInt(6, query.getCategoryID());
            preparedStatement.setString(7, query.getStatus().toString());
            preparedStatement.setString(8, query.getPrivacy().toString());
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
     * A method to delete a Query from the database
     * @param query - query be to deleted
     */
    @Override
    public void deleteQuery(Query query) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Queries WHERE QueryID = ?");
            preparedStatement.setInt(1, query.getMessageID());
            preparedStatement.executeUpdate();

            //removing the reply of the query from the query table
            ReplyDao replyDao = new ReplyDaoImpl(super.getDbUrl(), super.getDbUser(), super.getDbPassword());
            replyDao.deleteReply(query.getMessageID());

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

    /**
     * A method to delete a Query from the database
     * @param queryID - ID of the query to delete
     */
    @Override
    public void deleteQuery(int queryID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Query query = this.getQuery(queryID);
        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Queries WHERE QueryID = ?");
            preparedStatement.setInt(1, query.getMessageID());
            preparedStatement.executeUpdate();

            //removing the reply of the query from the query table
            ReplyDao replyDao = new ReplyDaoImpl(super.getDbUrl(), super.getDbUser(), super.getDbPassword());
            replyDao.deleteReply(query.getMessageID());

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
