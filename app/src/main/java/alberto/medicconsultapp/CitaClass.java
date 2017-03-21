package alberto.medicconsultapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Created by Ashto on 14/03/2017.
 */

public class CitaClass {
    private String dni_Paciente;
    private String dni_Medico;
    private String data;

    private static String driverDB= "org.postgresql.Driver";
    private static String typeDB = "jdbc:postgresql";
    private static String urlDB = "://192.168.1.10:5432/db_TFG";
    private static String userDB = "postgres";
    private static String passDB = "password";


    public String getDni_Paciente(){
        return this.dni_Paciente;
    }
    public String getDni_Medico(){
        return this.dni_Medico;
    }
    public String getdata(){return this.data; }
    public void setDni_Paciente(String dni){
        this.dni_Paciente = dni;
    }
    public void setDni_Medico(String dni){
        this.dni_Medico = dni;
    }

    public void CitaClass(String dniPaciente, String data){
        this.dni_Paciente = dniPaciente;
        this.data = data;
    }

    public void setCita(String data){
        this.data = data;
        String stsql = "UPDATE tbl_cita SET dni_pacient = ? where data = ?";
        PreparedStatement st;

        try {
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(typeDB + urlDB, userDB, passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1, this.getDni_Paciente());
            st.setString(2,this.getdata());
            st.executeUpdate();

        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }

    }
}
