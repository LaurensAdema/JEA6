package database.objects;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "USERENTITY")
@XmlRootElement
public class User implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    @Column(unique=true)
    private String email;
    private String password;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;

    public User() {
    }
    
    public User(String email, String password, Profile profile)
    {
        this.email = email;
        this.password = password;
        this.profile = profile;
    }
    
    public User(domain.User user)
    {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.profile = user.getProfile().Convert();
    }
    
    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }
    
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public Profile getProfile()
    {
        return profile;
    }

    public void setProfile(Profile profile)
    {
        this.profile = profile;
    }
    
    public domain.User Convert()
    {
        return new domain.User(this);
    }
}
