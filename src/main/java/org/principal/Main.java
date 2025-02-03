package org.principal;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;


public class Main {
    public static void main(String[] args) {
        boolean tickersOk;
        List tickers;
        try {
            tickers = obtenerListaTickersEmpresas();
            tickersOk = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(tickersOk){
            Indice sp500 = rellenarIndiceInfo(tickers);
            sp500.mostrarValores();
        }



    }

    private static List obtenerListaTickersEmpresas() throws IOException {
        List<String> tickers = new ArrayList<>();
        String url = "https://en.wikipedia.org/wiki/List_of_S%26P_500_companies";

        Document doc = Jsoup.connect(url).get();
        Element table = doc.select("table.wikitable").first();

        if (table != null) {
            Elements rows = table.select("tbody tr"); // Obtener todas las filas de la tabla

            for (Element row : rows) {
                Elements columns = row.select("td"); // Obtener las columnas
                if (!columns.isEmpty()) {
                    String ticker = columns.get(0).text(); // Primera columna = Ticker
                    tickers.add(ticker);
                }

            }
        }

        return tickers;
    }

    private static Indice rellenarIndiceInfo(List ticker){
        Indice res = new Indice();

        for(int x = 0; x < ticker.size();x++){
            res.agregarValor(pyEmpresaDatos(ticker.get(x).toString()));
            out.println(x);
        }
        return res;
    }

    private static Valor pyEmpresaDatos(String ticker){
        String result = ""; // Para almacenar la salida del script Python

        try {
            // Ruta del script de Python
            String pythonScript = "scripts/datosempresa.py";  // Ruta relativa al script de Python
            String command = "python " + pythonScript + " " + ticker;  // Comando para ejecutar el script

            // Ejecutar el proceso que llama al script
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);

            // Obtener la salida del proceso
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                result += line + "\n"; // Concatenar la salida del script
            }

            process.waitFor(); // Esperar a que el script termine

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println("Salida del script Python: " + result);  // Para depurar la salida

        // Procesar la respuesta JSON usando Gson
        JsonObject jsonObject = JsonParser.parseString(result).getAsJsonObject();

        // Extraer los datos del JSON
        String empresa = jsonObject.has("empresa") ? jsonObject.get("empresa").getAsString() : "Desconocido";
        String peRatio = jsonObject.has("pe_ratio") ? jsonObject.get("pe_ratio").getAsString() : "N/A";
        String sector = jsonObject.has("sector") ? jsonObject.get("sector").getAsString() : "Desconocido";

        // Imprimir los resultados
        //System.out.println("Empresa: " + empresa);
        //System.out.println("P/E Ratio: " + peRatio);
        //System.out.println("Sector: " + sector);

        Valor valor = new Valor(ticker,empresa, Double.parseDouble(peRatio), sector);

        return valor;
    }
}