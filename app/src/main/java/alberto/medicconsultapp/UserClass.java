package alberto.medicconsultapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Ashto on 16/03/2017.
 */

public class UserClass {

    private static String driverDB= "org.postgresql.Driver";
    private static String typeDB = "jdbc:postgresql";
    private static String urlDB = "://192.168.1.10:5432/db_TFG";
    private static String userDB = "postgres";
    private static String passDB = "xeupeukoip";

    private static String dni;
    private static String password;

    public String getDni(){return this.dni;}
    public String getPassword(){return this.password;}

    //Constructor
    public UserClass(String dni, String password){
        this.dni = dni;
        this.password = password;
    }

    public void setDni(String dni){
        this.dni = dni;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public boolean consultarLogin(){
        String stsql = "SELECT password FROM tbl_usuari WHERE dni = ?";
        PreparedStatement st; //Como PreparedStatement deja poner los valores al a variable ? antes de enviar la consulta
        ResultSet rs;
        Statement s = null;
        boolean verificado = false;

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(typeDB+urlDB,userDB,passDB);
            //Statement st = conn.createStatement();
            st = conn.prepareStatement(stsql);
            st.setString(1,this.getDni());
            s = conn.createStatement();
            rs = s.executeQuery("SELECT password from tbl_usuari where dni = '1234'");
            //rs = st.executeQuery();


            if(rs.next()){ //No contiene el valor de la tabla que deberia
                if(rs.getString("password").equals(this.getPassword()))
                    verificado = true;
            }

        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return verificado;
    }

}
