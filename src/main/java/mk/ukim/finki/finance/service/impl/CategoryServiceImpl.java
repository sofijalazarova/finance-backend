package mk.ukim.finki.finance.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Category;
import mk.ukim.finki.finance.model.dto.CategoryDto;
import mk.ukim.finki.finance.repository.CategoryRepository;
import mk.ukim.finki.finance.service.CategoryService;
import mk.ukim.finki.finance.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllByUser(User user) {
        return this.categoryRepository.findAllByUser(user);
    }

    @Override
    public Optional<Category> save(CategoryDto categoryDto, User user) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setEmoji(categoryDto.getEmoji());
        category.setUser(user);

        return Optional.of(this.categoryRepository.save(category));
    }

    @Override
    public Optional<Category> edit(Long id, CategoryDto categoryDto, User user) {
        Category category = this.categoryRepository.findById(id).orElseThrow();
        category.setName(categoryDto.getName());
        category.setEmoji(categoryDto.getEmoji());
        category.setUser(user);
        return Optional.of(this.categoryRepository.save(category));
    }

    @Override
    public void archive(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow();
        category.setIsArchived(true);
        this.categoryRepository.save(category);
    }

    @Override
    public void restore(Long id) {
        Category category = this.categoryRepository.findById(id).orElseThrow();
        category.setIsArchived(false);
        this.categoryRepository.save(category);
    }
}
