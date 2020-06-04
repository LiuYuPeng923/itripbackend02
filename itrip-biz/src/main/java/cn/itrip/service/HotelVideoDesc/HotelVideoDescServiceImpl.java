package cn.itrip.service.HotelVideoDesc;

import cn.itrip.beans.pojo.ItripAreaDic;
import cn.itrip.beans.pojo.ItripHotel;
import cn.itrip.beans.pojo.ItripHotelFeature;
import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.beans.vo.hotel.HotelVideoDescVO;
import cn.itrip.dao.areadic.ItripAreaDicMapper;
import cn.itrip.dao.hotel.ItripHotelMapper;
import cn.itrip.dao.hotelfeature.ItripHotelFeatureMapper;
import cn.itrip.dao.labeldic.ItripLabelDicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HotelVideoDescServiceImpl implements HotelVideoDescService {
    @Resource
    private ItripHotelFeatureMapper hotelFeatureMapper;
    @Resource
    private ItripLabelDicMapper labelDicMapper;
    @Resource
    private ItripAreaDicMapper itripAreaDicMapper;
    @Resource
    private ItripHotelMapper itripHotelMapper;


    @Override
    public HotelVideoDescVO getHotelVideoDesc(Long id) throws Exception {
        HotelVideoDescVO vo = new HotelVideoDescVO();
        ItripHotel itripHotel = itripHotelMapper.getItripHotelById(id);
        vo.setHotelName(itripHotel.getHotelName());

        List<ItripAreaDic> hotelAreaLsit = itripHotelMapper.getHotelAreaByHotelId(id);
        List<String> tradingAreaNameList = new ArrayList<>();
        for (ItripAreaDic dic : hotelAreaLsit) {
            tradingAreaNameList.add(dic.getName());
        }
        vo.setTradingAreaNameList(tradingAreaNameList);

        List<ItripLabelDic> hotelFeatureByHotelId = itripHotelMapper.getHotelFeatureByHotelId(id);
        List<String> hotelFeatureList = new ArrayList<>();
        for (ItripLabelDic dic : hotelFeatureByHotelId) {
            hotelFeatureList.add(dic.getName());
        }
        vo.setHotelFeatureList(hotelFeatureList);

        return vo;
    }
}
