package mk.ukim.finki.finance.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class UserConnectedAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String provider;
    private String providerId;
    private LocalDateTime connectedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public UserConnectedAccount(String provider, String providerId,  User user) {
        this.provider = provider;
        this.providerId = providerId;
        this.connectedAt = LocalDateTime.now();
        this.user = user;
    }

}
