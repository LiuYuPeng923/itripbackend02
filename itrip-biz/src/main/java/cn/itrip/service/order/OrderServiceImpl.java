package cn.itrip.service.order;

import cn.itrip.beans.pojo.ItripHotelTempStore;
import cn.itrip.beans.vo.store.StoreVO;
import cn.itrip.common.DateUtil;
import cn.itrip.dao.hoteltempstore.ItripHotelTempStoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Resource
    private ItripHotelTempStoreMapper tempStoreMapper;

    @Override
    public boolean validationRoomStore(Map map) throws Exception {
        List<Date> dateList = DateUtil.getBetweenDates((Date) map.get("startTime"), (Date) map.get("endTime"));
        for (Date date : dateList) {
            map.put("time", date);
            ItripHotelTempStore tempStore = tempStoreMapper.queryByTime(map);
            if (tempStore == null) {
                ItripHotelTempStore store = new ItripHotelTempStore();
                store.setHotelId((Long) map.get("hotelId"));
                store.setRoomId((Long) map.get("roomId"));
                store.setStore(20);
                store.setRecordDate(date);
                store.setCreationDate(new Date());
                tempStoreMapper.insertItripHotelTempStore(store);
            }

            List<StoreVO> list = tempStoreMapper.queryRoomStore(map);
            for (StoreVO vo : list) {
                if (vo.getStore() < (Integer) map.get("count")) {
                    return false;
                }
            }
        }
        return true;
    }
}
