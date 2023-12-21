package io.github.lestegii.todo.component;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NavigationBar extends HorizontalLayout {

    private final H1 title = new H1("TODO");
    private final DrawerToggle drawerToggle = new DrawerToggle();

    public NavigationBar() {
        addClassName("navigation-bar");

        init();
    }

    private void init() {
        title.addClassNames("text-2xl", "text-bold", "m-m");

        // Alignment and sizing of the components
        setDefaultVerticalComponentAlignment(Alignment.CENTER);
        setWidthFull();
        expand(title);
        addClassNames("py-0", "px-m", "bg-base", "shadow-s", "text-l", "text-bold", "text-primary");

        add(drawerToggle, title);

    }

}
