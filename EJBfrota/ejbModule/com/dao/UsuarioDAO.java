package com.dao;

import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;


import com.model.Usuario;

@Stateless
public class UsuarioDAO extends GenericDAO<Usuario> {

	public UsuarioDAO() {
		super(Usuario.class);
	}

	    public Usuario findUserByEmail(String email){
	        Map<String, Object> parametros = new HashMap<String, Object>();
	        parametros.put("email", email);    
	 
	        return super.findOneResult(Usuario.FIND_BY_EMAIL, parametros);
	}
	    

}
