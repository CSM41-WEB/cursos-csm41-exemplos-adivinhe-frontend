package aulas.web.adivinhe.entity;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa a chave primária de um registo de jogo.
 * @author Wilson Horstmeyer Bogado
 */
public class JogoPK {

    private Integer jogador;

    private LocalDate dataHora;

    public Integer getJogador() {
        return jogador;
    }

    public void setJogador(Integer jogador) {
        this.jogador = jogador;
    }

    public LocalDate getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDate dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.jogador);
        hash = 71 * hash + Objects.hashCode(this.dataHora);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final JogoPK other = (JogoPK) obj;
        if (!Objects.equals(this.jogador, other.jogador)) {
            return false;
        }
        return Objects.equals(this.dataHora, other.dataHora);
    }

    @Override
    public String toString() {
        return "JogoPK{" + "jogador=" + jogador + ", dataHora=" + dataHora + '}';
    }
}
