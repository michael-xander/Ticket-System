package view.dashboard;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * An abstract class for a pop up window to create something
 * Created by Michael on 2015/08/30.
 */
public abstract class CreateWindow extends PopupWindow
{
    private Button saveButton = new Button("Save");

    public Button getSaveButton(){return saveButton;}

    public void setSaveButton(Button saveButton){this.saveButton = saveButton;}

    /**
     * Builds the cancel and save buttons at the buttom of any create window
     * @return the footer
     */
    @Override
    public Component buildFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button cancel = new Button("Cancel");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        saveButton.addStyleName(ValoTheme.BUTTON_PRIMARY);

        footer.addComponents(cancel, saveButton);
        footer.setExpandRatio(cancel, 1);
        footer.setComponentAlignment(cancel, Alignment.TOP_RIGHT);
        return footer;
    }

    public abstract void setSaveButtonFunction();

    public abstract boolean inputIsValid();
}
