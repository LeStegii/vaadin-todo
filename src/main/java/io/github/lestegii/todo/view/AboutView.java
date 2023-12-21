package io.github.lestegii.todo.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@PageTitle("About | Todo")
@Route(value = "about", layout = MainView.class)
@PermitAll
public class AboutView extends VerticalLayout {

    H1 header = new H1("About");

    public AboutView() {
        addClassName("about-view");

        init();
    }

    private void init() {
        header.addClassNames("text-bold");

        add(
                header,
                new Text(
                        "This is a simple todo app built with Vaadin and Spring Boot. It has been made as a part of a term paper about 'Vaadin Flow'."
                )
        );
    }
}
