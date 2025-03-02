package mk.ukim.finki.finance.repository;

import mk.ukim.finki.finance.model.Category;
import mk.ukim.finki.finance.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c JOIN FETCH c.user WHERE c.user = :user")
    List<Category> findAllByUser(User user);
    List<Category> findAllByIsArchivedFalse();
}
