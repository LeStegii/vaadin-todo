package io.github.lestegii.todo.util;

import com.vaadin.flow.component.Component;
import io.github.lestegii.todo.view.AboutView;
import io.github.lestegii.todo.view.AdminView;
import io.github.lestegii.todo.view.TodoView;

import java.util.Map;

public class Constants {

    public static final Map<String, Class<? extends Component>> ROUTES = Map.of(
            "List", TodoView.class,
            "About", AboutView.class,
            "Admin", AdminView.class
    );

}
