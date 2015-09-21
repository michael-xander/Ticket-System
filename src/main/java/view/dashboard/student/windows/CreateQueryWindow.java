package view.dashboard.student.windows;

import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import model.domain.category.Category;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.CreateWindow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple window displayed to create a query
 * Created by Michael on 2015/08/18.
 */

public class CreateQueryWindow extends CreateWindow
{
    private final String courseID;

    private ComboBox privacyComboBox;
    private ComboBox categoryComboBox;
    private TextField subject;
    private RichTextArea richTextArea;
    private List<Category> existingCategories;

    public CreateQueryWindow(final String courseID)
    {
        this.courseID = courseID;
        existingCategories = TicketSystemUI.getDaoFactory().getCategoryDao().getAllCategoriesForCourse(this.courseID);

        setCaption("Creating Query for " + courseID);
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
        setSaveButtonFunction();
    }

    public Component buildContent()
    {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);
        view.addComponent(buildComboBoxes());

        subject = new TextField("Subject");
        subject.setWidth("100%");
        view.addComponent(subject);

        richTextArea = new RichTextArea("Content");
        richTextArea.setWidth("100%");
        view.addComponent(richTextArea);

        view.addComponent(buildFooter());

        Responsive.makeResponsive(view);
        return view;
    }



    @Override
    public void setSaveButtonFunction() {
        getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSaveButton().setComponentError(null);
                if(inputIsValid())
                {

                    Query query = new Query();
                    query.setSubject(subject.getValue().trim());
                    query.setText(richTextArea.getValue().trim());
                    query.setDate(LocalDate.now());
                    query.setCourseID(courseID);
                    query.setSender((String) VaadinSession.getCurrent().getAttribute("userID"));
                    query.setStatus(Query.Status.PENDING);
                    query.setCategoryName((String) categoryComboBox.getValue());

                    switch((String) privacyComboBox.getValue())
                    {
                        case "Public" :
                            query.setPrivacy(Query.Privacy.PUBLIC);
                            break;
                        case "Administrator" :
                            query.setPrivacy(Query.Privacy.ADMINISTRATOR);
                            break;
                        case "Convener" :
                            query.setPrivacy(Query.Privacy.CONVENER);
                            break;
                    }

                    Category chosenCategory = getChosenCategory(query.getCategoryName());

                    if(chosenCategory.getTemplateAnswer()!= null && !chosenCategory.getTemplateAnswer().isEmpty())
                    {

                        close();
                        UI.getCurrent().addWindow(new CategoryTemplateAnswerWindow(chosenCategory, query));
                    }
                    else
                    {
                        TicketSystemUI.getDaoFactory().getQueryDao().addQuery(query);
                        Notification notification = new Notification("Query created", Notification.Type.HUMANIZED_MESSAGE);
                        notification.setDescription("Your query has successfully been submitted.");
                        notification.setDelayMsec(2500);
                        notification.show(Page.getCurrent());

                        close();
                        UI.getCurrent().getNavigator().navigateTo(query.getCourseID());
                    }

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

        privacyComboBox.setComponentError(null);
        categoryComboBox.setComponentError(null);
        subject.setComponentError(null);
        richTextArea.setComponentError(null);

        if(privacyComboBox.getValue() == null || (privacyComboBox.getValue()).equals(""))
        {
            isValid = false;
            privacyComboBox.setComponentError(new UserError("A newly created query should have a privacy setting"));
        }

        if(categoryComboBox.getValue() == null || categoryComboBox.getValue().equals(""))
        {
            isValid = false;
            categoryComboBox.setComponentError(new UserError("A newly created query should fall under a category"));
        }

        if(subject.getValue().trim().isEmpty())
        {
            isValid = false;
            subject.setComponentError(new UserError("A newly created query should have a subject"));
        }

        if(richTextArea.getValue().trim().isEmpty())
        {
            isValid = false;
            richTextArea.setComponentError(new UserError("A newly created query should have content"));
        }

        return isValid;
    }

    private Component buildComboBoxes()
    {
        HorizontalLayout comboBoxes = new HorizontalLayout();
        comboBoxes.setSpacing(true);
        categoryComboBox = new ComboBox("Category", getAvailableCategories());
        privacyComboBox = new ComboBox("Privacy", getAvailablePrivacySettings());

        comboBoxes.addComponents(categoryComboBox, privacyComboBox);
        return comboBoxes;
    }

    /*
     * Obtains the category from the existing categories with the given name
     */
    private Category getChosenCategory(String categoryName)
    {
        for(Category category : existingCategories)
        {
            if(category.getCategoryName().equals(categoryName))
                return category;
        }

        return null;
    }

    /*
     * Returns the available names of query categories for the course this window is for
     */
    private List<String> getAvailableCategories()
    {

        ArrayList<String> categoryNames = new ArrayList<>();

        for(Category category : existingCategories)
        {
            categoryNames.add(category.getCategoryName());
        }

        return categoryNames;
    }

    /*
     * Returns available privacy settings for a query
     */
    private List<String> getAvailablePrivacySettings()
    {
        ArrayList<String> privacySettings = new ArrayList<>();
        privacySettings.add("Public");
        privacySettings.add("Administrator");
        privacySettings.add("Convener");
        return privacySettings;
    }
}
