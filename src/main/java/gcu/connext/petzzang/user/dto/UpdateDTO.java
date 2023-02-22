package gcu.connext.petzzang.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDTO {

    public String uploadImg;
    public String chgName;
}