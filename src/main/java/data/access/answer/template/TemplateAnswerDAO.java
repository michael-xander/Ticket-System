package data.access.answer.template;

import model.domain.answer.template.TemplateAnswer;

import java.util.List;

/**
 * A DAO interface for template answer
 * Created by Michael on 2015/09/20.
 */
public interface TemplateAnswerDAO {
    TemplateAnswer getTemplateAnswer(int ID);
    void addTemplateAnswer(TemplateAnswer templateAnswer);
    void updateTemplateAnswer(TemplateAnswer templateAnswer);
    List<TemplateAnswer> getTemplateAnswersForUser(String userID);
    void deleteTemplateAnswer(TemplateAnswer templateAnswer);

}
