package alberto.medicconsultapp;

import java.util.Date;

/**
 * Created by Ashto on 14/03/2017.
 */

public class CitaClass {
    private String dni_Paciente;
    private String dni_Medico;
    private Date data;


    public String getDni_Paciente(){
        return dni_Paciente;
    }
    public String getDni_Medico(){
        return dni_Medico;
    }
    public void setDni_Paciente(String dni){
        this.dni_Paciente = dni;
    }
    public void setDni_Medico(String dni){
        this.dni_Medico = dni;
    }
}
