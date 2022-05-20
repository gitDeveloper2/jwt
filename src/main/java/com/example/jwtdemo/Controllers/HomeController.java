package com.example.jwtdemo.Controllers;

import com.example.jwtdemo.Models.JwtRequest;
import com.example.jwtdemo.Models.JwtResponse;
import com.example.jwtdemo.utils.JwUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private AuthenticationManager authenticationManager;
@Autowired
private JwUtility jwUtility;
@Autowired
private UserDetailsService userDetailsService;

    @GetMapping("/home")
    public String home(){
        return "welcome home";
    }
    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new Exception("Invalid Credentials",e);
        }

final UserDetails userDetails=userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token=jwUtility.generateToken(userDetails);
        return new JwtResponse(token);





    }

}
