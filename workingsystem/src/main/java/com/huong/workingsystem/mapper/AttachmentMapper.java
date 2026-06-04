package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Attachment;
import com.huong.workingsystem.model.request.AttachmentRequest;
import com.huong.workingsystem.model.response.AttachmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring"  ,  uses = {FileMapper.class})
public interface AttachmentMapper {
    @Mapping(source = "." , target = "fileUrl" , qualifiedByName = "toFullFileUrl")
    AttachmentResponse convertEnToRes(Attachment attachment);
    Attachment convertReqToEn(AttachmentRequest attachmentRequest);
}
