package com.mrhenry.api;

import com.mrhenry.api.output.building.TotalItem;
import com.mrhenry.builder.BuildingSearchBuilder;
import com.mrhenry.dto.BuildingDTO;
import com.mrhenry.service.IBuildingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class BuildingAPI {
	
	@Autowired
	private IBuildingService buildingService;
	
	@PostMapping(value = {"/api/building"})
	public BuildingDTO save(@RequestBody BuildingDTO model) {
		return buildingService.save(model);
	}

	@PutMapping(value = {"/api/building/{id}"})
	public void save(@PathVariable("id") Long id,@RequestBody BuildingDTO model) {
		buildingService.save(model, id);
	}

	@DeleteMapping(value = {"/api/building"})
	public void delete(@RequestBody Long[] ids) {
		buildingService.delete(ids);
	}

	@GetMapping(value = {"/api/building"})
	public List<BuildingDTO> findAll(@RequestParam Map<String, Object> buildingQuery) {

		BuildingSearchBuilder builder = initBuilder(buildingQuery);
		Pageable pageable = PageRequest.of((Integer.parseInt(buildingQuery.get("page").toString()) -1), Integer.parseInt(buildingQuery.get("maxPageItem").toString()));

		return buildingService.findAll(builder, pageable);
	}


	@GetMapping(value = {"/api/building/count"})
	public TotalItem countAll(@RequestParam Map<String, Object> buildingQuery) {
		BuildingSearchBuilder builder = initBuilder(buildingQuery);
		return  new TotalItem(buildingService.countAll(builder));
	}

	@GetMapping(value = {"/api/building/{id}"})
	public BuildingDTO findById(@PathVariable("id") Long id) {

		return buildingService.findById(id);
	}

	private BuildingSearchBuilder initBuilder(Map<String, Object> buildingQuery) {
		String[] buildingTypes = new String[]{};
		if(StringUtils.isNotBlank((String) buildingQuery.get("buildingTypes"))) {
			buildingTypes = ((String) buildingQuery.get("buildingTypes")).split(",");
		}

		BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder()
				.setName((String) buildingQuery.get("name")).setWard((String) buildingQuery.get("ward"))
				.setStreet((String) buildingQuery.get("street")).setAreaRentFrom((String) buildingQuery.get("areaRentFrom"))
				.setAreaRentTo((String) buildingQuery.get("areaRentTo")).setCostRentFrom((String) buildingQuery.get("costRentFrom"))
				.setCostRentTo((String) buildingQuery.get("costRentTo")).setNumberOfBasement((String) buildingQuery.get("numberOfBasement"))
				.setBuildingTypes(buildingTypes).setDistrict((String) buildingQuery.get("district"))
				.setBuildingArea((String) buildingQuery.get("buildingArea")).setDirection((String) buildingQuery.get("direction"))
				.build();
		return builder;
	}
}
