package data.access.category;

import model.domain.category.Category;

import java.util.List;

/**
 * CategoryDao.java
 * An interface for the Category DAO
 * Created by Michael on 2015/08/10.
 */
public interface CategoryDao
{
    Category getCategory(int categoryID);

    List<Category> getAllCategories();

    void addCategory(Category category);

    void updateCategory(Category category);

    void deleteCategory(Category category);
}
