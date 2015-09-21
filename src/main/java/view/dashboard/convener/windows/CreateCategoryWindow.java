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
 * A popup window that provides controls to create a category
 * Created by Michael on 2015/08/30.
 */
public class CreateCategoryWindow extends CreateWindow
{
    private final String courseID;
    private TextField name, description;
    private RichTextArea templateAnswer;
    private List<Category> existingCategories;

    public CreateCategoryWindow(final String courseID)
    {
        this.courseID = courseID;
        setCaption("Creating Category for " + courseID);
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
        setSaveButtonFunction();

        existingCategories = TicketSystemUI.getDaoFactory().getCategoryDao().getAllCategoriesForCourse(courseID);
    }

    @Override
    public void setSaveButtonFunction() {
        getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSaveButton().setComponentError(null);
                if(inputIsValid())
                {
                    Category category = new Category();
                    category.setCategoryName(name.getValue().trim());
                    category.setCategoryDescription(description.getValue().trim());
                    category.setCourse(courseID);
                    category.setTemplateAnswer(templateAnswer.getValue().trim());
                    TicketSystemUI.getDaoFactory().getCategoryDao().addCategory(category);

                    Notification notification = new Notification("Category created", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(2500);
                    notification.setDescription("Your Category has successfully been created.");
                    notification.show(Page.getCurrent());

                    close();
                    UI.getCurrent().getNavigator().navigateTo(courseID + " Categories");
                }
                else
                {
                    getSaveButton().setComponentError(new UserError("The input provided is invalid"));
                }
            }
        });

    }

    /**
     * Checks whether input in the name and description field is valid
     * @return - returns whether input in textfields is valid or not
     */
    @Override
    public boolean inputIsValid()
    {
        boolean isValid = true;
        name.setComponentError(null);
        description.setComponentError(null);

        if(name.getValue().trim().isEmpty())
        {
            isValid = false;
            name.setComponentError(new UserError("A new category should have a name!"));
        }
        else
        {
            for(Category category : existingCategories)
            {
                if(category.getCategoryName().equals(name.getValue()))
                {
                    isValid = false;
                    name.setComponentError(new UserError("A new category cannot have the same name as an existing category"));
                }
            }
        }

        if(description.getValue().trim().isEmpty())
        {
            isValid = false;
            description.setComponentError(new UserError("A new category should have a description!"));
        }

        return isValid;
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        name = new TextField("Category Name");
        name.setWidth("100%");
        view.addComponent(name);

        description = new TextField("Category Description");
        description.setWidth("100%");
        view.addComponent(description);

        templateAnswer = new RichTextArea("Template Answer : Provided to queries of this category (Optional)");
        templateAnswer.setWidth("100%");
        view.addComponent(templateAnswer);

        view.addComponent(buildFooter());

        Responsive.makeResponsive(view);
        return view;
    }
}
