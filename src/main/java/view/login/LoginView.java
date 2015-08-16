package view.login;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.*;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.Component;
import com.vaadin.ui.themes.ValoTheme;
import data.access.DaoFactory;
import model.domain.user.Role;
import model.domain.user.User;


/**
 * LoginView.java
 * A Layout that contains creates the login form
 * Created by Michael on 2015/08/15.
 */
public class LoginView extends VerticalLayout implements View {

    private TextField userIdField;
    private PasswordField passwordField;

    //factory object to provide access to data access objects
    private DaoFactory daoFactory;
    private Navigator navigator;

    public LoginView()
    {
        String dbUrl = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUrl");
        String dbUserName = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbUserName");
        String dbPassword = VaadinServlet.getCurrent().getServletContext().getInitParameter("dbPassword");
        daoFactory = new DaoFactory(dbUrl, dbUserName, dbPassword);

        setSizeFull();

        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
    }

    /*
     * This method is called whenever the navigator navigates to this view
     */
    @Override
    public void enter(ViewChangeEvent event)
    {
        navigator = event.getNavigator();
        Page.getCurrent().setTitle("Login");
    }

    /*
     * A method that generates the login form that is attached to this view
     */
    private Component buildLoginForm()
    {
        final VerticalLayout loginPanel = new VerticalLayout();
        loginPanel.setSizeUndefined();
        loginPanel.setSpacing(true);
        Responsive.makeResponsive(loginPanel);

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());
        return loginPanel;
    }

    /*
     * A method that generates and returns the fields for the login form
     */
    private Component buildFields()
    {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);

         userIdField = new TextField("User ID");

        passwordField = new PasswordField("Password");

        final Button signInButton = new Button("Sign In");
        signInButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signInButton.setClickShortcut(KeyCode.ENTER);
        signInButton.focus();

        signInButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                signInButton.setComponentError(null);
                Notification notification;

                if (isValidUser(userIdField.getValue(), passwordField.getValue())) {
                    //if user is verified than display a notification about the successful login
                    notification = new Notification("Successful login!");
                    notification.setDescription("The credentials you provided are correct! Welcome to the Query Ticket System");
                    notification.setPosition(Position.BOTTOM_CENTER);
                    notification.show(Page.getCurrent());

                    //set the userID so that other views attached to the navigator use it to look up the user
                    VaadinSession.getCurrent().setAttribute("userID", userIdField.getValue());

                    navigator.navigateTo(getPageToNavigateTo(userIdField.getValue()));
                } else {
                    //use a notification to inform the user about an unsuccessful login attempt
                    notification = new Notification("Unsuccessful login attempt");
                    notification.setDescription("The credentials provided are incorrect.");
                    notification.setPosition(Position.BOTTOM_CENTER);
                    notification.show(Page.getCurrent());
                    signInButton.setComponentError(new UserError("The credentials provided are incorrect"));
                }

            }
        });

        fields.addComponents(userIdField, passwordField, signInButton);
        fields.setComponentAlignment(signInButton, Alignment.BOTTOM_LEFT);
        return fields;
    }

    /*
     * A method to that checks the user ID to decide whether or not the ID provided is that of student user or
     * that of a convener. Returns the name of the page to navigate to
     */
    private String getPageToNavigateTo(String userID)
    {
        User user = daoFactory.getUserDao().getUser(userID);

        //if the user is only signed up for one course and the role of that course is that of a convener then the page
        //to navigate to is that of the convener
        if(user.getCourseIDs().size() == 1)
        {
            for(String courseID : user.getCourseIDs())
            {
                if(user.getRoleForCourse(courseID) == Role.CONVENER)
                {
                    return "convener";
                }
                else
                {
                    return "student";
                }
            }
            return "student";
        }
        else
        {
            return "student";
        }
    }

    /*
     * A method to build the labels that are to be added to the login form
     */
    private Component buildLabels()
    {
        HorizontalLayout labels = new HorizontalLayout();
        labels.setSpacing(true);
        Label welcomeLabel = new Label("Query Ticket System");
        welcomeLabel.setSizeUndefined();
        welcomeLabel.addStyleName(ValoTheme.LABEL_H4);
        welcomeLabel.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcomeLabel);

        return labels;
    }


    /*
     * A method to check whether the given user id exists in the database with the given password
     */
    private boolean isValidUser(String userID, String password)
    {
        if(userID.isEmpty() || password.isEmpty())
            return false;
        else
        {
            return daoFactory.getLoginDao().isUser(userID,password);
        }
    }
}
