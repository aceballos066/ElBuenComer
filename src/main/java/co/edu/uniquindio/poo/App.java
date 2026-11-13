package co.edu.uniquindio.poo;

import co.edu.uniquindio.poo.model.EntregaSegura;
import co.edu.uniquindio.poo.model.RapidoExpress;
import co.edu.uniquindio.poo.model.Restaurante;
import co.edu.uniquindio.poo.viewController.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Aplicación JavaFX del restaurante El Buen Comer.
 */
public class App extends Application {

    private Stage primaryStage;

    public static Restaurante restaurante = new Restaurante(
            "El Buen Comer",
            "901234567-8",
            "Armenia, Quindío"
    );

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("El Buen Comer");
        inicializarData();
        openViewPrincipal();
    }

    public void inicializarData() {
        restaurante.agregarServicioDomicilio(new RapidoExpress());
        restaurante.agregarServicioDomicilio(new EntregaSegura());
    }

    public void openViewPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("primary.fxml"));

            VBox rootLayout = loader.load();

            PrimaryViewController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openProductos() {
        cargarVista("productos.fxml", ProductoViewController.class);
    }

    public void openClientes() {
        cargarVista("clientes.fxml", ClienteViewController.class);
    }

    public void openPedidos() {
        cargarVista("pedidos.fxml", PedidoViewController.class);
    }

    public void openVentas() {
        cargarVista("ventas.fxml", VentasViewController.class);
    }

    private <T> void cargarVista(String archivoFxml, Class<T> tipoController) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(archivoFxml));

            AnchorPane rootLayout = loader.load();

            Object controller = loader.getController();

            if (controller instanceof ProductoViewController productoController) {
                productoController.setApp(this);

            } else if (controller instanceof ClienteViewController clienteController) {
                clienteController.setApp(this);

            } else if (controller instanceof PedidoViewController pedidoController) {
                pedidoController.setApp(this);

            } else if (controller instanceof VentasViewController ventasController) {
                ventasController.setApp(this);
            }

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
