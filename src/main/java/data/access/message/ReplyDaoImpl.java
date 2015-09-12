package data.access.message;

import data.access.Dao;
import model.domain.message.Message;
import model.domain.message.Reply;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ReplyDaoImpl.java
 * A DAO for the Reply class.
 * Created by Michael on 2015/08/11.
 */

public class ReplyDaoImpl extends Dao implements ReplyDao, Serializable
{
    private transient Logger logger = Logger.getLogger(ReplyDaoImpl.class.getName());

    public ReplyDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        super(dbUrl, dbUser, dbPassword);
    }

    /**
     * A method that returns the Message object from the database with given Reply ID
     * @param replyID - ID of the Reply to check for
     * @return Message object if exists in database of else null
     */
    @Override
    public Reply getReply(int replyID)
    {
        Reply reply = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Replies WHERE ReplyID = ?");
            preparedStatement.setInt(1, replyID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                reply = readReply(connection, resultSet);
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
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return reply;
    }

    //a method to read in the contents of a query
    private Reply readReply(Connection connection, ResultSet resultSet) throws SQLException
    {
        Reply reply = new Reply();
        reply.setMessageID(resultSet.getInt(1));
        reply.setSender(resultSet.getString(2));
        reply.setText(resultSet.getString(3));
        reply.setDate(LocalDate.parse(resultSet.getString(4)));
        return reply;
    }

    /**
     * A method that adds provided Reply to the database
     * @param reply - Reply to be added
     */
    @Override
    public void addReply(Reply reply)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Replies (SenderID, Content, ReplyDate) VALUES (?,?,?)");
            preparedStatement.setString(1, reply.getSenderID());
            preparedStatement.setString(2, reply.getText());
            preparedStatement.setString(3, reply.getDate().toString());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            // get the ID of the reply
            preparedStatement = connection.prepareStatement("SELECT ReplyID FROM Replies WHERE SenderID = ? AND (Content = ? AND ReplyDate = ?)");
            preparedStatement.setString(1, reply.getSenderID());
            preparedStatement.setString(2, reply.getText());
            preparedStatement.setString(3, reply.getDate().toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                int replyID = resultSet.getInt(1);
                preparedStatement.close();
                //insert the query IDs to which the given reply is a reply to

                for(Integer queryID : reply.getQueryIds())
                {
                    preparedStatement = connection.prepareStatement("INSERT INTO Query_Replies (QueryID, ReplyID) VALUES (?,?)");
                    preparedStatement.setInt(1, queryID);
                    preparedStatement.setInt(2, replyID);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                }
            }

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
     * A method to delete a Reply from the database
     * @param replyID - ID of the reply to delete
     */
    @Override
    public void deleteReply(int replyID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Replies WHERE ReplyID = ?");
            preparedStatement.setInt(1, replyID);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            //delete from the Query_Reply table as well
            preparedStatement = connection.prepareStatement("DELETE FROM Query_Replies WHERE ReplyID = ?");
            preparedStatement.setInt(1, replyID);
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

    @Override
    public Collection<Reply> getAllRepliesForQueryID(int queryID) {
        Set<Reply> replies = new HashSet<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Query_Replies WHERE QueryID = ?");
            preparedStatement.setInt(1, queryID);
            ResultSet resultSet = preparedStatement.executeQuery();

            Set<Integer> replyIDs = new HashSet<>();

            while(resultSet.next())
            {
                replyIDs.add(resultSet.getInt(2));
            }

            preparedStatement.close();

            for(Integer replyID : replyIDs)
            {
                preparedStatement = connection.prepareStatement("SELECT * FROM Replies WHERE ReplyID = ?");
                preparedStatement.setInt(1, replyID);
                resultSet = preparedStatement.executeQuery();

                if(resultSet.next())
                {
                    Reply reply = readReply(connection, resultSet);
                    replies.add(reply);
                }
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
            catch (SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return replies;
    }
}
