/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbl.autorama;

import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.*;
import org.json.simple.JSONObject;
import view.*;

/**
 *
 * @author mathe
 */
public class Cliente implements Serializable {
    
    //Listas de cada classe necessária para funcionamento
    static ArrayList<Piloto> listaPilotos = new ArrayList();
    static ArrayList<Carro> listaCarros = new ArrayList();
    static ArrayList<Equipe> listaEquipes = new ArrayList();
    static ArrayList<Pista> listaPistas = new ArrayList();
    static ArrayList<Corrida> listaCorridas = new ArrayList();
    
    static String[] tags; //Lista das tags disponíveis embaixo do leitor RFID

    public static Socket cliente; //Conexão com o socket
    static DataOutputStream saida;
    static DataInputStream entrada;
    static BufferedReader stdIn;
    
    static String[] infoTags;
    static String[] infoTempos;
    
    static boolean recebeuDados = false;

    private static String inicioCorrida = null;
    public static boolean fimCorrida = false;

    /**
     * Inicia o programa
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        preencherDados();
        //carregarDados();

        TelaConfig telaConfig = new TelaConfig();
        telaConfig.setVisible(true);
    }
    
    /**
     * Se conecta ao servidor
     * @return 
     */
    public static boolean conectarServidor() {
        try {
            cliente = new Socket("augusto.ddns.net", 5025);
            //cliente = new Socket("127.0.0.1", 5025);
            saida = new DataOutputStream(cliente.getOutputStream());
            //entrada = new DataInputStream(cliente.getInputStream());
            stdIn = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        } catch (IOException ex) {
            System.out.println("Não foi possível se conectar ao servidor");
            return false;
        }
        return cliente.isConnected(); //Retorna se foi possível se conectar com o servidor
    }

    public static boolean desconectarServidor() {
        try {
            saida.close();
            stdIn.close();
            cliente.close();
        } catch (IOException ex) {
            System.out.println("Não foi possível desconectar do servidor");
        }
        return cliente.isConnected();
    }
    
    public static void enviarMensagem(String comando, String conteudo) throws IOException {
            JSONObject json = new JSONObject();
            json.put("comando", comando);
            json.put("conteudo", conteudo);
            System.out.println(json);
            
            saida.writeUTF(json.toJSONString());
            saida.flush();
    }
    
    public static String receberMensagem() throws IOException {
        
        //BufferedReader stdIn =new BufferedReader(new InputStreamReader(cliente.getInputStream()));
        System.out.println("Aguardando mensagem...");
        String in = stdIn.readLine();
        System.out.println(in);
        
        return in;
    }

    public static void enviarConfig(String serial, String regiao, String protocolo, String baudrate, String potencia) throws IOException {
        System.out.println("Enviando configurações do leitor");

        // write the message we want to send
        saida.writeUTF(serial + ";" + regiao + ";" + protocolo + ";" + baudrate + ";" + potencia);
        saida.flush(); // send the message
    }
    
    public static boolean conectado() {
        if(cliente != null)
            return cliente.isConnected();
        else
            return false;
    }

    public static void cadastrarPiloto(String nome, String apelido, String nasc, String nacio, int carro, int equipe, boolean atv, double pts) {
        /*if (carro.equals("")) {
            carro = "0";
        }

        if (equipe.equals("")) {
            equipe = "0";
        }*/
        //else
        //listaEquipes.get(Integer.parseInt(equipe)).setListaPilotos() + listaPilotos.size();

        listaPilotos.add(new Piloto(listaPilotos.size() + 1, nome, apelido, nasc, nacio, listaCarros.get(carro), listaEquipes.get(equipe), atv, pts));
    }

    public static String cadastrarEquipe(String nome, String apelido, String nacio, String ano, double pts) {
        int qtd = listaEquipes.size();
        
        if(!listaEquipes.isEmpty()) {
        
            for(int i = 0; i < qtd; i++) {
                if(nome.equals(listaEquipes.get(i).getNome()))
                    return "A equipe já existe";
            }
        }
        listaEquipes.add(new Equipe(qtd + 1, nome, apelido, nacio, ano, pts));
        //JOptionPane.showMessageDialog(null, listaEquipes.get(listaEquipes.size() - 1).getNome());
        return "Equipe cadastrada com sucesso";
    }

    public static void cadastrarCorrida(int quali, int voltas, int pista, int[] pilotos) {
        listaCorridas.add(new Corrida(listaCorridas.size() + 1, quali, voltas, pista, pilotos));
        JOptionPane.showMessageDialog(null, listaCorridas.get(listaCorridas.size() - 1).getQualificacao());
    }

    public static String cadastrarPista(String nome, String pais, String recorde, String pilotoRecorde) {
        listaPistas.add(new Pista(listaPistas.size() + 1, nome, pais, recorde, pilotoRecorde));
        //JOptionPane.showMessageDialog(null, listaPistas.get(listaPistas.size() - 1).getNome());
        return "Pista cadastrada com sucesso";
    }

    public static String cadastrarCarro(String tag, String modelo, String marca, String cor, String numero, int equipe) {
        int qtd = listaCarros.size();
        Carro aux;
       if(!tag.equals("")) {
        if(!listaCarros.isEmpty()) {
            for(int i = 0; i < qtd; i++) {
                aux = listaCarros.get(i);
                    if(tag.equals(aux.getTag()))
                        return "A tag escolhida já está sendo utilizada";
                    else if(numero.equals(aux.getNumero()) && equipe == aux.getEquipe().getId()) {
                        return "O carro já existe";
                    }
                }
            }
        }
        listaCarros.add(new Carro(listaCarros.size() + 1, tag, modelo, marca, cor, numero, listaEquipes.get(equipe)));
        System.out.println(listaCarros.get(listaCarros.size() - 1).getEquipe().getNome());
        return "Carro cadastrado com sucesso";
    }

    public static String preencherDados() {
        listaEquipes.clear();
        listaCarros.clear();
        listaPilotos.clear();
        listaPistas.clear();
        listaCorridas.clear();
        
        //Adicionando equipes
        listaEquipes.add(new Equipe(1, "Ferrari", "", "Itália", "1947", 100.0));
        listaEquipes.add(new Equipe(2, "Mercedes", "", "Alemanha", "1926", 100.0));
        
        //Adicionando carros
        listaCarros.add(new Carro(1, "b'E2000017221101321890548C'", "v1", "Ferrari", "Vermelho", "12", listaEquipes.get(0)));
        listaCarros.add(new Carro(2, "b'E20000172211012518905484'", "v2", "Mercedes", "Azul", "23", listaEquipes.get(0)));
        listaCarros.add(new Carro(3, "b'E2000017221101241890547C'", "v3", "IBM", "Amarelo", "34", listaEquipes.get(1)));
        listaCarros.add(new Carro(4, "b'E20000172211011718905474'", "v4", "Ford", "Verde", "46", listaEquipes.get(1)));
        
        //Adicionando pilotos
        listaPilotos.add(new Piloto(1, "Lewis Hamilton", "", "07/01/1985", "Reino Unido", listaCarros.get(0), listaEquipes.get(0), true, 100.0));
        listaPilotos.add(new Piloto(2, "Ayrton Senna", "", "21/03/1960", "Brasil", listaCarros.get(1), listaEquipes.get(0), false, 100.0));
        listaPilotos.add(new Piloto(3, "Max Verstappen", "", "30/09/1997", "Bélgica", listaCarros.get(2), listaEquipes.get(1), true, 100.0));
        listaPilotos.add(new Piloto(4, "Michael Schumacher", "", "03/01/1969", "Alemanha", listaCarros.get(3), listaEquipes.get(1), false, 100.0));

        //Adicionando pistas
        listaPistas.add(new Pista(1, "Interlagos", "Brasil", "100", "Ayrton Senna"));
        listaPistas.add(new Pista(2, "Mônaco", "Itália", "100", "Lewis Hamilton"));

        //Adicionando corridas
        listaCorridas.add(new Corrida(1, 2, 5, 1, new int[]{1, 3}));

        //escreverDados();
        return "Informações preenchidas com sucesso";
    }
    
    public static void definirTags(String listaTags) {
        //String aux = Arrays.toString(listaTags);
        System.out.println(listaTags);
        
        StringBuilder sb = new StringBuilder(listaTags);
        
        sb.deleteCharAt(0);
        sb.deleteCharAt(sb.length()-1);
        
        System.out.println(sb.toString());
        
        tags = sb.toString().replace(" ", "").split(",");
    }

    public static void carregarDados() {
        
        boolean endOfFile = false;
        Piloto tempPiloto;
        Equipe tempEquipe;
        Carro tempCarro;
        Pista tempPista;
        Corrida tempCorrida;

        try {
            FileInputStream arquivo = new FileInputStream("data/pilotos.obf");
            ObjectInputStream stream = new ObjectInputStream(arquivo);
            tempPiloto = (Piloto) stream.readObject();

            while (endOfFile != true) {
                try {
                    listaPilotos.add(tempPiloto);
                    tempPiloto = (Piloto) stream.readObject();
                } catch (EOFException e) {
                    endOfFile = true;
                }
            }
            stream.close();

            endOfFile = false;

            //Carro
            arquivo = new FileInputStream("data/carros.obf");
            stream = new ObjectInputStream(arquivo);
            tempCarro = (Carro) stream.readObject();

            while (endOfFile != true) {
                try {
                    listaCarros.add(tempCarro);
                    tempCarro = (Carro) stream.readObject();
                } catch (EOFException e) {
                    endOfFile = true;
                }
            }
            stream.close();

            endOfFile = false;

            //Equipe
            arquivo = new FileInputStream("data/equipes.obf");
            stream = new ObjectInputStream(arquivo);
            tempEquipe = (Equipe) stream.readObject();

            while (endOfFile != true) {
                try {
                    listaEquipes.add(tempEquipe);
                    tempEquipe = (Equipe) stream.readObject();
                } catch (EOFException e) {
                    endOfFile = true;
                }
            }
            stream.close();

            endOfFile = false;

            //Pista
            arquivo = new FileInputStream("data/pistas.obf");
            stream = new ObjectInputStream(arquivo);
            tempPista = (Pista) stream.readObject();

            while (endOfFile != true) {
                try {
                    listaPistas.add(tempPista);
                    tempPista = (Pista) stream.readObject();
                } catch (EOFException e) {
                    endOfFile = true;
                }
            }
            stream.close();

            endOfFile = false;

            //Corrida
            arquivo = new FileInputStream("data/corridas.obf");
            stream = new ObjectInputStream(arquivo);
            tempCorrida = (Corrida) stream.readObject();

            while (endOfFile != true) {
                try {
                    listaCorridas.add(tempCorrida);
                    tempCorrida = (Corrida) stream.readObject();
                } catch (EOFException e) {
                    endOfFile = true;
                }
            }
            stream.close();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não foi encontrado");
        } catch (IOException e) {
            System.out.println("Arquivo não pode ser lido");
        } catch (ClassNotFoundException e) {
            System.out.println("Classe não encontrada");
        }
    }

    public static void escreverDados() {
        try {
            FileOutputStream arquivoPilotos = new FileOutputStream("data/pilotos.obf");
            ObjectOutputStream pilotosStream = new ObjectOutputStream(arquivoPilotos);

            for (Piloto pilotosAux : listaPilotos) {
                pilotosStream.writeObject(pilotosAux);
            }
            pilotosStream.close();

            //Carros
            FileOutputStream arquivoCarros = new FileOutputStream("data/carros.obf");
            ObjectOutputStream carrosStream = new ObjectOutputStream(arquivoCarros);

            for (Carro carrosAux : listaCarros) {
                carrosStream.writeObject(carrosAux);
            }
            carrosStream.close();

            //Equipes
            FileOutputStream arquivoEquipes = new FileOutputStream("data/equipes.obf");
            ObjectOutputStream equipesStream = new ObjectOutputStream(arquivoEquipes);

            for (Equipe equipesAux : listaEquipes) {
                equipesStream.writeObject(equipesAux);
            }
            equipesStream.close();

            //Pistas
            FileOutputStream arquivoPistas = new FileOutputStream("data/pistas.obf");
            ObjectOutputStream pistasStream = new ObjectOutputStream(arquivoPistas);

            for (Pista pistasAux : listaPistas) {
                pistasStream.writeObject(pistasAux);
            }
            pistasStream.close();

            //Corridas
            FileOutputStream arquivoCorridas = new FileOutputStream("data/corridas.obf");
            ObjectOutputStream corridasStream = new ObjectOutputStream(arquivoCorridas);

            for (Corrida corridasAux : listaCorridas) {
                corridasStream.writeObject(corridasAux);
            }
            corridasStream.close();
        } catch (IOException e) {
            System.out.println("Error occurred while saving");
            e.printStackTrace();
        }
    }
    
    public static void qualificacao(int pistaIndice) throws IOException, InterruptedException {
        boolean fim = false;
        
        TelaQualificacao tq = new TelaQualificacao();
        
        tq.getNomePista().setText(listaPistas.get(pistaIndice).getNome());
        
        String tags = "";
        
        for(Piloto p: listaPilotos) {
            tags += p.getCarro().getTag() + ";";
            p.menorVolta = 100;
        }
        
        StringBuilder sb = new StringBuilder(tags);
        
        sb.deleteCharAt(sb.length()-1);
        
        enviarMensagem("Tags", sb.toString());
        
        System.out.println("Iniciando qualificação\n");
        
        tq.getStatus().setText("Em andamento");
        
        while(!fim) {
            String mensagem = receberMensagem();
            Gson g = new Gson();
            Volta v = g.fromJson(mensagem, Volta.class);
            
            if(v.getTag().equals("Fim")) {
                fim = true;
            } else {
                for(Piloto p : listaPilotos){
                    if(p.getCarro().getTag().equals(v.getTag())) {
                        System.out.println("O piloto " + p.getNome() + " fez uma volta no tempo " + v.getTempo());
                        if(Double.parseDouble(v.getTempo()) < p.menorVolta)
                            p.menorVolta = Double.parseDouble(v.getTempo());
                    }
                }
            }
            
            //Atualizar classificação
            Collections.sort(listaPilotos);
            System.out.println("\nClassificação");
            
            for(int i=0; i < listaPilotos.size(); i++) {
                tq.getPos(i+1).setText(listaPilotos.get(i).getNome());
                tq.getTempo(i+1).setText(String.valueOf(listaPilotos.get(i).menorVolta));
                System.out.println((i+1) + " - " + listaPilotos.get(i).getNome() + " - " + listaPilotos.get(i).menorVolta);
            }
        }
        
        System.out.println("Fim da qualificação");
        tq.getStatus().setText("Fim da qualificação");
        tq.setVisible(true);
    }
    
    public static ArrayList<Piloto> getListaPilotos() {
        return listaPilotos;
    }

    public static ArrayList<Carro> getListaCarros() {
        return listaCarros;
    }

    public static ArrayList<Equipe> getListaEquipes() {
        return listaEquipes;
    }

    public static ArrayList<Pista> getListaPistas() {
        return listaPistas;
    }

    public static ArrayList<Corrida> getListaCorridas() {
        return listaCorridas;
    }
    
    public static String[] getTags() {
        return tags;
    }
    
    public static void setFimCorrida(boolean o) {
        fimCorrida = o;
    }
    
    public static void setInicioCorrida(String i) {
        inicioCorrida = i;
    }
    
}