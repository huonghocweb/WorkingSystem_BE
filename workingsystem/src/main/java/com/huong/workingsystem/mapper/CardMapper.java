package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.request.CardRequest;
import com.huong.workingsystem.model.response.card.CardDetailResponse;
import com.huong.workingsystem.model.response.card.CardSummaryResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring" , uses = {UserMapper.class , LabelMapper.class , AttachmentMapper.class , CommentMapper.class})
public interface CardMapper {
    CardSummaryResponse convertEnToResSum(Card card);
    CardDetailResponse convertEnToResDe(Card card);
    Card convertReqToEn(CardRequest cardRequest);
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    Card updateEntityFormRequest(CardRequest cardRequest , @MappingTarget Card card );
}
