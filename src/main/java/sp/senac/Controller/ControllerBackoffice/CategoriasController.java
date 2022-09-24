package sp.senac.Controller.ControllerBackoffice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import sp.senac.Model.Categoria;
import sp.senac.Repository.CategoriaRepository;

@RestController
@RequestMapping("Backoffice/Categorias")
public class CategoriasController 
{
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ModelAndView Listar()
    {
        ModelAndView mv = new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Categorias/Listar.html");

        List<Categoria> categorias = categoriaRepository.findAll();

        mv.addObject("categorias", categorias);

        return mv;
    }

    @GetMapping("/Adicionar")
    public ModelAndView Adicionar()
    {
        ModelAndView mv = new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Categorias/Adicionar.html");

        mv.addObject("categoria", new Categoria());

        return mv;
    }

    @PostMapping("/Adicionar")
    public ModelAndView Adicionar(@ModelAttribute("categoria") @Valid Categoria c, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Categorias/Adicionar.html");
        }
        else
        {
            ModelAndView mv = new ModelAndView("redirect:/Backoffice/Categorias");

            c.setAtivo(true);

            categoriaRepository.save(c);

            return mv;
        }
    }

    @GetMapping("/Alterar/{id}")
    public ModelAndView Alterar(@PathVariable int id)
    {
        Categoria c = categoriaRepository.findById(id).get();

        if(c == null)
        {
            return new ModelAndView("redirect:/Backoffice/Categorias");
        }
    
        ModelAndView mv = new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Categorias/Adicionar.html");

        mv.addObject("categoria", c);

        return mv;
    }

    @PostMapping("/Alterar/{id}")
    public ModelAndView Alterar(@PathVariable int id, @ModelAttribute("categoria") @Valid Categoria c, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            return new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Categorias/Adicionar.html");
        }
        else
        {
            ModelAndView mv = new ModelAndView("redirect:/Backoffice/Categorias");

            Categoria cAux = categoriaRepository.findById(id).get();

            if(cAux == null)
            {
                return new ModelAndView("redirect:/Backoffice/Categorias");
            }

            cAux.setNome(c.getNome());

            categoriaRepository.save(cAux);

            return mv;
        }
    }

    @GetMapping("/Excluir/{id}")
    public ModelAndView Excluir(@PathVariable int id)
    {
        Categoria c = categoriaRepository.findById(id).get();

        if(c == null)
        {
            return new ModelAndView("redirect:/Backoffice/Categorias");
        }

        if(c.getAtivo())
        {
            c.setAtivo(false); 
        }
        else
        {
            c.setAtivo(true);
        }

        categoriaRepository.save(c);

        return new ModelAndView("redirect:/Backoffice/Categorias");
    }
}