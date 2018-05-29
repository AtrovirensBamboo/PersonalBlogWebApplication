package com.example.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;

    @NotEmpty
    @NotNull(message = "username不能为空")
    @Size(min=1,max=20,message = "username长度必须小于20")
    @Pattern(regexp="^[a-zA-Z0-9]+$",message = "username只允许大小写字母,数字,下划线")
    private String username;

    @NotEmpty
    @NotNull(message = "密码不能为空")
    @Size(min=1,max=20,message = "password长度必须小于20")
    @Pattern(regexp="\\S+",message = "password允许所有非空字符")
    private String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
