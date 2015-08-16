package data.access.message;

import model.domain.message.Query;

import java.util.List;

/**
 * QueryDao.java
 * An interface for the Query DAO
 * Created by Marcelo on 2015/08/08.
 */

public interface QueryDao {

    Query getQuery(int queryID);

    List<Query> getAllQueries();

    List<Query> getAllQueriesFromUser(String userID);

    void addQuery(Query query);

    void deleteQuery(Query query);

    void deleteQuery(int queryID);

    void updateQueryRole(Query query);
}
