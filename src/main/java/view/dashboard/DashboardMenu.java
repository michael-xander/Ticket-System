package view.dashboard;

import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
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

    public DashboardMenu()
    {
        setPrimaryStyleName("valo-menu");
        setId("dashboard-menu");
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }
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
        logoWrapper.addStyleName("valo-menu-title");
        return logoWrapper;
    }

    public Component buildUserMenu()
    {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        settings.setWidth("100%");
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

    public Component buildContent()
    {
        //final VerticalLayout menuContent = new VerticalLayout();
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    public Component buildDashboardMenuItem()
    {
        final MenuBar dashboardMenu = new MenuBar();
        dashboardMenu.addStyleName("user-menu");
        dashboardMenu.setWidth("100%");

        MenuBar.MenuItem dashboardMenuItem = dashboardMenu.addItem("Dashboard", null);

        dashboardMenuItem.addItem("All Queries", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                UI.getCurrent().getNavigator().navigateTo("dashboard");
            }
        });

        return dashboardMenu;
    }
}
