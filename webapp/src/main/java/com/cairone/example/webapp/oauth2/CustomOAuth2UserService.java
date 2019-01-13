package com.cairone.example.webapp.oauth2;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cairone.example.webapp.domain.UsuarioEntity;
import com.cairone.example.webapp.exceptions.OAuth2AuthenticationProcessingException;
import com.cairone.example.webapp.repositories.UsuarioRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired private UsuarioRepository usuarioRepository = null;
	@Autowired private PasswordEncoder passwordEncoder = null;

	@Override @Transactional(readOnly=true)
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
	}

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        
    	OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<UsuarioEntity> userOptional = usuarioRepository.findByEmail(oAuth2UserInfo.getEmail());
        UsuarioEntity user = null;
        if(userOptional.isPresent()) {
        	user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private UsuarioEntity registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
    	
    	Long id = usuarioRepository.getMaxId();
		
		if(id == 0) id = 0L;
		id++;
		
    	UsuarioEntity user = new UsuarioEntity();
        
        user.setId(id);
        user.setNombre(oAuth2UserInfo.getName());
        user.setUsername(oAuth2UserInfo.getName());
        user.setPassword(passwordEncoder.encode("none"));
        user.setCorreoElectronico(oAuth2UserInfo.getEmail());
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsEnabled(true);
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
                
        return usuarioRepository.save(user);
    }

    private UsuarioEntity updateExistingUser(UsuarioEntity existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setNombre(oAuth2UserInfo.getName());
        return usuarioRepository.save(existingUser);
    }
}
