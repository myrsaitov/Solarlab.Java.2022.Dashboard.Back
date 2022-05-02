package ru.solarlab.study.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home redirection to swagger api documentation
 */
@Controller /* Компонент слоя управления */
public class HomeController {

    @RequestMapping("/") /* Задаёт адрес, по которому весь 
        контроллер или его метод доступен на клиенте */
    public String index() {

        return "redirect:swagger-ui.html";
    
    }

}