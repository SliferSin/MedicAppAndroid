package alberto.medicconsultapp;

import java.sql.Connection;

/**
 * Created by Ashto on 13/03/2017.
 */

public interface BaseDatos {
    public Connection conectar(String url);
}
