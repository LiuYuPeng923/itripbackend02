package cn.itrip.service.TargetImg;

import cn.itrip.beans.vo.ItripImageVO;

import java.util.List;
import java.util.Map;

public interface RoomImgService {

    List<ItripImageVO> queryImg(Map map) throws Exception;

}
