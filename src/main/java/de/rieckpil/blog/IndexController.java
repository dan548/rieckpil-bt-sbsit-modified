package de.rieckpil.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IndexController {

  @PostMapping("/result")
  public String saveInputs(@ModelAttribute SampleInputs sampleInputs) {
    return "result";
  }

  @GetMapping("/sampleInputs")
  public String sampleInputs(Model model) {
    model.addAttribute("sampleInputs", new SampleInputs());
    return "sampleInputs";
  }

}
