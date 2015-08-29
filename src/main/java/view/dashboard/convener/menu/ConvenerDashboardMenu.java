package view.dashboard.convener.menu;


import com.vaadin.ui.Component;

import com.vaadin.ui.VerticalLayout;
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
        setSizeUndefined();
        setCompositionRoot(buildContent());
    }

    @Override
    public Component buildMenuItems() {
        VerticalLayout menuItemsLayout = new VerticalLayout();
        menuItemsLayout.addComponent(buildDashboardButton());
        return menuItemsLayout;
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
}
