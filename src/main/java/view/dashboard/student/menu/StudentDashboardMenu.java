package view.dashboard.student.menu;


import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.user.Role;
import view.dashboard.DashboardMenu;

/**
 * A side menu for a student user
 * Created by Michael on 2015/08/18.
 */
public class StudentDashboardMenu extends DashboardMenu
{
    public StudentDashboardMenu()
    {
        super();
    }

    @Override
    public Component buildMenuItems()
    {
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

            if(getUser().getRoleForCourse(courseID).equals(Role.TA))
            {
                course.addItem("Templates", new MenuBar.Command() {
                    @Override
                    public void menuSelected(MenuBar.MenuItem menuItem) {
                        UI.getCurrent().getNavigator().navigateTo("templates");
                    }
                });
            }

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

}
