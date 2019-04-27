package com.alvicom.exercise.domain.model;

import com.alvicom.exercise.domain.model.TransactionModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@Component
public class ReportModel {

//    private String accountNumber;
    private List<Map<String, TransactionModel>> mapList;

}
