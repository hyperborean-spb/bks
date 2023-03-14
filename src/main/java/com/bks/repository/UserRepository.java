package com.bks.repository;

import com.bks.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<Client, Long> {

/*вероятно, ignore case*/

	Page<Client> findByNameContainingIgnoreCaseOrderByName(Pageable pageable, String name);

	Page<Client> findByBirthDateAfterOrderByBirthDate(Pageable pageable, LocalDate birthdate);



	/*List<User> findByState(State state);

	@Query("SELECT  d.capacity FROM Drone d WHERE d.serialNumber = :serial")
	Integer getCapacityForSerial(@Param("serial") String serial);

	@Modifying
	@Query("update Notification n set n.isProcessed = true, n.processedAt = current_timestamp, n.processedBy = :userId, n.result = :result where id = :notificationId")
	void confirmNotificationProcessed(@Param("notificationId") UUID notificationId, @Param("userId") UUID userId, @Param("result") String result);
	*/
}