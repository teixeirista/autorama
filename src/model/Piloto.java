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
public class Piloto implements Serializable, Comparable<Piloto> {
    
    private int id;
    private String nome, apelido, nascimento, nacionalidade;
    private Carro carro;
    private Equipe equipe;
    private double pontos;
    private boolean emAtividade;
    private ArrayList<String> voltas;
    public double menorVolta = 100.0;
    
    public Piloto(int id, String nome, String ap, String nasc, String nacio, Carro carro, Equipe equipe, boolean atv, double pts) {
        this.id = id;
        this.nome = nome;
        this.apelido = ap;
        this.nascimento = nasc;
        this.nacionalidade = nacio;
        this.carro = carro;
        this.equipe = equipe;
        this.emAtividade = atv;
        this.pontos = pts;
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

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro c) {
        this.carro = c;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe e) {
        this.equipe = e;
    }

    public boolean isEmAtividade() {
        return emAtividade;
    }

    public void setEmAtividade(boolean emAtividade) {
        this.emAtividade = emAtividade;
    }

    public double getPontos() {
        return pontos;
    }

    public void setPontos(double pontos) {
        this.pontos = pontos;
    }
    
    public ArrayList<String> getVoltas() {
        return voltas;
    }

    public void addVolta (String v) {
        this.voltas.add(v);
    }

    @Override
    public int compareTo(Piloto o) {
        if (this.menorVolta > o.menorVolta) { 
            return 1; 
        } if (this.menorVolta < o.menorVolta) { 
            return -1;
        } 
        return 0;
    }
}
