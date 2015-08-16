package data.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Dao.java
 * A Dao object that handles a connection to the Database
 * Created by Michael on 2015/08/10.
 */

public abstract class Dao
{
    private String dbUser;
    private String dbPassword;
    private String dbUrl;

    public Dao(String dbUrl, String dbUser, String dbPassword)
    {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public String getDbUser()
    { return dbUser;}

    public String getDbPassword()
    { return dbPassword;}

    public String getDbUrl()
    { return dbUrl;}
}
