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
        menuItemsLayout.addComponent(buildDashboardButton());

        for(String courseID : getUser().getCourseIDs())
        {
            Button courseButton = new Button();
            courseButton.setCaption(courseID);
            courseButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    UI.getCurrent().getNavigator().navigateTo(courseButton.getCaption());
                }
            });
            courseButton.setWidth("100%");
            //courseButton.addStyleName(ValoTheme.BUTTON_BORDERLESS_COLORED);
            menuItemsLayout.addComponent(courseButton);
        }
        return menuItemsLayout;
    }

}
