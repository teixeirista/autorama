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
public class Pista implements Serializable {
    
    private int id;
    private String nome, pais, recorde, pilotoRecorde;
    
    public Pista(int id, String nome, String pais, String recorde, String pilotoRecorde) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.recorde = recorde;
        this.pilotoRecorde = pilotoRecorde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPilotoRecorde() {
        return pilotoRecorde;
    }

    public void setPilotoRecorde(String pilotoRecorde) {
        this.pilotoRecorde = pilotoRecorde;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getRecorde() {
        return recorde;
    }

    public void setRecorde(String recorde) {
        this.recorde = recorde;
    }
}
