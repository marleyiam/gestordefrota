package com.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.dao.UsuarioDAO;
import com.model.Usuario;

@Stateless
@Local(UsuarioFacade.class)
public class UsuarioFacadeImp {
	
	@EJB
    private UsuarioDAO usuarioDao;
    
	public void save(Usuario usuario){
		usuarioDao.create(usuario);
	}
 
    public Usuario update(Usuario usuario) {
    	usuarioDados(usuario);
 
        return usuarioDao.update(usuario);
    }
 
    public void delete(Usuario usuario) {
    	usuarioDao.delete(usuario);
    }
 
    public Usuario find(int entityID) {
        return usuarioDao.find(entityID);
    }
 
    public List<Usuario> findAll() {
        return usuarioDao.findAll();
    }
    
    public Usuario findUserByEmail(String email) {
        return usuarioDao.findUserByEmail(email);
    }
    
    //método de validação
    private void usuarioDados(Usuario usuario){
        boolean hasError = false;
 
        if(usuario == null){
            hasError = true;
        }
 
        if (usuario.getNome() == null || "".equals(usuario.getNome().trim())){
            hasError = true;
        }
 
        if (hasError){
            throw new IllegalArgumentException("Faltam dados do usuario");
        }
    }

}
