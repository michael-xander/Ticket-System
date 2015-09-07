package view.dashboard.convener.windows;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.faq.Faq;
import view.TicketSystemUI;
import view.dashboard.PopupWindow;

import java.time.LocalDate;

/**
 * Created by marcelo on 01-09-2015.
 */
public class EditFaqWindow extends PopupWindow {
    private final Faq faq;

    private TextField question;
    private RichTextArea richTextArea;

    public EditFaqWindow(final Faq faq)
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

        question = new TextField("Question");
        question.setWidth("100%");
        question.setValue(faq.getQuestion());
        question.addStyleName(ValoTheme.LABEL_H3);
        question.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        view.addComponent(question);

        richTextArea = new RichTextArea("Answer");
        richTextArea.setWidth("100%");
        richTextArea.setValue(faq.getAnswer());
        view.addComponent(richTextArea);

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

        Button cancel = new Button("Cancel");
        cancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                close();
            }
        });
        cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE, null);

        Button save = new Button("Update");
        save.addStyleName(ValoTheme.BUTTON_PRIMARY);
        save.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                faq.setQuestion(question.getValue());
                faq.setAnswer(richTextArea.getValue());
                faq.setDate(LocalDate.now());
                faq.setCourseID(faq.getCourseID());

                TicketSystemUI.getDaoFactory().getFaqDao().updateFaq(faq);
                Notification notification = new Notification("Faq Updated", Notification.Type.HUMANIZED_MESSAGE);
                notification.setDescription("Your faq has successfully been updated.");
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
