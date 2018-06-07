package application.demo.ui.layouts.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import application.demo.service.NewsService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import application.demo.domain.News;

public class NewsView extends VerticalLayout implements View {
	private static final long serialVersionUID = 1L;

	public static final String NAME = "NewsView";

	private Panel panel;
	private TextField subject;
	private TextArea content;
	
	SimpleDateFormat sdf = new SimpleDateFormat("E , d MMMMM yyyy , hh:mm ");
	
	ArrayList<News> news = NewsService.getAllNews();

	VerticalLayout vLayout = new VerticalLayout();
	VerticalLayout leftVLayout = new VerticalLayout();
	VerticalLayout rightVLayout = new VerticalLayout();
	HorizontalLayout hLayout = new HorizontalLayout();

	Button addButton = new Button("Add News");

	public  NewsView() {
		if(news == null) {
			news = new ArrayList<>();
		}

		Collections.reverse(news);
		vLayout = createLayout();

		ArrayList<Panel> panels = createNewsPanels();

		for (int i = 0; i < panels.size(); i++) {
			panels.get(i).setSizeFull();

			if (i % 2 == 0) {
				leftVLayout.addComponent(panels.get(i));
			} else {
				rightVLayout.addComponent(panels.get(i));
			}
		}

		hLayout.addComponents(leftVLayout, rightVLayout);
		hLayout.setComponentAlignment(leftVLayout, Alignment.BOTTOM_LEFT);
		hLayout.setComponentAlignment(rightVLayout, Alignment.BOTTOM_RIGHT);
		hLayout.setSizeFull();
		hLayout.setSpacing(true);

		leftVLayout.setSpacing(true);
		rightVLayout.setSpacing(true);

		vLayout.addComponent(hLayout);
		vLayout.setMargin(true);
		vLayout.setSpacing(true);

		this.addComponent(vLayout);
		this.setSpacing(true);
		this.setMargin(true);
	}

	private VerticalLayout createLayout() {
		VerticalLayout result = new VerticalLayout();
		
		CustomLayout customLayout = new CustomLayout("CarouselLayout");
		customLayout.setSizeFull();
		result.addComponent(customLayout);
		
		result.addComponent(addButton);
		addButton.setIcon(FontAwesome.PLUS_CIRCLE);
		addButton.addStyleName(ValoTheme.BUTTON_SMALL);
		addButton.setStyleName(ValoTheme.BUTTON_BORDERLESS);
		addButton.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Window subWindow = new Window("Add News");
				AddNewsPopup popup = new AddNewsPopup();

				UI.getCurrent().addWindow(popup);
			}
		});
		
		return result;
	}

	private ArrayList<Panel> createNewsPanels() {
		ArrayList<Panel> result = new ArrayList<Panel>();
		
		for (News n : news) {
			subject = new TextField();
			content = new TextArea();
			subject.setValue(n.getSubject());

			String text = "WRITTEN BY : " + n.getPublisher() + "\n" + sdf.format(n.getDate())
								+ "\n\n" + n.getContent(); 
			content.setValue(text);
			content.setSizeFull();

			panel = new Panel(n.getSubject());
			panel.setContent(content);
			panel.addStyleName("my-class");
			// panel.setSizeFull();
			 panel.setScrollTop(Integer.MAX_VALUE);
			panel.setWidth("50%");
			panel.setHeight("50%");

			result.add(panel);
		}
		
		return result;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
