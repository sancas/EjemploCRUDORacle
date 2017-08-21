package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    Connection conectar = null;
    public ConexionBD(){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conectar = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.24:1521:xe","usuario1","donbosco");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public Connection getConnection(){
        return conectar;
    }
    public void desconectar(){
        conectar = null;
    }
}
