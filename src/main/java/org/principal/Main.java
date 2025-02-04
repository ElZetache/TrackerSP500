package org.principal;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import objfinanzas.Indice;
import objfinanzas.EmpresaAnalisis;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Indice indx = new Indice();
        Scanner input = new Scanner(System.in);
        int opcion = -1;

        //para el desarrollo trabajare con el sp500, pero la idea es que se pueda introducir cual quiere el usuario
        indx = rellenarIndice("sp500");
        System.out.println("Índice: " + indx.getNombre());
        System.out.println("Total de empresas: " + indx.getEmpresas().size());
        //for (Empresa empresa : indx.getEmpresas()) {
        //    System.out.println(empresa.getTicker() + " - " + empresa.getNombre());
        //}

        // indx.mostrarValores();
        while(opcion != 0){
        opcion = mostrarMenuPrincipal();
        switch (opcion){
            case 1:
                System.out.print("¿Cual es el PER maximo que quieres ver? : ");
                int per = Integer.parseInt(input.nextLine());
                indx.perMenor(per);
                break;
            case 2:
                System.out.print("¿Cual es el EV/EBITDA maximo que quieres ver? : ");
                int evEbitda = Integer.parseInt(input.nextLine());
                indx.evEbitdaMenor(evEbitda);
            default:
                break;
                }
        }

    }

    private static Indice rellenarIndice(String nombreIndice) {
        Indice res = new Indice();
        List<EmpresaAnalisis> empresaAnalises = new ArrayList<>();

        try {
            // Ruta al script de Python
            String pythonScriptPath = "scripts/datosempresa.py";

            // Ejecutar el script de Python
            ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath);
            Process process = pb.start();

            // Capturar la salida del script (el JSON)
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder jsonOutput = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonOutput.append(line);
            }

            // Esperar a que termine el proceso
            process.waitFor();

            // Convertir el JSON en objetos Java usando Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonOutput.toString());

            for (JsonNode node : rootNode) {
                EmpresaAnalisis empresaAnalisis = new EmpresaAnalisis();
                empresaAnalisis.setTicker(node.get("Ticker").asText());
                empresaAnalisis.setNombre(node.get("Empresa").asText());
                empresaAnalisis.setPer(node.get("PER").isNull() ? 0 : node.get("PER").asDouble());
                empresaAnalisis.setSector(node.get("Sector").asText());
                empresaAnalisis.setIndustria(node.get("Industria").asText());
                empresaAnalisis.setPrecioActual(node.get("Precio Actual").isNull() ? 0 : node.get("Precio Actual").asDouble());
                empresaAnalisis.setEvEbitda(node.get("EV/EBITDA").isNull() ? 0 : node.get("EV/EBITDA").asDouble());

                empresaAnalises.add(empresaAnalisis);
            }

            // Asignar las empresas al índice
            res.setNombre(nombreIndice);
            res.setEmpresas(empresaAnalises);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    private static int mostrarMenuPrincipal(){
        int res = -1;
        Scanner scanner = new Scanner(System.in);

        System.out.println("MENU");
        System.out.println("-------------------------------");
        System.out.println("1. Filtrar por PER");
        System.out.println("2. Filtrar por EV/Ebitda");

        while (res == -1) {  // El bucle seguirá pidiendo la edad hasta que sea un número válido
            System.out.println("Introduce una opcion:");

            // Intentar leer un número
            try {

                res = Integer.parseInt(scanner.nextLine());  // Intenta convertir la entrada a un número entero
                if (res < 0) {
                    System.out.println("Por favor, ingresa un número positivo.");
                    res = -1; // Reiniciar la entrada si el número es negativo
                }
            } catch (NumberFormatException e) {
                // Si el usuario no ingresa un número, se lanza una excepción NumberFormatException
                System.out.println("¡Error! Por favor, ingresa un número válido.");
            }
        }

        return res;
    }





}