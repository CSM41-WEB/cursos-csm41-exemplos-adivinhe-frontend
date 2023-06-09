package aulas.web.adivinhe;

import aulas.web.adivinhe.entity.Jogador;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Suporte à view de administração de jogadores.
 * @author Wilson Horstmeyer Bogado
 */
@Named
@ViewScoped
public class AdminJogadoresBean implements Serializable {
    private static final String MSG_ERRO = "Não foi possível obter a lista de jogadores";
    
    private List<Jogador> jogadores;

    private static final String URL_BACKEND =
        ResourceBundle.getBundle("aulas.web.adivinhe.config").getString("adivinhe.backend.url");
    
    @Inject
    private JogadorBean jogadorBean;

    public AdminJogadoresBean() {
    }

    public List<Jogador> getJogadores() {
        if (jogadores == null) {
            try (Client client = ClientBuilder.newClient()) {
                jogadores = client.target(URL_BACKEND + "/jogador/list")
                        .register(jogadorBean.getAuthentication())
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Jogador>>() {});
            } catch (Throwable t) {
                var fm = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_ERRO, t.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, fm);
                FacesContext.getCurrentInstance().getExternalContext().log(MSG_ERRO, t);
                jogadores = Collections.EMPTY_LIST;
            }
        }
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
