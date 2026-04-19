package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.VisibilityMapper;
import com.huong.workingsystem.model.entity.Visibility;
import com.huong.workingsystem.model.response.VisibilityResponse;
import com.huong.workingsystem.repo.VisibilityRepo;
import com.huong.workingsystem.service.VisibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisibilityServiceImpl implements VisibilityService {
    private final VisibilityMapper visibilityMapper;
    private final VisibilityRepo visibilityRepo;
    @Override
    public List<VisibilityResponse> getAllVisibility() {
        List<Visibility>  visibilities = visibilityRepo.findAll()   ;
        return visibilities.stream().map(visibilityMapper ::  convertEnToRes)
                .collect(Collectors.toList());
    }
}
