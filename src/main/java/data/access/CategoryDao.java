package data.access;

import model.domain.user.Category;

import java.util.List;

/**
 * Created by marcelo on 08-08-2015.
 */
public interface CategoryDao {

    Category getCategory(int categoryID);

    List<Category> getAllCategory();

    void updateCategory(Category category);

    void addCourse(Category category);

    void deleteCourse(Category category);
}
