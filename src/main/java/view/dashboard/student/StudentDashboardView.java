package view.dashboard.student;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import data.access.DaoFactory;
import model.domain.message.Query;
import model.domain.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2015/08/15.
 */
public class StudentDashboardView extends VerticalLayout implements View {

    private DaoFactory daoFactory;

    private Navigator navigator;
    //label for the User ID
    private Label userNameLabel;

    private User student;

    private Grid queryGrid;

    private Component queryFormLayout;

    //components for the query form
    private ComboBox privacyComboBox;
    private ComboBox categoryComboBox;
    private TextField subjectTextField;
    private TextArea queryContentTextArea;

    private Tree menuItemTree;

    private String currentFocusCourse;

    public StudentDashboardView()
    {
        String dbUrl = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUrl");
        String dbUser = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUserName");
        String dbPassword = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbPassword");
        daoFactory = new DaoFactory(dbUrl, dbUser, dbPassword);

        setSpacing(true);
        setMargin(true);

        Button logoutButton = new Button("Log out");
        logoutButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        logoutButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                navigator.navigateTo("login");
            }
        });

        addComponent(logoutButton);
        setComponentAlignment(logoutButton, Alignment.TOP_RIGHT);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.addComponent(buildSideBar());
        horizontalLayout.addComponent(buildQueryTable());
        queryFormLayout = buildQueryForm();
        horizontalLayout.addComponent(queryFormLayout);
        queryFormLayout.setVisible(false);
        addComponent(horizontalLayout);
    }

    private Component buildSideBar()
    {
        VerticalLayout sideBar = new VerticalLayout();
        userNameLabel = new Label();
        sideBar.addComponent(userNameLabel);
        menuItemTree = new Tree("Courses");

        sideBar.addComponent(menuItemTree);
        sideBar.setSpacing(true);
        return sideBar;

    }

    private void buildMenuItems()
    {
        for(String courseID : student.getCourseIDs())
        {
            menuItemTree.addItem(courseID);
            menuItemTree.setChildrenAllowed(courseID, false);
        }

        menuItemTree.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                Notification.show(itemClickEvent.getItemId() + " clicked.");
                currentFocusCourse = (String) itemClickEvent.getItemId();
                queryFormLayout.setVisible(true);
            }
        });
    }

    public Component buildQueryTable()
    {
        queryGrid = new Grid();

        queryGrid.addColumn("Subject", String.class);
        queryGrid.addColumn("Status", String.class);
        queryGrid.addColumn("Course", String.class);
        queryGrid.addColumn("Privacy", String.class);
        queryGrid.setColumnOrder("Subject", "Status", "Course", "Privacy");

        return queryGrid;
    }

    public void buildGridForCourse(String courseID)
    {
        if(courseID.isEmpty())
        {
            for(Query query : daoFactory.getQueryDao().getAllQueriesFromUser(student.getUserID()))
            {
                queryGrid.addRow(query.getSubject(), query.getStatus().toString(), query.getCourseID(), query.getPrivacy().toString());
            }
        }
        else
        {

        }
    }
    public Component buildQueryForm()
    {
        FormLayout queryForm = new FormLayout();
        queryForm.setSpacing(true);

        ArrayList<String> tempArray = new ArrayList<>();
        tempArray.add("General");
        tempArray.add("Public");
        tempArray.add("Private");
        privacyComboBox = new ComboBox("Privacy", tempArray);
        categoryComboBox = new ComboBox("Category", getQueryCategories());

        HorizontalLayout boxes = new HorizontalLayout(privacyComboBox, categoryComboBox);
        boxes.setSpacing(true);
        queryForm.addComponent(boxes);

        subjectTextField = new TextField("Subject");
        queryContentTextArea = new TextArea("Content");

        queryForm.addComponent(subjectTextField);
        queryForm.addComponent(queryContentTextArea);

        Button submitButton = new Button("Submit");
        submitButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent)
            {
                if(queryCreated())
                {
                    Notification.show("Query submitted", Notification.Type.ASSISTIVE_NOTIFICATION);
                    queryFormLayout.setVisible(false);
                }
                else
                {
                    Notification.show("Enter all information", Notification.Type.WARNING_MESSAGE);
                }
            }
        });

        Button clearButton = new Button("Clear");
        clearButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                privacyComboBox.clear();
                categoryComboBox.clear();
                subjectTextField.clear();
                queryContentTextArea.clear();
            }
        });
        HorizontalLayout buttonsLayout = new HorizontalLayout(submitButton, clearButton);
        buttonsLayout.setSpacing(true);
        queryForm.addComponent(buttonsLayout);
        return queryForm;
    }

    private boolean queryCreated()
    {
        if(privacyComboBox.getValue() == null || categoryComboBox.getValue() == null || subjectTextField.getValue().isEmpty() ||
                queryContentTextArea.getValue().isEmpty())
        {
            return false;
        }
        else
        {
            Query query = new Query();
            query.setSubject(subjectTextField.getValue());
            query.setText(queryContentTextArea.getValue());
            query.setDate(LocalDate.now());
            query.setCourseID(currentFocusCourse);
            query.setSender(student.getUserID());
            query.setStatus(Query.Status.PENDING);
            query.setCategoryID(1);

            switch((String)privacyComboBox.getValue())
            {
                case "General" :
                    query.setPrivacy(Query.Privacy.GENERAL);
                    break;
                case "Public"  :
                    query.setPrivacy(Query.Privacy.PUBLIC);
                    break;
                case "Private" :
                    query.setPrivacy(Query.Privacy.PRIVATE);
                    break;
            }

            queryGrid.addRow(query.getSubject(), query.getStatus().toString(), query.getCourseID(), query.getPrivacy().toString());
            daoFactory.getQueryDao().addQuery(query);
            return true;
        }
    }

    private List<String> getQueryCategories()
    {
        ArrayList<String> categoryNames = new ArrayList<String>();
        categoryNames.add("Test");
        categoryNames.add("Exam");
        categoryNames.add("Marks");
        return categoryNames;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {
        navigator = viewChangeEvent.getNavigator();

        Object tempObject = VaadinSession.getCurrent().getAttribute("userID");

        if(tempObject != null)
        {
            String userID = (String) tempObject;
            student = daoFactory.getUserDao().getUser(userID);

            userNameLabel.setValue(student.getFirstName() + " " + student.getLastName());
            buildMenuItems();
            buildGridForCourse("");
        }
        else
        {
            navigator.navigateTo("login");
        }
    }
}
