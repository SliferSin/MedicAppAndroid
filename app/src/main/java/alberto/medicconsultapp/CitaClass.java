package alberto.medicconsultapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Ashto on 14/03/2017.
 */

public class CitaClass {
    private String dni_Paciente;
    private String dni_Medico;
    private String data;

    private static String driverDB= "org.postgresql.Driver";
    private static String urlDB = "jdbc:postgresql://192.168.1.11:5432/db_TFG";
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

    public CitaClass(String dniPaciente, String data){
        this.dni_Paciente = dniPaciente;
        //this.dni_Medico = "";
        this.data = data;
    }

    public void setCita(CitaClass cita){

        String stsql = "UPDATE tbl_cita SET dni_pacient = ? where data = ?";
        PreparedStatement st;

        try {
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB, userDB, passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1, cita.getDni_Paciente());
            st.setString(2, cita.getdata());
            st.executeUpdate();

            st.close();
            conn.close();
        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
    }

    public void searchMedico(String dni_paciente){
        String stsql = "SELECT dni_metge FROM tbl_medicopaciente WHERE dni_pacient = ?";
        PreparedStatement st;
        ResultSet rs;

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB,userDB,passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1,dni_paciente);
            rs = st.executeQuery();
            if(rs.next()){
                this.setDni_Medico(rs.getString("dni_metge"));
            }
            st.close();
            conn.close();
        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
    }

    public Boolean searchCita(String data){
        String stsql = "select * from tbl_cita cit  " +
                       "join tbl_medicopaciente medpac on cit.dni_metge = medpac.dni_metge " +
                       "where medpac.dni_pacient = ?";// and cit.data = ?";
        String stsql2;
        PreparedStatement st;
        ResultSet rs;

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB,userDB,passDB);
            stsql2 = stsql;
            st = conn.prepareStatement(stsql2 + "and cit.data = ?");
            st.setString(1,this.getDni_Paciente());
            st.setString(2,this.getdata());
            rs = st.executeQuery();
            if(rs.next()){
                //La fecha seleccionada esta disponible
            }
            else{
                conn.close();
                st.close();
                st = conn.prepareStatement(stsql + "and cit.data > ? limit 1");
                st.setString(1,this.getDni_Paciente());
                st.setString(2,this.getdata());
                rs = st.executeQuery();
                if(rs.next()){
                    //devuelve la fecha más cercana a la seleccionada
                }
                //Resultado nulo

            }

        }catch (SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
    }
}
