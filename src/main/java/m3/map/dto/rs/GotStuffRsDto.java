package m3.map.dto.rs;


import lombok.*;
import lombok.experimental.SuperBuilder;
import m3.lib.dto.rs.UserIdRsDto;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class GotStuffRsDto extends UserIdRsDto {

    private Long hummerQty;
    private Long lightningQty;
    private Long shuffleQty;
    private Long goldQty;
}
