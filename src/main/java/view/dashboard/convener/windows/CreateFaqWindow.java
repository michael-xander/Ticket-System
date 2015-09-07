package view.dashboard.convener.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.category.Category;
import model.domain.faq.Faq;
import model.domain.message.Query;
import view.TicketSystemUI;
import view.dashboard.PopupWindow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple window displayed to create a faq
 * Created by Marcelo on 2015/08/31.
 */

public class CreateFaqWindow extends PopupWindow
{
    private final String courseID;

    private TextField question;
    private RichTextArea richTextArea;

    public CreateFaqWindow(final String courseID)
    {
        this.courseID = courseID;
        setCaption("Create Faq for " + courseID);
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

        question = new TextField("Question");
        question.setWidth("100%");
        view.addComponent(question);

        richTextArea = new RichTextArea("Answer");
        richTextArea.setWidth("100%");
        view.addComponent(richTextArea);

        view.addComponent(buildFooter());

        Responsive.makeResponsive(view);
        return view;
    }

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

        Button save = new Button("Save");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Faq faq = new Faq();
                faq.setQuestion(question.getValue());
                faq.setAnswer(richTextArea.getValue());
                faq.setDate(LocalDate.now());
                faq.setCourseID(courseID);

                TicketSystemUI.getDaoFactory().getFaqDao().addFaq(faq);
                Notification notification = new Notification("Faq created", Notification.Type.HUMANIZED_MESSAGE);
                notification.setDescription("Your faq has successfully been submitted.");
                notification.show(Page.getCurrent());

                close();
                UI.getCurrent().getNavigator().navigateTo(faq.getCourseID() + " FAQs");
            }
        });

        save.setClickShortcut(ShortcutAction.KeyCode.ENTER, null);
        footer.addComponents(cancel, save);
        footer.setExpandRatio(cancel, 1);
        footer.setComponentAlignment(cancel, Alignment.TOP_RIGHT);
        return footer;
    }

}
