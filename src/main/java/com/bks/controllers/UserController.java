package com.bks.controllers;

import com.bks.domain.Account;
import com.bks.domain.User;
import com.bks.domain.enums.State;
import com.bks.service.UserService;
import com.bks.service.dto.DroneDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
}