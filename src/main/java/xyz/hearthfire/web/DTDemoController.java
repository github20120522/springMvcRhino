package xyz.hearthfire.web;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.hearthfire.dao.CommonDao;
import xyz.hearthfire.dao.PagerData;

import java.util.Map;

/**
 * Created by fz on 2016/1/16.
 */
@Controller
@RequestMapping("/dt")
public class DTDemoController {

    @Autowired
    private CommonDao commonDao;

    @RequestMapping("/dtPage")
    public String dtPage() {

        return "jsp/dtDemo/dtCommonList";
    }

    @RequestMapping("/dtListJson")
    @ResponseBody
    public Map<String, Object> dtListJson(@RequestParam(value = "draw", required = false, defaultValue = "0") int draw,
                                          @RequestParam(value = "start", required = false, defaultValue = "0") int start,
                                          @RequestParam(value = "length", required = false, defaultValue = "20") int length,
                                          @RequestParam("name") String name) {

        Map<String, Object> result = Maps.newHashMap();
        String countSql = "select count(*) from t_user where 1=1 ";
        String sql = "select id, user_name as name, real_name as realName, is_activity as isActivity ";
        sql += "from t_user where 1=1 ";
        Map<String, Object> params = Maps.newHashMap();
        if (StringUtils.isNotBlank(name)) {
            countSql += "and user_name = :name ";
            sql += "and user_name = :name ";
            params.put("name", name);
        }
        sql += "limit " + start + ", " + length;
        PagerData<Map> pagerData = commonDao.queryPagerDataBySql(countSql, sql, params, Map.class);
        result.put("draw", draw);
        result.put("recordsTotal", pagerData.getTotalCount());
        result.put("recordsFiltered", pagerData.getTotalCount());
        result.put("data", pagerData.getData());
        return result;
    }
}
