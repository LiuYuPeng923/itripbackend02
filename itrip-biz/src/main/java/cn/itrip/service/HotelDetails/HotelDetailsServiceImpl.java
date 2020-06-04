package cn.itrip.service.HotelDetails;

import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.dao.hotel.ItripHotelMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HotelDetailsServiceImpl implements HotelDetailsService {
    @Resource
    private ItripHotelMapper itripHotelMapper;

    @Override
    public List<ItripLabelDic> getHotleDetailByHotelId(Long id) throws Exception {
        return itripHotelMapper.getHotelFeatureByHotelId(id);
    }
}
