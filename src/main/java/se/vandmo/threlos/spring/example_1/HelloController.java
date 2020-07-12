package se.vandmo.threlos.spring.example_1;

import static java.lang.String.format;
import static java.util.Locale.ROOT;

import java.util.concurrent.CompletableFuture;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/hello")
  public CompletableFuture<String> hello() {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    return CompletableFuture.supplyAsync(() -> {
      String nameNow = SecurityContextHolder.getContext().getAuthentication().getName();
      if (!name.equals(nameNow)) {
        System.out.println(format(ROOT, "What!? How did %s become %s?", name, nameNow));
      }
      return "world";
    });
  }

}