package data.access.message;

import model.domain.message.Reply;

import java.util.Collection;

/**
 * QueryDao.java
 * An interface for the Query DAO
 * Created by Michael on 2015/08/08.
 */

public interface ReplyDao
{
    Reply getReply(int replyID);

    void addReply(Reply reply);

    void deleteReply(int replyID);

    Collection<Reply> getAllRepliesForQueryID(int queryID);
}
