package spring.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderDetail {
    @Id
    private String orderId;

}
