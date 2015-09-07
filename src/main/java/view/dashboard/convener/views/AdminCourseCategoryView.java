package view.dashboard.convener.views;

import com.vaadin.server.Responsive;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.category.Category;
import view.TicketSystemUI;
import view.dashboard.convener.windows.CreateCategoryWindow;

import java.util.Collection;

/**
 * A view that provides edit and viewing capabilities for categories to a convener
 * Created by Michael on 2015/08/30.
 */

public class AdminCourseCategoryView extends AdminCategoryTableView
{
    private final String courseID;

    public AdminCourseCategoryView(final String courseID)
    {
        super();
        this.courseID = courseID;

        addComponent(buildToolbar(this.courseID + " Categories"));

        super.addCategoriesToTable(getCategories());

        addComponent(super.getTable());
        setExpandRatio(super.getTable(), 1);
    }

    @Override
    public Collection<Category> getCategories() {
        return TicketSystemUI.getDaoFactory().getCategoryDao().getAllCategoriesForCourse(courseID);
    }

    @Override
    public Component buildToolbar(String toolbarHeader) {
        HorizontalLayout header = new HorizontalLayout();
        header.setSpacing(true);
        Responsive.makeResponsive(header);

        Label title = new Label(toolbarHeader);
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);

        Component filter = buildFilter();
        header.addComponent(filter);
        header.addComponent(buildCreateCategory());

        return header;
    }

    public Button buildCreateCategory()
    {
        final Button createCategory = new Button("Create Category");

        createCategory.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getUI().addWindow(new CreateCategoryWindow(courseID));
            }
        });

        return createCategory;
    }
}
