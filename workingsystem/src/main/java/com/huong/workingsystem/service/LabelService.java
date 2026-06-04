package com.huong.workingsystem.service;

import com.huong.workingsystem.model.request.LabelRequest;
import com.huong.workingsystem.model.response.LabelResponse;

import java.util.List;

public interface LabelService {
    List<LabelResponse> getLabelsByBoard(Integer boardId);
    LabelResponse createLabel(LabelRequest labelRequest );
    LabelResponse updateLabel(Integer  labelId , LabelRequest labelRequest);
    void deleteLabel(Integer labelId);
}
