package cn.itrip.service.GetCommentList;

import cn.itrip.beans.vo.comment.ItripListCommentVO;
import cn.itrip.common.Page;

import java.util.Map;

public interface GetCommentService {
    Page<ItripListCommentVO> getCommentList(Map map) throws Exception;
}
