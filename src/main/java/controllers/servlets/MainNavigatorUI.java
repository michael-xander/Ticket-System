package controllers.servlets;

/**
 * Created by Michael on 2015/08/15.
 */

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class MainNavigatorUI extends UI
{
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Navigator navigator = new Navigator(this, this);

    }
}