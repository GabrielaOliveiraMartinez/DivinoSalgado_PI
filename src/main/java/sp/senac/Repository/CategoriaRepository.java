package sp.senac.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sp.senac.Model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>
{
    @Query("SELECT c FROM Categoria c WHERE c.ativo = 1")
    List<Categoria> buscaCategoriasAtivas();
}
