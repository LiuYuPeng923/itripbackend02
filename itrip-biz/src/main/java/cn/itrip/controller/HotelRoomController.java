package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripLabelDic;
import cn.itrip.beans.vo.ItripImageVO;
import cn.itrip.beans.vo.ItripLabelDicVO;
import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.itrip.beans.vo.hotelroom.SearchHotelRoomVO;
import cn.itrip.common.DateUtil;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ErrorCode;
import cn.itrip.service.BedType.RoomBedTypeService;
import cn.itrip.service.HotelRoomByHotel.HotelRoomByHotelService;
import cn.itrip.service.TargetImg.RoomImgService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping(value = "/api/hotelroom")
public class HotelRoomController {
    @Resource
    private HotelRoomByHotelService hotelRoomByHotelService;

    @RequestMapping(value = "/queryhotelroombyhotel", method = RequestMethod.POST)
    @ResponseBody
    public Dto queryHotelRoomByHotel(SearchHotelRoomVO vo) {
        Map<String, Object> map = new HashMap<>();
        Date startDate = vo.getStartDate();
        Date endDate = vo.getEndDate();
        List<Date> dateList = DateUtil.getBetweenDates(startDate, endDate);
        if (EmptyUtils.isEmpty(vo.getHotelId())) {
            return DtoUtil.returnFail("酒店id不能为空", ErrorCode.BIZ_HOTELORDATE_ERROR);
        }
        if (EmptyUtils.isEmpty(startDate) && EmptyUtils.isEmpty(endDate)) {
            return DtoUtil.returnFail("酒店入住及退房时间不能为空", ErrorCode.BIZ_HOTELORDATE_ERROR);
        }
        if (startDate.after(endDate)) {
            return DtoUtil.returnFail("入住时间不能大于退房时间", ErrorCode.BIZ_HOTELORDATE_ERROR);
        }
        map.put("hotelId", vo.getHotelId());
        map.put("roomBedTypeId", vo.getRoomBedTypeId());
        map.put("sHavingBreakfast", vo.getIsHavingBreakfast());
        map.put("payType", vo.getPayType());
        map.put("isBook", vo.getIsBook());
        map.put("isCancel", vo.getIsCancel());
        map.put("isTimelyResponse", vo.getIsTimelyResponse());
        map.put("timesList", dateList);
        try {
            List<ItripHotelRoomVO> queryhotelroombyhotel = hotelRoomByHotelService.queryhotelroombyhotel(map);
            List<List<ItripHotelRoomVO>> roomList = new ArrayList<>();
            for (ItripHotelRoomVO roomVo : queryhotelroombyhotel) {
                List<ItripHotelRoomVO> tempList = new ArrayList<>();
                tempList.add(roomVo);
                roomList.add(tempList);

            }
            return DtoUtil.returnSuccess("获取成功", roomList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_SYSTEMEXCEPTION);
        }
    }

    @Resource
    private RoomImgService imgService;

    @RequestMapping(value = "getimg/{targetId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto getImg(@PathVariable String targetId) {
        if (EmptyUtils.isEmpty(targetId)) {
            return DtoUtil.returnFail("targetId不能为空", ErrorCode.BIZ_ROOMID_NOTNULL);
        }
        Map map = new HashMap();
        map.put("type", 2);
        map.put("targetId", targetId);
        try {
            List<ItripImageVO> itripImageVOS = imgService.queryImg(map);
            return DtoUtil.returnDataSuccess(itripImageVOS);

        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_GETIMG_FAIL);
        }

    }

    @Resource
    private RoomBedTypeService roomBedTypeService;

    @RequestMapping(value = "/queryhotelroombed", method = RequestMethod.GET)
    @ResponseBody
    public Dto queryHotelRoomBed() {

        List<ItripLabelDicVO> vos = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", 1);

        try {
            List<ItripLabelDic> roombetyp = roomBedTypeService.queryRoomBedType(map);
            for (ItripLabelDic labelDic : roombetyp) {
                ItripLabelDicVO labelDicVO = new ItripLabelDicVO();
                BeanUtils.copyProperties(labelDic, labelDicVO);
                vos.add(labelDicVO);
            }
            return DtoUtil.returnDataSuccess(vos);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_GETROOMBEDTYP_FAIL);
        }

    }


}
