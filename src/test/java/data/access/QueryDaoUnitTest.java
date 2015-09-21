package data.access;

import data.access.message.QueryDao;
import model.domain.message.Query;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * A class that tests the functionality of the Query DAO
 * Created by Michael on 2015/08/08.
 */
public class QueryDaoUnitTest {

    /**
     * A method to test the getQuery method of the Query DAO
     */
    @Test
    public void queryDaoGetQueryTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        QueryDao queryDao = daoFactory.getQueryDao();

        Query query = queryDao.getQuery(-1);

        assertNull(query);
    }

    /**
     * A method to test the getAllQueries method for the Query DAO
     */
    @Test
    public void queryDaoGetAllQueriesTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        QueryDao queryDao = daoFactory.getQueryDao();
        List<Query> queries = queryDao.getAllQueries();

        assertNotNull(queries);
    }
}
