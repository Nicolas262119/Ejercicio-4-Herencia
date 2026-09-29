public abstract class Vehiculo {
    protected String placa;
    protected String marca;
    protected String modelo;
    protected double tarifaDiaria;
    protected boolean disponible;

    public Vehiculo(String placa, String marca, String modelo,
            double tarifaDiaria) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tarifaDiaria = tarifaDiaria;
        this.disponible = true;
    }

    public abstract double calcularCosto(int dias);

    public abstract String describir();

    public String getPlaca() {
        return placa;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void marcarOcupado() {
        disponible = false;
    }

    public void marcarDisponible() {
        disponible = true;
    }

    @Override
    public String toString() {
        String estado;

        if (disponible) {
            estado = "Disponible";
        } else {
            estado = "Alquilado";
        }

        return "Placa: " + placa
                + " | Marca: " + marca
                + " | Modelo: " + modelo
                + " | Tarifa diaria: Q"
                + String.format("%.2f", tarifaDiaria)
                + " | " + describir()
                + " | Estado: " + estado;
    }
}