package sp.senac.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sp.senac.Model.Produto;


//Os Repositorys servem para guardar os repositorios de banco de dados, onde voce coloca algumas validacaoes que o banco de dados 
//precisa fazer ou verificar
//Aqui estou verificando se os produtos estao disponiveis(ativos) e ordenando estes produtos que estao ativos


public interface ProdutoRepository extends JpaRepository<Produto, Integer>
{
    @Query("SELECT p FROM Produto p WHERE p.ativo = 1")
    List<Produto> buscaProdutosAtivos();

    @Query("SELECT p FROM Produto p ORDER BY p.ativo")
    List<Produto> buscaProdutosOrdenandoPorStatus();
}