package io.github.lestegii.todo.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import io.github.lestegii.todo.component.NavigationBar;
import io.github.lestegii.todo.util.Constants;

public class MainView extends AppLayout {

    private final NavigationBar header = new NavigationBar();
    private final VerticalLayout links = new VerticalLayout();

    public MainView() {
        init();
    }


    private void init() {
        addToNavbar(header);
        addToDrawer(links);

        // Drawer toggle button
        Constants.ROUTES.forEach((route, view) -> {
            RouterLink link = new RouterLink(route, view);
            link.setHighlightCondition(HighlightConditions.sameLocation());
            links.add(link);
        });
    }
}
