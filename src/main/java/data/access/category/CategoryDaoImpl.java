package data.access.category;

import data.access.Dao;
import model.domain.category.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CategoryDaoImpl.java
 * A DAO for the Category class.
 * Created by Michael on 2015/08/10.
 */


public class CategoryDaoImpl extends Dao implements CategoryDao
{

    private Logger logger = Logger.getLogger(CategoryDao.class.getName());

    public CategoryDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        super(dbUrl, dbUser, dbPassword);
    }

    @Override
    public Category getCategory(int categoryID)
    {
        Category category = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Categories WHERE CategoryID = ?");
            preparedStatement.setInt(1, categoryID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                category = readCategory(connection, resultSet);
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
            catch(SQLException ex)
            {
                logger.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
        return category;
    }

    private Category readCategory(Connection connection, ResultSet resultSet) throws SQLException
    {
        Category category = new Category();
        category.setCategoryID(resultSet.getInt(1));
        category.setCategoryName(resultSet.getString(2));
        category.setCategoryDescription(resultSet.getString(3));
        category.setCourse(resultSet.getString(4));
        return category;
    }
    @Override
    public List<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection  = super.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Categories");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Category category = readCategory(connection, resultSet);
                categories.add(category);
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
        return categories;
    }

    @Override
    public void addCategory(Category category)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection  = super.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Categories (CategoryName, Description, CourseID) VALUES (?,?,?)");
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setString(2, category.getCategoryDescription());
            preparedStatement.setString(3, category.getCourseID());
            preparedStatement.executeUpdate();
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
    }

    @Override
    public void updateCategory(Category category) {

    }

    @Override
    public void deleteCategory(Category category)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM Categories WHERE CategoryID = ?");
            preparedStatement.setInt(1, category.getCategoryID());
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
}
