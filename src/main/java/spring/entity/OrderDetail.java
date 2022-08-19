package spring.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Setter
@Getter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private int qty;

}
