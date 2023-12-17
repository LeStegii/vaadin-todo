package io.github.lestegii.todo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

/**
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@PWA(name = "TODO", shortName = "TODO")
@Theme(value = "default")
public class AppShell implements AppShellConfigurator {

    @Override
    public void configurePage(AppShellSettings settings) {
        // TODO: Icon
    }


}
