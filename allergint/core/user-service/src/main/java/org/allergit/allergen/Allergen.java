package org.allergit.allergen;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.allergit.entity.User;
import org.allergit.user.enums.AllergenSeverity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "allergens")
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AllergenSeverity severity;

    @ElementCollection
    @CollectionTable(name = "allergen_months", joinColumns = @JoinColumn(name = "allergen_id"))
    @Column(name = "month")
    private Set<String> monthsOfManifestation = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
