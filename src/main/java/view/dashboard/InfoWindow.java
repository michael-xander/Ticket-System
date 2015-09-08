package view.dashboard;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A pop window that shows information
 * Created by Michael on 2015/08/30.
 */
public abstract class InfoWindow extends PopupWindow
{
    @Override
    public Component buildFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button ok = new Button("Okay");
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        ok.setClickShortcut(ShortcutAction.KeyCode.ENTER, null);

        footer.addComponent(ok);
        footer.setExpandRatio(ok, 1);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        return footer;
    }
}
