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
 * Created by Michael on 2015/08/15.
 */
public class LoginView extends VerticalLayout implements View {

    private TextField userIdField;
    private PasswordField passwordField;
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

    @Override
    public void enter(ViewChangeEvent event)
    {
        navigator = event.getNavigator();
        Page.getCurrent().setTitle("Login");
    }

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
                    notification = new Notification("Successful login!");
                    notification.setDescription("The credentials you provided are correct! Welcome to the Query Ticket System");
                    notification.setPosition(Position.BOTTOM_CENTER);
                    notification.show(Page.getCurrent());
                    VaadinSession.getCurrent().setAttribute("userID", userIdField.getValue());

                    navigator.navigateTo(getPageToNavigateTo(userIdField.getValue()));
                } else {
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

    private String getPageToNavigateTo(String userID)
    {
        User user = daoFactory.getUserDao().getUser(userID);

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
