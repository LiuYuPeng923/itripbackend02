package cn.itrip.service.AddComent;

import cn.itrip.beans.pojo.ItripComment;
import cn.itrip.beans.pojo.ItripImage;
import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.beans.vo.comment.ItripScoreCommentVO;

import java.util.List;

public interface CommentService {
    int addComent(ItripComment comment, List<ItripImage> imageList) throws Exception;
    List<ItripLabelDicVO> getTravelType()throws Exception;
    ItripScoreCommentVO getHotelScore(Long id)throws Exception;

}
