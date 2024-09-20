package org.example.demo4;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import static javafx.scene.control.TableView.TableViewSelectionModel;

/**
 * Clase principal que representa una aplicación JavaFX para gestionar una lista de personas.
 * Permite agregar, eliminar y restaurar filas en una tabla que muestra información sobre las personas.
 */
public class HelloApplication extends Application {
    // Campos para agregar los detalles de una persona (nombre, apellido, fecha de nacimiento)
    private TextField fNameField;  // Campo de texto para el nombre
    private TextField lNameField;  // Campo de texto para el apellido
    private DatePicker dobField;   // Selector de fecha para la fecha de nacimiento
    private TableView<Person> table;  // Tabla donde se mostrará la lista de personas

    /**
     * Método principal para lanzar la aplicación.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        Application.launch(args);  // Método principal para lanzar la aplicación
    }

    /**
     * Inicializa y muestra la ventana de la aplicación.
     * @param stage La etapa principal de la aplicación.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage stage) {
        // Inicializamos los campos de texto y la tabla
        fNameField = new TextField();
        lNameField = new TextField();
        dobField = new DatePicker();

        // Inicializamos la tabla con una lista de personas
        table = new TableView<>(PersonTableUtil.getPersonList());

        // Permitir la selección de múltiples filas en la tabla
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();
        tsm.setSelectionMode(SelectionMode.MULTIPLE);

        // Agregar columnas a la tabla (ID, nombre, apellido, fecha de nacimiento)
        table.getColumns().addAll(
                PersonTableUtil.getIdColumn(),
                PersonTableUtil.getFirstNameColumn(),
                PersonTableUtil.getLastNameColumn(),
                PersonTableUtil.getBirthDateColumn()
        );

        // Crear el panel de entrada para agregar nuevas personas
        GridPane newDataPane  = this.getNewPersonDataPane();

        // Botón para restaurar las filas eliminadas
        Button restoreBtn = new Button("Restore Rows");
        restoreBtn.setOnAction(e -> restoreRows());

        // Botón para eliminar las filas seleccionadas
        Button deleteBtn = new Button("Delete Selected Rows");
        deleteBtn.setOnAction(e -> deleteSelectedRows());

        // Crear un contenedor VBox para organizar el panel de entrada, los botones y la tabla
        VBox root = new VBox(newDataPane, new HBox(restoreBtn, deleteBtn), table);
        root.setSpacing(5);
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Crear la escena y mostrarla
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Adding/Deleting Rows in a TableViews");
        stage.show();
    }

    /**
     * Crea un panel de entrada para ingresar datos de una nueva persona.
     * @return Un objeto GridPane que contiene los campos de entrada y el botón "Add".
     */
    public GridPane getNewPersonDataPane() {
        GridPane pane = new GridPane();
        pane.setHgap(10);  // Espacio horizontal entre elementos
        pane.setVgap(5);   // Espacio vertical entre elementos

        // Agregamos campos y etiquetas al panel
        pane.addRow(0, new Label("First Name:"), fNameField);
        pane.addRow(1, new Label("Last Name:"), lNameField);
        pane.addRow(2, new Label("Birth Date:"), dobField);

        // Botón para agregar una nueva persona
        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> addPerson());

        // Agregamos el botón "Add" al panel
        pane.add(addBtn, 2, 0);
        return pane;
    }

    /**
     * Elimina las filas seleccionadas en la tabla.
     * Muestra un mensaje si no se selecciona ninguna fila.
     */
    public void deleteSelectedRows() {
        TableViewSelectionModel<Person> tsm = table.getSelectionModel();

        // Si no hay selección, mostramos un mensaje y retornamos
        if (tsm.isEmpty()) {
            System.out.println("Please select a row to delete.");
            return;
        }

        // Obtenemos los índices de las filas seleccionadas
        ObservableList<Integer> list = tsm.getSelectedIndices();
        Integer[] selectedIndices = new Integer[list.size()];
        selectedIndices = list.toArray(selectedIndices);

        // Ordenamos los índices de las filas seleccionadas
        Arrays.sort(selectedIndices);

        // Eliminamos las filas desde la última seleccionada hasta la primera
        for(int i = selectedIndices.length - 1; i >= 0; i--) {
            tsm.clearSelection(selectedIndices[i].intValue());
            table.getItems().remove(selectedIndices[i].intValue());
        }
    }

    /**
     * Restaura las filas originales en la tabla.
     */
    public void restoreRows() {
        table.getItems().clear();  // Limpiamos la tabla
        table.getItems().addAll(PersonTableUtil.getPersonList());  // Restauramos la lista original de personas
    }

    /**
     * Crea un objeto Person a partir de los campos de texto.
     * @return Un objeto Person con los datos ingresados.
     */
    public Person getPerson() {
        return new Person(fNameField.getText(), lNameField.getText(), dobField.getValue());
    }

    /**
     * Agrega una nueva persona a la tabla.
     * Limpia los campos de texto después de agregar la persona.
     */
    public void addPerson() {
        Person p = getPerson();  // Creamos una persona con los datos ingresados
        table.getItems().add(p);  // Añadimos la persona a la tabla
        clearFields();  // Limpiamos los campos de texto
    }

    /**
     * Limpia los campos de texto después de agregar una persona.
     */
    public void clearFields() {
        fNameField.setText(null);  // Limpiamos el campo de nombre
        lNameField.setText(null);  // Limpiamos el campo de apellido
        dobField.setValue(null);   // Limpiamos el campo de fecha de nacimiento
    }
}
