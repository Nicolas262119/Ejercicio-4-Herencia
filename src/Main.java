import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RentaMovil negocio = new RentaMovil();
        int opcion;

        inicializarFlota(negocio);

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Seleccione una opcion: ");

            switch (opcion) {
                case 1:
                    registrarVehiculo(scanner, negocio);
                    break;

                case 2:
                    System.out.println("\nCATALOGO DE VEHICULOS");
                    System.out.println(negocio.consultarFlota());
                    break;

                case 3:
                    cotizarYAlquilar(scanner, negocio);
                    break;

                case 4:
                    registrarDevolucion(scanner, negocio);
                    break;

                case 5:
                    System.out.println("\n" + negocio.reporteGeneral());
                    break;

                case 6:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 6);

        scanner.close();
    }

    private static void inicializarFlota(RentaMovil negocio) {
        negocio.registrarVehiculo(
                new Automovil(
                        "AUT-101", "Toyota", "Corolla",
                        250, 5, false
                )
        );

        negocio.registrarVehiculo(
                new Automovil(
                        "AUT-102", "Honda", "Civic",
                        300, 5, true
                )
        );

        negocio.registrarVehiculo(
                new Motocicleta(
                        "MOT-201", "Honda", "CB190",
                        125, 190
                )
        );

        negocio.registrarVehiculo(
                new Motocicleta(
                        "MOT-202", "Yamaha", "MT-03",
                        175, 321
                )
        );

        negocio.registrarVehiculo(
                new CamionetaCarga(
                        "CAM-301", "Toyota", "Hilux",
                        200, 1.5
                )
        );

        negocio.registrarVehiculo(
                new CamionetaCarga(
                        "CAM-302", "Ford", "Ranger",
                        225, 2.0
                )
        );
    }

    private static void mostrarMenu() {
        System.out.println("\n===== RENTA MOVIL =====");
        System.out.println("1. Registrar vehiculo");
        System.out.println("2. Consultar flota");
        System.out.println("3. Cotizar o confirmar alquiler");
        System.out.println("4. Registrar devolucion");
        System.out.println("5. Ver reporte general");
        System.out.println("6. Salir");
    }

    private static void registrarVehiculo(
            Scanner scanner, RentaMovil negocio) {

        System.out.println("\nTIPOS DE VEHICULO");
        System.out.println("1. Automovil");
        System.out.println("2. Motocicleta");
        System.out.println("3. Camioneta de carga");

        int tipo = leerEntero(
                scanner, "Seleccione el tipo: "
        );

        if (tipo < 1 || tipo > 3) {
            System.out.println("Tipo de vehiculo no valido.");
            return;
        }

        String placa = leerTexto(
                scanner, "Placa: "
        ).toUpperCase();

        if (placa.isEmpty()) {
            System.out.println(
                    "La placa no puede estar vacia."
            );
            return;
        }

        if (negocio.buscarPorPlaca(placa) != null) {
            System.out.println(
                    "Ya existe un vehiculo con esa placa."
            );
            return;
        }

        String marca = leerTexto(scanner, "Marca: ");
        String modelo = leerTexto(scanner, "Modelo: ");

        double tarifa = leerDecimalPositivo(
                scanner, "Tarifa diaria: Q"
        );

        Vehiculo nuevoVehiculo;

        if (tipo == 1) {
            int pasajeros = leerEnteroPositivo(
                    scanner, "Cantidad de pasajeros: "
            );

            String respuesta = leerTexto(
                    scanner,
                    "¿La transmision es automatica? (s/n): "
            );

            boolean automatica =
                    respuesta.equalsIgnoreCase("s");

            nuevoVehiculo = new Automovil(
                    placa,
                    marca,
                    modelo,
                    tarifa,
                    pasajeros,
                    automatica
            );

        } else if (tipo == 2) {
            int cilindraje = leerEnteroPositivo(
                    scanner, "Cilindraje: "
            );

            nuevoVehiculo = new Motocicleta(
                    placa,
                    marca,
                    modelo,
                    tarifa,
                    cilindraje
            );

        } else {
            double capacidad = leerDecimalPositivo(
                    scanner,
                    "Capacidad en toneladas: "
            );

            nuevoVehiculo = new CamionetaCarga(
                    placa,
                    marca,
                    modelo,
                    tarifa,
                    capacidad
            );
        }

        if (negocio.registrarVehiculo(nuevoVehiculo)) {
            System.out.println(
                    "Vehiculo registrado correctamente."
            );
        } else {
            System.out.println(
                    "No se pudo registrar el vehiculo."
            );
        }
    }

    private static void cotizarYAlquilar(
            Scanner scanner, RentaMovil negocio) {

        String placa = leerTexto(
                scanner, "Placa del vehiculo: "
        );

        if (negocio.buscarPorPlaca(placa) == null) {
            System.out.println(
                    "No existe un vehiculo con esa placa."
            );
            return;
        }

        int dias = leerEnteroPositivo(
                scanner, "Cantidad de dias: "
        );

        System.out.println("\nCOTIZACION");
        System.out.println(
                negocio.cotizar(placa, dias)
        );

        String respuesta = leerTexto(
                scanner,
                "¿Desea confirmar el alquiler? (s/n): "
        );

        if (respuesta.equalsIgnoreCase("s")) {
            if (negocio.confirmarAlquiler(placa, dias)) {
                System.out.println(
                        "Alquiler confirmado correctamente."
                );
            } else {
                System.out.println(
                        "El vehiculo no esta disponible."
                );
            }
        } else {
            System.out.println(
                    "Cotizacion cancelada. "
                    + "No se realizaron cambios."
            );
        }
    }

    private static void registrarDevolucion(
            Scanner scanner, RentaMovil negocio) {

        String placa = leerTexto(
                scanner, "Placa del vehiculo: "
        );

        if (negocio.buscarPorPlaca(placa) == null) {
            System.out.println(
                    "No existe un vehiculo con esa placa."
            );
        } else if (negocio.registrarDevolucion(placa)) {
            System.out.println(
                    "Devolucion registrada correctamente."
            );
        } else {
            System.out.println(
                    "El vehiculo ya estaba disponible."
            );
        }
    }

    private static String leerTexto(
            Scanner scanner, String mensaje) {

        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(
            Scanner scanner, String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(
                        scanner.nextLine()
                );
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe ingresar un numero entero."
                );
            }
        }
    }

    private static int leerEnteroPositivo(
            Scanner scanner, String mensaje) {

        int numero;

        do {
            numero = leerEntero(scanner, mensaje);

            if (numero <= 0) {
                System.out.println(
                        "El numero debe ser mayor que cero."
                );
            }
        } while (numero <= 0);

        return numero;
    }

    private static double leerDecimalPositivo(
            Scanner scanner, String mensaje) {

        while (true) {
            try {
                System.out.print(mensaje);

                double numero = Double.parseDouble(
                        scanner.nextLine()
                );

                if (numero > 0) {
                    return numero;
                }

                System.out.println(
                        "El numero debe ser mayor que cero."
                );

            } catch (NumberFormatException e) {
                System.out.println(
                        "Debe ingresar un numero valido."
                );
            }
        }
    }
}