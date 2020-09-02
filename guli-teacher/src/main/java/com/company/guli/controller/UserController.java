package com.company.guli.controller;

import com.company.guli.result.AppResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @PostMapping("/login")
    public AppResponse login(){
        Map<String,String> map = new HashMap<>();
        map.put("token","admin");
        return AppResponse.ok(map);
    }

    @PostMapping("/logout")
    public void logout(){
        
    }

    @GetMapping("info")
    public AppResponse info(){
        //"data":{"roles":["admin"],
        // "name":"admin",
        // "avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}

        Map<String,String> map = new HashMap<>();
        map.put("roles","[\"admin\"]");
        map.put("name","admin");
        map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return AppResponse.ok(map);
    }
}
