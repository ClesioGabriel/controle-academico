/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unimontes.ccet.dcc.pg1.model.dao.service;

/**
 *
 * @author Avell
 */
public class Utils {
    public static String validaCPF(String cpf) {
        if (cpf == null) return null;
        String clean = cpf.replaceAll("\\D", "");
        if (clean.isBlank() || clean.length() != 11) return null;
        return clean;
    }
    
    public static String validaNome(String nome) {
        if(nome.isBlank()) return null;
        if(nome.length() >= 255) return null;
        return nome;
    }
    
    public static Integer validaAnoNascimento(int ano) {
        int currentYear = java.time.Year.now().getValue();
        if(ano < 1900 || ano > currentYear) return null;
        return ano;
    }

    public static String formatCPF(String cpf) {
        if (cpf == null) return null;
        String clean = cpf.replaceAll("\\D", "");
        if (clean.length() != 11) return cpf;
        return clean.substring(0,3) + "." + clean.substring(3,6) + "." + clean.substring(6,9) + "-" + clean.substring(9);
    }

    public static String toDisplayDate(String isoDate) {
        if (isoDate == null || isoDate.isBlank()) return "";
        try {
            java.time.LocalDate d = java.time.LocalDate.parse(isoDate);
            java.time.format.DateTimeFormatter f = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return d.format(f);
        } catch (Exception e) {
            return isoDate;
        }
    }

    public static String toISODate(String brDate) {
        if (brDate == null || brDate.isBlank()) return null;
        try {
            java.time.format.DateTimeFormatter f = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.LocalDate d = java.time.LocalDate.parse(brDate, f);
            return d.toString(); // ISO
        } catch (Exception e) {
            return null;
        }
    }
    
}
