package mk.ukim.finki.finance.service;

import mk.ukim.finki.finance.model.Category;
import mk.ukim.finki.finance.model.dto.CategoryDto;
import mk.ukim.finki.finance.user.User;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAllByUser(User user);
    Optional<Category> save(CategoryDto categoryDto, User user);
    Optional<Category> edit(Long id, CategoryDto categoryDto, User user);
    void archive(Long id);
    void restore(Long id);
}
