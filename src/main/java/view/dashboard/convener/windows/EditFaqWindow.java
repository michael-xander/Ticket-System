package view.dashboard.convener.windows;

import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.faq.Faq;
import view.TicketSystemUI;
import view.dashboard.CreateWindow;

import java.time.LocalDate;

/**
 * Created by marcelo on 01-09-2015.
 */
public class EditFaqWindow extends CreateWindow {
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
        setSaveButtonFunction();
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
    public void setSaveButtonFunction() {
        getSaveButton().setCaption("Update");
        getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSaveButton().setComponentError(null);
                if(inputIsValid())
                {
                    faq.setQuestion(question.getValue());
                    faq.setAnswer(richTextArea.getValue());
                    faq.setDate(LocalDate.now());
                    faq.setCourseID(faq.getCourseID());

                    TicketSystemUI.getDaoFactory().getFaqDao().updateFaq(faq);
                    Notification notification = new Notification("Faq Updated", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDelayMsec(2500);
                    notification.setDescription("Your faq has successfully been updated.");
                    notification.show(Page.getCurrent());

                    close();
                    UI.getCurrent().getNavigator().navigateTo(faq.getCourseID() + " FAQs");
                }
                else
                {
                    getSaveButton().setComponentError(new UserError("Input provided is invalid"));
                }
            }
        });
    }

    @Override
    public boolean inputIsValid() {
        boolean isValid = true;
        question.setComponentError(null);
        richTextArea.setComponentError(null);

        if(question.getValue().isEmpty())
        {
            isValid = false;
            question.setComponentError(new UserError("A question has to be specified for an FAQ"));
        }

        if(richTextArea.getValue().trim().isEmpty())
        {
            isValid = false;
            richTextArea.setComponentError(new UserError("An answer has to be specified for an FAQ"));
        }
        return isValid;
    }
}
