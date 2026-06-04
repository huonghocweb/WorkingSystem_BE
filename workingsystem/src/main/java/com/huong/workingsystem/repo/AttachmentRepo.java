package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment , Integer> {

    @Query(value = "SELECT * FROM attachments WHERE attachment_id =:attachmentId ",nativeQuery = true)
    Optional<Attachment> findAttachmentIncludeDelete(@Param("attachmentId") Integer attachmentId);
}
