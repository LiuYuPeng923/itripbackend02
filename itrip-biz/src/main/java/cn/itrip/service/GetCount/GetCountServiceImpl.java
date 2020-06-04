package cn.itrip.service.GetCount;

import cn.itrip.dao.comment.ItripCommentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class GetCountServiceImpl implements GetCountService {
    @Resource
    private ItripCommentMapper commentMapper;

    @Override
    public Integer queryCount(Map map) throws Exception {
        return commentMapper.getItripCommentCountByMap(map);
    }
}
