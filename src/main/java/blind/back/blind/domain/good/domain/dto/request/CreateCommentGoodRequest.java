package blind.back.blind.domain.good.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class CreateCommentGoodRequest implements Serializable {
    private Long comment_id;
    @JsonProperty
    private boolean is_good;
}
