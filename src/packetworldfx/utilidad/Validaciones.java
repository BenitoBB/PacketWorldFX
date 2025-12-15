package packetworldfx.utilidad;

import java.net.URLEncoder;
import java.text.Normalizer;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

/**
 *
 * @authors Ohana & Benito
 */
public class Validaciones {

    @SafeVarargs
    public static boolean validarCamposObligatorios(TextField[] campos, ComboBox<?>... combos) {
        for (TextField campo : campos) {
            if (campo.getText() == null || campo.getText().trim().isEmpty()) {
                Utilidades.mostrarAlertaSimple(
                        "Campos requeridos",
                        "Por favor, completa todos los campos obligatorios.",
                        Alert.AlertType.WARNING
                );
                campo.requestFocus();
                return false;
            }
        }

        for (ComboBox<?> combo : combos) {
            if (combo.getValue() == null) {
                Utilidades.mostrarAlertaSimple(
                        "Campos requeridos",
                        "Por favor, selecciona todas las opciones obligatorias.",
                        Alert.AlertType.WARNING
                );
                combo.requestFocus();
                return false;
            }
        }

        return true;
    }

    // Validar que un campo obligatorio no esté vacío
    public static boolean campoObligatorio(TextField campo, String mensaje) {
        if (campo.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campo requerido", mensaje, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    // Formatear solo letras
    public static void soloLetras(TextField campo) {
        campo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*")) {
                return change;
            }
            return null;
        }));
    }

    // Formatear solo números
    public static void soloNumeros(TextField campo) {
        campo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    // Solo números decimales
    public static void soloNumerosDecimales(TextField campo) {
        campo.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[0-9.]*")) {
                return change;
            }
            return null;
        }));
    }

    // Números positivos
    public static boolean validarNumeroPositivo(TextField campo, String nombreCampo) {
        try {
            double valor = Double.parseDouble(campo.getText().trim());
            if (valor <= 0) {
                Utilidades.mostrarAlertaSimple(
                        "Valor inválido",
                        nombreCampo + " debe ser mayor a cero",
                        Alert.AlertType.WARNING
                );
                campo.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Utilidades.mostrarAlertaSimple(
                    "Valor inválido",
                    nombreCampo + " debe ser un número válido",
                    Alert.AlertType.WARNING
            );
            campo.requestFocus();
            return false;
        }
        return true;
    }

    // Dimensiones positivas
    public static boolean validarDimensiones(
            TextField peso,
            TextField alto,
            TextField ancho,
            TextField profundidad) {

        return validarNumeroPositivo(peso, "Peso")
                && validarNumeroPositivo(alto, "Alto")
                && validarNumeroPositivo(ancho, "Ancho")
                && validarNumeroPositivo(profundidad, "Profundidad");
    }

    // Formatear letras y números
    public static void letrasYNumeros(TextField tf) {
        tf.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[a-zA-Z0-9 ]*")) {
                return change;
            }
            return null;
        }));
    }

    // Validar teléfono
    public static boolean validarTelefono(TextField campo) {
        String telefono = campo.getText().trim();
        if (!telefono.matches("\\d{10}")) {
            Utilidades.mostrarAlertaSimple(
                    "Teléfono inválido",
                    "El teléfono debe contener exactamente 10 números",
                    Alert.AlertType.WARNING
            );
            return false;
        }
        return true;
    }

    // Validar correo
    public static boolean validarCorreo(TextField campo) {
        String correo = campo.getText().trim();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!correo.matches(regex)) {
            Utilidades.mostrarAlertaSimple(
                    "Correo inválido",
                    "El correo electrónico no tiene un formato válido",
                    Alert.AlertType.WARNING
            );
            return false;
        }
        return true;
    }

    // Validar CURP
    public static boolean validarCurp(TextField campo) {
        String curp = campo.getText().trim();
        if (!curp.matches("[A-Za-z0-9]{18}")) {
            Utilidades.mostrarAlertaSimple(
                    "CURP inválida",
                    "La CURP debe tener 18 caracteres alfanuméricos",
                    Alert.AlertType.WARNING
            );
            return false;
        }
        return true;
    }

    // Validar campo opcional según condición
    public static boolean validarCondicional(TextField campo, boolean condicion, String mensaje) {
        if (condicion && campo.getText().trim().isEmpty()) {
            Utilidades.mostrarAlertaSimple("Campo requerido", mensaje, Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    // Retornar texto o null
    public static String textoONull(TextField campo) {
        String texto = campo.getText();
        return (texto == null || texto.trim().isEmpty()) ? null : texto.trim();
    }

    // Validar año lógico
    public static boolean validarAnio(TextField campo) {
        String texto = campo.getText().trim();
        if (texto.isEmpty()) {
            return false;
        }

        try {
            int anio = Integer.parseInt(texto);
            int anioActual = java.time.Year.now().getValue();
            if (anio < 1990 || anio > anioActual + 1) {
                Utilidades.mostrarAlertaSimple(
                        "Año inválido",
                        "El año debe estar entre 1990 y " + (anioActual + 1),
                        Alert.AlertType.WARNING
                );
                campo.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            Utilidades.mostrarAlertaSimple(
                    "Año inválido",
                    "El año debe ser un número válido",
                    Alert.AlertType.WARNING
            );
            campo.requestFocus();
            return false;
        }
        return true;
    }

    // Número de Identificación Interna 
    public static String generarNII(int anio, String vin) {
        if (vin == null) {
            vin = "";
        }
        return anio + (vin.length() >= 4 ? vin.substring(0, 4) : vin);
    }

    // Normalizar tipo de unidad 
    public static String normalizarTipoUnidad(String tipo) {
        if (tipo == null) {
            return null;
        }

        return Normalizer.normalize(tipo, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String codificarURL(String texto) {
        try {
            return URLEncoder.encode(texto, "UTF-8");
        } catch (Exception e) {
            return texto.replace(" ", "%20");
        }
    }

}
