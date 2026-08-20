package com.huong.workingsystem.model.entity;

import com.huong.workingsystem.model.enums.BoardListTypeCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "board_list_type")
public class BoardListType {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name ="board_list_type_id")
    private Integer boardListTypeId ;

    @Enumerated(EnumType.STRING)
    @Column(name = "board_list_type_code")
    private BoardListTypeCode boardListTypeCode;

    @Column(name = "board_list_type_title")
    private String boardListTypeTitle;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "boardListType")
    private List<BoardList> boardLists;
}
