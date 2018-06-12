package ma.ade.kwetter2.database.objects;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "USERENTITY")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "user.getByEmail", query="SELECT u FROM User u WHERE u.email LIKE :email"),
        @NamedQuery(name = "user.getAll", query="SELECT U FROM User U"),
        //TODO: Create followers
        @NamedQuery(name = "user.getFollowers", query="SELECT U FROM User U")
})
public class User implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    @Column(unique=true)
    private String email;
    private String password;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Profile profile;
    @ManyToMany
    private Set<User> following;

    public User() {
    }

    public User(long id, String email, String password, Profile profile, Set<User> following)
    {
        this.id = id;
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.following = following;
    }
    
    public User(String email, String password, Profile profile)
    {
        this.email = email;
        this.password = password;
        this.profile = profile;
        this.following = new HashSet<>();
    }
    
    public User(ma.ade.kwetter2.domain.User user)
    {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.profile = user.getProfile().Convert();
        if (user.getFollowing() != null) {
            this.following = user.getFollowing().stream().map(ma.ade.kwetter2.domain.User::convert).collect(Collectors.toSet());
        } else {
            this.following = new HashSet<>();
        }
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public ma.ade.kwetter2.domain.User Convert()
    {
        return new ma.ade.kwetter2.domain.User(this);
    }
}
