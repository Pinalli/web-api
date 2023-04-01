package com.pinalli.web.api.repository;


import com.pinalli.web.api.handler.ObrigatoryFieldException;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import com.pinalli.web.api.model.User;
import java.util.List;

@Repository
public class UserRepository {
    public void save(User user) {
        if(user.getLogin() == null) {
            throw new ObrigatoryFieldException("login");
        }
        if (user.getPassword() == null) {
            throw new ObrigatoryFieldException("password");
        }
        if (user.getId() == null)
            System.out.println("SAVE - Recebendo o usuário na camada de repositório");
        else
            System.out.println("UPDATE - Recebendo o usuário na camada de repositório");
        System.out.println(user);
    }

    public void deleteById(Integer id) {
        System.out.println(String.format("DELETE/id - Recebendo o id: %d para excluir um usuário", id));
        System.out.println(id);
    }

    public List<User> findAll() {
        System.out.println("Listando os usuarios");
        List<User> users = new ArrayList<>();
        users.add(new User("alberto", "password"));
        users.add(new User("pinalli", "masterpass"));
        return users;
    }

    public User findById(Integer id) {
        System.out.println(String.format("GET/id - Recebendo o id: %d para localizar um usuário", id));
        return new User("alberto", "password");
    }
    public User finByUsername(String name) {
        System.out.println(String.format("FIND/username - Recebendo o username: %d para localizar um usuário", name));
        return new User("alberto", "password");
    }
}