package com.example.application.View;

import com.example.application.Presenter.StartSessionPresenter;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("")
public class StartSessionView extends VerticalLayout {
    private StartSessionPresenter presenter;

    public StartSessionView(){
        presenter = new StartSessionPresenter(this);

    }

    public void startSession(String userID) {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("userID", List.of(userID));
        QueryParameters queryParameters = new QueryParameters(parameters);
        UI.getCurrent().access(() -> getUI().ifPresent(ui -> ui.navigate("MarketView", queryParameters)));
    }
}
