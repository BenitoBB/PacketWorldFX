package packetworldfx.utilidad;

import java.net.URLEncoder;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
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

    // Método auxiliar para Número Guía
    private static String abreviar(String texto) {
        if (texto == null || texto.isEmpty()) {
            return "XXX";
        }

        texto = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toUpperCase();

        return texto.length() >= 3 ? texto.substring(0, 3) : texto;
    }

    // Fórmula para generar Número Guía
    public static String generarNumeroGuia(
            String remitente,
            String destinatario,
            String origen,
            String destino) {

        String rem = abreviar(remitente);
        String des = abreviar(destinatario);
        String ori = abreviar(origen);
        String dest = abreviar(destino);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String fecha = LocalDateTime.now().format(formato);

        int random = new Random().nextInt(900) + 100; // 3 dígitos

        return "PW-" + ori + "-" + dest + "-" + fecha + "-" + random;
    }

    public static boolean validarNumeroGuia(String guia) {
        if (guia == null || guia.isEmpty()) {
            Utilidades.mostrarAlertaSimple(
                    "Número de guía inválido",
                    "El número de guía no puede estar vacío",
                    Alert.AlertType.WARNING
            );
            return false;
        }

        String regex = "^PW-[A-Z]{3}-[A-Z]{3}-\\d{8}-\\d{6}-\\d{3}$";
        if (!guia.matches(regex)) {
            Utilidades.mostrarAlertaSimple(
                    "Número de guía inválido",
                    "El formato del número de guía no es válido",
                    Alert.AlertType.WARNING
            );
            return false;
        }

        return true;
    }
    
    // Fórmula para calcular el Costo Envío 
    public static double calcularCostoEnvio(
            double pesoKg,
            double alto,
            double ancho,
            double profundidad,
            int cantidadPaquetes,
            boolean zonaRural) {

        double tarifaBase = 50.0;
        double tarifaPeso = 12.5;       // por kg
        double tarifaVolumen = 0.02;    // cm³
        double recargoRural = 40.0;

        double volumen = alto * ancho * profundidad;

        double costo = tarifaBase
                + (pesoKg * tarifaPeso)
                + (volumen * tarifaVolumen);

        costo = costo * cantidadPaquetes;

        if (zonaRural) {
            costo += recargoRural;
        }

        return Math.round(costo * 100.0) / 100.0;
    }

}
