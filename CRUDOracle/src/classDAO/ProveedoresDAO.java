package classDAO;

import classVO.ProveedoresVO;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProveedoresDAO {

    public static String registrarProveedores(ProveedoresVO prov) {
        String result = null, last = null;
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        String sql = "INSERT INTO proveedores values(null,?,?,?,?)";
        try {
            pst = cn.prepareStatement(sql);
            pst.setString(1, prov.getNombre());
            pst.setString(2, prov.getTelefono());
            pst.setString(3, prov.getDireccion());
            pst.setString(4, prov.getEmail());
            pst.execute();
            pst = cn.prepareStatement("SELECT MAX(id_proveedor) AS id FROM proveedores");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                last = rs.getString(1);
            }
            result = "Proveedor registrado con exito, ID:" + last;
        } catch (SQLException e) {
            result = "Error en la consulta: " + e.getMessage();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    pst.close();
                }
            } catch (Exception e) {
                result = "Error: " + e;
            }
        }
        return result;
    }

    public static String actualizarProveedores(ProveedoresVO prov) {
        String result = null, last = null;
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE proveedores SET nombre=?,telefono=?,direccion=?,email=? WHERE id_proveedor=?";
        try {
            pst = cn.prepareStatement(sql);
            pst.setString(1, prov.getNombre());
            pst.setString(2, prov.getTelefono());
            pst.setString(3, prov.getDireccion());
            pst.setString(4, prov.getEmail());
            pst.setInt(5, prov.getId_proveedor());
            pst.execute();

            result = "Proveedor actualizado con exito, ID:" + prov.getId_proveedor();
        } catch (SQLException e) {
            result = "Error en la consulta: " + e.getMessage();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    pst.close();
                }
            } catch (Exception e) {
                result = "Error: " + e;
            }
        }
        return result;
    }

    public static ProveedoresVO buscarProveedores(String clave) {
        ProveedoresVO prov = new ProveedoresVO();
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT * FROM proveedores WHERE id_proveedor = ?";
        try {
            pst = cn.prepareStatement(sql);
            pst.setString(1, clave);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                prov.setId_proveedor(Integer.parseInt(rs.getString(1)));
                prov.setNombre(rs.getString(2));
                prov.setTelefono(rs.getString(3));
                prov.setDireccion(rs.getString(4));
                prov.setEmail(rs.getString(5));
            }
            prov.setResultado("Busqueda exitosa");
        } catch (SQLException e) {
            prov.setResultado("Error en la consulta: " + e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    pst.close();
                }
            } catch (Exception e) {
                prov.setResultado("Error: " + e);
            }
        }
        return prov;
    }

    public static String eliminarProveedores(String clave) {
        String result = null;
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        String sql = "DELETE FROM proveedores WHERE id_proveedor = ?";
        try {
            pst = cn.prepareStatement(sql);
            pst.setString(1, clave);
            pst.executeUpdate();
            result = "Proveedor eliminado con exito";
        } catch (SQLException e) {
            result = "Error en la consulta: " + e.getMessage();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    pst.close();
                }
            } catch (Exception e) {
                result = "Error: " + e;
            }
        }
        return result;
    }

    public static ArrayList<ProveedoresVO> getListProveedores() {
        ArrayList<ProveedoresVO> arrProv = new ArrayList<ProveedoresVO>();
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        ProveedoresVO prov = null;
        String sql = "SELECT * FROM proveedores";
        try {
            pst = cn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                prov = new ProveedoresVO();
                prov.setId_proveedor(Integer.parseInt(rs.getString(1)));
                prov.setNombre(rs.getString(2));
                if (arrProv.isEmpty()) {
                    arrProv.add(0, prov);
                } else {
                    arrProv.add(prov);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    pst.close();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        return arrProv;
    }
}
