package view.dashboard.student.views;


import com.vaadin.event.ItemClickEvent;
import model.domain.faq.Faq;
import view.TicketSystemUI;
import view.dashboard.UserFaqTableView;
import view.dashboard.student.windows.FaqInfoWindow;

/**
 * A view for the course convener to observe faqs for a single course
 * Created by Marcelo on 2015/08/18.
 */
public abstract class StudentFaqTableView extends UserFaqTableView
{
    public StudentFaqTableView()
    {
        super.setTable(buildTable());

        super.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                int faqID = (Integer) itemClickEvent.getItemId();
                Faq faq = TicketSystemUI.getDaoFactory().getFaqDao().getFaq(faqID);
                getUI().addWindow(new FaqInfoWindow(faq));
            }
        });
    }
}
