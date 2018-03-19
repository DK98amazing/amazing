package Distributed;

import com.mapper.api.distributed.UserService;

public class UserServiceImpl implements UserService{
    public String sayHello(String name) {
        return name;
    }
}
