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


    @Test
    void saqueMaiorQueSaldoLancaExcecaoENaoAlteraEstado() {
        Conta acc = new Conta();
        acc.deposito(100);
        // Verifica que lança a exceção ao tentar sacar mais do que tem
        assertThrows(IllegalStateException.class, () -> acc.saque(150));
        // Efeito colateral: garante que nada mudou na conta
        assertEquals(100, acc.getSaldo());
        assertEquals(1, acc.getEntradas().size());
    }

   // bunus 
    @Test
    void transferenciaComSucessoAtualizaSaldosEExtratos() {
        Conta origem = new Conta();
        Conta destino = new Conta();
        origem.deposito(100);
        destino.deposito(50);
        origem.transferenciaPara(destino, 30);

        assertEquals(70, origem.getSaldo());
        assertEquals(80, destino.getSaldo());

        assertEquals(2, origem.getEntradas().size());
        Entrada entradaOrigem = origem.getEntradas().get(1);
        assertEquals("SAQUE", entradaOrigem.tipo());
        assertEquals(30, entradaOrigem.valor());
        assertEquals(70, entradaOrigem.saldoAposOperacao());

        assertEquals(2, destino.getEntradas().size());
        Entrada entradaDestino = destino.getEntradas().get(1);
        assertEquals("DEPOSITO", entradaDestino.tipo());
        assertEquals(30, entradaDestino.valor());
        assertEquals(80, entradaDestino.saldoAposOperacao());
    }


    @Test
    void transferenciaComValorInvalidoLancaExcecaoENaoAlteraEstado() {
        Conta origem = new Conta();
        Conta destino = new Conta();
        origem.deposito(100);
        destino.deposito(50);
        assertThrows(IllegalArgumentException.class, () -> origem.transferenciaPara(destino, 0));
        assertThrows(IllegalArgumentException.class, () -> origem.transferenciaPara(destino, -10));
        // Sem efeitos colaterais
        assertEquals(100, origem.getSaldo());
        assertEquals(50, destino.getSaldo());
        assertEquals(1, origem.getEntradas().size());
        assertEquals(1, destino.getEntradas().size());
    }


    @Test
    void transferenciaComSaldoInsuficienteLancaExcecaoENaoAlteraEstado() {
        Conta origem = new Conta();
        Conta destino = new Conta();
        origem.deposito(100);
        destino.deposito(50);
        assertThrows(IllegalStateException.class, () -> origem.transferenciaPara(destino, 150));
        assertEquals(100, origem.getSaldo());
        assertEquals(50, destino.getSaldo());
        assertEquals(1, origem.getEntradas().size());
        assertEquals(1, destino.getEntradas().size());
    }

    
    @Test
    void transferenciaParaContaNulaLancaExcecaoENaoAlteraEstado() {
        Conta origem = new Conta();
        origem.deposito(100);
        assertThrows(IllegalArgumentException.class, () -> origem.transferenciaPara(null, 50));
        assertEquals(100, origem.getSaldo());
        assertEquals(1, origem.getEntradas().size());
    }


}