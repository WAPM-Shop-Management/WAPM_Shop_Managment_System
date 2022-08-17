package spring.dto;

import lombok.*;

import java.util.Date;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {

    private int id;

    private int customerId;

    private int itemId;

    private int qty;

    private Date odPlaceAt;

    private String odStatus;

}
