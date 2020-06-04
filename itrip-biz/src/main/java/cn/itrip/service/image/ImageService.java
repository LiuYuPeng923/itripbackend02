package cn.itrip.service.image;

import cn.itrip.beans.pojo.ItripImage;

import java.util.List;
import java.util.Map;

public interface ImageService {

    List<ItripImage> getImageList(Map map) throws Exception;
}
