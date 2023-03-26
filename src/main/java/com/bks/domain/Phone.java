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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne  (fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client client;

	@Pattern(regexp = "7\\d{10}",
	message = "номер телефона не соответствует ожидаемому формату")
	private  String phone;
}