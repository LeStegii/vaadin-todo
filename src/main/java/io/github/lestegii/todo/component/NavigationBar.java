package io.github.lestegii.todo.component;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;

public class NavigationBar extends HorizontalLayout {

    private final H1 title = new H1("TODO");
    private final DrawerToggle drawerToggle = new DrawerToggle();
    private final Button logoutButton = new Button("Logout", VaadinIcon.SIGN_OUT.create());

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

        // Logout button
        logoutButton.addClickListener(click -> fireEvent(new LogoutButtonEvent(this, false)));

        add(drawerToggle, title, logoutButton);

    }

    public Registration addLogoutButtonListener(ComponentEventListener<LogoutButtonEvent> listener) {
        return addListener(LogoutButtonEvent.class, listener);
    }


    public static class LogoutButtonEvent extends ComponentEvent<NavigationBar> {
        public LogoutButtonEvent(NavigationBar source, boolean fromClient) {
            super(source, fromClient);
        }
    }

}
