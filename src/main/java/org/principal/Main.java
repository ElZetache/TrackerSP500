package org.principal;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import objfinanzas.Indice;
import objfinanzas.Empresa;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Indice indx = new Indice();

        //para el desarrollo trabajare con el sp500, pero la idea es que se pueda introducir cual quiere el usuario
        indx = rellenarIndice("sp500");
        System.out.println("Índice: " + indx.getNombre());
        System.out.println("Total de empresas: " + indx.getEmpresas().size());
        for (Empresa empresa : indx.getEmpresas()) {
            System.out.println(empresa.getTicker() + " - " + empresa.getNombre());
        }
        indx.mostrarValores();

    }

    private static Indice rellenarIndice(String nombreIndice) {
        Indice res = new Indice();
        List<Empresa> empresas = new ArrayList<>();

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
                Empresa empresa = new Empresa();
                empresa.setTicker(node.get("Ticker").asText());
                empresa.setNombre(node.get("Empresa").asText());
                empresa.setPer(node.get("PER").isNull() ? 0 : node.get("PER").asDouble());
                empresa.setSector(node.get("Sector").asText());
                empresa.setIndustria(node.get("Industria").asText());
                empresa.setPrecioActual(node.get("Precio Actual").isNull() ? 0 : node.get("Precio Actual").asDouble());


                empresas.add(empresa);
            }

            // Asignar las empresas al índice
            res.setNombre(nombreIndice);
            res.setEmpresas(empresas);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    };

}