package data.access.message;

import model.domain.message.Query;

import java.util.List;

/**
 * Created by marcelo on 08-08-2015.
 */
public interface QueryDao {

    Query getQuery(int queryID);

    List<Query> getAllQueries();

    List<Query> getAllQueriesFromUser(int userID);

    void addQuery(Query query);

    void deleteQuery(Query query);
}
