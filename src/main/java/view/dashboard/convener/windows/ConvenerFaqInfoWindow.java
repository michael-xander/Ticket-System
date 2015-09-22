package view.dashboard.convener.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.faq.Faq;
import view.dashboard.InfoWindow;

/**
 * A simple window to see the information for an FAQ for a convener
 * Created by Michael on 2015/09/17.
 */
public class ConvenerFaqInfoWindow extends InfoWindow{

    private final Faq faq;

    public ConvenerFaqInfoWindow(final Faq faq)
    {
        this.faq = faq;
        setCaption("View Faq");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        Label title = new Label(faq.getQuestion());
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        view.addComponent(title);

        Label content = new Label(faq.getAnswer(), ContentMode.HTML);
        view.addComponent(content);

        Label date = new Label("(last modified " + faq.getDate().toString() + ")");
        date.addStyleName(ValoTheme.LABEL_SMALL);
        view.addComponent(date);

        view.addComponent(buildFooter());
        return view;
    }

    @Override
    public Component buildFooter()
    {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);

        Button okay = new Button("Okay");
        okay.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        okay.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        Button edit = new Button("Edit Faq");
        edit.addStyleName(ValoTheme.BUTTON_PRIMARY);
        edit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
                UI.getCurrent().addWindow(new EditFaqWindow(faq));
            }
        });
        footer.addComponents(okay, edit);
        footer.setExpandRatio(okay, 1);
        footer.setComponentAlignment(okay, Alignment.TOP_RIGHT);
        return footer;
    }
}
