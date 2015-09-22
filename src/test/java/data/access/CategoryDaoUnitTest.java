package data.access;

import data.access.category.CategoryDao;
import model.domain.category.Category;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * A class to test the functionality of the Category DAO
 * Created by Michael on 2015/08/10.
 */
public class CategoryDaoUnitTest
{
    /**
     * A method that tests the getAllCategories method of the Category DAO
     */
    @Test
    public void categoryDaoGetAllCategoriesTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        CategoryDao categoryDao = daoFactory.getCategoryDao();
        List<Category> categories = categoryDao.getAllCategories();

        assertNotNull(categories);
    }

    /**
     * A method to test the getCategory method of the Category DAO
     */
    @Test
    public void categoryDaoGetCategoryTest()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        CategoryDao categoryDao = daoFactory.getCategoryDao();
        Category category = categoryDao.getCategory(-1);

        assertNull(category);
    }

    /**
     * A method to test that getAllCategoriesForCourse method of the Category DAO
     */
    public void categoryDaoGetAllCategoriesForCourse()
    {
        DaoFactory daoFactory = new DaoFactory(
                TestDataAccessProperties.DB_URL,
                TestDataAccessProperties.DB_USERNAME,
                TestDataAccessProperties.DB_PASSWORD
        );

        CategoryDao categoryDao = daoFactory.getCategoryDao();
        List<Category> categories = categoryDao.getAllCategoriesForCourse("CSC3002F 2015");

        assertNotNull(categories);
        assertTrue(!categories.isEmpty());
    }
}
