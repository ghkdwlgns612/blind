package blind.back.blind.domain.post.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class PagePostRequest implements Serializable{
    private Integer page;
    private Integer size;

    @Builder
    public PagePostRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
}
