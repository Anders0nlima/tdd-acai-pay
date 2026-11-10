package br.ufpa.tdd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conta {
    private int saldo = 0;
    private List<Entrada> entradas = new ArrayList<>();

    public int getSaldo(){
        return saldo;
    }

    public List<Entrada> getEntradas(){
        return entradas;
    }

    public void deposito(int quantia){
        this.saldo += quantia;
    }

}