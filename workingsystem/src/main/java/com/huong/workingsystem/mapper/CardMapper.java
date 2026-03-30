package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.response.card.CardDetailResponse;
import com.huong.workingsystem.model.response.card.CardSummaryResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring" , uses = {UserMapper.class})
public interface CardMapper {
    CardSummaryResponse convertEnToResSum(Card card);

    CardDetailResponse convertEnToResDe(Card card);
}
