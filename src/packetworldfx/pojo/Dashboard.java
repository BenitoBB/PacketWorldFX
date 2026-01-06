package packetworldfx.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @authors Ohana & Benito
 */
public class Dashboard {
    public class DashboardResumen {

        private int totalEnvios;
        private int totalClientes;
        private int totalColaboradores;
        private int enviosEntregados;
        private BigDecimal ingresosTotales;

        public DashboardResumen() {
        }

        public int getTotalEnvios() {
            return totalEnvios;
        }

        public int getTotalClientes() {
            return totalClientes;
        }

        public int getTotalColaboradores() {
            return totalColaboradores;
        }

        public int getEnviosEntregados() {
            return enviosEntregados;
        }

        public BigDecimal getIngresosTotales() {
            return ingresosTotales;
        }

        public void setTotalEnvios(int totalEnvios) {
            this.totalEnvios = totalEnvios;
        }

        public void setTotalClientes(int totalClientes) {
            this.totalClientes = totalClientes;
        }

        public void setTotalColaboradores(int totalColaboradores) {
            this.totalColaboradores = totalColaboradores;
        }

        public void setEnviosEntregados(int enviosEntregados) {
            this.enviosEntregados = enviosEntregados;
        }

        public void setIngresosTotales(BigDecimal ingresosTotales) {
            this.ingresosTotales = ingresosTotales;
        }
    }

    public class DashboardConteo {

        private String etiqueta;
        private int total;

        public DashboardConteo() {
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        public int getTotal() {
            return total;
        }

        public void setEtiqueta(String etiqueta) {
            this.etiqueta = etiqueta;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public class DashboardEnvio {

        private String numeroGuia;
        private Date fechaEnvio;
        private BigDecimal costo;

        public DashboardEnvio() {
        }

        public String getNumeroGuia() {
            return numeroGuia;
        }

        public Date getFechaEnvio() {
            return fechaEnvio;
        }

        public BigDecimal getCosto() {
            return costo;
        }

        public void setNumeroGuia(String numeroGuia) {
            this.numeroGuia = numeroGuia;
        }

        public void setFechaEnvio(Date fechaEnvio) {
            this.fechaEnvio = fechaEnvio;
        }

        public void setCosto(BigDecimal costo) {
            this.costo = costo;
        }   
    }
}
