package mk.ukim.finki.finance.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {

    private String name;
    private String emoji;

    public CategoryDto(String name, String emoji) {
        this.name = name;
        this.emoji = emoji;
    }
}
