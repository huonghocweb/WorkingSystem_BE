package com.huong.workingsystem.event.CardMove;

import java.time.LocalDateTime;

public record CardMoveEvent(
        Integer cardId ,
        Integer boardId ,
        Integer oldBoardListId,
        Integer newBoardListId ,
        LocalDateTime moveAt
) {
}
