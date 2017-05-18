package alberto.medicconsultapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ashto on 16/03/2017.
 */

public class UserClass {

    private static String driverDB= "org.postgresql.Driver";
    private static String urlDB = "jdbc:postgresql://192.168.1.10:5432/db_TFG";
    private static String userDB = "postgres";
    private static String passDB = "password";

    private static String dni;
    private static String password;

    public String getDni(){return this.dni;}
    public String getPassword(){return this.password;}

    public void setDni(String dni){
        this.dni = dni;
    }
    public void setPassword(String password){
        this.password = password;
    }

    //Constructor
    public UserClass(String dni, String password){
        this.dni = dni;
        this.password = password;
    }

    /***
     * Comprueba que el password almacenado en el dni del paciente corresponde con el enviado
     * @return
     */
    public boolean consultarLogin(){
        String stsql = "SELECT password FROM tbl_usuari WHERE dni = ?";
        PreparedStatement st; //Como PreparedStatement deja poner los valores al a variable ? antes de enviar la consulta
        ResultSet rs;
        boolean verificado = false;

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB,userDB,passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1,this.getDni());

            rs = st.executeQuery();

            if(rs.next()){
                if(rs.getString("password").equals(this.getPassword()))
                    verificado = true;
            }
            st.close();
            conn.close();

        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return verificado;
    }
    public String getUserName(String dni){
        String stsql = "SELECT nom FROM tbl_usuari WHERE dni = ?";
        String name = "";
        PreparedStatement st;
        ResultSet rs;

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB,userDB,passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1,this.getDni());

            rs = st.executeQuery();
            name = rs.getString("nom");

            st.close();
            conn.close();
        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return name;
    }
}
