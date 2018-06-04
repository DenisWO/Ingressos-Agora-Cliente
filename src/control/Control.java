/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.util.ArrayList;
import model.Ingresso;
import model.User;

/**
 *
 * @author dwo
 */
public class Control {
    private static User user;
    
    public Control(User user){
        this.user = user;
    }
    
    public static boolean verificaLogin(String email, String senha) throws Exception{
        return Control.user.realizarLogin(email, senha);
    }
    public static ArrayList<Ingresso> verIngressosComprados() throws IOException{
        ArrayList<Ingresso> ingressos = Control.user.verIngressosComprados();
        return ingressos;
    }
    public static void verTodosIngressos() throws IOException{
        Control.user.comprarIngresso();
    }
    public static void fechaPrograma() throws IOException{
        Control.user.fechaPrograma();
    }
}
