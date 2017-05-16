package alberto.medicconsultapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Ashto on 02/05/2017.
 */

public class HistorialClass {
    private String data;
    private String dni_paciente ;
    private String nom;
    private String cognoms;
    private int edat;
    private String enfermetat;
    private String observacions;

    private static String driverDB= "org.postgresql.Driver";
    private static String urlDB = "jdbc:postgresql://192.168.1.10:5432/db_TFG";
    private static String userDB = "postgres";
    private static String passDB = "password";

    /*Constructor*/
    public HistorialClass(String dni,String data){
        this.dni_paciente = dni;
        this.data = data + " 00:00:00";
        this.nom = "";
        this.cognoms = "";
        this.edat = 0;
        this.enfermetat = "";
        this.observacions = "";
    }
    public String getDni(){
        return this.dni_paciente;
    }
    public String getDataHistorial(){
        return this.data;
    }
    public String getNom(){return this.nom;}
    public String getCognoms(){return this.cognoms;}
    public String getEnfermetat(){return this.enfermetat;}
    public String getObservacions(){return this.observacions;}
    public int getEdat(){return this.edat;}

    public void setNom(String nom){
        this.nom = nom;
    }
    public void setCognoms(String cognoms){
        this.cognoms = cognoms;
    }
    public void setEdat(int edat){
        this.edat = edat;
    }
    public void setEnfermetat(String enfermetat){
        this.enfermetat = enfermetat;
    }
    public void setObservacions(String observacions){
        this.observacions = observacions;
    }


    /*** Hace la conexión y busca los datos para terminar de rellenar el objeto historial ***/
    public boolean fillHistorial(HistorialClass historial){
        String stsql = "SELECT u.nom,u.cognoms,u.edat,h.malaltia,h.observacions " +
                       "FROM tbl_historial h INNER JOIN tbl_usuari u ON(h.id_pacient = u.dni)" +
                       "WHERe h.data::date = ? and h.id_pacient = ?";
        PreparedStatement st;
        ResultSet rs;
        boolean correcto = false;

        try {
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB, userDB, passDB);
            st = conn.prepareStatement(stsql);
            st.setTimestamp(1, Timestamp.valueOf(historial.getDataHistorial()));
            st.setString(2, historial.getDni());
            rs = st.executeQuery();

            if(rs.next()){
                this.setNom(rs.getString("nom"));
                this.setCognoms(rs.getString("cognoms"));
                this.setEdat(rs.getInt("edat"));
                this.setEnfermetat(rs.getString("malaltia"));
                this.setObservacions(rs.getString("observacions"));
                correcto = true;
            }
            else correcto = false;

            st.close();
            conn.close();
        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return correcto;
    }
    public boolean searchHistorial(String dni, String data){
        String stsql = "SELECT * from tbl_historial WHERE id_pacient = ? and data::date = ?";
        PreparedStatement st;
        ResultSet rs;
        boolean encontrado = false;
        data = data + " 00:00:00";

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB, userDB, passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1, dni);
            st.setTimestamp(2, Timestamp.valueOf(data));
            rs = st.executeQuery();

            if(rs.next()){
                encontrado = true;
            }

            st.close();
            conn.close();
        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return encontrado;
    }
}