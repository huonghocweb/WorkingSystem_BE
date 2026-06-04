package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.LabelMapper;
import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.entity.Label;
import com.huong.workingsystem.model.request.LabelRequest;
import com.huong.workingsystem.model.response.LabelResponse;
import com.huong.workingsystem.repo.BoardRepo;
import com.huong.workingsystem.repo.CardRepo;
import com.huong.workingsystem.repo.LabelRepo;
import com.huong.workingsystem.service.LabelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {
    private final LabelRepo labelRepo;
    private final LabelMapper labelMapper;
    private final BoardRepo boardRepo;
    private final CardRepo  cardRepo;

    @Override
    public List<LabelResponse> getLabelsByBoard(Integer boardId) {
        List<Label> labelsByBoard = labelRepo.getLabelsByBoard(boardId);
        return labelsByBoard.stream()
                .map(labelMapper :: convertEnToRes)
                .toList();
    }

    @Override
    public LabelResponse createLabel(LabelRequest labelRequest) {
        Label label = labelMapper.convertReqToEn(labelRequest);
        label.setBoard(labelRequest.getBoardId() != null ?
                        boardRepo.findById(labelRequest.getBoardId())
                                .orElseThrow(()-> new EntityNotFoundException("not found board"))
                        : null);
        return labelMapper.convertEnToRes(labelRepo.save(label));
    }

    @Override
    public LabelResponse updateLabel(Integer labelId, LabelRequest labelRequest) {
        return labelRepo.findById(labelId).map(labelExists  ->  {
            labelExists = labelMapper.updateEntityFromRequest(labelRequest , labelExists);
                if(labelRequest.getBoardId() != null) {
                    labelExists.setBoard(boardRepo.findById(labelRequest.getBoardId())
                            .orElseThrow(()-> new EntityNotFoundException("Not found board")));
                }
            return labelMapper.convertEnToRes(labelRepo.save(labelExists));
            })
                .orElseThrow(()-> new EntityNotFoundException("Not found label"));
    }

    @Transactional
    @Override
    public void deleteLabel(Integer labelId) {
        Label label = labelRepo.findById(labelId)
                .orElseThrow(()-> new EntityNotFoundException("Not found label"));
        List<Card> cards = cardRepo.getCardsLinkLabels(labelId);
        System.out.println(cards.size());
        for (Card card : cards) {
            card.getLabels().remove(label);
        }
        labelRepo.delete(label);
    }
}
