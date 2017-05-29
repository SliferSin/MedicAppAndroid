package alberto.medicconsultapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class CitaClass {
    private String dni_Paciente;
    private String dni_Medico;
    private String data;

    private static String driverDB= "org.postgresql.Driver";
    private static String urlDB = "jdbc:postgresql://192.168.1.10:5432/db_TFG";
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
    public void setData(String data){this.data = data;}

    public CitaClass(String dniPaciente, String data){
        this.dni_Paciente = dniPaciente;
        this.dni_Medico = "";
        this.data = data;
    }

    /***
     * Actualiza la tbl_cita con el dni del usuario que hizo la solicitud de cita
     * @param cita Objecto CitaClass de donde sacaremos la información
     */
    public void setCita(CitaClass cita){

        String stsql = "UPDATE tbl_cita SET dni_pacient = ? where data = ?";
        PreparedStatement st;

        try {
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB, userDB, passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1, cita.getDni_Paciente());
            st.setTimestamp(2, Timestamp.valueOf(this.getdata()));
            st.executeUpdate();

            st.close();
            conn.close();
        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
    }

    /***
     * Busca el médico asociado al paciente
     * @param dni_paciente String que contiene el dni del usuario
     */
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

    /***
     * Busca la fecha más cercana a la seleccionada por el usuario
     * @param data String con la fecha incluida la hora
     * @return
     */
    public String searchNearestCita(String data){
        String stsql = "select * from tbl_cita cit  " +
                        "join tbl_medicopaciente medpac on cit.dni_metge = medpac.dni_metge " +
                        "where medpac.dni_pacient = ? and cit.data > ? limit 1";
        PreparedStatement st;
        ResultSet rs;

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB,userDB,passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1,this.getDni_Paciente());
            st.setTimestamp(2, Timestamp.valueOf(this.getdata()));
            rs = st.executeQuery();

            if(rs.next()){
                //La fecha seleccionada esta disponible y la devolvemos
                return rs.getString("data");
            }
            else{
                return null;
            }

        }catch (SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return null;
    }

    /***
     * Busca si al fecha solicitada esta disponible
     * @param data String con la fecha a buscar
     * @return booleano según se ha podido reservar o no
     */
    public Boolean searchCita(String data){
        String stsql = "select * from tbl_cita cit  " +
                       "join tbl_medicopaciente medpac on cit.dni_metge = medpac.dni_metge " +
                       "where medpac.dni_pacient = ? and cit.data = ?";
        PreparedStatement st;
        ResultSet rs;

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB,userDB,passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1,this.getDni_Paciente());
            st.setTimestamp(2, Timestamp.valueOf(this.getdata()));
            rs = st.executeQuery();

            if(rs.next()){
                //La fecha seleccionada esta disponible
                return true;
            }
            else{
                return false;
            }

        }catch (SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return null;
    }
}