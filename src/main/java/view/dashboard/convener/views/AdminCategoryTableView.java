package view.dashboard.convener.views;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Component;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.category.Category;
import view.dashboard.UserTableView;

import java.util.Collection;

/**
 * The table view for a Convener's categories
 * Created by Michael on 2015/08/30.
 */
public abstract class AdminCategoryTableView extends UserTableView
{
    public AdminCategoryTableView()
    {
        setSizeFull();
        setSpacing(true);
        super.setTable(buildTable());

        super.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {

            }
        });
    }

    /**
     * Builds the table that contains the Categories
     * @return the built table
     */
    @Override
    public Table buildTable()
    {
        final Table table = new Table();
        table.setSizeFull();

        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);

        table.addContainerProperty("Name", String.class, "(default)");
        table.addContainerProperty("Description", String.class, "(default)");
        table.addContainerProperty("Template Answer", String.class, "(default)");

        table.setImmediate(true);

        return table;
    }

    /**
     * Builds the filter for the table
     * @return the filter created
     */
    @Override
    public Component buildFilter()
    {
        final TextField filter = new TextField();
        filter.setIcon(FontAwesome.SEARCH);
        filter.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        return filter;
    }

    public abstract Collection<Category> getCategories();

    /**
     * Adds the provided category to the table
     * @param category - Category to add to the table
     */
    public void addCategoryToTable(Category category)
    {
        super.getTable().addItem(new Object[] {
                category.getCategoryName(),
                category.getCategoryDescription(),
                category.getTemplateAnswer()
        }, category.getCategoryID());
    }

    /**
     * Adds the collection of categories to the table
     * @param categories - collection of categories to add to the table
     */
    public void addCategoriesToTable(Collection<Category> categories)
    {
        for(Category category : categories)
        {
            addCategoryToTable(category);
        }
    }
}
