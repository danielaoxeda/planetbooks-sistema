// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package com.planetbooks.controllers.views;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatalogoController {
 
   @GetMapping("/catalogo")
   public String catalogo(Model model) {
      model.addAttribute("test", "sadas");
      return "Catalogo";
   }
}
