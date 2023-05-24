package aulas.web.adivinhe;

import aulas.web.adivinhe.entity.Jogador;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Representa a sessão do jogador.
 * @author Wilson Horstmeyer Bogado
 */
@Named
@ViewScoped
public class CadastroBean implements Serializable {

    private Jogador jogador = new Jogador();

    public CadastroBean() {
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public String cadastrar() {
        String action = null;
        try {
            action = "/admin/admin_jogadores?faces-redirect=true";
        } catch (Throwable t) {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Erro ao gravar registro", t.getLocalizedMessage());
            FacesContext.getCurrentInstance().addMessage(null, fm);
            FacesContext.getCurrentInstance().getExternalContext()
                    .log("Erro ao cadastrar " + jogador, t);
        }
        return action;
    }
}
