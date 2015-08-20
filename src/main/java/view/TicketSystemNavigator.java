package view;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

/**
 * TicketSystemNavigator.java
 * A navigator for the Ticket System
 * Created by Michael on 2015/08/18.
 */

public abstract class TicketSystemNavigator extends Navigator
{
    public TicketSystemNavigator(final ComponentContainer container)
    {
        super(UI.getCurrent(), container);
    }
}
