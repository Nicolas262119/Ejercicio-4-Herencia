import java.util.HashMap;

public class RentaMovil {
    private HashMap<String, Vehiculo> flota;
    private double ingresosAcumulados;

    public RentaMovil() {
        flota = new HashMap<String, Vehiculo>();
        ingresosAcumulados = 0;
    }

    public boolean registrarVehiculo(Vehiculo nuevoVehiculo) {
        String placa = nuevoVehiculo.getPlaca().toUpperCase();

        if (placa.isEmpty() || flota.containsKey(placa)) {
            return false;
        }

        flota.put(placa, nuevoVehiculo);
        return true;
    }

    public Vehiculo buscarPorPlaca(String placa) {
        return flota.get(placa.toUpperCase());
    }

    public String cotizar(String placa, int dias) {
        Vehiculo encontrado = buscarPorPlaca(placa);

        if (encontrado == null) {
            return "No existe un vehiculo con esa placa.";
        }

        if (dias <= 0) {
            return "La cantidad de dias debe ser mayor que cero.";
        }

        double costo = encontrado.calcularCosto(dias);

        return encontrado.toString()
                + "\nDias solicitados: " + dias
                + "\nCosto total: Q" + String.format("%.2f", costo);
    }

    public boolean confirmarAlquiler(String placa, int dias) {
        Vehiculo encontrado = buscarPorPlaca(placa);

        if (encontrado == null) {
            return false;
        }

        if (!encontrado.isDisponible() || dias <= 0) {
            return false;
        }

        double costo = encontrado.calcularCosto(dias);

        ingresosAcumulados = ingresosAcumulados + costo;
        encontrado.marcarOcupado();

        return true;
    }

    public boolean registrarDevolucion(String placa) {
        Vehiculo encontrado = buscarPorPlaca(placa);

        if (encontrado == null) {
            return false;
        }

        if (encontrado.isDisponible()) {
            return false;
        }

        encontrado.marcarDisponible();
        return true;
    }

    public String consultarFlota() {
        if (flota.isEmpty()) {
            return "No hay vehiculos registrados.";
        }

        String resultado = "";

        for (Vehiculo actual : flota.values()) {
            resultado = resultado + actual.toString() + "\n";
        }

        return resultado;
    }

    public String reporteGeneral() {
        int totalAutomoviles = 0;
        int disponiblesAutomoviles = 0;

        int totalMotocicletas = 0;
        int disponiblesMotocicletas = 0;

        int totalCamionetas = 0;
        int disponiblesCamionetas = 0;

        for (Vehiculo actual : flota.values()) {
            if (actual instanceof Automovil) {
                totalAutomoviles++;

                if (actual.isDisponible()) {
                    disponiblesAutomoviles++;
                }
            } else if (actual instanceof Motocicleta) {
                totalMotocicletas++;

                if (actual.isDisponible()) {
                    disponiblesMotocicletas++;
                }
            } else if (actual instanceof CamionetaCarga) {
                totalCamionetas++;

                if (actual.isDisponible()) {
                    disponiblesCamionetas++;
                }
            }
        }

        String reporte = "REPORTE GENERAL\n";

        reporte = reporte
                + "Automoviles: " + totalAutomoviles
                + " | Disponibles: " + disponiblesAutomoviles
                + " | Alquilados: "
                + (totalAutomoviles - disponiblesAutomoviles)
                + "\n";

        reporte = reporte
                + "Motocicletas: " + totalMotocicletas
                + " | Disponibles: " + disponiblesMotocicletas
                + " | Alquiladas: "
                + (totalMotocicletas - disponiblesMotocicletas)
                + "\n";

        reporte = reporte
                + "Camionetas de carga: " + totalCamionetas
                + " | Disponibles: " + disponiblesCamionetas
                + " | Alquiladas: "
                + (totalCamionetas - disponiblesCamionetas)
                + "\n";

        reporte = reporte
                + "Ingresos acumulados: Q"
                + String.format("%.2f", ingresosAcumulados);

        return reporte;
    }
}