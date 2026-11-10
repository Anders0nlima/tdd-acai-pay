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
        return Collections.unmodifiableList(entradas);
    }

    public void deposito(int quantia){
        if(quantia <= 0){
            throw new IllegalArgumentException("A quantia para depósito deve ser maior que zero.");
        }
        this.saldo += quantia;
        this.entradas.add(new Entrada("DEPOSITO", quantia, this.saldo));
    
    }

    public void saque(int quantia) {
        if (quantia <= 0){
            throw new IllegalArgumentException("A quantia para saque deve ser maior que zero.");
        }

        if(quantia > this.saldo){
            throw new IllegalStateException("Saldo insuficiente para realizar o saque");
        }
        
        this.saldo -= quantia;
        this.entradas.add(new Entrada("SAQUE", quantia, this.saldo));
    }

}