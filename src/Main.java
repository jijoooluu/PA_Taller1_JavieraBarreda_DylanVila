import ucn.ArchivoSalida;
import ucn.Registro;
import ucn.StdIn;
import ucn.StdOut;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        int cantidadBombas = 4;
        double precioLitro = 1293;
        double descuento = 0.15;
        double totalPagar = 0;
        int totalClientes = 0;
        int totalMiembros = 0;
        double totalLitrosVendidos = 0;
        double totalDineroRecaudado = 0;

        //listas para uso de clientes
        String[] ruts = new String[1000];
        String[] nombres = new String[1000];
        double[] ahorros = new double[1000];
        boolean[] esMiembro = new boolean[1000];
        int[] usoBombas = new int[cantidadBombas];
        double[] bombas = {500, 500, 500, 500};


        //Contadores
        int bombaMasUsada = -99999;
        double litrosMoto = 0;
        double litrosAuto = 0;
        double litrosCamioneta = 0;
        double litrosCamion = 0;
        double litrosBus = 0;
        double litros = 0;


        ArchivoSalida arch = new ArchivoSalida("Estadisticas.txt");


        int opcion;
        while (true) {
            StdOut.println("============================");
            StdOut.println("=       BIENVENIDOS A      =");
            StdOut.println("=       COMBUSTIBLES       =");
            StdOut.println("=         MC QUEEN         =");
            StdOut.println("============================");
            StdOut.println("==     MENU PRINCIPAL     ==");
            StdOut.println("============================");
            StdOut.println("= [1].Cargar Combustible   =");
            StdOut.println("= [2].Ver Miembros         =");
            StdOut.println("= [3].Estadisticas         =");
            StdOut.println("= [4].Salir                =");
            StdOut.println("============================");
            StdOut.println("== SELECCIONE UNA OPCION: ==");
            StdOut.println("============================");
            opcion = StdIn.readInt();

            if (opcion == 1) {
                StdOut.println("Ingrese su RUT:");
                String rut = StdIn.readLine();

                // Buscar si el cliente está registrado
                int indiceCliente = -1;
                for (int i = 0; i < totalClientes; i++) {

                    if (ruts[i].equals(rut)) {
                        indiceCliente = i;
                        break;
                    }
                }

                // Si no está registrado
                if (indiceCliente == -1) {
                    StdOut.println("Usted no es miembro. ¿Desea registrarse? (SI/NO)");
                    String registro = StdIn.readLine();
                    if (registro.equalsIgnoreCase("si") || registro.equalsIgnoreCase("SI")) {
                        StdOut.println("Ingrese su nombre:");
                        String nombre = StdIn.readLine();
                        ruts[totalClientes] = rut;
                        nombres[totalClientes] = nombre;
                        ahorros[totalClientes] = 0;
                        esMiembro[totalClientes] = true;
                        indiceCliente = totalClientes;
                        totalMiembros++;
                        StdOut.println("Se ha registrado exitosamente. Se le cobrará $2500 de membresía.");
                        totalClientes++;

                    } else if (registro.equalsIgnoreCase("no") || registro.equalsIgnoreCase("NO")) {
                        ruts[totalClientes] = rut;
                        nombres[totalClientes] = "Usuario";
                        ahorros[totalClientes] = 0;
                        esMiembro[totalClientes] = false;
                        indiceCliente = totalClientes;
                        totalClientes++;

                    } else {
                        StdOut.println("Opcion no valida. Intente de nuevo.");
                        continue;

                    }
                    StdOut.println("ESTADO DE LAS BOMBAS:");
                    for (int i = 0; i < cantidadBombas; i++) {
                        String estado = (bombas[i] < 10) ? "NO DISPONIBLE" : bombas[i] + "litros";
                        StdOut.println("Bomba" + (i + 1) + ":" + estado);

                    }

                    StdOut.println("SELECCIONE UNA BOMBA (1-2-3-4)");
                    int bomba = StdIn.readInt() - 1;

                    if (bomba < 0 || bomba >= cantidadBombas) {
                        StdOut.println("Número de bomba inválido. Intente de nuevo.");
                        continue;
                    }

                    StdOut.println("Ingrese tipo de vehículo (moto,auto, camioneta, camión/bus)");
                    String tipoVehiculo = StdIn.readLine();

                    if (tipoVehiculo.equals("moto")) {
                        litrosMoto += litros;
                    } else if (tipoVehiculo.equals("auto")) {
                        litrosAuto += litros;
                    } else if (tipoVehiculo.equals("camioneta")) {
                        litrosCamioneta += litros;
                    } else if (tipoVehiculo.equals("bus") || tipoVehiculo.equals("camion")) {
                        litrosBus += litros;
                        litrosCamion += litros;
                    } else {
                        StdOut.println("Tipo de vehículo no valido. Intentelo nuevamente.");
                        continue;
                    }


                    StdOut.println("Ingrese cantidad de litros a cargar:");
                    litros = StdIn.readDouble();
                    totalLitrosVendidos += litros;
                    totalDineroRecaudado += totalPagar;

                    if (litros > bombas[bomba]) {
                        StdOut.println("No hay suficiente combustible en la bomba. Intente con otra bomba.");
                        continue;
                    }
                    double precioTotal = litros * precioLitro;
                    double descuentoTotal = esMiembro[indiceCliente] ? precioTotal * descuento : 0;
                    totalPagar = precioTotal - descuentoTotal;
                    bombas[bomba] -= litros;
                    totalLitrosVendidos += litros;
                    totalDineroRecaudado += totalPagar;
                    usoBombas[bomba]++;

                    if (esMiembro[indiceCliente]) {
                        ahorros[indiceCliente] += descuentoTotal;

                    }


                    StdOut.println("------BOLETA------");
                    String nombreMiembro = nombres[indiceCliente];
                    StdOut.println("Nombre: " + nombreMiembro);
                    StdOut.println("Rut: " + rut);

                    StdOut.println("Litros cargados: " + litros);
                    StdOut.println("Tipo de vehículo: " + tipoVehiculo);
                    StdOut.println("Precio por litro: $" + precioLitro);
                    StdOut.println("Precio total: $" + precioTotal);
                    StdOut.println("Descuento aplicado: $" + descuentoTotal);
                    StdOut.println("Total pagar: $" + totalPagar);
                    StdOut.println("Gracias por su compla. ¡Vuelva pronto!");
                }
                //Registro: Ver miembros:
            } else if (opcion == 2) {
                if (totalClientes == 0) {
                    StdOut.println("No hay miembros registrados");
                }  else {
                    // Ordenar por ahorro de mayor a menor
                    for (int i = 0; i < totalClientes - 1; i++) {
                        for (int j = 0; j < totalClientes - i - 1; j++) {
                            if (ahorros[j] < ahorros[j + 1]) {
                                // Intercambiar ahorros
                                double contAhorro = ahorros[j];
                                ahorros[j] = ahorros[j + 1];
                                ahorros[j + 1] = contAhorro;

                                // Intercambiar nombres
                                String contNombre = nombres[j];
                                nombres[j] = nombres[j + 1];
                                nombres[j + 1] = contNombre;

                                // Intercambiar ruts
                                String contRut = ruts[j];
                                ruts[j] = ruts[j + 1];
                                ruts[j + 1] = contRut;

                                // Intercambiar estado de miembro
                                boolean contEsMiembro = esMiembro[j];
                                esMiembro[j] = esMiembro[j + 1];
                                esMiembro[j + 1] = contEsMiembro;
                            }
                        }
                    }

                    StdOut.println("Miembros registrados ordenados por ahorro:");
                    for (int i = 0; i < totalClientes; i++) {
                        if(esMiembro[i]) { // solo se muestran miembros
                            StdOut.println("Nombre: " + nombres[i] + " - Ahorro: $" + ahorros[i]);
                        }
                    }
                }

            } else if (opcion == 3) {
                StdOut.println("----ESTADÍSTICAS----");
                StdOut.println("Porcentaje de miembros:" + (totalClientes > 0 ? ((double) totalMiembros / totalClientes) * 100 : 0) + "%");
                String vehiculoMasUsado = "moto";
                double maxCarga = litrosMoto;
                if (litrosAuto> maxCarga) {
                    maxCarga = litrosAuto;
                    vehiculoMasUsado = "auto";
                }
                if (litrosCamioneta > maxCarga) {
                    maxCarga = litrosCamioneta;
                    vehiculoMasUsado = "camioneta";
                }
                if (litrosCamion > maxCarga) {
                    maxCarga = litrosCamion;
                    vehiculoMasUsado = "camion/bus";
                }
                if (litrosBus > maxCarga){
                    maxCarga = litrosBus;
                    vehiculoMasUsado = "camion/bus";
                }
                StdOut.println("Vehículo que más cargó:" + vehiculoMasUsado);
                bombaMasUsada = 0;
                for (int i = 1; i < usoBombas.length; i++) {
                    if (usoBombas[i] > usoBombas[bombaMasUsada]) {
                        bombaMasUsada = i;
                    }
                }
                StdOut.println("Bomba más usada: " + (bombaMasUsada + 1));
                StdOut.println("Litros Vendidos:" + (totalLitrosVendidos));
                StdOut.println("Dinero recaudado: " + (totalDineroRecaudado));


            } else if (opcion == 4) {
                StdOut.println("Gracias por visitarnos. ¡Vuelva pronto!");

                Registro regSal = new Registro(4);
                regSal.agregarCampo(totalLitrosVendidos / (totalClientes > 0 ? totalClientes: 1) * 100 + "%");
                regSal.agregarCampo(bombaMasUsada);
                regSal.agregarCampo(totalLitrosVendidos);
                regSal.agregarCampo(totalDineroRecaudado);

                arch.writeRegistro(regSal);
                arch.close();

                break; // Termina el bucle while

            } else {
                StdOut.println("Opción no válida. Intente de nuevo..");


            }
        }
    }
}