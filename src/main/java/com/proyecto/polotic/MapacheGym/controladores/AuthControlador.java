
package com.proyecto.polotic.MapacheGym.controladores;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class AuthControlador {


    @GetMapping("/login")
    public ModelAndView showLoginForm(Model model,
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "logout", required = false) String logout) {

        ModelAndView maw = new ModelAndView();
        boolean showHeader = false;
        maw.setViewName("fragments/base");
        maw.addObject("title", "Iniciar sesión");
        maw.addObject("view", "auth/login");
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        maw.addObject("showHeader", showHeader);
        return maw;
    }

     @GetMapping({ "/loginSuccess" })
     public RedirectView loginCheck() {
         return new RedirectView("/home");
     }

 /*    @GetMapping("/registro")
    public ModelAndView registro(RegistroDto registroDto) {
        ModelAndView maw = new ModelAndView();
        boolean showHeader = false;
        maw.setViewName("fragments/base");
        maw.addObject("title", "Registrarse");
        maw.addObject("view", "auth/registro");
        maw.addObject("registroDto", registroDto);
        maw.addObject("showHeader", showHeader);
        return maw;
    } */

/*     @PostMapping("/registro")
    public ModelAndView registrar(@RequestParam(name = "g-recaptcha-response") String recaptchaResponse,
            @Valid RegistroDto registroDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String captchaVerifyMessage = recaptchaServicio.verifyRecaptcha(ip, recaptchaResponse);

        if (captchaVerifyMessage != "") {
            br.rejectValue("recaptcha", "recaptcha", captchaVerifyMessage);
        }

        if (br.hasErrors()) {
            return this.registro(registroDto);
        }

        Empleado u = new Empleado();
        u.setUsuario(registroDto.getUsuario());
        u.setContrasenia(codificador.encode(registroDto.getPassword()));
        u.setRol(rolRepositorio.findByNombre("Administrador")
                .orElseThrow(() -> new IllegalArgumentException("Error al crear usuario")));

        empleadoRepositorio.save(u);

        HomeControlador hc = new HomeControlador();
        return hc.home();
    } */

}