package com.jaejoo.fitdoweb.common.mapper;

import com.jaejoo.fitdocore.user.req.FriendSimpleInfo;
import com.jaejoo.fitdoweb.user.web.res.FriendSimpleInfosResponse;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReadFriendsMapper {
    ReadFriendsMapper INSTANCE = Mappers.getMapper(ReadFriendsMapper.class);

    @Named("toFriendSimpleInfosResponse")
    FriendSimpleInfosResponse toFriendSimpleInfosResponse(FriendSimpleInfo friendSimpleInfo);

    @IterableMapping(qualifiedByName = "toFriendSimpleInfosResponse")
    List<FriendSimpleInfosResponse> toFriendSimpleInfosResponse(List<FriendSimpleInfo> friendSimpleInfos);
}
