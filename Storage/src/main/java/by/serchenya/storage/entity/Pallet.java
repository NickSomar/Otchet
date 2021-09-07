package by.serchenya.storage.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "pallet")
@NoArgsConstructor
@Data
public class Pallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String customer;
    private double weight;
    private String product;

    @ManyToOne(fetch = FetchType.LAZY)
    private Storage storage;

    public Pallet(String customer, double weight, String product, Storage storage) {
        this.customer = customer;
        this.weight = weight;
        this.product = product;
        this.storage = storage;
    }
}
