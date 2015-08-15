package controllers.servlets;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Michael on 2015/08/15.
 */
public class LoginView extends VerticalLayout implements View {

    public LoginView()
    {
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeEvent event)
    {

    }

    private Component buildLoginForm()
    {
        return null;
    }

    private Component buildFields()
    {
        return null;
    }
}
