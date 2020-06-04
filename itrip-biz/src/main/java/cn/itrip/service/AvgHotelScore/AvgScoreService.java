package cn.itrip.service.AvgHotelScore;

import cn.itrip.beans.vo.comment.ItripScoreCommentVO;

public interface AvgScoreService {
    ItripScoreCommentVO getCommentAvgScore(Long id) throws Exception;
}
