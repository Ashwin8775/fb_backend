package com.iocl.fb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.entities.ItemManagement;

public interface ItemMgmRepo extends JpaRepository<ItemManagement, Long>{

}
