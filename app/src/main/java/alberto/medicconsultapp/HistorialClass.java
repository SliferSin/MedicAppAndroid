package alberto.medicconsultapp;

/**
 * Created by Ashto on 02/05/2017.
 */

public class HistorialClass {
    private String data;
    private String dni_paciente;

    private static String driverDB= "org.postgresql.Driver";
    private static String urlDB = "jdbc:postgresql://192.168.1.10:5432/db_TFG";
    private static String userDB = "postgres";
    private static String passDB = "password";

    /*Constructor*/
    public void HistorialClass(String dni,String data){
        this.dni_paciente = dni;
        this.data = data;
    }
    public String getDni(){
        return this.dni_paciente;
    }
    public String getDataHistorial(){
        return this.data;
    }

    public String getHistorial(String dni){
        
    }
}
