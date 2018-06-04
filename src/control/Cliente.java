/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Ingresso;

/**
 *
 * @author dwo
 */
public class Cliente {
   private static int portaServidor = 8999;
   private static String ip = "127.0.0.1";
   public static String criptografaSaida(String entrada){
       char[] alfabeto = new char[128];
       for(int i=0; i<128; i++){
           alfabeto[i] = (char) i;
       }
       String saida = "";
       for(int i=0; i<entrada.length(); i++){
           for(int j=0; j<128; j++){
               if(entrada.charAt(i) == alfabeto[j]){
                   saida += alfabeto[j+3];
               }
           }
       }
       return saida;
   }
   public static String descriptografaEntrada(String saida){
       char[] alfabeto = new char[128];
       for(int i=0; i<128; i++){
           alfabeto[i] = (char) i;
       }
       String entrada = "";
       for(int i=0; i<saida.length(); i++){
           for(int j=0; j<128; j++){
               if(saida.charAt(i) == alfabeto[j]){
                   entrada += alfabeto[j-3];
               }
           }
       }
       return entrada;
   }
   public static void fechaPrograma() throws IOException{
       String codigo = "1111/";
       Socket socket = new Socket("127.0.0.1", portaServidor);
       DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
       saida.writeBytes(criptografaSaida(codigo) + '\n');
   }
   public static ArrayList<String> realizarLoginCliente(String email, String senha) throws IOException, Exception{
       /*Realizar login sera funçao de codigo 0000*/
       int it =0;
       String codigo = "0001/" + email + "/"+  senha + "/"; 
       //Efetua a primitiva socket
        Socket socket = socket();

        //Efetua a primitiva send
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.writeBytes(criptografaSaida(codigo) + '\n');


        //Efetua a primitiva receive
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String recebido = descriptografaEntrada(entrada.readLine());

        ArrayList<String> rec = new ArrayList<>();
        for(int i=0; i<recebido.length(); i++){
            if(recebido.charAt(i) == '/'){
                rec.add(recebido.substring(it, i));
                it = i+1;                
            }
        }
        //Efetua a primitiva close
        socket.close();
        return rec;
   }
   public static ArrayList<Ingresso> verMeusIngressos(String id) throws IOException{
       /*Buscar todos os ingressos desse cliente é codigo 0010*/
       int it =0;
       String codigo = "0010/" + id + "/";
       //Efetua a primitiva socket
        Socket socket = new Socket(ip, portaServidor);

        //Efetua a primitiva send
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.writeBytes(criptografaSaida(codigo) + '\n');


        //Efetua a primitiva receive
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String recebido = descriptografaEntrada(entrada.readLine());
        ArrayList<String> rec = new ArrayList<>();
        for(int i=0; i<recebido.length(); i++){
            if(recebido.charAt(i) == '/'){
                rec.add(recebido.substring(it, i));
                it = i+1;
            }
        }
        //Efetua a primitiva close
        socket.close();
        ArrayList<Ingresso> ingressos = new ArrayList<>();
        for (int i=1; i<rec.size(); i++){
            
           ingressos.add(desformatacaoIngresso(rec.get(i)));
        }
        
        return ingressos;
   }
   public static Ingresso desformatacaoIngresso(String info){
       ArrayList<String> array = new ArrayList<>();
       int it = 0;
       for(int i=0; i<info.length(); i++){
           if(info.charAt(i) == '|'){
               array.add(info.substring(it, i));
               it = i+1;
           }
       }
       Ingresso i = new Ingresso(Integer.parseInt(array.get(0)), Double.parseDouble(array.get(1)), array.get(2));
       return i;
   }
   public static ArrayList<Ingresso> verTodosIngressos() throws IOException{
       //Buscar todos os ingressos possui codigo 0100
       int it =0;
       String codigo = "0100" + "/";
       //Efetua a primitiva socket
        Socket socket = new Socket(ip, portaServidor);

        //Efetua a primitiva send
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.writeBytes(criptografaSaida(codigo) + '\n');


        //Efetua a primitiva receive
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String recebido = descriptografaEntrada(entrada.readLine());
        ArrayList<String> rec = new ArrayList<>();
        for(int i=0; i<recebido.length(); i++){
            if(recebido.charAt(i) == '/'){
                rec.add(recebido.substring(it, i));
                it = i+1;
            }
        }
        //Efetua a primitiva close
        socket.close();
        ArrayList<Ingresso> ingressos = new ArrayList<>();
        for (int i=1; i<rec.size(); i++) {
           ingressos.add(desformatacaoIngresso(rec.get(i)));
       }
        return ingressos;
   }
   public static Ingresso realizaCompra(int idCliente, int idIngresso, int idPagamento) throws IOException{
       /*Codigo da compra de ingresso e 1000*/
       int it = 0;
       String codigo = "1000/" + idCliente + "/" + idIngresso + "/" + idPagamento+  "/";
       //Efetua a primitiva socket
        Socket socket = new Socket(ip, portaServidor);

        //Efetua a primitiva send
        DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
        saida.writeBytes(criptografaSaida(codigo) + '\n');


        //Efetua a primitiva receive
        BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String recebido = descriptografaEntrada(entrada.readLine());
        ArrayList<String> rec = new ArrayList<>();
        for(int i=0; i<recebido.length(); i++){
            if(recebido.charAt(i) == '/'){
                rec.add(recebido.substring(it, i));
                it = i+1;
            }
        }
        //Efetua a primitiva close
        socket.close();
        Ingresso i = new Ingresso(Integer.parseInt(rec.get(0)), Double.valueOf(rec.get(1)), rec.get(2));
        return i;
       
   }
   public static Socket socket() throws IOException{
       ip = JOptionPane.showInputDialog("Endereço de IP: ");
       portaServidor = Integer.parseInt(JOptionPane.showInputDialog("Porta: "));
       Socket s = new Socket(ip, portaServidor);
       
       return s;
   }
   

}
