package aulas.web.adivinhe.entity;

import jakarta.json.bind.annotation.JsonbDateFormat;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Representa a chave primária de um registo de jogo.
 * @author Wilson Horstmeyer Bogado
 */
public class JogoPK {

    private Integer jogador;

    @JsonbDateFormat(Jogo.DATA_JOGO_PATTERN)
    private ZonedDateTime dataHora;

    public JogoPK() {
    }

    public JogoPK(Integer jogador, ZonedDateTime dataHora) {
        this.jogador = jogador;
        this.dataHora = dataHora;
    }

    public Integer getJogador() {
        return jogador;
    }

    public void setJogador(Integer jogador) {
        this.jogador = jogador;
    }

    public ZonedDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(ZonedDateTime dataHora) {
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
