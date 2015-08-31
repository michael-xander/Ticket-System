package view.dashboard.student.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.faq.Faq;

/**
 * A simple window to see the information of a faq
 * Created by Marcelo on 31/08/2015.
 */
public class FaqInfoWindow extends Window
{
    private final Faq faq;

    public FaqInfoWindow(final Faq faq)
    {
        this.faq = faq;
        setCaption("View Faq");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("40%");
        setContent(buildContent());
    }

    private Component buildContent()
    {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        Label title = new Label(faq.getQuestion());
        title.addStyleName(ValoTheme.LABEL_H3);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        view.addComponent(title);

        Label content = new Label(faq.getAnswer());
        view.addComponent(content);

        Label date = new Label("(last modified " + faq.getDate().toString() + ")");
        date.addStyleName(ValoTheme.LABEL_SMALL);
        view.addComponent(date);

        view.addComponent(buildFooter());
        return view;
    }

    private Component buildFooter()
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

