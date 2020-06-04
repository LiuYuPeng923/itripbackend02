package cn.itrip.service.getImage;

import cn.itrip.beans.vo.ItripImageVO;

import java.util.List;
import java.util.Map;

public interface GetImageService {
    List<ItripImageVO> getImage(Map map) throws Exception;
}
