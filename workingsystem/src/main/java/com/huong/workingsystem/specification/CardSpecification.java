package com.huong.workingsystem.specification;

import com.huong.workingsystem.model.entity.Card;
import org.springframework.data.jpa.domain.Specification;

public class CardSpecification {

    public static Specification<Card> notDelete() {
        return (root, query , cb) ->
             cb.isNull(root.get("deleteAt"));
    }

    public static Specification<Card> boardId(Integer boardId){
        return (root , query , cb) ->
                cb.equal(
                        root.get("boardList")
                                .get("board")
                                .get("id"),boardId
                );
    }

}
