package view.dashboard.student.windows;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.faq.Faq;
import view.dashboard.InfoWindow;

/**
 * A simple window to see the information of a faq
 * Created by Marcelo on 31/08/2015.
 */
public class FaqInfoWindow extends InfoWindow
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

    @Override
    public Component buildContent()
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

}

