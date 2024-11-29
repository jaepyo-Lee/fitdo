package com.jaejoo.fitdo.domain.user.service.application.req;

public record FriendApplyConfirmCommand(Long sendUserId, Long receiveUserId, Boolean isAccept) {
}
