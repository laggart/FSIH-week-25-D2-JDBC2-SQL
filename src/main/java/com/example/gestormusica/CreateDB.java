package com.example.gestormusica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateDB {

    private static final String URL = "jdbc:mysql://localhost:3306/Cantantes";
    private static final String username = "root";
    private static final String password = "";
    
    public static void main(String[] args){
        
        
        // cargamos controlador JDBC (opcional moderno) pero buena práctica asegurarlo
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el controlador de MySQL");
            e.printStackTrace();
            return; // Salir si no se carga el controlador
        }
        
        try (Connection conexion1 = DriverManager.getConnection(URL, username, password);
            Statement statement = conexion1.createStatement()) {

                System.out.println("Conexión establecida con éxito");
            
            // Crear tabla

            String createTableSQL = "CREATE TABLE IF NOT EXISTS cantantes ("
                    + "id INT NOT NULL AUTO_INCREMENT, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "edad INT, "
                    + "genero VARCHAR(50), "
                    + "pais VARCHAR(100), "
                    + "fechaAlta DATE, "
                    + "PRIMARY KEY (id))";
                statement.executeUpdate(createTableSQL);

            // Crear una entrada
            insertCantante(conexion1, "Freddie Mercury", 45, "Rock", "Zanzibar", "2024-07-20");
            insertCantante(conexion1, "Ronnie James Dio", 67, "Heavy Metal", "USA", "2024-07-20");
            insertCantante(conexion1, "Robert Plant", 75, "Hard Rock", "U.K.", "2024-07-20");

            // Read la BBDD
            System.out.println("Lista de Cantantes");
            readCantantes(conexion1);
            
            // Update la BBDD
            updateCantante(conexion1, 13, "Janise Lyn Joplin", 27, "Blues", "USA", "2024-07-20");

            // Leer BBDD despues de actualizar
            System.out.println("Actualización realizada, la nueva lista queda así: ");
            readCantantes(conexion1);
            
            // Delete Borrar un elemento BBDD
            deleteCantante(conexion1, 13);
            
            // Leer BBDD despues de Eliminar 
            System.out.println("realizada, la nueva lista queda así: ");
            readCantantes(conexion1);

            
        }  catch(SQLException e){
            System.out.println("********* oh noooo, Error: Problema con la conexión a la base de datos ********");
            e.printStackTrace();
        }   catch(Exception e) {
            //Mensajes de error o acciones alternativas
            System.out.println("********* oh noooo, Error********");
            e.printStackTrace();
        } 
        
        
    }
    // Metodo para insertar un cantante (CREATE)

    private static void insertCantante(Connection conexion1, String nombre, int edad, String Genero, String pais, String fechaAlta) throws SQLException {
        String insertSQL = "INSERT INTO cantantes (nombre, edad, Genero, pais, fechaAlta) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement preparedStatement = conexion1.prepareStatement(insertSQL)){
            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, edad);
            preparedStatement.setString(3, Genero);
            preparedStatement.setString(4, pais);
            preparedStatement.setDate(5, java.sql.Date.valueOf(fechaAlta));
            preparedStatement.executeUpdate(); 
        }
    }


    // Método para leer los cantantes (READ)

    private static void readCantantes(Connection conexion1) throws SQLException{
        String query = "SELECT * FROM Cantantes";
        try (Statement statement = conexion1.createStatement();
        ResultSet resultSet = statement.executeQuery(query)){ 

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Nombre: " + resultSet.getString("nombre"));
                System.out.println("Edad: " + resultSet.getInt("edad"));
                System.out.println("Genero: " + resultSet.getString("Genero"));
                System.out.println("País: " + resultSet.getString("pais"));
                System.out.println("Fecha de Alta: " + resultSet.getDate("fechaAlta"));
                System.out.println("----------");
            }

        }

    }

    // Método para actualizar un cantante (UPDATE)

    private static void updateCantante(Connection conexion1, int id, String nombre, int edad, String Genero, String pais, String fechaAlta) throws SQLException {
        String updateSQL = "UPDATE cantantes SET nombre= ?, edad = ?, Genero = ?, pais = ?, fechaAlta = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = conexion1.prepareStatement(updateSQL)){
            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, edad);
            preparedStatement.setString(3, Genero);
            preparedStatement.setString(4, pais);
            preparedStatement.setDate(5, java.sql.Date.valueOf(fechaAlta));
            preparedStatement.setInt(6, id);
        }
    }

    // Metodo para borrar un cantante (DELETE)

    private static void deleteCantante(Connection conexion1, int id) throws SQLException {
        String deleteSQL = "DELETE FROM cantantes WHERE id = ?";
        try (PreparedStatement preparedStatement = conexion1.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }

    }
    
}
