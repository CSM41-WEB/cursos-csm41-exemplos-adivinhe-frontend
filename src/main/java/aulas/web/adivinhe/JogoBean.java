package aulas.web.adivinhe;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Suporte à página de adivinhação de número.
 * @author Wilson Horstmeyer Bogado
 */
@Named
@ViewScoped
public class JogoBean implements Serializable {
    private Integer palpite;
    private Integer palpiteAnterior;
    private boolean certo = false;
    private String senha;

    @Inject
    private JogadorBean jogadorBean;

    public Integer getPalpite() {
        return palpite;
    }

    public void setPalpite(Integer palpite) {
        this.palpite = palpite;
    }

    public Integer getPalpiteAnterior() {
        return palpiteAnterior;
    }

    public boolean isCerto() {
        return this.certo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void tentar() {
        jogadorBean.incrementarTentativas();
        certo = palpite == jogadorBean.getNumeroAtual();
        palpiteAnterior = palpite;
        palpite = null;
    }

    public String reiniciar() {
        jogadorBean.jogarNovamente();
        palpite = palpiteAnterior = null;
        certo = false;
        return "/restrito/bemvindo?faces-redirect=true";
    }

    public String iniciarJogo() {
        return "/restrito/adivinhe?faces-redirect=true";
    }

    public String entrar() {
        String action = null;
        if ("s3gr3d0".equals(senha)) {
            action = "/restrito/bemvindo?faces-redirect=true";
        } else {
            FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha incorretos", null);
            FacesContext.getCurrentInstance().addMessage(null, fm);
        }
        return action;
    }

    public String sair() {
        jogadorBean.setApelido(null);
        return "/acesso?faces-redirect=true";
    }
}