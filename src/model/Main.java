/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Control;
import view.TelaLogin;

/**
 *1
 * @author dwo
 */
public class Main {
    public static void main(String[] args) throws Exception {
        User user = new User();
        Control c = new Control(user);
        TelaLogin.main();

        
    }
}
