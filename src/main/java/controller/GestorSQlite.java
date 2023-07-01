package controller;
import java.sql.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class GestorSQlite {

    private static Connection connection = null;
    private static Statement statement = null;
    private static String url = "jdbc:sqlite:src/main/java/basedatos/database.db";

    public static void ActualizarConexion() {
        try {
            connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ejecutarSQL(String sentenciaSQL) {
        try {
            ActualizarConexion();
            statement.execute(sentenciaSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD
    public static void insertarRegistro(String tabla, HashMap<String, String> nombre_valor)
    {
        String atributos = concatenarCampos(nombre_valor.keySet());
        String valores = concatenarCampos(nombre_valor.values());
        String insert = "INSERT INTO " + tabla + " (" + atributos + ") VALUES (" + valores + ")";
        ejecutarSQL(insert);
    }



    public static HashMap<String, String> obtenerRegistro(String tabla, String camposWhere, String valuesWhere)
    {
        HashMap<String, String> registros = new HashMap<>();
        String condicion = formarCondicionalWhere(camposWhere, valuesWhere);
        String select = "SELECT * FROM " + tabla + condicion;

        try {
            ActualizarConexion();
            ResultSet resultSet = statement.executeQuery(select);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnas = metaData.getColumnCount();

            while(resultSet.next()){
                for (int i = 1; i <= columnas; i++) {
                    String nombreCampo = metaData.getColumnName(i);
                    String valorCampo = resultSet.getString(nombreCampo);
                    registros.put(nombreCampo, valorCampo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return registros;
    }

    public static void eliminarRegistro(String tabla, String camposWhere, String valoresWhere)
    {
        String delete = "DELETE FROM " + tabla + formarCondicionalWhere(camposWhere, valoresWhere);
        ejecutarSQL(delete);
    }
    public static void modificarRegistro(String tabla, HashMap<String, String> nombre_valor)
    {
        String atributos = concatenarCampos(nombre_valor.keySet());
        String valores = concatenarCampos(nombre_valor.values());
        String update = "UPDATE " + tabla + " SET (" + atributos + ") VALUES (" + valores + ")";
        ejecutarSQL(update);
    }

    // Utilidades

    public static String concatenarCampos(Collection<String> campos){
        StringBuilder concatenado = new StringBuilder();
        for (String campo : campos) {
            concatenado.append(campo).append(", ");
        }
        concatenado.delete(concatenado.length() - 2, concatenado.length());
        return String.valueOf(concatenado);
    }

    public static HashMap<String, String> formarRegistrosHashMap(String campos, String valores){
        HashMap<String, String> hashmap = new HashMap<>();
        String[] keys = campos.split(",");
        String[] values = valores.split(",");

        for (int i = 0; i < keys.length; i++) {
            try{
                hashmap.put(keys[i], values[i]);
            } catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return hashmap;
    }

    public static String formarCondicionalWhere(String camposWhere, String valuesWhere){
        StringBuilder condicion = new StringBuilder();
        String[] campos = camposWhere.split(",");
        String[] valores = valuesWhere.split(",");

        if(camposWhere.isEmpty()){
            return "";
        }
        condicion.append(" WHERE ");
        for (int i = 0; i < campos.length; i++) {
            condicion.append(campos[i]).append(" = ");
            try{
                condicion.append(Integer.parseInt(valores[i]));
            } catch (NumberFormatException e){
                condicion.append("'").append(valores[i]).append("'");
            }
            condicion.append(" AND ");
        }
        condicion.delete(condicion.length() - 4, condicion.length());
        return String.valueOf(condicion);
    }

    public static int obtenerCantidadRegistros(String tabla) {
        int cantidad=0;
        try {
            String sql = "SELECT COUNT(*) AS cantidad FROM " + tabla;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                cantidad = resultSet.getInt("cantidad");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantidad;
    }
}