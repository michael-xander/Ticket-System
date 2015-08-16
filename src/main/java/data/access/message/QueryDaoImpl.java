package data.access.message;


import data.access.Dao;
import data.access.category.CategoryDao;
import data.access.category.CategoryDaoImpl;
import data.access.course.CourseDao;
import data.access.course.CourseDaoImpl;
import model.domain.message.Query;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by marcelo on 08-08-2015.
 */
public class QueryDaoImpl extends Dao implements QueryDao {

    private Logger logger = Logger.getLogger(QueryDaoImpl.class.getName());

    public QueryDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        super(dbUrl, dbUser, dbPassword);
    }

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

    private Query readQuery(Connection connection, ResultSet resultSet) throws SQLException
    {
        Query query = new Query();
        query.setMessageID(resultSet.getInt(1));
        query.setSender(resultSet.getString(2));
        query.setSubject(resultSet.getString(3));
        query.setText(resultSet.getString(4));
        //query.setDate(LocalDate.parse(resultSet.getString(5)));
        query.setDate(LocalDate.now());
        query.setCourseID(resultSet.getString(6));
        query.setCategoryID(resultSet.getInt(7));
        query.setStatus(Query.Status.valueOf(resultSet.getString(8)));
        query.setPrivacy(Query.Privacy.valueOf(resultSet.getString(9)));
        return query;
    }

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

    @Override
    public List<Query> getAllQueriesFromUser(String userID)
    {
        ArrayList<Query> queries = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Queries WHERE SenderID = ?");
            preparedStatement.setString(1, userID);
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
