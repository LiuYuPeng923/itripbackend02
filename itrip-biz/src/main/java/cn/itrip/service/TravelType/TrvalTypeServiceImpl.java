package cn.itrip.service.TravelType;

import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.dao.labeldic.ItripLabelDicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TrvalTypeServiceImpl implements TravelTypeService {
    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;

    @Override
    public List<ItripLabelDic> travelType(Map map) throws Exception {
        return itripLabelDicMapper.getItripLabelDicListByMap(map);
    }
}
