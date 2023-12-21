package io.github.lestegii.todo.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Admin | Todo")
@Route(value = "admin")
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {

        public AdminView() {
            addClassName("admin-view");

            init();
        }

        private void init() {
            add(
                    new H1("Admin view")
            );
        }

}
