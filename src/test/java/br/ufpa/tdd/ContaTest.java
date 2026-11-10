package br.ufpa.tdd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class ContaTest {

    @Test
    void novaContaIniciaComSaldo0() {
        Conta acc = new Conta();
        assertEquals(0, acc.getSaldo());
    }

    @Test 
    void novaContaIniciaComExtratoVazio(){
        Conta acc = new Conta();
        assertTrue(acc.getEntradas().isEmpty());
    }

    @Test
    void depositoValidoAlteraSaldo() {
        Conta acc = new Conta();
        acc.deposito(100);
        assertEquals(100, acc.getSaldo());
    }

    @Test 
    void depositoValidoCriaLancamentoNoExtrato(){
        Conta acc = new Conta();
        acc.deposito(100);
        assertEquals(1, acc.getEntradas().size());
        Entrada entrada = acc.getEntradas().get(0);
        assertEquals("DEPOSITO", entrada.tipo());
        assertEquals(100, entrada.valor());
        assertEquals(100, entrada.saldoAposOperacao());
    }

    @Test
    void saqueValidoAlteraSaldo() {
        Conta acc = new Conta();
        acc.deposito(100);
        acc.saque(40);
        assertEquals(60, acc.getSaldo());
    }

    @Test
    void saqueValidoCriaLancamentoNoExtrato() {
        Conta acc = new Conta();
        acc.deposito(100);
        acc.saque(40);
        assertEquals(2, acc.getEntradas().size());
        Entrada entradaSaque = acc.getEntradas().get(1);
        assertEquals("SAQUE", entradaSaque.tipo());
        assertEquals(40, entradaSaque.valor());
        assertEquals(60, entradaSaque.saldoAposOperacao());
    }


    @Test
    void extratoNaoPodeSerModificadoExternamente() {
        Conta acc = new Conta();
        acc.deposito(100);
        List<Entrada> extrato = acc.getEntradas();
        assertThrows(UnsupportedOperationException.class, () -> {
            extrato.add(new Entrada("DEPOSITO", 50, 150));
        });
    }

    @Test
    void depositoComValorZeroOuNegativoLancaExcecao() {
        Conta acc = new Conta();
        assertThrows(IllegalArgumentException.class, () -> acc.deposito(0));
        assertThrows(IllegalArgumentException.class, () -> acc.deposito(-50));
    }

    @Test
    void saqueComValorZeroOuNegativoLancaExcecao() {
        Conta acc = new Conta();
        acc.deposito(100);
        assertThrows(IllegalArgumentException.class, () -> acc.saque(0));
        assertThrows(IllegalArgumentException.class, () -> acc.saque(-50));
    }



}