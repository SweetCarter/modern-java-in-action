package java8demo.数据;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author gulh
 */
@Data
@NoArgsConstructor
public class User {
    
    private String name;
    private String password;
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, Integer age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }
}
