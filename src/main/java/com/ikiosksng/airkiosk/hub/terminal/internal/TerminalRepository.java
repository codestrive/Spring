package com.ikiosksng.airkiosk.hub.terminal.internal;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.ikiosksng.airkiosk.hub.terminal.Terminal;

public interface TerminalRepository extends JpaRepository<Terminal,Long> {

	Terminal findByDeviceId(String deviceId);

	@Query(value="select t from Terminal t where t.location.address=:address")
	Page<Terminal> findByLocationAddress(@Param("address")String address , Pageable pageable );
	
	@Query(value="select t from Terminal t where t.location.city=:city")
	Page<Terminal> findByLocationCity(@Param("city")String city , Pageable pageable );

	@Query(value="select t from Terminal t where t.location.state=:state")
	Page<Terminal> findByLocationState(@Param("state")String state , Pageable pageable );
	
	@Query(value="select t from Terminal t where t.location.country=:country")
	Page<Terminal> findByLocationCountry(@Param("country")String country , Pageable pageable );
}
