package domain;

import jakarta.persistence.*;

@MappedSuperclass
public class Entity<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private ID id;

    public Entity() {
    }

    Entity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return id;
    }
    public void setId(ID id) {
        this.id = id;
    }
}
