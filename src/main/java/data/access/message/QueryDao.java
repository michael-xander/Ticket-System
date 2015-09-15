package data.access.message;

import model.domain.message.Query;

import java.util.List;
import java.util.Set;

/**
 * QueryDao.java
 * An interface for the Query DAO
 * Created by Marcelo on 2015/08/08.
 */

public interface QueryDao {

    Query getQuery(int queryID);

    List<Query> getAllQueries();

    Set<Query> getAllQueriesForUser(String userID, String courseID);

    List<Query> getAllQueriesForCourse(String courseID, Query.Status status);

    void addQuery(Query query);

    void deleteQuery(int queryID);

    void updateQueryRole(Query query);

    void updateQueryForwardStatus(Query query);
}
