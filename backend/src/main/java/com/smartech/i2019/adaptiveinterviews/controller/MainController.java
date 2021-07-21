package com.smartech.i2019.adaptiveinterviews.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:8081"})
@RestController
@Tag(name = "Главная страница", description = "Управление корневой конечной точкой")
public class MainController {

}