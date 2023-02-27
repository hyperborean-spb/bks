package com.musala.dronedispatchcontroller.domain;

import com.musala.dronedispatchcontroller.domain.enums.Model;
import com.musala.dronedispatchcontroller.domain.enums.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Drone {

	@Id
	@Size(min =  1, max =  100)
	private String serialNumber;

	@Column (nullable = false)
	@Min(0)
	@Max(500)
	private Integer weight;

	@Column (nullable = false)
	@Min(0)
	@Max(100)
	private Integer capacity;

	@Enumerated(EnumType.STRING)
	private State state;

	@Enumerated(EnumType.STRING)
	private Model model;
}