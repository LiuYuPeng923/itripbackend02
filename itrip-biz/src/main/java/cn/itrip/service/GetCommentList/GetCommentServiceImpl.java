package cn.itrip.service.GetCommentList;

import cn.itrip.beans.vo.comment.ItripListCommentVO;
import cn.itrip.common.Page;
import cn.itrip.dao.comment.ItripCommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class GetCommentServiceImpl implements GetCommentService {
    @Resource
    private ItripCommentMapper commentMapper;

    @Override
    public Page<ItripListCommentVO> getCommentList(Map map) throws Exception {
        List list = commentMapper.getItripCommentListByMap(map);
        Integer total = commentMapper.getItripCommentCountByMap(map);

        Integer pageNo = (Integer) map.get("pageNo");
        Integer pageSize = (Integer) map.get("pageSize");
        Page page = new Page(pageNo, pageSize, total);
        return page;
    }
}
