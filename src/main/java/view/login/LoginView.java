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
import view.TicketSystemUI;


/**
 * LoginView.java
 * A Layout that contains creates the login form
 * Created by Michael on 2015/08/15.
 */
public class LoginView extends VerticalLayout {




    public LoginView()
    {
        setSizeFull();

        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
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
        loginPanel.addStyleName("login-panel");

        loginPanel.addComponent(buildLabels());
        loginPanel.addComponent(buildFields());
        loginPanel.addComponent(new CheckBox("Remember me", true));
        return loginPanel;
    }

    /*
     * A method that generates and returns the fields for the login form
     */
    private Component buildFields()
    {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.addStyleName("fields");

        final TextField userIdField = new TextField("User ID");
        userIdField.setIcon(FontAwesome.USER);
        userIdField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        final PasswordField passwordField = new PasswordField("Password");
        passwordField.setIcon(FontAwesome.LOCK);
        passwordField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

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
                    notification = new Notification("Successful login!",
                            "The credentials you provided are correct! Welcome to the Query Ticket System",
                            Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(2500);
                    notification.setPosition(Position.BOTTOM_CENTER);
                    notification.show(Page.getCurrent());

                    //set the userID so that other views attached to the navigator use it to look up the user
                    VaadinSession.getCurrent().setAttribute("userID", userIdField.getValue());
                    ((TicketSystemUI) getUI()).updateContent();

                } else
                {
                    //use a notification to inform the user about an unsuccessful login attempt
                    notification = new Notification("Unsuccessful login attempt",
                            "The credentials you provided are incorrect!",
                            Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(2500);
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
     * A method to build the labels that are to be added to the login form
     */
    private Component buildLabels()
    {

        CssLayout labels = new CssLayout();
        labels.addStyleName("labels");

        Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);
        labels.addComponent(welcome);

        Label title = new Label("Query Ticket System");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        labels.addComponent(title);
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
            return TicketSystemUI.getDaoFactory().getLoginDao().isUser(userID,password);
        }
    }
}
