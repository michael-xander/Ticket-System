package view.dashboard;

import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import model.domain.user.User;
import view.TicketSystemUI;

/**
 * A side menu for the user
 * Created by Michael on 2015/08/18.
 */
public abstract class DashboardMenu extends CustomComponent
{
    private User user = TicketSystemUI.getDaoFactory().getUserDao().getUser(
            (String) VaadinSession.getCurrent().getAttribute("userID")
    );

    public void setUser(User user)
    {
        this.user = user;
    }

    public User getUser()
    {
        return user;
    }

    public Component buildTitle()
    {
        Label logo = new Label("TicSys <strong>Dashboard</strong>", ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        return logoWrapper;
    }

    public Component buildUserMenu()
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
                ((TicketSystemUI)UI.getCurrent()).userSignOut();
            }
        });
        return settings;
    }

    public abstract Component buildMenuItems();

    public abstract Component buildContent();

    public Button buildDashboardButton()
    {
        Button button = new Button();
        button.setCaption("Dashboard");
        button.setWidth("100%");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo("dashboard");
            }
        });
        return button;
    }
}
