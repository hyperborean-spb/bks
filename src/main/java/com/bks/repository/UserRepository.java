package com.bks.repository;

import com.bks.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

/*вероятно, ignore case*/

	List<User> findByNameContainingIgnoreCaseOrderByName(String name);

	List<User> findByBirthDateAfterOrderByBirthDate(LocalDate birthData);



	/*List<User> findByState(State state);

	@Query("SELECT  d.capacity FROM Drone d WHERE d.serialNumber = :serial")
	Integer getCapacityForSerial(@Param("serial") String serial);

	@Modifying
	@Query("update Notification n set n.isProcessed = true, n.processedAt = current_timestamp, n.processedBy = :userId, n.result = :result where id = :notificationId")
	void confirmNotificationProcessed(@Param("notificationId") UUID notificationId, @Param("userId") UUID userId, @Param("result") String result);
	*/
}