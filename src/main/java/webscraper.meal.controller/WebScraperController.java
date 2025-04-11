package webscraper.meal.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webscraper.meal.service.WebScraperService;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/team04/scraper")
public class WebScraperController {

    private final WebScraperService webScraperService;

    public WebScraperController(WebScraperService webScraperService) {
        this.webScraperService = webScraperService;
    }

    @GetMapping("/meal")
    public Map<String, Object> getMeal() {
        return webScraperService.getMeal();
    }

//    @GetMapping("/events")
//    public Map<String, Object> getEvents() {
//        return webScraperService.getEvents();
//    }


//    @GetMapping("/weather")
//    public Map<String, Object> getWeather() {
//        return webScraperService.getWeather();
//    }
}
