package database.objects;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Profile implements Serializable {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    private String firstName;
    private String lastName;
    private String bio;
    private String location;
    private String website;
    private String pictureLocation;
    
    @OneToOne(mappedBy = "profile")
    private User user;

    public Profile() {
    }

    public Profile(String firstName, String lastName, String bio, String location, String website, String pictureLocation)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.location = location;
        this.website = website;
        this.pictureLocation = pictureLocation;
    }
    
    public Profile(domain.Profile profile)
    {
        this.id = profile.getId();
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.bio = profile.getBio();
        this.location = profile.getLocation();
        this.website = profile.getWebsite();
        this.pictureLocation = profile.getPictureLocation();
    }
    
    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id = id;
    }
    
    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getWebsite()
    {
        return website;
    }

    public void setWebsite(String website)
    {
        this.website = website;
    }

    public String getPictureLocation()
    {
        return pictureLocation;
    }

    public void setPictureLocation(String pictureLocation)
    {
        this.pictureLocation = pictureLocation;
    }
    
    public domain.Profile Convert()
    {
        return new domain.Profile(this);
    }
}
