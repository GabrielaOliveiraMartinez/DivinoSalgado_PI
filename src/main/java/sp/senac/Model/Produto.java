package sp.senac.Model;

import java.io.Serializable;


import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import javax.validation.*;

import org.springframework.*;
import org.springframework.context.annotation.Description;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.MultipartFile;




//Aqui estamos atribuindo que esta é uma entidade que será uma tabela de banco de dados
@Entity
@Table(name = "Produtos")
public class Produto implements Serializable 
{
    //Aqui estamos criando um identificador para esta entidade que é o ID
    //Onde gera um numero aleatorio e nao fixo para cada identificador
    
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column
    private int Id;

    
    //NotBlank é uma validação do SpringBoot, para que traga uma mensagem se tiver um campo em branco(somnente para tipo String)
    //@Size é para declarar o maximo ou minimo de caracteres aceitos
    @NotBlank(message = "Preencha o nome do produto!")
    @Size(max = 60)
    private String nome;

    //@NotNull é para verificar se o campo requirido nao retorna um valor nulo
    //@NumberFormat é para declarar um tipo de formatação(o nosso como Decimal para produtos)
    //@Min é para fazer validacao pra verificar se o valor correspondente é menor que zero ou valor minimamente aceitavel
    @NotNull(message = "Preencha o preço do produto!")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 0, message = "O preço não pode ser negativo")
    private double preco;


    //@NotEmpty serve para que nao deixe uma caixa de caracteres receber um campo vazio
    @NotEmpty(message = "Prencha a descrição!")
    @Size(max = 150)
    private String descricao;


    @Transient
    private String imagemParaSalvar;

    @Transient
    private MultipartFile imagem;

    private boolean ativo;

    public Produto()
    {

    }

    public Produto(int id, @NotBlank(message = "Preencha o nome do produto!") @Size(max = 60) String nome,
            @NotNull(message = "Preencha o preço do produto!") @Min(value = 0, message = "O preço não pode ser negativo") double preco,
            @NotEmpty(message = "Prencha a descrição!") @Size(max = 150) String descricao, String imagemParaSalvar,
            MultipartFile imagem, boolean ativo) 
    {
        Id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.imagemParaSalvar = imagemParaSalvar;
        this.imagem = imagem;
        this.ativo = ativo;
    }

    public int getId() 
    {
        return Id;
    }

    public void setId(int id) 
    {
        Id = id;
    }

    public String getNome() 
    {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public double getPreco() 
    {
        return preco;
    }

    public void setPreco(double preco) 
    {
        this.preco = preco;
    }

    public String getDescricao() 
    {
        return descricao;
    }

    public void setDescricao(String descricao) 
    {
        this.descricao = descricao;
    }

    public String getImagemParaSalvar() 
    {
        return imagemParaSalvar;
    }

    public void setImagemParaSalvar(String imagemParaSalvar) 
    {
        this.imagemParaSalvar = imagemParaSalvar;
    }

    public MultipartFile getImagem() 
    {
        return imagem;
    }

    public void setImagem(MultipartFile imagem) 
    {
        this.imagem = imagem;
    }

    public boolean getAtivo() 
    {
        return ativo;
    }

    public void setAtivo(boolean ativo) 
    {
        this.ativo = ativo;
    }


    @NotNull(message = "Selecione uma categoria!")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    (
        name = "idCategoria", nullable = false
    )
    private Categoria idCategoria;
    
    public Categoria getIdCategoria()
    {
        return idCategoria;
    }

    public void setIdCategoria(Categoria c)
    {
        this.idCategoria = c;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Id;
        return result;
    }

}