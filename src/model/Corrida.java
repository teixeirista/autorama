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
public class Corrida implements Serializable {
    
    private int id;
    private int qualificacao, numVoltas, pista, listaPilotos[];
    
    public Corrida(int id, int quali, int voltas, int pista, int pilotos[]) {
        this.id = id;
        this.qualificacao = quali;
        this.numVoltas = voltas;
        this.pista = pista;
        this.listaPilotos = pilotos.clone();
    }

    public int getId() {
        return id;
    }

    public int getQualificacao() {
        return qualificacao;
    }

    public int getNumVoltas() {
        return numVoltas;
    }

    public int getPista() {
        return pista;
    }

    public int[] getListaPilotos() {
        return listaPilotos;
    }    
}
