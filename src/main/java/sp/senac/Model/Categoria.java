package sp.senac.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "categoria")
public class Categoria 
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCategoria")
    private int idCategoria;

    @NotBlank(message = "O campo 'Nome' é obrigatório.")
    private String nome;

    private boolean ativo;

    @OneToMany(mappedBy = "idCategoria", fetch = FetchType.LAZY)
    private Set<Produto> produtos;

    public Set<Produto> getProdutos() 
    {
        return produtos;
    }

    public boolean getAtivo() 
    {
        return ativo;
    }

    public void setAtivo(boolean ativo) 
    {
        this.ativo = ativo;
    }

    public void setProdutos(Set<Produto> produtos) 
    {
        this.produtos = produtos;
    }

    public int getIdCategoria() 
    {
        return idCategoria;
    }

    public void setIdCategoria(int id) 
    {
        this.idCategoria = id;
    }

    public String getNome() 
    {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

}