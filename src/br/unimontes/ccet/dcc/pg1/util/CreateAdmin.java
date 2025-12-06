package br.unimontes.ccet.dcc.pg1.util;

import br.unimontes.ccet.dcc.pg1.model.dao.service.PasswordUtils;

/**
 * Utility to generate a salted password hash to insert into the DB.
 * Usage: run this main and supply the desired password as the first arg.
 */
public class CreateAdmin {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage: java CreateAdmin <password>");
            return;
        }
        String pass = args[0];
        String hash = PasswordUtils.generateSaltedHash(pass);
        System.out.println("Generated hash:\n" + hash);
        System.out.println("Use this value in the 'usuario.senha' field for the admin user.");
    }
}
