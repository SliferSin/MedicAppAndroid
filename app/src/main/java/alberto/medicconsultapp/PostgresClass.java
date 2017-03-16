package alberto.medicconsultapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Ashto on 13/03/2017.
 */

public class PostgresClass implements BaseDatos {
    private static String driverDB= "org.postgresql.Driver";
    private static String typeDB = "jdbc:postgresql";
    private static String urlDB = "://192.168.1.10:5432/db_TFG";
    private static String userDB = "postgres";
    private static String passDB = "password";

    public Connection conectar(){
        Connection conn = null;
        try{
            Class.forName(driverDB);
            conn = DriverManager.getConnection(typeDB+urlDB,userDB,passDB);

        }catch (SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }
        catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return conn;
    }

    public boolean consultarLogin(String dni,String pass,Connection conn ){
        String stsql = "SELECT password FROM tbl_usuari WHERE dni = ?";
        PreparedStatement st; //Como PreparedStatement deja poner los valores al a variable ? antes de enviar la consulta
        ResultSet rs;
        boolean verificado = false;
        try{
            //Statement st = conn.createStatement();
            st = conn.prepareStatement(stsql);
            st.setString(1,dni);
            rs = st.executeQuery();

            if(rs.next()){ //Comprobar condición
                if(rs.toString() == pass)
                    verificado = true;
            }

        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+se.toString());
        }
        return verificado;
    }
}
