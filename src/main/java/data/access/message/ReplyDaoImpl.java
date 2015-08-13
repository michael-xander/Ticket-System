package data.access.message;

import data.access.Dao;
import model.domain.message.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Michael on 2015/08/11.
 */
public class ReplyDaoImpl extends Dao implements ReplyDao
{
    private Logger logger = Logger.getLogger(ReplyDaoImpl.class.getName());

    public ReplyDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public Message getReply(int replyID)
    {
        Message reply = null;
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
                reply = new Message();
                reply.setMessageID(resultSet.getInt(1));
                reply.setSender(resultSet.getString(2));
                reply.setText(resultSet.getString(3));
                reply.setDate(LocalDate.parse(resultSet.getString(4)));
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

    @Override
    public void addReply(Message reply)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Replies (ReplyID, SenderID, Content, ReplyDate) VALUES (?,?,?,?)");
            preparedStatement.setInt(1, reply.getMessageID());
            preparedStatement.setString(2, reply.getSenderID());
            preparedStatement.setString(3, reply.getText());
            preparedStatement.setString(4, reply.getDate().toString());

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

    @Override
    public void deleteReply(Message reply)
    {
        deleteReply(reply.getMessageID());
    }

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
}
