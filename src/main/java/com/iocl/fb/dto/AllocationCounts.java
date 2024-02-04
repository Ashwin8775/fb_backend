package com.iocl.fb.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationCounts {

	private int nextcategory;

	Map<Integer, Integer> allocDetails;

}
