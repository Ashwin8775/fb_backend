package com.iocl.fb.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.iocl.fb.dto.OccupancyDetailsDTO;
import com.iocl.fb.entities.FlatOccupDet;
import com.iocl.fb.entities.FlatOccupDetId;

public interface FlatOccupRepo extends JpaRepository<FlatOccupDet, FlatOccupDetId> {

	@Query("SELECT new com.iocl.fb.dto.OccupancyDetailsDTO(c.empCode, c.empName, 'Grade ' || SUBSTRING(e.catName, 2, 1) as currGrade, c.desig, "
			+ "TO_CHAR(c.requestDate, 'yyyy-MM-dd'),CASE a.appCat WHEN 31 THEN 'Category 1' WHEN 32 THEN 'Category 2' ELSE NULL END, TO_CHAR(a.allotmentDate, 'yyyy-MM-dd'), "
			+ "TO_CHAR(a.occupationDate, 'yyyy-MM-dd'), a.requestId, a.houseId, b.houseNumber, b.buildingName, "
			+ "d.localityCode,f.locCode, c.emailId) " + "FROM FlatOccupDet a " + "INNER JOIN House b ON a.houseId = b.houseId "
			+ "INNER JOIN FbRequestHeader c ON a.requestId = c.requestId AND c.status = :status "
			+ "INNER JOIN FbRequestDetails d ON c.requestId = d.requestId AND d.itemStatus = :status "
			+ "INNER JOIN Locality f ON d.localityCode = f.localityCode "
			+ "INNER JOIN GlobalVariables e ON c.grade = e.catValue "
			+ "WHERE e.category='GRADE' and (:paramType = 'empCode' AND a.empCode = :dynamicParam) OR "
			+ "      (:paramType = 'requestId' AND a.requestId = :dynamicParam)")
	List<OccupancyDetailsDTO> findOccupancyDetails(String paramType, Long dynamicParam, Integer status);

	@Modifying
	@Query("UPDATE FlatOccupDet SET vacatedOn=TO_CHAR(:vacatedOn, 'YYYYMMDD'),updatedBy= :updatedBy,updatedOn=TO_DATE(current_date(), 'DD-MON-YYYY'),remarks= concat(remarks, '#$#',:remarks) where requestId = :requestId and houseId= :houseId")
	int updateFlatOccupDets(Date vacatedOn, String updatedBy, String remarks, Long requestId,Long houseId);

}
