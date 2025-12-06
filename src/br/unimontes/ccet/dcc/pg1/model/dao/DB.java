/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unimontes.ccet.dcc.pg1.model.dao;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {

    private String url = "jdbc:mysql://localhost:3306/controle_academico";
    private String user = "root";
    private String password = "";

    private Connection db;
    private static DB instancia;

    private DB() throws SQLException {
        loadConfig();
        db = DriverManager.getConnection(url, user, password);
    }

    private void loadConfig() {
        try (InputStream is = new FileInputStream("config.properties")) {
            Properties p = new Properties();
            p.load(is);

            String propUrl = p.getProperty("db.url");
            String propUser = p.getProperty("db.user");
            String propPassword = p.getProperty("db.password");

            if (propUrl != null && !propUrl.isBlank()) {
                url = propUrl;
            }

            if (propUser != null) {
                user = propUser;
            }

            if (propPassword != null) {
                password = propPassword;
            }

        } catch (Exception ex) {
            // Usa os valores padrão
        }
    }

    public static DB getInstancia() throws SQLException {
        if (instancia == null) {
            instancia = new DB();
        }
        return instancia;
    }

    public Connection getConnection() {
        return db;
    }
}

