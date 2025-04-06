package org.vaadin.example;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.Tag;

@Tag("canvas")
@JsModule("./chartloader.js")
public class ChartJsWrapper extends Div {

    public ChartJsWrapper(String chartId, String jsonData) {
        setId(chartId);
        getElement().setAttribute("width", "900");
        getElement().setAttribute("height", "500");

        getElement().executeJs("window.loadChart($0, $1)", chartId, jsonData);
    }
}
