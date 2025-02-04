import java.io.*;

public class TestPython {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", "C:\\Users\\ahuertab\\Documents\\sp500.py");
            pb.redirectErrorStream(true); // Captura errores también
            Process p = pb.start();

            // Leer salida del script
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            p.waitFor(); // Esperar a que termine
            System.out.println("JSON devuelto por Python: " + output.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
