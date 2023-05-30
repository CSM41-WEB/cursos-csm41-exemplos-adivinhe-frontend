package aulas.web.adivinhe;

import aulas.web.adivinhe.entity.Jogo;
import aulas.web.adivinhe.entity.JogoPK;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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

    private static final String MSG_ERRO_JOGO = "Erro ao salvar jogo";
    
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

    private void registraJogo() {
        Jogo jogo = new Jogo();
        LocalDateTime dt = LocalDateTime.now();
        jogo.setJogoPK(new JogoPK(jogadorBean.getJogador().getCodigo(), ZonedDateTime.now()));
        jogo.setPontuacao(jogadorBean.getPontos());
        Entity<Jogo> entity = Entity.entity(jogo, MediaType.APPLICATION_JSON);
        try (Client client = ClientBuilder.newClient()) {
            client.target("http://localhost:8084/jogo/new")
                .register(jogadorBean.getAuthentication())
                .request(MediaType.APPLICATION_JSON)
                .post(entity);
        } catch (Throwable t) {
            var fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_ERRO_JOGO, t.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, fm);
            FacesContext.getCurrentInstance().getExternalContext().log(MSG_ERRO_JOGO, t);
        }
    }
    
    public void tentar() {
        jogadorBean.incrementarTentativas();
        certo = palpite == jogadorBean.getNumeroAtual();
        palpiteAnterior = palpite;
        if (certo) registraJogo();
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

    public String sair() {
        return "/acesso?faces-redirect=true";
    }
}
