package cn.itrip.service.AvgHotelScore;

import cn.itrip.beans.vo.comment.ItripScoreCommentVO;
import cn.itrip.dao.comment.ItripCommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AvgScoreServiceImpl implements AvgScoreService {
    @Resource
    private ItripCommentMapper commentMapper;

    @Override
    public ItripScoreCommentVO getCommentAvgScore(Long id) throws Exception {
        return commentMapper.getCommentAvgScore(id);
    }
}
