package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.App;
import javafx.fxml.FXML;

/**
 * Controlador de la vista principal.
 */
public class PrimaryViewController {

    private App app;

    @FXML
    void onOpenProductos() {
        app.openProductos();
    }

    @FXML
    void onOpenClientes() {
        app.openClientes();
    }

    @FXML
    void onOpenPedidos() {
        app.openPedidos();
    }

    @FXML
    void onOpenVentas() {
        app.openVentas();
    }

    public void setApp(App app) {
        this.app = app;
    }
}