package view.dashboard.convener.windows;

import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import model.domain.category.Category;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.CreateWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * A window that provides functionality to edit the category of a query
 * Created by Michael on 2015/09/20.
 */
public class UpdateQueryCategoryWindow extends CreateWindow {

    private final Query query;
    private ComboBox categories;
    private final ConvenerQueryInfoWindow queryInfoWindow;

    public UpdateQueryCategoryWindow(final Query query, final ConvenerQueryInfoWindow queryInfoWindow)
    {
        this.query = query;
        this.queryInfoWindow = queryInfoWindow;

        setCaption("Change query category");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("30%");
        setContent(buildContent());
        setSaveButtonFunction();
    }

    @Override
    public void setSaveButtonFunction() {
        getSaveButton().setCaption("Update");
        getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSaveButton().setComponentError(null);
                if(inputIsValid())
                {
                    query.setCategoryName((String) categories.getValue());
                    TicketSystemUI.getDaoFactory().getQueryDao().updateQuery(query);
                    queryInfoWindow.updateQueryCategoryLabel();

                    Notification notification = new Notification("Query category updated", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDescription("The category of the query has successfully been updated.");
                    notification.setDelayMsec(2500);
                    notification.show(Page.getCurrent());

                    close();
                }
                else
                {
                    getSaveButton().setComponentError(new UserError("Input provided is invalid"));
                }
            }
        });

    }

    @Override
    public boolean inputIsValid() {
        boolean isValid = true;
        categories.setComponentError(null);

        if(categories.getValue() == null || categories.getValue().equals(""))
        {
            isValid = false;
            categories.setComponentError(new UserError("A category should be picked in order to update the query category"));
        }

        return isValid;
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        List<Category> courseCategories = TicketSystemUI.getDaoFactory().getCategoryDao().getAllCategoriesForCourse(query.getCourseID());

        ArrayList<String> courseCategoryNames = new ArrayList<>();

        for(Category category : courseCategories)
        {
            courseCategoryNames.add(category.getCategoryName());
        }

        categories = new ComboBox("Category", courseCategoryNames);
        categories.setWidth("100%");
        view.addComponent(categories);
        view.addComponent(buildFooter());
        return view;
    }
}
