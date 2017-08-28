package classDAO;

import classVO.DiscosVO;
import conexion.ConexionBD;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscosDAO {
     public static String registrarDisco(DiscosVO discoVO) {
        String result = null, last = null;
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        String sql = "INSERT INTO usuario1.discos VALUES(null,?,?,?,?,?)";
        try {
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                pst.setString(1, discoVO.getNombre());
                pst.setString(2, discoVO.getAutor());
                pst.setString(3, discoVO.getGenero());
                pst.setDouble(4, discoVO.getPrecio());
                pst.setInt(5, discoVO.getId_proveedor());
                pst.executeUpdate();
                pst = cn.prepareStatement("SELECT MAX(id_disco) AS id FROM usuario1.discos");
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    last = rs.getString(1);
                }
                result = "Disco registrado con exito, ID: " + last;
            }
        } catch (SQLException e) {
            result = "Error durante el registro: " + e.getMessage();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    pst.close();
                }
            } catch (Exception e) {
                result = "Error " + e;
            }
        }
        return result;
    }

    public static String actualizarDisco(DiscosVO discoVO) {
        String result = null;
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        String sql = "UPDATE usuario1.discos SET nombre=?,autor=?,genero=?,precio=?,id_proveedor=? WHERE id_disco=?";
        try {
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                pst.setString(1, discoVO.getNombre());
                pst.setString(2, discoVO.getAutor());
                pst.setString(3, discoVO.getGenero());
                pst.setDouble(4, discoVO.getPrecio());
                pst.setInt(5, discoVO.getId_proveedor());
                pst.setInt(6, discoVO.getId_disco());
                pst.executeUpdate();
                result = "Disco Actualizado con exito, ID: " + discoVO.getId_disco();
            }
        } catch (SQLException e) {
            result = "Error durante el registro: " + e.getMessage();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    pst.close();
                }
            } catch (Exception e) {
                result = "Error " + e;
            }
        }
        return result;
    }

    public static DiscosVO buscarDisco(String clave) {
        DiscosVO discoVO = new DiscosVO();
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        String sql = "SELECT * FROM usuario1.discos WHERE id_disco = ?";
        try {
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(clave));
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    discoVO.setId_disco(Integer.parseInt(rs.getString(1)));
                    discoVO.setNombre(rs.getString(2));
                    discoVO.setAutor(rs.getString(3));
                    discoVO.setGenero(rs.getString(4));
                    discoVO.setPrecio(Double.parseDouble(rs.getString(5)));
                    discoVO.setId_proveedor(Integer.parseInt(rs.getString(6)));
                }
                discoVO.setResultado("Busqueda exitosa");
            }
        } catch (SQLException e) {
            discoVO.setResultado("Error en la consulta: " + e.getMessage());
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    pst.close();
                }
            } catch (Exception e) {
                discoVO.setResultado("Error " + e);
            }
        }
        return discoVO;
    }
    
    public static String eliminarDisco(String clave) {
        String result = null;
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        String sql = "DELETE FROM usuario1.discos WHERE id_disco = ?";
        try {
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                pst.setInt(1, Integer.parseInt(clave));
                pst.executeUpdate();
                result = "Disco eliminado con exito";
            }
        } catch (SQLException e) {
            result = "Error en la consulta: " + e.getMessage();
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                    pst.close();
                }
            } catch (Exception e) {
                result = "Error " + e;
            }
        }
        return result;
    }
    
    public static ArrayList<DiscosVO> getListDiscos(){
        ArrayList<DiscosVO> arrDiscos = new ArrayList<DiscosVO>();
        ConexionBD cc = new ConexionBD();
        Connection cn = cc.getConnection();
        PreparedStatement pst = null;
        DiscosVO discoVO = null;
        String sql = "SELECT * FROM usuario1.discos";
        try {
            if (cn != null) {
                pst = cn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while(rs.next()) {
                    discoVO = new DiscosVO();
                    discoVO.setId_disco(Integer.parseInt(rs.getString(1)));
                    discoVO.setNombre(rs.getString(2));
                    discoVO.setAutor(rs.getString(3));
                    discoVO.setPrecio(Double.parseDouble(rs.getString(5)));
                    arrDiscos.add(discoVO);
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
                System.out.println("Error " + e);
            }
        }
        return arrDiscos;
    }
}
