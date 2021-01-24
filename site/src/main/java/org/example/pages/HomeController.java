package org.example.pages;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.example.entities.employees.EmployeeDTO;
import org.example.util.ApiClient;
import org.example.util.JsonBodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  private static final Logger log = LoggerFactory.getLogger(HomeController.class);

  @GetMapping("/home")
  public String index(Model model) {
    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://localhost:9443/employees"))
        .timeout(Duration.ofMinutes(1)).GET().build();

    model.addAttribute("date", Date.from(Instant.now()));

    @SuppressWarnings("unchecked")
    Class<List<EmployeeDTO>> clazz = (Class) List.class;
    try {

      List<EmployeeDTO> employees = ApiClient.getApiClient()
          .send(request, new JsonBodyHandler<List<EmployeeDTO>>(clazz)).body().get();

      model.addAttribute("employees", employees);

      return "index";
    } catch (IOException | InterruptedException e) {
      log.error(e.getMessage());
    }

    return null;
  }
}
