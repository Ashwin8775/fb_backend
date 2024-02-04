package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.dto.ApprovalReponseDto;
import com.iocl.fb.dto.ApprovaldetailsDto;
import com.iocl.fb.entities.FbRequestHeader;

public interface FbReqHdrRepository extends JpaRepository<FbRequestHeader, Long> {

	FbRequestHeader findByEmpCodeAndAppCatAndStatus(Long empcode, int appCat, int status);

	@Query("SELECT new com.iocl.fb.dto.ApprovalReponseDto(a.requestId, a.empName, TO_CHAR(a.requestDate, 'yyyy-MM-dd'), "
			+ "a.status,d.catDescription, c.localityName, a.overridingFlag) " + "FROM FbRequestHeader a "
			+ "INNER JOIN FbRequestDetails b ON a.requestId = b.requestId " + "AND b.itemStatus NOT IN :itemStatusList "
			+ "AND b.locCode IN (SELECT locCode FROM Admin WHERE empCode = :empCode AND status = 'A' AND role = 'A') "
			+ "INNER JOIN Locality c ON b.localityCode = c.localityCode "
			+ "INNER JOIN GlobalVariables d ON a.status = d.catValue "
			+ "WHERE a.requestDate BETWEEN TO_DATE(:fromDate, 'DD-MON-YYYY') AND TO_DATE(:toDate, 'DD-MON-YYYY') "
			+ "AND a.status = :status and d.category='FLAT_REQ_STATUS'" + "ORDER BY a.requestId")
	List<ApprovalReponseDto> pendingApprovalList(@Param("empCode") Long empCode, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("itemStatusList") List<Integer> itemStatusList,
			@Param("status") Integer status);

	@Query("SELECT new com.iocl.fb.dto.ApprovalReponseDto(a.requestId,a.empName, TO_CHAR(a.requestDate, 'yyyy-MM-dd'), a.status, d.catDescription, c.localityName) "
			+ "FROM FbRequestHeader a " + "INNER JOIN FbRequestDetails b ON a.requestId = b.requestId "
			+ "AND (COALESCE(:useItemStatusList, false) = true AND b.itemStatus IN :itemStatusList OR COALESCE(:useItemStatusList, false) = false AND b.itemStatus NOT IN :itemStatusList) "
			+ "INNER JOIN Locality c ON b.localityCode = c.localityCode "
			+ "INNER JOIN GlobalVariables d ON a.status = d.catValue "
			+ "WHERE a.requestDate BETWEEN TO_DATE(:fromDate, 'DD-MON-YYYY') AND TO_DATE(:toDate, 'DD-MON-YYYY') "
			+ "AND a.empCode = :empCode " + "AND d.category = 'FLAT_REQ_STATUS' "
			+ "AND (COALESCE(:useStatusList, false) = true AND a.status IN :statusList OR COALESCE(:useStatusList, false) = false AND a.status NOT IN :statusList)")
	List<ApprovalReponseDto> processAndCancelList(@Param("empCode") Long empCode, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("itemStatusList") List<Integer> itemStatusList,
			@Param("statusList") List<Integer> statusList, @Param("useItemStatusList") boolean useItemStatusList,
			@Param("useStatusList") boolean useStatusList);

	@Query("SELECT new com.iocl.fb.dto.ApprovalReponseDto(a.requestId,a.empName, TO_CHAR(a.requestDate, 'yyyy-MM-dd'), a.status, d.catDescription, c.localityName) "
			+ "FROM FbRequestHeader a " + "INNER JOIN FbRequestDetails b ON a.requestId = b.requestId "
			+ "AND b.itemStatus NOT IN :itemStatusList " + "INNER JOIN Locality c ON b.localityCode = c.localityCode "
			+ "INNER JOIN GlobalVariables d ON a.status = d.catValue "
			+ "WHERE a.requestDate BETWEEN TO_DATE(:fromDate, 'DD-MON-YYYY') AND TO_DATE(:toDate, 'DD-MON-YYYY') "
			+ "AND a.empCode = :empCode " + "AND a.status = :status")
	List<ApprovalReponseDto> appRejList(@Param("empCode") Long empCode, @Param("fromDate") String fromDate,
			@Param("toDate") String toDate, @Param("status") Integer status,
			@Param("itemStatusList") List<Integer> itemStatusList);

	@Query("SELECT new com.iocl.fb.dto.ApprovaldetailsDto(a.requestId, a.empName,"
			+ "CASE a.appCat WHEN 31 THEN 'Category 1' WHEN 32 THEN 'Category 2' ELSE NULL END,"
			+ " a.division, a.currIocLocCode, d.catName as grade, a.desig,b.localityCode, c.localityName, a.contact1, \r\n"
			+ "  CASE a.familyRetn WHEN 17 THEN 'Family Retained' WHEN -17 THEN 'Family not Retained' ELSE NULL END, \r\n"
			+ "  CASE a.ownAcco WHEN 22 THEN 'Have Accommodation' WHEN -22 THEN 'Don''t have Accommodation' ELSE NULL END, a.remarks,a.adminRemarks,b.prefOrder,b.itemStatus) \r\n"
			+ "FROM FbRequestHeader a \r\n" + "INNER JOIN FbRequestDetails b ON a.requestId = b.requestId \r\n"
			+ "INNER JOIN Locality c ON b.localityCode = c.localityCode \r\n"
			+ "INNER JOIN GlobalVariables d ON a.grade = d.catValue " + "WHERE a.requestId = :reqId")
	ApprovaldetailsDto approvalDets(@Param("reqId") Long reqId);

	@Modifying
	@Query("UPDATE FbRequestHeader SET status= :status,dateApproved = current_date(),adminRemarks = :adminRemarks where requestId = :requestId")
	int updateApprovalDets(@Param("status") int status, @Param("adminRemarks") String adminRemarks,
			@Param("requestId") Long requestId);

	@Modifying
	@Query("UPDATE FbRequestHeader SET status= :status where requestId = :requestId")
	int updateDets(@Param("status") int status, @Param("requestId") Long requestId);

	@Modifying
	@Query("UPDATE FbRequestHeader e SET e.status = :newStatus WHERE e.requestId IN :ids")
	void updateStatusForIds(int newStatus, List<Long> ids);

}
