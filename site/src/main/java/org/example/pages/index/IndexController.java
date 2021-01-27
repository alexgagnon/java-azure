package org.example.pages.index;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
  private static final Logger log = LoggerFactory.getLogger(IndexController.class);

  @Autowired
  private IndexService indexService;

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("employees", indexService.getEmployees());
    return "index";
  }
}
