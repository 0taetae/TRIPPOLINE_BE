package com.ssafy.trip.spot.controller;

import com.ssafy.trip.spot.dto.request.SearchSpotInBoundRequest;
import com.ssafy.trip.spot.dto.request.SpotSearchRequestDto;
import com.ssafy.trip.spot.dto.response.SearchSpotInBoundResponse;

import java.util.List;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.spot.dto.SidoDto;
import com.ssafy.trip.spot.dto.SigunguDto;
import com.ssafy.trip.spot.dto.SpotDto;
import com.ssafy.trip.spot.dto.SpotTypeDto;
import com.ssafy.trip.spot.service.SpotService;

@Slf4j
@RestController
@RequestMapping("/api/spot")
@PreAuthorize("hasRole('ROLE_USER')")
public class SpotController {
	private final SpotService spotService;

	public SpotController(SpotService spotService) {
		this.spotService = spotService;
	}

	@GetMapping("/sidos")
	public ResponseEntity<?> selectAllSidos(){
		List<SidoDto> sidoList = spotService.selectAllSidos();
		if(sidoList==null || sidoList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sidoList, HttpStatus.OK);
	}

	@GetMapping("/sigungus/{sidoCode}")
	public ResponseEntity<?> selectBySido(@PathVariable int sidoCode){
		List<SigunguDto> sigunguList = spotService.selectBySido(sidoCode);
		if(sigunguList==null || sigunguList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sigunguList, HttpStatus.OK);
	}

	@GetMapping("/sigungu/{sigunguCode}/attraction")
	public ResponseEntity<?> selectBySigungu(@PathVariable int sigunguCode){
		List<SpotDto> spotList = spotService.selectBySigungu(sigunguCode);
		if(spotList==null || spotList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(spotList, HttpStatus.OK);
	}

	@GetMapping("/contenttypes")
	public ResponseEntity<?> selectAllSpotTypes(){
		List<SpotTypeDto> spotTypeList = spotService.selectAllSpotTypes();
		if(spotTypeList==null || spotTypeList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(spotTypeList, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity<List<SpotDto>> searchSpots(@RequestBody SpotSearchRequestDto filterRequest){
		log.info("sidoCode : "+filterRequest.getAreaCode());
		log.info("sigunguCode : "+filterRequest.getSiGunGuCode());
		log.info("contentTypeId : "+filterRequest.getContentTypeId());
		log.info("keyword" + filterRequest.getKeyword());

		List<SpotDto> spotList = spotService.selectSpotBySidoAndSigunguAndContentTypeAndKeyword(filterRequest);
		if(spotList==null || spotList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(spotList, HttpStatus.OK);
	}

	@GetMapping("/currentLocation")
	public ResponseEntity<List<SpotDto>> nearbySearchSpot(
			@RequestParam double minLatitude,
			@RequestParam double maxLatitude,
			@RequestParam double minLongitude,
			@RequestParam double maxLongitude,
			@RequestParam(required = false) Integer contentTypeId,
			@RequestParam(required = false, defaultValue = "") String keyword)  {

			List<SpotDto> nearbySpots = spotService.selectSpotsInBounds(
					minLatitude,
					maxLatitude,
					minLongitude,
					maxLongitude,
					contentTypeId,
					keyword
			);

			if (nearbySpots.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(nearbySpots, HttpStatus.OK);
	}

	@GetMapping("/{spotId}")
	public ResponseEntity<?> selectSpotById(@PathVariable int spotId) {
		SpotDto spotDto = spotService.selectSpotById(spotId);
		if (spotDto == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(spotDto, HttpStatus.OK);
	}

	@GetMapping("/user-spots")
	public ResponseEntity<List<SpotDto>> getUserSpots(@RequestParam String userId) {
		List<SpotDto> spots = spotService.selectSpotsFromUserPlan(userId);
		if (spots.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(spots, HttpStatus.OK);
	}
}

