package com.puvar.cloudservice.controller;

import com.google.common.collect.Lists;
import com.puvar.cloudcommon.common.constants.PlainResponse;
import com.puvar.cloudservice.domain.ManageLog;
import com.puvar.cloudservice.domain.ManageLogEs;
import com.puvar.cloudservice.domain.Slump;
import com.puvar.cloudservice.es.ManageLogEsDao;
import com.puvar.cloudservice.es.SlumpEsDao;
import com.puvar.cloudservice.service.test.ManageLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/***
 * Es controller
 * @Auther: dingyuanmeng
 * @Date: 2019/8/26
 * @version : 1.0
 */
@Api(value = "EsController restful api")
@RestController
@RequestMapping("/es")
public class EsController {
    private static final Logger log = LoggerFactory.getLogger(EsController.class);

    @Autowired
    private SlumpEsDao slumpEsDao;
    @Autowired
    private ManageLogEsDao manageLogEsDao;
    @Autowired
    private ManageLogService manageLogService;

    /**
     * 增强ElasticsearchRepository的功能操作
     */
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 保存到es
     *
     * @return
     */
    @ApiOperation(value = "保存水果数据到es", notes = "save es")
    @RequestMapping(value = "save", method = RequestMethod.GET)
    public PlainResponse saveEs() {
        List<Slump> list = Lists.newArrayList();
        list.add(new Slump().setSlumpId(1).setSlumpName("苹果").setSlumpColor("红色").setSlumpFlavor("甜"));
        list.add(new Slump().setSlumpId(2).setSlumpName("橘子").setSlumpColor("黄色").setSlumpFlavor("酸甜"));
        list.add(new Slump().setSlumpId(3).setSlumpName("葡萄").setSlumpColor("紫色").setSlumpFlavor("甜"));
        list.add(new Slump().setSlumpId(4).setSlumpName("猕猴桃").setSlumpColor("棕色").setSlumpFlavor("甜"));
        List<Slump> save = (List<Slump>) slumpEsDao.saveAll(list);
        log.info("saveEs save = {}", save);
        return PlainResponse.successResponse();
    }

    /**
     * 查询es中所有数据
     *
     * @return
     */
    @ApiOperation(value = "查询es中所有的数据", notes = "select all es")
    @RequestMapping(value = "selectEsAll", method = RequestMethod.GET)
    public PlainResponse selectEsAll() {
        Iterable<Slump> all = slumpEsDao.findAll();
        log.info("selectEsAll slumps = {}", all);
        // 这个地方会抛异常。待解决
        return PlainResponse.successDataResponse(all);
    }

    /**
     * 根据条件查询es数据
     *
     * @return
     */
    @ApiOperation(value = "根据参数查询es数据", notes = "select by param")
    @RequestMapping(value = "selectByParam", method = RequestMethod.GET)
    public PlainResponse selectByParam() {
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.must(QueryBuilders.matchQuery("slumpName", "苹果"));
        Iterable<Slump> search = slumpEsDao.search(query);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.queryStringQuery("甜")).build();
        Page<Slump> search1 = slumpEsDao.search(searchQuery);
        return PlainResponse.successDataResponse(search1);
    }

    /**
     * 根据条件查询
     *
     * @return
     */
    @ApiOperation(value = "根据条件查询es数据", notes = "select condition")
    @RequestMapping(value = "selectByCondition", method = RequestMethod.GET)
    public PlainResponse selectByCondition() {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.queryStringQuery("*甜")).build();
        Page<Slump> search = slumpEsDao.search(searchQuery);
        // 词条查询
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("slumpFlavor", "酸");
        Iterable<Slump> search1 = slumpEsDao.search(matchQueryBuilder);
        return PlainResponse.successDataResponse(search1);
    }

    /**
     * mysql中的日志保存到es中
     *
     * @return
     */
    @ApiOperation(value = "保存日志数据到es", notes = "save manage_log todo es")
    @RequestMapping(value = "saveManageLog", method = RequestMethod.GET)
    public PlainResponse saveManageLog() {
        List<ManageLog> all = manageLogService.findAll();
        for (ManageLog manageLog : all) {
            System.out.println(manageLog);
        }
        List<ManageLogEs> logs = ((List<ManageLog>) all).stream().map(p -> {
            return new ManageLogEs(p.getId(), p.getLogId(), p.getSystem(),
                    p.getRequestUrl(), p.getRequestMethod(), p.getOperateDesc(),
                    p.getParamValue(), p.getResultValue(), p.getExceptionValue(), p.getTimeLength());
        }).collect(Collectors.toList());

        manageLogEsDao.saveAll(logs);
        log.info("saveEs save = {}", all);
        return PlainResponse.successResponse();
    }

    @ApiOperation(value = "保存日志数据到es", notes = "save manage_log todo es")
    @GetMapping("saveOne")
    public PlainResponse save() {
        ManageLog manageLog = new ManageLog();
        manageLog.setParamValue("1");
        manageLog.setExceptionValue("1");
        manageLog.setLogId("1");
        manageLog.setOperateDesc("1");
        manageLog.setRequestMethod("1");
        manageLog.setResultValue("1");
        manageLog.setSystem("1");
        manageLog.setRequestUrl("1");
        manageLog.setTimeLength(1);
        manageLogService.save(manageLog);
        return PlainResponse.successResponse();
    }

    /**
     * 查询es中所有日志数据
     *
     * @return
     */
    @ApiOperation(value = "查询es中所有的数据", notes = "select all es")
    @RequestMapping(value = "selectEsManageLog", method = RequestMethod.GET)
    public PlainResponse selectEsManageLog() {
        Iterable<ManageLogEs> all = manageLogEsDao.findAll();
        log.info("selectEsAll slumps = {}", all);
        // 这个地方会抛异常。待解决
        return PlainResponse.successDataResponse(Lists.newArrayList(all));
    }

}
