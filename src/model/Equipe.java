/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author mathe
 */
public class Equipe implements Serializable {
    
    private int id;
    private String nome, apelido, nacionalidade, ano;
    private ArrayList<Piloto> listaPilotos;
    private ArrayList<Carro> listaCarros;
    private double pontos;
    
    public Equipe(int id, String nome, String apelido, String nacio, String ano, double pontos) {
        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.nacionalidade = nacio;
        this.ano = ano;
        this.pontos = pontos;
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

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public ArrayList<Piloto> getListaPilotos() {
        return listaPilotos;
    }

    public void addPiloto(Piloto p) {
        this.listaPilotos.add(p);
    }

    public ArrayList<Carro> getListaCarros() {
        return listaCarros;
    }

    public void addCarro(Carro c) {
        this.listaCarros.add(c);
    }

    public double getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
