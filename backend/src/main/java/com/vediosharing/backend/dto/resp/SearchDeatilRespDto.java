package com.vediosharing.backend.dto.resp;

import com.vediosharing.backend.dao.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SearchDeatilRespDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/5 22:03
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class SearchDeatilRespDto {
    List<VideoDetailRespDto> videoDetail;
    List<User> userList;
}
