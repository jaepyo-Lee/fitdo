package com.jaejoo.fitdocore.user.req;

public record FriendApplyConfirmCommand(Long sendUserId, Long receiveUserId, Boolean isAccept) {
}
