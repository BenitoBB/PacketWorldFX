package packetworldfx;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import packetworldfx.dominio.DashboardImp;
import packetworldfx.pojo.Dashboard.DashboardConteo;
import packetworldfx.pojo.Dashboard.DashboardEnvio;
import packetworldfx.pojo.Dashboard.DashboardResumen;

/**
 * FXML Controller class
 *
 * @authors Ohana & Benito
 */
public class FXMLDashboardController implements Initializable {

    @FXML
    private Label lblTotalEnvios;
    @FXML
    private Label lbTotalClientes;
    @FXML
    private Label lbTotalColaboradores;
    private PieChart pieEnviosEstatus;
    private BarChart<String, Number> barEnvioSucursal;
    @FXML
    private TableView<DashboardEnvio> tvUltimosEnvios;
    @FXML
    private TableColumn<DashboardEnvio, String> colNumeroGuia;
    @FXML
    private TableColumn<DashboardEnvio, String> colFecha;
    @FXML
    private TableColumn<DashboardEnvio, Number> colCosto;
    @FXML
    private Label lbTotalEnviosEntregados;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarResumen();
        configurarTabla();
        cargarUltimosEnvios();
    }

    private void cargarResumen() {
        DashboardResumen resumen = DashboardImp.obtenerResumen();

        if (resumen != null) {
            lblTotalEnvios.setText(String.valueOf(resumen.getTotalEnvios()));
            lbTotalClientes.setText(String.valueOf(resumen.getTotalClientes()));
            lbTotalColaboradores.setText(String.valueOf(resumen.getTotalColaboradores()));
            lbTotalEnviosEntregados.setText(String.valueOf(resumen.getEnviosEntregados()));
        }
    }

    private void configurarTabla() {
        colNumeroGuia.setCellValueFactory(
                new PropertyValueFactory<>("numeroGuia")
        );

        colFecha.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return new javafx.beans.property.SimpleStringProperty(
                    sdf.format(cellData.getValue().getFechaEnvio())
            );
        });

        colCosto.setCellValueFactory(
                new PropertyValueFactory<>("costo")
        );
    }

    private void cargarUltimosEnvios() {
        List<DashboardEnvio> lista = DashboardImp.obtenerUltimosEnvios();

        if (lista != null) {
            ObservableList<DashboardEnvio> datos
                    = FXCollections.observableArrayList(lista);

            tvUltimosEnvios.setItems(datos);
        }
    }

}
