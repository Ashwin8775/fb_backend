package com.iocl.fb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.dto.RequestDetailsDto;
import com.iocl.fb.entities.FbRequestDetails;

public interface FbReqDetRepo extends JpaRepository<FbRequestDetails, Long> {

	@Modifying
	@Query("UPDATE FbRequestDetails SET itemStatus= :status where requestId = :requestId")
	int updateApprovalDets(@Param("status") int status, @Param("requestId") Long requestId);

	Optional<FbRequestDetails> findItemStatusByRequestIdAndLocalityCode(Long requestId, int locCode);

	@Modifying
	@Query("UPDATE FbRequestDetails SET itemStatus= :status,dateUpdated=current_date(),remarks= :remarks,updatedBy= :updatedBy where requestId = :requestId and localityCode= :localityCode")
	int updateDets(@Param("status") int status, @Param("remarks") String remarks, @Param("updatedBy") Long updatedBy,
			@Param("requestId") Long requestId, @Param("localityCode") int localityCode);

	@Modifying
	@Query("UPDATE FbRequestDetails SET itemStatus= :status where requestId = :requestId and localityCode= :localityCode")
	int updateFinalDets(@Param("status") int status, @Param("requestId") Long requestId,
			@Param("localityCode") int localityCode);

	@Modifying
	@Query("UPDATE FbRequestDetails SET itemStatus= :status,prefOrder=:prefOrder where requestId = :requestId and localityCode= :localityCode")
	int updatePrefDets(@Param("status") int status, @Param("prefOrder") String prefOrder,
			@Param("requestId") Long requestId, @Param("localityCode") int localityCode);

	@Modifying
	@Query("UPDATE FbRequestDetails SET itemStatus= :status,prefOrder=:prefOrder,dateUpdated=current_date() where requestId IN :ids and localityCode= :localityCode")
	int updatePrefDetsForIds(int status, String prefOrder, List<Long> ids, int localityCode);

	List<FbRequestDetails> findRequestIdsByLocalityCodeAndItemStatus(int localityCode, int status);

	@Modifying
	@Query("UPDATE FbRequestDetails SET itemStatus= :status,dateUpdated=current_date() where requestId IN :id and localityCode= :localityCode")
	int updateStatus(int status, Long id, int localityCode);

	@Query("SELECT new com.iocl.fb.dto.RequestDetailsDto(A.requestId, A.appCat, A.locCode, A.localityCode, "
			+ "A.itemStatus, A.prefOrder, B.rnk) " + "FROM FbRequestDetails A "
			+ "INNER JOIN DynamicWaitListView B ON A.requestId = B.requestId "
			+ "WHERE A.locCode = :loccode AND A.localityCode = :localityCode AND A.itemStatus = :newStatus AND A.appCat=:appCat order by B.rnk asc")
	List<RequestDetailsDto> findRequestDetailsByLocalityAndStatus(int loccode, int localityCode,
			int newStatus, int appCat);

}
