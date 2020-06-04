package cn.itrip.controller;

import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.*;
import cn.itrip.beans.vo.ItripAreaDicVO;
import cn.itrip.beans.vo.hotel.*;
import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;
import cn.itrip.beans.vo.hotelroom.SearchHotelRoomVO;
import cn.itrip.common.DateUtil;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ErrorCode;
import cn.itrip.service.HotCity.HotCityService;
import cn.itrip.service.HotelDetails.HotelDetailsService;
import cn.itrip.service.HotelFacilities.HotelFacilitiesService;
import cn.itrip.service.HotelFeature.HotelFeatureService;
import cn.itrip.service.HotelPolicy.HotelPolicyService;
import cn.itrip.service.HotelRoomByHotel.HotelRoomByHotelService;
import cn.itrip.service.HotelVideoDesc.HotelVideoDescService;
import cn.itrip.service.areadic.AreaDicService;
import cn.itrip.service.image.ImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("/api/hotel")
public class HotelController {
    @Resource
    private AreaDicService areaDicService;

    @RequestMapping(value = "/querytradarea/{cityId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto queryTradeArea(@PathVariable String cityId) {
        if (EmptyUtils.isEmpty(cityId)) {
            return DtoUtil.returnFail("cityId不能为空", ErrorCode.BIZ_CITYID_NOTNULL);
        }
        Map map = new HashMap();
        map.put("isTradingArea", 1);
        map.put("parent", cityId);

        try {
            List<ItripAreaDic> areaDicList = areaDicService.getAreaDicList(map);
            List<ItripAreaDicVO> voList = new ArrayList<>();
            for (ItripAreaDic dic : areaDicList) {
                ItripAreaDicVO vo = new ItripAreaDicVO();
                BeanUtils.copyProperties(dic, vo);
                voList.add(vo);
            }
            return DtoUtil.returnDataSuccess(voList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_UNKNOWN);
        }


    }

    @Resource
    private ImageService imageService;

    @RequestMapping(value = "/getimg/{targetId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto getImage(@PathVariable String targetId) {
        if (EmptyUtils.isEmpty(targetId)) {
            return DtoUtil.returnFail("targetId不能为空", ErrorCode.BIZ_TARGETID_NOTNULL);
        }
        Map map = new HashMap();
        map.put("type", 0);
        map.put("targetId", targetId);

        try {
            List<ItripImage> imageList = imageService.getImageList(map);
            return DtoUtil.returnDataSuccess(imageList);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_GETIMAGEFAIL);
        }

    }

    @Resource
    private HotelVideoDescService hotelVideoDesc;

    @RequestMapping(value = "/getvideodesc/{hotelId}", method = RequestMethod.GET)
    @ResponseBody
    public Dto getVideoDesc(@PathVariable String hotelId) {
        if (EmptyUtils.isEmpty(hotelId)) {
            return DtoUtil.returnFail("hotelId不能为空", ErrorCode.BIZ_HOTLEID_NOTNULL);
        }


        try {
            HotelVideoDescVO hotelVideoDesc = this.hotelVideoDesc.getHotelVideoDesc(Long.parseLong(hotelId));
            return DtoUtil.returnDataSuccess(hotelVideoDesc);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_GETFEATUREFAIL);
        }

    }

    @Resource
    private HotelFacilitiesService hotelFacilitiesService;

    @RequestMapping(value = "/queryhotelfacilities/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Dto queryHotelFacilities(@PathVariable Long id) {
        if (EmptyUtils.isEmpty(id)) {
            return DtoUtil.returnFail("id不能为空", ErrorCode.BIZ_ID_NOTNULL);
        }

        try {
            ItripSearchFacilitiesHotelVO itripSearchFacilitiesHotelVO = hotelFacilitiesService.queryHotelFacilites(id);
            return DtoUtil.returnDataSuccess(itripSearchFacilitiesHotelVO.getFacilities());
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_GETFACILITIESFAIL);
        }


    }

    @Resource
    private HotelFeatureService hotelFeatureService;

    @RequestMapping(value = "/queryhotelfeature", method = RequestMethod.GET)
    @ResponseBody
    public Dto queryHotelFeature() {
        List<ItripSearchDetailsHotelVO> vos = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", 16);
        try {
            List<ItripLabelDic> hotelFeature = hotelFeatureService.getHotelFeature(map);
            for (ItripLabelDic labelList : hotelFeature) {
                ItripSearchDetailsHotelVO vo = new ItripSearchDetailsHotelVO();
                BeanUtils.copyProperties(labelList, vo);
                vos.add(vo);
            }
            return DtoUtil.returnDataSuccess(vos);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_GETLABELfAIL);
        }


    }

    @Resource
    private HotCityService hotCityService;

    @RequestMapping(value = "/queryhotcity/{type}", method = RequestMethod.GET)
    @ResponseBody
    public Dto queryHotCity(@PathVariable Integer type) {
        if (EmptyUtils.isEmpty(type)) {
            return DtoUtil.returnFail("type不能为空", ErrorCode.BIZ_TYPE_NOTNULL);
        }
        Map map = new HashMap();
        map.put("isChina", type);
        map.put("isHot", 1);
        try {
            List<ItripAreaDic> hotelListByMap = hotCityService.getHotelListByMap(map);
            List<SearchHotCityVO> vos = new ArrayList<>();
            for (ItripAreaDic dic : hotelListByMap) {
                SearchHotCityVO vo = new SearchHotCityVO();
                BeanUtils.copyProperties(dic, vo);
                vos.add(vo);
            }
            return DtoUtil.returnDataSuccess(vos);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_GETTYPEfAIL);
        }

    }

    @Resource
    private HotelDetailsService detailsService;

    @RequestMapping(value = "/queryhoteldetails/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Dto queryHotelDetail(@PathVariable Long id) {
        if (EmptyUtils.isEmpty(id)) {
            return DtoUtil.returnFail("id不能为空", ErrorCode.BIZ_HOTELID_NOTNULL);
        }

        try {
            List<ItripLabelDic> hotleList = detailsService.getHotleDetailByHotelId(id);
            List<ItripSearchDetailsHotelVO> list = new ArrayList<>();
            for (ItripLabelDic dic : hotleList) {
                ItripSearchDetailsHotelVO vo = new ItripSearchDetailsHotelVO();
                BeanUtils.copyProperties(dic, vo);
                list.add(vo);
            }
            return DtoUtil.returnDataSuccess(list);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_GETDETAILSFAIL);
        }

    }

    @Resource
    private HotelPolicyService policyService;

    @RequestMapping(value = "/queryhotelpolicy/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Dto queryHotelPolicy(@PathVariable Long id) {
        if (EmptyUtils.isEmpty(id)) {
            return DtoUtil.returnFail("酒店id不能为空", ErrorCode.BIZ_GETHOTELID_NOTNULL);
        }
        try {
            ItripSearchPolicyHotelVO itripSearchPolicyHotelVO = policyService.queryHotelPolicy(id);
            return DtoUtil.returnDataSuccess(itripSearchPolicyHotelVO);
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail(e.getMessage(), ErrorCode.BIZ_ASYSYTEMERROR);
        }

    }


}
