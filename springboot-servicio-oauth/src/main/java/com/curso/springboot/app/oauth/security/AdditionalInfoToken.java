package com.curso.springboot.app.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.curso.springboot.app.commons.model.entity.Usuario;
import com.curso.springboot.app.oauth.services.IUsuarioService;

@Component
public class AdditionalInfoToken implements TokenEnhancer {

	@Autowired
	private IUsuarioService usuarioService;
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Usuario usuario = usuarioService.getUserByUsername(authentication.getName());
		
 		Map<String, Object> info = new HashMap<String, Object>();
 		info.put("nombre" , usuario.getNombre());
 		info.put("apellidos", usuario.getApellidos());
 		info.put("email", usuario.getEmail());
 		
 		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}
