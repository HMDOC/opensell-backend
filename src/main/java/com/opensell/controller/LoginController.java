package com.opensell.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opensell.repository.LoginRepository;

/**
 * @author Quoc Dung
 */

@CrossOrigin("http://localhost/")
@RestController
public class LoginController {

    @Autowired
    private LoginRepository loginRepository;

    // Tu dois faire un record
    // Moi je te dirais return des chiffres et interprete dans le frontend ou
    // Si le user est valide retourne les informations de lutilisateur utiles, aussi les sites, il dise pas
    // le userbname est invalide, il te dise username or password invalid
    @PostMapping("/login")
    public int login(@RequestParam String username, @RequestParam String pwd) {
        if (loginRepository.findByUsername(username) ==  1) {
            if (loginRepository.checkPassword(username, pwd) == 1) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 3;
        }
    }
}
