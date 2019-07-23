/*
 * Demostración del uso del JFileChooser.
 * Creado el: 23/07/2019 4:27 pm.
 * Versión: 1.0.
 */
package demostracionjfilechooser;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * Demostración del uso del JFileChooser.
 * @author Ingeniero en Computación: Ricardo Presilla.
 * @version 1.0.
 */
public class DemostracionJFileChooser extends JFrame {
    /**Muestra el contenido de la salida.*/
    private final JTextArea areaSalida;
    /***/
    private JScrollPane panel;
    /**Constructor.
     * 
     * @throws IOException 
     */
    public DemostracionJFileChooser() throws IOException {
        super("Demostración de JFileChooser");
        this.areaSalida = new JTextArea();
        this.panel=new JScrollPane(areaSalida);
        this.add(panel);
        this.analizarRuta();
    }
    /**
     * Muestra información acerca del archivo o directorio que especifica el 
     * usuario.
     * @throws IOException 
     */
    private void analizarRuta() throws IOException {
        Path ruta = obtenerRutaArchivoODirectorio();
        if (ruta != null && Files.exists(ruta)){
            /* recopila información sobre el archivo (o directorio)*/
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%s:%n", ruta.getFileName()));
            builder.append(String.format("%s un directorio%n",
            Files.isDirectory(ruta) ? "Es" : "No es"));
            builder.append(String.format("%s una ruta absoluta%n",
            ruta.isAbsolute() ? "Es" : "No es"));
            builder.append(String.format("Ultima modificacion: %s%n",
            Files.getLastModifiedTime(ruta)));
            builder.append(String.format("Tamanio: %s%n", Files.size(ruta)));
            builder.append(String.format("Ruta: %s%n", ruta));
            builder.append(String.format("Ruta absoluta: %s%n", ruta.toAbsolutePath()));
            if (Files.isDirectory(ruta)) /* muestra en pantalla el listado del directorio*/
            {
                builder.append(String.format("%nContenido del directorio:%n"));
                /* objeto para iterar a través del contenido de un directorio*/
                DirectoryStream<Path> flujoDirectorio = Files.newDirectoryStream(ruta);
                for (Path p : flujoDirectorio)
                    builder.append(String.format("%s%n", p));
            }
            areaSalida.setText(builder.toString()); /*muestra el contenido del objeto string */
        }
        else // El objeto Path no existe
        {
            JOptionPane.showMessageDialog(this, ruta.getFileName() +
            " no existe.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }// fin Metodo analizarRuta
    /** 
     * Permite al usuario especificar el nombre del archivo o directorio
     * @return Regresa el directorio como un objeto Path.
     */
    private Path obtenerRutaArchivoODirectorio(){
        // configura el diálogo para permitir la selección de un archivo o directorio
        JFileChooser selectorArchivos = new JFileChooser();
        selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int resultado = selectorArchivos.showOpenDialog(this);
        /* si el usuario hizo clic en el botón Cancelar en el cuadro de diálogo, regresa*/
        if (resultado == JFileChooser.CANCEL_OPTION)
            System.exit(1);
        // devuelve objeto Path que representa el archivo seleccionado
        return selectorArchivos.getSelectedFile().toPath();
    }//Fin método obtenerRutaArchivoODirectorio
    /**
     * Para ejecutar el proyecto.
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        DemostracionJFileChooser aplicacion = new DemostracionJFileChooser();
        aplicacion.setSize(400, 400);
        aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aplicacion.setVisible(true);
    }
    
}
