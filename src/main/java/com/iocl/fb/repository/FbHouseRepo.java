package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.dto.HouseDetailsDTO;
import com.iocl.fb.dto.HouseListDto;
import com.iocl.fb.dto.LocalityDto;
import com.iocl.fb.dto.VacantFlatdetailsDto;
import com.iocl.fb.entities.House;

public interface FbHouseRepo extends JpaRepository<House, Long> {

	@Modifying
	@Query("UPDATE House SET status= :status where houseId = :houseId")
	int updateStatus(@Param("status") int status, @Param("houseId") Long houseId);

	@Query("SELECT new com.iocl.fb.dto.HouseDetailsDTO(a.houseId, a.houseNumber, a.residTCode, e.residDesc, "
			+ "a.residSCode, f.residSName, a.areaSqft, a.buildingName, b.colName, c.localityName, d.locName, "
			+ "a.status, d.locCode) " + "FROM House a " + "INNER JOIN Colony b ON a.colCode = b.colCode "
			+ "INNER JOIN Locality c ON b.localityCode = c.localityCode "
			+ "INNER JOIN Location d ON c.locCode = d.locCode "
			+ "INNER JOIN ResidType e ON a.residTCode = e.residTcode "
			+ "INNER JOIN ResidentSize f ON a.residSCode = f.residSCode "
			+ "WHERE d.locCode IN (SELECT locCode FROM Admin WHERE empCode = :empCode) "
			+ "AND a.status IN (41, 42, 46, 47, -41) "
			+ "AND (COALESCE(:locationParam, NULL) IS NULL OR d.locCode = :locationParam) "
			+ "AND (COALESCE(:localityParam, NULL) IS NULL OR c.localityCode = :localityParam) "
			+ "AND (COALESCE(:colonyParam, NULL) IS NULL OR b.colCode = :colonyParam) "
			+ "AND (COALESCE(:statusParam, NULL) IS NULL OR a.status = :statusParam) " + "ORDER BY a.houseId")
	List<HouseDetailsDTO> findHouseDetailsByAdmin(Long empCode, @Param("locationParam") Integer locationParam,
			@Param("localityParam") Integer localityParam, @Param("colonyParam") Long colonyParam,
			@Param("statusParam") Integer statusParam);

	@Query("SELECT new com.iocl.fb.dto.LocalityDto(c.localityCode, c.localityName, COALESCE(COUNT(*), 0) AS vacant) "
			+ "FROM House a " + "INNER JOIN Colony b ON a.colCode = b.colCode "
			+ "INNER JOIN Locality c ON b.localityCode = c.localityCode "
			+ "INNER JOIN Location d ON c.locCode = d.locCode AND d.locCode = :locCode "
			+ "WHERE a.status = 41 AND a.renovatedFlag != 59 " + "GROUP BY c.localityCode, c.localityName")
	List<LocalityDto> findVacantLocalities(Integer locCode);

	@Query("SELECT new com.iocl.fb.dto.VacantFlatdetailsDto(a.houseId,a.houseNumber,c.localityName, b.address1, d.residSName) "
			+ "FROM House a " + "INNER JOIN Colony b ON a.colCode = b.colCode "
			+ "INNER JOIN Locality c ON b.localityCode = c.localityCode "
			+ "INNER JOIN ResidentSize d ON a.residSCode = d.residSCode "
			+ "WHERE a.status = 41 AND b.localityCode = :localitycode")
	List<VacantFlatdetailsDto> findVacantHousesDetails(Integer localitycode);

	@Query("SELECT new com.iocl.fb.dto.HouseListDto(a.houseId, a.houseNumber, b.colName, "
			+ "d.residSName, a.buildingName, a.areaSqft, b.address1, b.address2, b.pinCode) " + "FROM House a "
			+ "INNER JOIN Colony b ON a.colCode = b.colCode "
			+ "INNER JOIN Locality c ON b.localityCode = c.localityCode "
			+ "INNER JOIN ResidentSize d ON a.residSCode = d.residSCode "
			+ "WHERE b.localityCode = :localityCode AND a.houseId IN :houseIds")
	List<HouseListDto> findHousesByLocalityAndIds(Integer localityCode, List<Long> houseIds);

}
