package domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "UserEntity")
@XmlRootElement
public class User implements Serializable {
    
    @Id @GeneratedValue
    private Long id;   
    @Column(unique=true)
    private String name;
    private Integer age;
    private String skill;

    public User() {
    }

    public User(String name, Integer age, String skill) {
        this.name = name;
        this.age = age;
        this.skill = skill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer Age) {
        this.age = Age;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String Skill) {
        this.skill = Skill;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.name, other.name);
    }
}
