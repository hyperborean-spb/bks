package com.bks.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name  = "email_data")
@Setter
@Getter
@NoArgsConstructor
public class Mail {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne  (fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Client client;

	@Email
	private  String mail;
}