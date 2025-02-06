package mk.ukim.finki.finance.repository;

import mk.ukim.finki.finance.model.Category;
import mk.ukim.finki.finance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUser(User user);
}
