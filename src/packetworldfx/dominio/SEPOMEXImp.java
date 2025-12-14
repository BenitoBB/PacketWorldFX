package packetworldfx.dominio;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 *
 * @authors Ohana & Benito
 */
public class SEPOMEXImp {

    public static HashMap<String, Object> buscarPorCP(String cp) {

        HashMap<String, Object> respuesta = new HashMap<>();
        List<String> ciudades = new ArrayList<>();
        List<String> colonias = new ArrayList<>();
        String estado = null;

        try {
            InputStream is = SEPOMEXImp.class
                    .getResourceAsStream("/sepomex/CPdescarga.txt");

            if (is == null) {
                respuesta.put("error", true);
                respuesta.put("mensaje", "Archivo SEPOMEX no encontrado");
                return respuesta;
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8)
            );

            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {

                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] campos = linea.split("\\|");

                if (campos.length < 6) {
                    continue;
                }

                String cpArchivo = campos[0].trim();

                if (cpArchivo.equals(cp)) {

                    estado = campos[4].trim();
                    String ciudad = campos[3].trim();
                    String colonia = campos[1].trim();

                    if (!ciudades.contains(ciudad)) {
                        ciudades.add(ciudad);
                    }

                    if (!colonias.contains(colonia)) {
                        colonias.add(colonia);
                    }
                }
            }

            if (estado == null) {
                respuesta.put("error", true);
                respuesta.put("mensaje", "CP no encontrado");
            } else {
                respuesta.put("error", false);
                respuesta.put("estado", estado);
                respuesta.put("ciudades", ciudades);
                respuesta.put("colonias", colonias);
            }

        } catch (Exception e) {
            e.printStackTrace();
            respuesta.put("error", true);
            respuesta.put("mensaje", "Error al leer SEPOMEX");
        }

        return respuesta;
    }
}
