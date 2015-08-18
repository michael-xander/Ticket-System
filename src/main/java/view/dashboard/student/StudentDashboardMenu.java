package view.dashboard.student;

import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.user.User;
import view.TicketSystemUI;

/**
 * Created by Michael on 2015/08/18.
 */
public class StudentDashboardMenu extends CustomComponent
{
    private User user = TicketSystemUI.getDaoFactory().getUserDao().getUser(
            (String)VaadinSession.getCurrent().getAttribute("userID")
    );

    public StudentDashboardMenu()
    {
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }

    private Component buildContent()
    {
        final VerticalLayout menuContent = new VerticalLayout();
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle()
    {
        Label logo = new Label("TicSys Student <strong>Dashboard</strong>", ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        return logoWrapper;
    }

    private Component buildUserMenu()
    {
        final MenuBar settings = new MenuBar();
        MenuBar.MenuItem settingsItem = settings.addItem("", null);
        settingsItem.setText(user.getFirstName() + " " + user.getLastName());

        settingsItem.addItem("Preferences", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {

            }
        });
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {

            }
        });
        return settings;
    }

    private Component buildMenuItems()
    {
        VerticalLayout menuItemsLayout = new VerticalLayout();
        menuItemsLayout.addComponent(buildDashboardButton());

        for(String courseID : user.getCourseIDs())
        {
            Button courseButton = new Button();
            courseButton.setCaption(courseID);
            courseButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    UI.getCurrent().getNavigator().navigateTo(courseButton.getCaption());
                }
            });
            menuItemsLayout.addComponent(courseButton);
        }
        return menuItemsLayout;
    }

    private Button buildDashboardButton()
    {
        Button button = new Button();
        button.setCaption("Dashboard");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo("dashboard");
            }
        });
        return button;
    }
}
