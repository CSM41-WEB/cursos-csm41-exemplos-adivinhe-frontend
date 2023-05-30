package aulas.web.adivinhe;

import aulas.web.adivinhe.entity.Jogador;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;

/**
 * Representa a sessão do jogador.
 * @author Wilson Horstmeyer Bogado
 */
@Named
@SessionScoped
public class JogadorBean implements Serializable {
    private int numeroAtual;
    private int minimo = 1;
    private int maximo = 9;
    private int numTentativas = 0;
    
    private Jogador jogador;
    private BasicAuthentication authentication;
    
    public JogadorBean() {
        novoNumero();
    }

    public BasicAuthentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(BasicAuthentication authentication) {
        this.authentication = authentication;
    }
    
    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }
    
    public int getNumeroAtual() {
        return numeroAtual;
    }

    public void setNumeroAtual(int numeroAtual) {
        this.numeroAtual = numeroAtual;
    }

    public int getMinimo() {
        return minimo;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public int getNumTentativas() {
        return numTentativas;
    }

    public void setNumTentativas(int numTentativas) {
        this.numTentativas = numTentativas;
    }

    /**
     * Calcula o número de pontos que o jogador tem neste momento.
     * @return O número de pontos
     */
    public int getPontos() {
        int pontos = maximo - minimo - numTentativas + 1;
        return  pontos < 0 ? 0 : pontos;
    }

    public void incrementarTentativas() {
        this.numTentativas++;
    }

    public void jogarNovamente() {
        numTentativas = 0;
        novoNumero();
    }

    private void novoNumero() {
        this.numeroAtual = (int) (Math.random() * (maximo - minimo) + minimo + 1);
    }
}
