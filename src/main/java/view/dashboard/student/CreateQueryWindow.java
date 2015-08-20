package view.dashboard.student;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.category.Category;
import model.domain.message.Query;
import view.TicketSystemUI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple window displayed to create a query
 * Created by Michael on 2015/08/18.
 */

public class CreateQueryWindow extends Window
{
    private final String courseID;

    private ComboBox privacyComboBox;
    private ComboBox categoryComboBox;
    private TextField subject;
    private RichTextArea richTextArea;

    public CreateQueryWindow(final String courseID)
    {
        this.courseID = courseID;
        setCaption("Create Query for " + courseID);
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
    }

    private Component buildContent()
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

    private Component buildFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button cancel = new Button("Cancel");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        Button save = new Button("Save");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Query query = new Query();
                query.setSubject(subject.getValue());
                query.setText(richTextArea.getValue());
                query.setDate(LocalDate.now());
                query.setCourseID(courseID);
                query.setSender((String) VaadinSession.getCurrent().getAttribute("userID"));
                query.setStatus(Query.Status.PENDING);
                query.setCategoryID(getCategoryCode((String) categoryComboBox.getValue()));

                switch((String) privacyComboBox.getValue())
                {
                    case "General" :
                        query.setPrivacy(Query.Privacy.GENERAL);
                        break;
                    case "Public" :
                        query.setPrivacy(Query.Privacy.PUBLIC);
                        break;
                    case "Private" :
                        query.setPrivacy(Query.Privacy.PRIVATE);
                        break;
                }

                TicketSystemUI.getDaoFactory().getQueryDao().addQuery(query);
                Notification notification = new Notification("Query created", Notification.Type.HUMANIZED_MESSAGE);
                notification.setDescription("Your query has successfully been submitted.");
                notification.show(Page.getCurrent());

                close();
                UI.getCurrent().getNavigator().navigateTo(query.getCourseID());
            }
        });

        save.setClickShortcut(ShortcutAction.KeyCode.ENTER, null);
        footer.addComponents(cancel, save);
        footer.setExpandRatio(cancel, 1);
        footer.setComponentAlignment(cancel, Alignment.TOP_RIGHT);
        return footer;
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
     * Returns the available names of query categories for the course this window is for
     */
    private List<String> getAvailableCategories()
    {
        List<Category> categories = TicketSystemUI.getDaoFactory().getCategoryDao().getAllCategoriesForCourse(courseID);

        ArrayList<String> categoryNames = new ArrayList<>();

        for(Category category : categories)
        {
            categoryNames.add(category.getCategoryName());
        }

        return categoryNames;
    }

    /*
     * Returns the category ID for the given category name
     */
    private int getCategoryCode(String categoryName)
    {
        List<Category> categories = TicketSystemUI.getDaoFactory().getCategoryDao().getAllCategoriesForCourse(courseID);

        for(Category category : categories)
        {
            if(category.getCategoryName().equals(categoryName))
                return category.getCategoryID();
        }

        return 1;
    }
    /*
     * Returns available privacy settings for a query
     */
    private List<String> getAvailablePrivacySettings()
    {
        ArrayList<String> privacySettings = new ArrayList<>();
        privacySettings.add("General");
        privacySettings.add("Public");
        privacySettings.add("Private");
        return privacySettings;
    }
}
