package cn.itrip.service.AddComent;

import cn.itrip.beans.pojo.ItripComment;
import cn.itrip.beans.pojo.ItripImage;
import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.beans.vo.comment.ItripScoreCommentVO;
import cn.itrip.dao.comment.ItripCommentMapper;
import cn.itrip.dao.hotelorder.ItripHotelOrderMapper;
import cn.itrip.dao.image.ItripImageMapper;
import cn.itrip.dao.labeldic.ItripLabelDicMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ComentServiceImpl implements CommentService {
    @Resource
    private ItripCommentMapper commentMapper;
    @Resource
    private ItripImageMapper imageMapper;
    @Resource
    private ItripHotelOrderMapper orderMapper;
    @Resource
    private ItripLabelDicMapper dicMapper;

    @Override
    @Transactional
    public int addComent(ItripComment comment, List<ItripImage> imageList) throws Exception {
        int sum = comment.getFacilitiesScore() + comment.getServiceScore() + comment.getPositionScore() + comment.getHygieneScore();
        int score = (int) Math.round(sum * 1.0 / 4);
        comment.setScore(score);
        if (commentMapper.insertItripComment(comment) > 0) {
            if (imageList != null && imageList.size() > 0) {
                for (ItripImage img : imageList) {
                    imageMapper.insertItripImage(img);
                }
            }
        }

        orderMapper.updateHotelOrderStatus(comment.getOrderId(), comment.getCreatedBy());
        return 1;
    }

    @Override
    public List<ItripLabelDicVO> getTravelType() throws Exception {
        return dicMapper.getItripLabelDicByParentId(107L);
    }

    @Override
    public ItripScoreCommentVO getHotelScore(Long id) throws Exception {
        return commentMapper.getCommentAvgScore(id);
    }
}
