package com.facade;

import java.util.List;

import javax.ejb.Local;

import com.model.Usuario;

@Local
public interface UsuarioFacade {
	public Usuario findUserByEmail(String email);
	
	public abstract void save(Usuario usuario);
	 
    public abstract Usuario  update(Usuario usuario);
 
    public abstract void delete(Usuario usuario);
 
    public abstract Usuario  find(int entityID);
 
    public abstract List<Usuario> findAll();

}
