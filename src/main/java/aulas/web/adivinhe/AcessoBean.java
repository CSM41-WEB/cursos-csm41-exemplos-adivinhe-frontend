package aulas.web.adivinhe;

import aulas.web.adivinhe.entity.Jogador;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;

/**
 * Suporte á página de acesso.
 * @author Wilson Horstmeyer Bogado
 */
@Named
@RequestScoped
public class AcessoBean implements Serializable {
    private String usuario;
    private String senha;
    
    private static final String MSG_ERRO = "Erro inesperado de login";
    
    @Inject
    private JogadorBean jogadorBean;
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    private FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
    
    private HttpServletRequest getRequest() {
        return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    }
    
    private String getViewInicial(Jogador jogador) {
        String apelido = jogador.getApelido();
        return "admin".equals(apelido) ? "/admin/admin_jogadores?faces-redirect=true"
                                       : "/restrito/bemvindo?faces-redirect=true"; 
    }
    
    public String login() {
        String action = null;
        var basicAuth = new BasicAuthentication(usuario, senha);
        try (Client client = ClientBuilder.newClient()) {
            Jogador jogador = client.target("http://localhost:8084/jogador/info/apelido/{apelido}")
                    .register(basicAuth)
                    .resolveTemplate("apelido", usuario)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Jogador.class);
            jogadorBean.setAuthentication(basicAuth);
            jogadorBean.setJogador(jogador);
            action = getViewInicial(jogador);
        } catch (NotAuthorizedException e) {
            var fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário ou senha inválidos", null);
            FacesContext.getCurrentInstance().addMessage(null, fm);
        } catch (Throwable t) {
            var fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_ERRO, t.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, fm);
            FacesContext.getCurrentInstance().getExternalContext().log(MSG_ERRO, t);
        }
        return action;
    }
    
    public String logout() {
        try {
            var request = getRequest();
            request.getSession().invalidate();
        } catch (Exception e) {
            getFacesContext().getExternalContext().log(usuario, e);
        }
        return "/acesso?faces-redirect=true";
    }
}
