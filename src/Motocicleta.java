public class Motocicleta extends Vehiculo {
    private int cilindraje;

    public Motocicleta(String placa, String marca, String modelo,
            double tarifaDiaria, int cilindraje) {

        super(placa, marca, modelo, tarifaDiaria);
        this.cilindraje = cilindraje;
    }

    @Override
    public double calcularCosto(int dias) {
        double costo = tarifaDiaria * dias;

        if (cilindraje > 250) {
            costo = costo + 75;
        }

        return costo;
    }

    @Override
    public String describir() {
        return "Motocicleta | Cilindraje: " + cilindraje + " cc";
    }
}