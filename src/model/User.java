/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import control.Cliente;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author dwo
 */
public class User {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private ArrayList<Ingresso> meusIngressos;
    private ArrayList<Ingresso>  ingressosDisponiveis;
    
    public User(){
        this.nome = "";
        this.email = "";
        this.senha = "";
        this.id = 0;
        this.meusIngressos = new ArrayList<>();
        
    }
    public boolean realizarLogin(String email, String senha) throws Exception{
        this.email = email;
        this.senha = senha;
        ArrayList<String> rec = Cliente.realizarLoginCliente(this.getEmail(), this.getSenha());
        while(!rec.get(0).equals("0001")){
            JOptionPane.showMessageDialog(null, "Erro de Login");
            realizarLogin(email, senha);
        }
        this.id = Integer.parseInt(rec.get(1));
        this.nome = rec.get(2);
        this.email = rec.get(3);
        this.senha = rec.get(4);
        return true;
    }

    public ArrayList<Ingresso> verIngressosComprados() throws IOException{
        //enviar para o servidor a solicitacao de ver os ingressos
        this.meusIngressos = Cliente.verMeusIngressos(String.valueOf(this.id));
        
        /*for (Ingresso meusIngresso : meusIngressos) {
            JOptionPane.showMessageDialog(null, meusIngresso.getEvento());
        }*/
        return this.meusIngressos;
    }
    public void comprarIngresso() throws IOException{
        //enviar para o servidor a soliciticao de ver todos os ingressos
        this.ingressosDisponiveis = Cliente.verTodosIngressos();
        String result = "Ingressos disponiveis: \n";
        for (Ingresso ingresso : this.ingressosDisponiveis) {
            result += "Numero do ingresso: " + ingresso.getId() + " - " + ingresso.getEvento() + " por apenas: " + ingresso.getPreco() + " \n";
        }
        
       //JOptionPane.showMessageDialog(null, result);
       int idIngresso = Integer.parseInt(JOptionPane.showInputDialog(result + "\nQual ingresso deseja comprar?"));
       System.out.println("Escolha a forma de pagamento: \n1 - A vista\n2 - Debito\n3 - Credito");
       int idPagamento = Integer.parseInt(JOptionPane.showInputDialog("Qual a forma de pagamento? \n1 - A vista\n2 - Debito\n3 - Credito"));
       this.meusIngressos.add(Cliente.realizaCompra(this.id, idIngresso, idPagamento));
       JOptionPane.showMessageDialog(null, "Compra realizada com sucesso!");
    }
    public void fechaPrograma() throws IOException{
        Cliente.fechaPrograma();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
