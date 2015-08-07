package data.access;

/**
 * LoginDao.java
 * An interface for the login DAO
 * Created by Michael on 2015/08/07.
 */
public interface LoginDao {

    public boolean isUser(String userID, String password);
}

