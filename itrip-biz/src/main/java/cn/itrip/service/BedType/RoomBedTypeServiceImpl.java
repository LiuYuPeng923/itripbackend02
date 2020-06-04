package cn.itrip.service.BedType;

import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.dao.labeldic.ItripLabelDicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RoomBedTypeServiceImpl implements RoomBedTypeService {
    @Resource
    private ItripLabelDicMapper itripLabelDicMapper;

    @Override
    public List<ItripLabelDic> queryRoomBedType(Map map) throws Exception {
        return itripLabelDicMapper.getItripLabelDicListByMap(map);
    }
}
