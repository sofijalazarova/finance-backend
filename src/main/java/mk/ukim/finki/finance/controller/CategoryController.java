package mk.ukim.finki.finance.controller;

import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Category;
import mk.ukim.finki.finance.model.dto.CategoryDto;
import mk.ukim.finki.finance.service.CategoryService;
import mk.ukim.finki.finance.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("byUser")
    public ResponseEntity<List<Category>> findByUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.categoryService.findAllByUser(user));
    }

    @PostMapping("add")
    public ResponseEntity<Category> add(@AuthenticationPrincipal User user, @RequestBody CategoryDto categoryDto) {
        try {
            return this.categoryService.save(categoryDto, user)
                    .map(account -> ResponseEntity.ok().body(account))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Category> edit(@AuthenticationPrincipal User user, @PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        try {
            return this.categoryService.edit(id,categoryDto, user)
                    .map(account -> ResponseEntity.ok().body(account))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
