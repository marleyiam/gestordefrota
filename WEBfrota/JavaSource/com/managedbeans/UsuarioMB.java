package com.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.facade.UsuarioFacade;
import com.model.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioMB {

	@EJB
	private UsuarioFacade usuarioFacade;

	private static final String CREATE_USUARIO = "Novo Usuario";
	private static final String DELETE_USUARIO = "deleteUsuario";
	private static final String UPDATE_USUARIO = "updateUsuario";
	private static final String LISTA_USUARIOS = "listaUsuarios";
	private static final String FIQUE_NA_MESMA_PAGINA = null;

	private Usuario usuario;

	public Usuario getUsuario() {
		if (usuario == null) {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			String userEmail = context.getUserPrincipal().getName();

			usuario = usuarioFacade.findUserByEmail(userEmail);
		}

		return usuario;
	}

	public Usuario getNewUsuario() {
		Usuario newusuario = new Usuario();
		return newusuario;
	}
	
	public void setNewUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getAllUsuarios() {
		return usuarioFacade.findAll();
	}

	public String updateUsuario() {
		return UPDATE_USUARIO;
	}

	public String updateUsuarioFinal() {
		try {
			usuarioFacade.update(usuario);
		} catch (EJBException e) {
			sendErrorMessageToUser("Erro!!!. Check se se o usuario tem email");
			return FIQUE_NA_MESMA_PAGINA;
		}

		sendInfoMessageToUser("Operação Completa: Update");
		return LISTA_USUARIOS;
	}

	public String deleteUsuario() {
		return DELETE_USUARIO;
	}

	public String deleteUsuarioFinal() {
		try {
			usuarioFacade.delete(usuario);
		} catch (EJBException e) {
			sendErrorMessageToUser("Erro. deu reiera");
			return FIQUE_NA_MESMA_PAGINA;
		}

		sendInfoMessageToUser("Operação Completa: Delete");

		return LISTA_USUARIOS;
	}

	public String createUsuario() {
		return CREATE_USUARIO;
	}


	public String cadastrarUsuario() {
		try {		
			usuarioFacade.save(getNewUsuario());
			//usuario = new Usuario();
		} catch (EJBException e) {
			sendErrorMessageToUser("Erro!!!. Check se se o usuario tem email");
			return FIQUE_NA_MESMA_PAGINA;
		}

		sendInfoMessageToUser("Operação Completa: Update");
		return LISTA_USUARIOS;

	}

	public String listaUsuarios() {
		return LISTA_USUARIOS;
	}

	/** CONTROLE DO USUÁRIO NA SESSÃO (Permissões,obj FacesContext,Mensagens) */

	public boolean isUserAdmin() {
		return getRequest().isUserInRole("ADMIN");
	}

	public String logOut() {
		getRequest().getSession().invalidate();
		return "logout";
	}

	private HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	private void sendInfoMessageToUser(String menssagem) {
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				menssagem, menssagem));
	}

	private void sendErrorMessageToUser(String menssagem) {
		FacesContext context = getContext();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				menssagem, menssagem));
	}

	private FacesContext getContext() {
		FacesContext context = FacesContext.getCurrentInstance();
		return context;
	}
}
