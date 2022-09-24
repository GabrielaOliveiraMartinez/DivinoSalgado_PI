package sp.senac.Controller.ControllerBackoffice;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import sp.senac.Model.Produto;
import sp.senac.Repository.ProdutoRepository;
import sp.senac.Utils.FileUploadUtil;

@RestController
@RequestMapping("Backoffice/Produtos")
public class ProdutoController 
{
    @Autowired
    private ProdutoRepository produtoRepository;


    @GetMapping
    public ModelAndView Listar()
    {
        ModelAndView mv = new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Produtos/Listar.html");

        List<Produto> produtos = produtoRepository.buscaProdutosOrdenandoPorStatus();

        mv.addObject("produtos", produtos);

        return mv;
    }

    @GetMapping("/DivinoSalgadoPi/src/main/resources/templates/Produtos/Adicionar.html")
    public ModelAndView Adicionar() 
    {
        ModelAndView mv = new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Produtos/Adicionar.html");

        mv.addObject("produtos", new Produto());

        return mv;
    }

    @PostMapping("/DivinoSalgadoPi/src/main/resources/templates/Produtos/Adicionar.html")
    public ModelAndView Adicionar(@ModelAttribute("produtos") @Valid Produto p, @RequestParam("imagem")MultipartFile imagem, BindingResult br) throws IOException
    {
        if(br.hasErrors())
        {
            return new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Produtos/Adicionar.html");
        }else
        {
            ModelAndView mv = new ModelAndView("redirect:/Backoffice/Produtos");

            String arquivo = StringUtils.cleanPath(imagem.getOriginalFilename());

            p.setImagemParaSalvar(arquivo);
            p.setAtivo(true);

            String diretorioSalvarImagem = "/DivinoSalgadoPi/src/main/resources/static/img/ProdutosImagens" + UUID.randomUUID();
            
            FileUploadUtil.saveFile(diretorioSalvarImagem, arquivo, imagem);

            produtoRepository.save(p);

            return mv;
        }
    }
    


    @GetMapping("/Alterar/{id}")
    public ModelAndView Alterar(@PathVariable int id)
    {
        Produto p = produtoRepository.findById(id).get();

        if(p == null)
        {
            return new ModelAndView("redirect:/Backoffice/Produtos");
        }

        ModelAndView mv = new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Produtos/Adicionar.html");
        
        mv.addObject("prdoutos", p);

        return mv;
    }


    @PostMapping("/Alterar/{id}")
    public ModelAndView Alterar(@PathVariable int id, @ModelAttribute("produtos") @Valid Produto p, @RequestParam("imagem") MultipartFile imagem, BindingResult br) throws IOException
    {
        if(br.hasErrors())
        {
            return new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Produtos/Adicionar.html");
        }
        else
        {
            ModelAndView mv = new ModelAndView("redirect:/Backoffice/Produtos");

            Produto pAux = produtoRepository.findById(id).get();

            if(pAux == null)
            {
                pAux.setNome(p.getNome());
                pAux.setPreco(p.getPreco());
                pAux.setDescricao(p.getDescricao());
                pAux.setImagemParaSalvar(p.getImagemParaSalvar());
                pAux.setImagem(p.getImagem());
                pAux.setAtivo(p.getAtivo());
                pAux.setIdCategoria(p.getIdCategoria());

                String arq = StringUtils.cleanPath(imagem.getOriginalFilename());

                pAux.setImagemParaSalvar(arq);

                String diretorioSalvarImagem = "/DivinoSalgadoPi/src/main/resources/static/img/ProdutosImagens";

                FileUploadUtil.saveFile(diretorioSalvarImagem, arq + "_" + UUID.randomUUID(), imagem);

                produtoRepository.save(pAux);

                
            }
            return mv;
        }
        

    }

    @GetMapping("/Excluir/{id}")
    public ModelAndView Excluir(@PathVariable int id)
    {
        Produto p = produtoRepository.findById(id).get();

        if(p == null)
        {
            return new ModelAndView("redirect:/Backoffice/Produtos");
        }

        if(p.getAtivo())
        {
            p.setAtivo(false);
        }
        else
        {
            p.setAtivo(true);
        }

        produtoRepository.save(p);

        return new ModelAndView("redirect:/Backoffice/Produtos");

    }


    @GetMapping("Detalhes/{id}")
    public ModelAndView Detalhes(@PathVariable int id)
    {
        ModelAndView mv = new ModelAndView("/DivinoSalgadoPi/src/main/resources/templates/Produtos/Detalhes.html");

        Produto p = produtoRepository.findById(id).get();

        mv.addObject("produtos", p);

        return mv;
    }
}