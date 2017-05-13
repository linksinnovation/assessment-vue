package co.th.linksinnovation.integrity.config;

import co.th.linksinnovation.integrity.model.Authority;
import co.th.linksinnovation.integrity.model.UserDetails;
import co.th.linksinnovation.integrity.model.authen.Authenticate;
import co.th.linksinnovation.integrity.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author Jirawong Wongdokpuang <jiraowng@linksinnovation.com>
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (iserviceAuthen(authentication)) {
            UserDetails findOne = userDetailsRepository.findOne(authentication.getName().toLowerCase());
            List<GrantedAuthority> grantedAuths;
            if (findOne != null) {
                grantedAuths = (List<GrantedAuthority>) findOne.getAuthorities();
            } else {
                grantedAuths = new ArrayList<>();
                grantedAuths.add(new SimpleGrantedAuthority("User"));
            }
            Authentication auth = new UsernamePasswordAuthenticationToken(authentication.getName(), authentication.getCredentials().toString(), grantedAuths);
            return auth;
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private boolean iserviceAuthen(Authentication authentication) {
        
        ResponseEntity<Authenticate> postForEntity = null;
        if (!mockAuthen(authentication.getCredentials().toString())) {
            RestTemplate rest = new RestTemplate();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("App-Key", "blySsonddG3ZDLU1d2V9hCEUyKt09iDdCKrndyFxAewAY4ny6xU4OOprDoJl5IWa.IqK3VvSVoQ6O4vt3-0kxA");

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("username", authentication.getName());
            map.add("password", authentication.getCredentials().toString());
            
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
            
            postForEntity = rest.exchange("https://api.mitrphol.com:3001/authentication/login", HttpMethod.POST, request, Authenticate.class);
        }

        if (mockAuthen(authentication.getCredentials().toString()) || postForEntity.getBody().getSuccess() != null) {
            UserDetails userDetails = userDetailsRepository.findOne(authentication.getName());
            if (userDetails == null) {
                userDetails = new UserDetails();
                userDetails.setUsername(authentication.getName().toLowerCase());
                userDetails.setPassword(authentication.getCredentials().toString());
                userDetails.setNameEn(postForEntity.getBody().getSuccess().getData().getUserInfo().getFullname().getEn());
                userDetails.setNameTh(postForEntity.getBody().getSuccess().getData().getUserInfo().getFullname().getTh());
                userDetails.setUserId(postForEntity.getBody().getSuccess().getData().getUserInfo().getId());
                userDetails.setPhoto(postForEntity.getBody().getSuccess().getData().getUserInfo().getPhoto());
                Authority authority = new Authority();
                authority.setAuthority("User");
                userDetails.addAuthority(authority);
            }
            userDetailsRepository.save(userDetails);
            return true;
        }

        return false;
    }

    /**
     *
     * @param pass
     * @return
     *
     * Mock Password Remove it in production
     *
     */
    private boolean mockAuthen(String pass) {
        return pass.equals("p@ss2017");
    }
}
