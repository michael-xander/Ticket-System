package view.dashboard.convener.windows;

import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import model.domain.category.Category;
import view.TicketSystemUI;
import view.dashboard.CreateWindow;

import java.util.List;

/**
 * A window to edit the information of a cateogory
 * Created by Michael on 2015/09/17.
 */
public class EditCategoryWindow extends CreateWindow {
    private final Category category;
    private TextField name, description;
    private RichTextArea templateAnswer;
    private List<Category> existingCategories;

    public EditCategoryWindow(final Category category)
    {
        this.category = category;
        setCaption("Editing Category");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
        setSaveButtonFunction();

        existingCategories = TicketSystemUI.getDaoFactory().getCategoryDao().getAllCategoriesForCourse(category.getCourseID());
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
                    category.setCategoryName(name.getValue().trim());
                    category.setCategoryDescription(description.getValue().trim());
                    category.setTemplateAnswer(templateAnswer.getValue().trim());

                    TicketSystemUI.getDaoFactory().getCategoryDao().updateCategory(category);

                    Notification notification = new Notification("Category updated", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(2500);
                    notification.setDescription("Your Category has successfully been updated.");
                    notification.show(Page.getCurrent());

                    close();
                    UI.getCurrent().getNavigator().navigateTo(category.getCourseID() + " Categories");
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
        name.setComponentError(null);
        description.setComponentError(null);

        if(name.getValue().trim().isEmpty())
        {
            isValid = false;
            name.setComponentError(new UserError("A category should have a name!"));
        }
        else
        {
            for(Category tempCategory : existingCategories)
            {
                if(tempCategory.getCategoryName().equals(name.getValue()) && category.getCategoryID() != tempCategory.getCategoryID() )
                {
                    isValid = false;
                    name.setComponentError(new UserError("A category cannot have the same name as an existing category"));
                }
            }
        }

        if(description.getValue().trim().isEmpty())
        {
            isValid = false;
            description.setComponentError(new UserError("A category should have a description!"));
        }

        return isValid;
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        name = new TextField("Category Name");
        name.setValue(category.getCategoryName());
        name.setWidth("100%");
        view.addComponent(name);

        description = new TextField("Category Description");
        description.setValue(category.getCategoryDescription());
        description.setWidth("100%");
        view.addComponent(description);

        templateAnswer = new RichTextArea("Template Answer : Provided to queries of this category (Optional)");
        templateAnswer.setValue(category.getTemplateAnswer());
        templateAnswer.setWidth("100%");
        view.addComponent(templateAnswer);

        view.addComponent(buildFooter());

        Responsive.makeResponsive(view);
        return view;
    }
}
