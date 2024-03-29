package view.dashboard.convener.menu;


import com.vaadin.ui.*;

import com.vaadin.ui.themes.ValoTheme;
import view.dashboard.DashboardMenu;

/**
 * A simple side menu for the Convener UI
 * Created by Michael on 2015/08/18.
 */
public class ConvenerDashboardMenu extends DashboardMenu
{
    public ConvenerDashboardMenu()
    {
        super();
    }

    /**
     * Build the menu items on the side bar
     * @return - the layout containing the menu items
     */
    @Override
    public Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        menuItemsLayout.addComponent(buildDashboardMenuItem());

        for(String courseID : getUser().getCourseIDs())
        {
            MenuBar courseMenu = new MenuBar();
            courseMenu.addStyleName("user-menu");
            courseMenu.setWidth("100%");

            MenuBar.MenuItem course = courseMenu.addItem(courseID, null);

            course.addItem("Queries", new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem menuItem) {
                    UI.getCurrent().getNavigator().navigateTo(courseID);
                }
            });

            course.addItem("Categories", new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem menuItem) {
                    UI.getCurrent().getNavigator().navigateTo(courseID + " Categories");
                }
            });

            course.addItem("FAQs", new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem menuItem) {
                    UI.getCurrent().getNavigator().navigateTo(courseID + " FAQs");
                }
            });

            menuItemsLayout.addComponent(courseMenu);
        }
        return menuItemsLayout;
    }

    @Override
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
        dashboardMenuItem.addItem("Templates", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
                UI.getCurrent().getNavigator().navigateTo("templates");
            }
        });
        return dashboardMenu;
    }

}
