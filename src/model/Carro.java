/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author mathe
 */
public class Carro implements Serializable {
    
    private int id; 
    private String tag;
    private String modelo, marca, cor, numero;
    private Piloto piloto;
    private Equipe equipe;
    
    public Carro(int i, String t, String modelo, String marca, String cor, String num, Equipe eq) {
        this.id = i;
        this.tag = t;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.numero = num;
        this.equipe = eq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto p) {
        this.piloto = p;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe e) {
        this.equipe = e;
    }
}
