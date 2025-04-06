package org.vaadin.example;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.dependency.JsModule;
import elemental.json.JsonObject;
import elemental.json.impl.JsonUtil;
import com.vaadin.flow.component.AttachEvent;
import org.vaadin.example.Canvas;
import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.AttachEvent;
import org.springframework.web.client.RestTemplate;


@Route("dashboard")
@JsModule("./chartloader.js")
public class TestAccessView extends VistaProtegida {

    private final Canvas canvas;

    public TestAccessView() {
        setSpacing(true);
        setPadding(true);

        H2 title = new H2("Evolución de clics por campaña");

        canvas = new Canvas();
        canvas.setId("grafico");
        canvas.getElement().getStyle().set("width", "800px");
        canvas.getElement().getStyle().set("height", "400px");

        add(title, canvas);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        try {
            String correo = SessionData.getCorreo();
            RestTemplate restTemplate = new RestTemplate();
            CampaignStatsDTO[] datos = restTemplate.getForObject(
                    "http://localhost:8080/api/resultados/estadisticas?correo=" + correo,
                    CampaignStatsDTO[].class
            );

            if (datos == null) return;

            JsonObject config = Json.createObject();
            config.put("type", "bar");

            JsonObject data = Json.createObject();
            JsonArray labels = Json.createArray();
            JsonArray clicSeries = Json.createArray();
            JsonArray totalSeries = Json.createArray();

            for (int i = 0; i < datos.length; i++) {
                labels.set(i, datos[i].getNombreCampana());
                clicSeries.set(i, datos[i].getClics());
                totalSeries.set(i, datos[i].getTotal());
            }

            data.put("labels", labels);

            JsonArray datasets = Json.createArray();

            JsonObject clicDataset = Json.createObject();
            clicDataset.put("label", "Clics");
            clicDataset.put("backgroundColor", "rgba(255, 99, 132, 0.5)");
            clicDataset.put("data", clicSeries);
            datasets.set(0, clicDataset);

            JsonObject totalDataset = Json.createObject();
            totalDataset.put("label", "Total");
            totalDataset.put("backgroundColor", "rgba(54, 162, 235, 0.5)");
            totalDataset.put("data", totalSeries);
            datasets.set(1, totalDataset);

            data.put("datasets", datasets);
            config.put("data", data);

            getElement().executeJs("loadChart($0, $1)", canvas.getElement(), config);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

