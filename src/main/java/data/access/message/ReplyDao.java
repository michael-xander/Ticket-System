package data.access.message;

import model.domain.message.Message;

/**
 * QueryDao.java
 * An interface for the Query DAO
 * Created by Michael on 2015/08/08.
 */

public interface ReplyDao
{
    Message getReply(int replyID);

    void addReply(Message reply);

    void deleteReply(Message reply);

    void deleteReply(int replyID);
}
