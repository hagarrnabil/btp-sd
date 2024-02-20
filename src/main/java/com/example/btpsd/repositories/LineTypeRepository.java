package com.example.btpsd.repositories;

import com.example.btpsd.model.LineType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LineTypeRepository extends CrudRepository<LineType, Long> {

    @Query("SELECT l FROM LineType l WHERE CONCAT(l.code, ' ', l.lineTypeCode, ' ', l.atpQuantity,' ',l.blanketLine,' ',l.contingencyLine,' ',l.informatoryLine" +
            ",' ',l.internalLine,' ',l.standardLine) LIKE %?1%")
    public List<LineType> search(String keyword);

}
