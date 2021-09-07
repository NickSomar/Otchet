package by.serchenya.storage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "storage")
@NoArgsConstructor
@Data
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int capacity;
    private String description;
    private String img;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pallet> pallets = new ArrayList<>();

    public Storage(String name, int capacity, String description, String img) {
        this.name = name;
        this.capacity = capacity;
        this.description = description;
        this.img = img;
    }

    public void addPallet(Pallet pallet) {
        pallets.add(pallet);
    }
}
