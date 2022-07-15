/**
 * @author Nipun Lakshitha <nipunlakshithasilva1999@gmail.com>
 * @since 8/07/21
 */

package spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private int id;

    private String name;

    private String address;

    private String email;

    private String telNo;

    private String nic;
}
