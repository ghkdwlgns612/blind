package blind.back.blind.domain.good.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class CreatePostGoodRequest implements Serializable {
    private Long post_id;
    @JsonProperty
    private boolean is_good;
}
