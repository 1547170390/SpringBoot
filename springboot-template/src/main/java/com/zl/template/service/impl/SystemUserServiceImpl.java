package com.zl.template.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.DynamicMapping;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.zl.template.service.SystemUserService;
import com.zl.template.domain.SystemUser;
import com.zl.template.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuzili
 * @description 针对表【system_user(用户信息表)】的数据库操作Service实现
 * @createDate 2024-09-15 10:12:57
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Autowired
    private SystemUserMapper systemUserMapper;

    private final String indexName = "system_user_index";

    @Override
    public String getHanData() throws IOException {
        SearchResponse<SystemUser> response = elasticsearchClient.search(s -> s
                        .index(indexName)
                        .query(q -> q
                                .match(t -> t
                                        .field("userName")
                                        .query("涵")
                                )
                        ),
                SystemUser.class);
        List<Hit<SystemUser>> hits = response.hits().hits();
        ArrayList<SystemUser> hange = new ArrayList<>();
        for (Hit<SystemUser> hit : hits) {
            SystemUser user = hit.source();
            hange.add(user);
            System.out.println(user);
        }
        return hange.toString();
    }

    //同步数据库用户到Elasticsearch
    public void systemUserToElasticsearch() {
        List<SystemUser> systemUsers = systemUserMapper.selectList(null);
        // 创建索引，只需执行一次
        try {
            if (!elasticsearchClient.indices().exists(e -> e.index(indexName)).value()) {
                elasticsearchClient.indices().create(c -> c.index(indexName)
                        .mappings(m -> m
                                .dynamic(DynamicMapping.False) //禁用动态映射
                                .properties("userName", p -> p.text(t -> t.analyzer("standard")))
                                .properties("email", p -> p.keyword(k -> k))
                                .properties("phonenumber", p -> p.keyword(k -> k))
                        ));
                System.out.println("user索引创建成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //插入用户数据
        for (SystemUser systemUser : systemUsers) {
            try {
                elasticsearchClient.index(i->i
                        .index(indexName)
                        .id(systemUser.getUserId().toString())
                        .document(systemUser));
                System.out.println("用户"+systemUser.getUserName().toString()+"已经成功索引到es");
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

    @Override
    public SystemUser  getSystemUser(Long userid) {
        return systemUserMapper.selectByUserId(userid);
    }
}




