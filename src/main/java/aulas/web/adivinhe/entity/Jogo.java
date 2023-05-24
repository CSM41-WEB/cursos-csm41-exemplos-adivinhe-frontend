package aulas.web.adivinhe.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Representa um registro de jogo.
 * @author Wilson Horstmeyer Bogado
 */
public class Jogo {
    
    public static final String DATA_JOGO_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    private JogoPK jogoPK;

    @NotNull
    @Min(value = 0, message = "Pontuação ${validatedValue} é inválida. A pontuação mínima permitida é {value}.")
    private Integer pontuacao;

    public JogoPK getJogoPK() {
        return jogoPK;
    }

    public void setJogoPK(JogoPK jogoPK) {
        this.jogoPK = jogoPK;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.jogoPK);
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
        final Jogo other = (Jogo) obj;
        if (!Objects.equals(this.jogoPK, other.jogoPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Jogo{" + "JogoPK=" + jogoPK + '}';
    }
}
