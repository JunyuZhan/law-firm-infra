package com.lawfirm.auth.controller;

import com.lawfirm.auth.service.UserService;
import com.lawfirm.common.model.Result;
import com.lawfirm.model.auth.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public Result<User> getUser(@PathVariable Long id) {
        return Result.ok().data(userService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    public Result<User> createUser(@RequestBody User user) {
        userService.save(user);
        return Result.ok().data(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.updateById(user);
        return Result.ok().data(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.removeById(id);
        return Result.ok();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('user:update')")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Boolean status) {
        userService.updateStatus(id, status);
        return Result.ok();
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasAuthority('user:update')")
    public Result<Void> updatePassword(@PathVariable Long id, @RequestParam String newPassword) {
        userService.updatePassword(id, newPassword);
        return Result.ok();
    }
} 