package data.access.category;

import data.access.Dao;
import model.domain.category.Category;

import java.io.Serializable;
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

public class CategoryDaoImpl extends Dao implements CategoryDao, Serializable
{

    private transient Logger logger = Logger.getLogger(CategoryDao.class.getName());

    public CategoryDaoImpl(String dbUrl, String dbUser, String dbPassword)
    {
        super(dbUrl, dbUser, dbPassword);
    }

    /**
     * A method that returns the Category object from the database with given Category ID
     * @param categoryID - ID of the Category to check for
     * @return Category object if exists in database of else null
     */
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

    /**
     * A method that returns the Category object read from the result set provided
     * @param connection - the connection between the solution and the data source
     * @param resultSet - an array with all the attributes of the category
     * @return Category object if exists in database of else null
     */
    private Category readCategory(Connection connection, ResultSet resultSet) throws SQLException
    {
        Category category = new Category();
        category.setCategoryID(resultSet.getInt(1));
        category.setCategoryName(resultSet.getString(2));
        category.setCategoryDescription(resultSet.getString(3));
        category.setCourse(resultSet.getString(4));
        return category;
    }

    /**
     * A method that returns all Categories in the database.
     * @return - List of all Categories
     */
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

    /**
     * A method that returns all Categories from a certain course in the database.
     * @param courseID - ID of the Course to get the categories from
     * @return - List of all Categories of that course
     */
    @Override
    public List<Category> getAllCategoriesForCourse(String courseID)
    {
        ArrayList<Category> categories = new ArrayList<>();
        Connection connection  = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM Categories WHERE CourseID = ?");
            preparedStatement.setString(1, courseID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next())
            {
                Category category = readCategory(connection, resultSet);
                categories.add(category);
            }
        }
        catch (SQLException ex)
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
        return categories;
    }

    /**
     * A method that adds provided Category to the database
     * @param category - Category to be added
     */
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

    /**
     * A method that updates the values of the given Category in that database
     * @param category - Category with values to be updated
     */
    @Override
    public void updateCategory(Category category) {
        Connection connection  = null;
        PreparedStatement preparedStatement = null;

        try
        {
            connection = super.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE Categories SET CategoryName = ?, Description = ?, CourseID = ? WHERE CategoryID = ?");
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setString(2, category.getCategoryDescription());
            preparedStatement.setString(3, category.getCourseID());
            preparedStatement.setInt(4, category.getCategoryID());
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

    /**
     * A method to delete a Category from the database
     * @param category - category to delete
     */
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
