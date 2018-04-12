package ma.ade.kwetter2.database.objects;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

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
    
    public User(ma.ade.kwetter2.domain.User user)
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
    
    public ma.ade.kwetter2.domain.User Convert()
    {
        return new ma.ade.kwetter2.domain.User(this);
    }
}
