package io.github.lestegii.todo.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

@PageTitle("Login | Todo")
@Route("login")
@PermitAll
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm loginForm = new LoginForm();

	public LoginView() {
		addClassName("login-view");
		setSizeFull();

		init();
	}

	private void init() {
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		loginForm.setAction("login");
		loginForm.setForgotPasswordButtonVisible(true);
		loginForm.addForgotPasswordListener(event -> Notification.show("It's not that hard..."));

		loginForm.addLoginListener(event -> UI.getCurrent().getSession().setAttribute("username", event.getUsername()));

		add(new H1("TODOs"), loginForm);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
			loginForm.setError(true);
		}
	}
}
