public class Automovil extends Vehiculo {
    private int pasajeros;
    private boolean transmisionAutomatica;

    public Automovil(String placa, String marca, String modelo,
            double tarifaDiaria, int pasajeros,
            boolean transmisionAutomatica) {

        super(placa, marca, modelo, tarifaDiaria);
        this.pasajeros = pasajeros;
        this.transmisionAutomatica = transmisionAutomatica;
    }

    @Override
    public double calcularCosto(int dias) {
        double costo = tarifaDiaria * dias;

        if (transmisionAutomatica) {
            costo = costo + (50 * dias);
        }

        return costo;
    }

    @Override
    public String describir() {
        String transmision;

        if (transmisionAutomatica) {
            transmision = "Automatica";
        } else {
            transmision = "Manual";
        }

        return "Automovil | Pasajeros: " + pasajeros
                + " | Transmision: " + transmision;
    }
}