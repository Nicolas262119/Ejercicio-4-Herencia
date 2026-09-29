public class CamionetaCarga extends Vehiculo {
    private double capacidadToneladas;

    public CamionetaCarga(String placa, String marca, String modelo,
            double tarifaDiaria, double capacidadToneladas) {

        super(placa, marca, modelo, tarifaDiaria);
        this.capacidadToneladas = capacidadToneladas;
    }

    @Override
    public double calcularCosto(int dias) {
        double costoBase = tarifaDiaria * dias;
        double recargo = 100 * capacidadToneladas * dias;

        return costoBase + recargo;
    }

    @Override
    public String describir() {
        return "Camioneta de carga | Capacidad: "
                + capacidadToneladas + " toneladas";
    }
}