package view.dashboard.student.menu;


import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import view.dashboard.DashboardMenu;

/**
 * A side menu for a student user
 * Created by Michael on 2015/08/18.
 */
public class StudentDashboardMenu extends DashboardMenu
{
    public StudentDashboardMenu()
    {
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }

    @Override
    public Component buildContent()
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

    @Override
    public Component buildMenuItems()
    {
        VerticalLayout menuItemsLayout = new VerticalLayout();
        menuItemsLayout.addComponent(buildDashboardMenuItem());


        for(String courseID : getUser().getCourseIDs())
        {
            MenuBar courseMenu = new MenuBar();
            MenuBar.MenuItem course = courseMenu.addItem(courseID, null);
            course.addItem("Queries", new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem menuItem) {
                    UI.getCurrent().getNavigator().navigateTo(courseID);
                }
            });

            course.addItem("FAQs", new MenuBar.Command() {
                @Override
                public void menuSelected(MenuBar.MenuItem menuItem) {
                    UI.getCurrent().getNavigator().navigateTo(courseID + " FAQs");
                }
            });

            courseMenu.setWidth("100%");
            menuItemsLayout.addComponent(courseMenu);
        }
        return menuItemsLayout;
    }

}
