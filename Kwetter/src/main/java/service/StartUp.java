/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import domain.User;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartUp {

    @Inject 
    private UserService service;
      
    public StartUp() {
    }
     
    @PostConstruct
    private void intData(){
        service.addUser(new User("Frank", 30, "Java"));
        service.addUser(new User("Monique", 29, "JavaScript"));
        service.addUser(new User("Jasmijn", 28, "C#"));
        service.addUser(new User("Feline", 26, "C++"));
        service.addUser(new User("Pim", 25, "C"));
        service.addUser(new User("Joris", 24, "Java EE"));
    }
    
}
