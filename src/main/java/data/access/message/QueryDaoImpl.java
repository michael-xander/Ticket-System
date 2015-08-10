package data.access.message;


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
public class QueryDaoImpl implements QueryDao {

    private Logger logger = Logger.getLogger(QueryDaoImpl.class.getName());

    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public QueryDaoImpl(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    @Override
    public Query getQuery(int queryID)
    {
        Query query = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM Queries WHERE QueryID = ?");
            preparedStatement.setInt(1, queryID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                CourseDao courseDao = new CourseDaoImpl(dbUrl,dbUser,dbPassword);
                CategoryDao categoryDao = new CategoryDaoImpl(dbUrl,dbUser,dbPassword);
                query = new Query();
                query.setMessageID(resultSet.getInt("messageID"));
                query.setSender(resultSet.getString("sender"));
                query.setText(resultSet.getString("text"));
                query.setDate(LocalDate.parse(resultSet.getString("date")));
                query.setSubject(resultSet.getString("subject"));
                query.setCourse(courseDao.getCourse(resultSet.getString("courseID")));
                query.setCategory(categoryDao.getCategory(resultSet.getInt("categoryID")));
                //set the status of the query
                switch(resultSet.getString("status")){
                    case "OPENED":
                        query.setStatus(Query.Status.OPENED);
                        break;
                    case "PENDING":
                        query.setStatus(Query.Status.PENDING);
                        break;
                    case "VIEWED":
                        query.setStatus(Query.Status.VIEWED);
                        break;
                    case "REPLIED":
                        query.setStatus(Query.Status.REPLIED);
                        break;
                    case "ARCHIVED":
                        query.setStatus(Query.Status.ARCHIVED);
                        break;
                }

                //get the privacy of the query
                switch(resultSet.getString("privacy")){
                    case "General":
                        query.setPrivacy(Query.Privacy.GENERAL);
                        break;
                    case "Public":
                        query.setPrivacy(Query.Privacy.PUBLIC);
                        break;
                    case "Private":
                        query.setPrivacy(Query.Privacy.PRIVATE);
                        break;
                }


                preparedStatement.clearParameters();
                preparedStatement.close();
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

    @Override
         public List<Query> getAllQueries()
    {
        ArrayList<Query> queries = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM Queries");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Query query = new Query();
                String[] tempArr = resultSet.getString(1).split(" ");

                CourseDao courseDao = new CourseDaoImpl(dbUrl,dbUser,dbPassword);
                CategoryDao categoryDao = new CategoryDaoImpl(dbUrl,dbUser,dbPassword);
                query = new Query();
                query.setMessageID(resultSet.getInt("messageID"));
                query.setSender(resultSet.getString("sender"));
                query.setText(resultSet.getString("text"));
                query.setDate(LocalDate.parse(resultSet.getString("date")));
                query.setSubject(resultSet.getString("subject"));
                query.setCourse(courseDao.getCourse(resultSet.getString("courseID")));
                query.setCategory(categoryDao.getCategory(resultSet.getInt("categoryID")));

                //get the status of the query
                switch(resultSet.getString("status")){
                    case "OPENED":
                        query.setStatus(Query.Status.OPENED);
                        break;
                    case "PENDING":
                        query.setStatus(Query.Status.PENDING);
                        break;
                    case "VIEWED":
                        query.setStatus(Query.Status.VIEWED);
                        break;
                    case "REPLIED":
                        query.setStatus(Query.Status.REPLIED);
                        break;
                    case "ARCHIVED":
                        query.setStatus(Query.Status.ARCHIVED);
                        break;
                }

                //get the privacy of the query
                switch(resultSet.getString("privacy")){
                    case "General":
                        query.setPrivacy(Query.Privacy.GENERAL);
                        break;
                    case "Public":
                        query.setPrivacy(Query.Privacy.PUBLIC);
                        break;
                    case "Private":
                        query.setPrivacy(Query.Privacy.PRIVATE);
                        break;
                }
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
    public List<Query> getAllQueriesFromUser(int userID)
    {
        ArrayList<Query> queries = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("SELECT * FROM Queries WHERE UserID = ?");
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Query query = new Query();
                String[] tempArr = resultSet.getString(1).split(" ");

                CourseDao courseDao = new CourseDaoImpl(dbUrl,dbUser,dbPassword);
                CategoryDao categoryDao = new CategoryDaoImpl(dbUrl,dbUser,dbPassword);
                query = new Query();
                query.setMessageID(resultSet.getInt("messageID"));
                query.setSender(resultSet.getString("sender"));
                query.setText(resultSet.getString("text"));
                query.setDate(LocalDate.parse(resultSet.getString("date")));
                query.setSubject(resultSet.getString("subject"));
                query.setCourse(courseDao.getCourse(resultSet.getString("courseID")));
                query.setCategory(categoryDao.getCategory(resultSet.getInt("categoryID")));
                //set the status of the query
                switch(resultSet.getString("status")){
                    case "OPENED":
                        query.setStatus(Query.Status.OPENED);
                        break;
                    case "PENDING":
                        query.setStatus(Query.Status.PENDING);
                        break;
                    case "VIEWED":
                        query.setStatus(Query.Status.VIEWED);
                        break;
                    case "REPLIED":
                        query.setStatus(Query.Status.REPLIED);
                        break;
                    case "ARCHIVED":
                        query.setStatus(Query.Status.ARCHIVED);
                        break;
                }
                switch(resultSet.getString("privacy")){
                    case "General":
                        query.setPrivacy(Query.Privacy.GENERAL);
                        break;
                    case "Public":
                        query.setPrivacy(Query.Privacy.PUBLIC);
                        break;
                    case "Private":
                        query.setPrivacy(Query.Privacy.PRIVATE);
                        break;
                }
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
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("INSERT INTO Queries (MessageID, CourseID, CategoryID, Sender, Text, Subject, Date, Status, Privacy ) VALUES (?,?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, query.getMessageID());
            preparedStatement.setString(2, query.getCourse().getCourseID());
            preparedStatement.setInt(3, query.getCategory().getCategoryID());
            preparedStatement.setString(4, query.getSender());
            preparedStatement.setString(5, query.getText());
            preparedStatement.setString(6, query.getDate().toString());
            preparedStatement.setString(7, query.getSubject());
            preparedStatement.setString(8, query.getStatus().name());
            preparedStatement.setString(9, query.getPrivacy().name());
            preparedStatement.executeUpdate();

            preparedStatement.clearParameters();
            preparedStatement.close();
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
            connection = DriverManager.getConnection(dbUrl,dbUser, dbPassword);
            preparedStatement = connection.prepareStatement("DELETE FROM Queries WHERE QueryID = ?");
            preparedStatement.setInt(1, query.getMessageID());
            preparedStatement.executeUpdate();

            preparedStatement.clearParameters();
            preparedStatement.close();
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
