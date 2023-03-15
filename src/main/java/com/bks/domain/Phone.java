package com.bks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name  = "phone_data")
@Setter
@Getter
@NoArgsConstructor
public class Phone {

	@Id
	private Long id;

	@ManyToOne  (fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;

	@Pattern(regexp = "7\\d{10}",
	message = "eleven digits only starting with seven")
	private  String phone;
}