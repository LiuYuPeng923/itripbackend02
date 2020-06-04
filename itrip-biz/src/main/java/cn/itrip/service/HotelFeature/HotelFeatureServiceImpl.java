package cn.itrip.service.HotelFeature;

import cn.itrip.beans.pojo.ItripHotelFeature;
import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.dao.hotelfeature.ItripHotelFeatureMapper;
import cn.itrip.dao.labeldic.ItripLabelDicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HotelFeatureServiceImpl implements HotelFeatureService {
    @Resource
    private ItripLabelDicMapper labelDicMapper;

    @Override
    public List<ItripLabelDic> getHotelFeature(Map map) throws Exception {
        return labelDicMapper.getItripLabelDicListByMap(map);
    }
}
