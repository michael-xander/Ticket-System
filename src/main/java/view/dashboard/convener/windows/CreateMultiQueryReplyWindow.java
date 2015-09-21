package view.dashboard.convener.windows;

import com.vaadin.data.Property;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import model.domain.answer.template.TemplateAnswer;
import model.domain.message.Query;
import model.domain.message.Reply;
import view.TicketSystemUI;
import view.dashboard.CreateWindow;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A window that enables a user to reply to multiple queries
 * Created by Michael on 2015/09/12.
 */
public class CreateMultiQueryReplyWindow extends CreateWindow {
    private final Collection<Query> queries;
    private RichTextArea richTextArea;
    private ComboBox templateComboBox;
    private List<TemplateAnswer> templates;

    public CreateMultiQueryReplyWindow(final Collection<Query> queries)
    {
        this.queries = queries;
        templates = TicketSystemUI.getDaoFactory().getTemplateAnswerDAO().getTemplateAnswersForUser(getUser().getUserID());

        setCaption("Create Reply");
        setModal(true);
        setClosable(false);
        setResizable(false);
        setWidth("50%");
        setContent(buildContent());
        setSaveButtonFunction();
    }

    @Override
    public void setSaveButtonFunction()
    {
        getSaveButton().setCaption("Reply");
        getSaveButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                getSaveButton().setComponentError(null);
                if(inputIsValid())
                {
                    Reply reply = new Reply();
                    reply.setText(richTextArea.getValue().trim());
                    reply.setSender(getUser().getUserID());
                    reply.setDate(LocalDate.now());

                    Set<Integer> queryIds = new HashSet<>();
                    Query tempQuery = null;
                    for(Query query : queries)
                    {
                        queryIds.add(query.getMessageID());
                        query.setStatus(Query.Status.REPLIED);
                        TicketSystemUI.getDaoFactory().getQueryDao().updateQueryRole(query);
                        tempQuery = query;
                    }

                    reply.setQueryIds(queryIds);
                    TicketSystemUI.getDaoFactory().getReplyDao().addReply(reply);

                    Notification notification = new Notification("Reply sent", Notification.Type.HUMANIZED_MESSAGE);
                    notification.setDescription("Your reply has successfully been submitted.");
                    notification.setDelayMsec(2500);
                    notification.show(Page.getCurrent());
                    UI.getCurrent().getNavigator().navigateTo(tempQuery.getCourseID());
                    close();
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
        richTextArea.setComponentError(null);

        if(richTextArea.getValue().trim().isEmpty())
        {
            isValid = false;
            richTextArea.setComponentError(new UserError("A reply must be provided"));
        }

        return isValid;
    }

    @Override
    public Component buildContent() {
        VerticalLayout view = new VerticalLayout();
        view.setMargin(true);
        view.setSpacing(true);

        for(Query query : queries)
        {
            VerticalLayout tempView = new VerticalLayout();
            tempView.setSpacing(true);

            Label author = new Label("Query by: " + query.getSenderID());
            author.addStyleName(ValoTheme.LABEL_H2);
            author.addStyleName(ValoTheme.LABEL_NO_MARGIN);
            tempView.addComponent(author);

            Label title = new Label("Subject: " + query.getSubject());
            title.addStyleName(ValoTheme.LABEL_H3);
            title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
            tempView.addComponent(title);

            Label content = new Label(query.getText());
            tempView.addComponent(content);

            view.addComponent(tempView);
        }

        templateComboBox = new ComboBox("Templates");
        templateComboBox.setWidth("100%");
        for(TemplateAnswer templateAnswer : templates)
        {
            templateComboBox.addItem(templateAnswer.getID());
            templateComboBox.setItemCaption(templateAnswer.getID(), templateAnswer.getQuestion());
        }
        templateComboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {

                if (valueChangeEvent.getProperty().getValue() == null) {
                    richTextArea.setValue("");
                } else {
                    for (TemplateAnswer templateAnswer : templates) {
                        if (valueChangeEvent.getProperty().getValue().equals(templateAnswer.getID())) {
                            richTextArea.setValue(templateAnswer.getAnswer());
                        }
                    }
                }
            }
        });
        view.addComponent(templateComboBox);

        richTextArea = new RichTextArea("Reply");
        richTextArea.setWidth("100%");
        view.addComponent(richTextArea);

        view.addComponent(buildFooter());
        return view;
    }
}
